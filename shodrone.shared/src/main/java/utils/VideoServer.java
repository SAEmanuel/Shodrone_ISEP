package utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVIOContext;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avformat;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacpp.PointerPointer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class VideoServer {
    // Enable FFmpeg debug logging
    static {
        avutil.av_log_set_level(avutil.AV_LOG_QUIET);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting VideoServer...");
        int port = 8000;

        File currentDir = new File(System.getProperty("user.dir"));
        System.out.println("Current working directory: " + currentDir.getAbsolutePath());

        File videosDir = new File(currentDir, "videos");
        if (!videosDir.exists() || !videosDir.isDirectory()) {
            System.out.println("videos directory not found in current directory, checking parent...");
            File parent = currentDir.getParentFile();
            if (parent != null) {
                videosDir = new File(parent, "videos");
            }
        }

        if (!videosDir.exists() || !videosDir.isDirectory()) {
            System.err.println("ERROR: Videos directory not found at: " + videosDir.getAbsolutePath());
            System.err.println("Please create a 'videos' directory and place your video files there.");
            System.exit(1);
        }

        System.out.println("Using videos directory: " + videosDir.getAbsolutePath());
        System.out.println("Contents of videos directory:");
        for (File f : videosDir.listFiles()) {
            System.out.println(" - " + f.getName());
        }

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/videos", new VideoHandler(videosDir));
            server.createContext("/www", new HtmlPageHandler(videosDir));
            server.setExecutor(null);
            server.start();

            System.out.println("Server started on port " + port);
            System.out.println("Test URLs:");
            System.out.println(" - http://localhost:" + port + "/www/player.html?video=drone_script_1_drones.mov");
            System.out.println(" - http://localhost:" + port + "/videos/drone_script_1_drones.mov (direct video access)");
        } catch (IOException e) {
            System.err.println("Failed to start server:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void convertMovToMp4(File inputFile, File outputFile) throws IOException {
        // System.out.println("Starting conversion: " + inputFile.getName() + " -> " + outputFile.getName());

        AVFormatContext inputFormatContext = new AVFormatContext(null);
        int ret = avformat.avformat_open_input(inputFormatContext, inputFile.getAbsolutePath(), null, null);
        if (ret < 0) {
            throw new IOException("Unable to open input file. Error code: " + ret);
        }

        ret = avformat.avformat_find_stream_info(inputFormatContext, (PointerPointer<?>) null);
        if (ret < 0) {
            throw new IOException("Unable to find stream info. Error code: " + ret);
        }

        AVFormatContext outputFormatContext = new AVFormatContext(null);
        ret = avformat.avformat_alloc_output_context2(outputFormatContext, null, null, outputFile.getAbsolutePath());
        if (outputFormatContext == null || ret < 0) {
            throw new IOException("Unable to create output context. Error code: " + ret);
        }

        int streamCount = inputFormatContext.nb_streams();
        for (int i = 0; i < streamCount; i++) {
            AVStream inStream = inputFormatContext.streams(i);
            AVCodecParameters inCodecPar = inStream.codecpar();

            AVStream outStream = avformat.avformat_new_stream(outputFormatContext, null);
            if (outStream == null) {
                throw new IOException("Unable to create output stream.");
            }

            avcodec.avcodec_parameters_copy(outStream.codecpar(), inCodecPar);
            outStream.codecpar().codec_tag(0);
        }

        if ((outputFormatContext.oformat().flags() & avformat.AVFMT_NOFILE) == 0) {
            AVIOContext pb = new AVIOContext(null);
            ret = avformat.avio_open(pb, outputFile.getAbsolutePath(), avformat.AVIO_FLAG_WRITE);
            if (ret < 0) {
                throw new IOException("Unable to open output file. Error code: " + ret);
            }
            outputFormatContext.pb(pb);
        }

        ret = avformat.avformat_write_header(outputFormatContext, (PointerPointer<?>) null);
        if (ret < 0) {
            throw new IOException("Error writing header. Error code: " + ret);
        }

        AVPacket pkt = new AVPacket();
        while (avformat.av_read_frame(inputFormatContext, pkt) >= 0) {
            AVStream inStream = inputFormatContext.streams(pkt.stream_index());
            AVStream outStream = outputFormatContext.streams(pkt.stream_index());

            pkt.pts(avutil.av_rescale_q_rnd(pkt.pts(), inStream.time_base(), outStream.time_base(),
                    avutil.AV_ROUND_NEAR_INF | avutil.AV_ROUND_PASS_MINMAX));
            pkt.dts(avutil.av_rescale_q_rnd(pkt.dts(), inStream.time_base(), outStream.time_base(),
                    avutil.AV_ROUND_NEAR_INF | avutil.AV_ROUND_PASS_MINMAX));
            pkt.duration(avutil.av_rescale_q(pkt.duration(), inStream.time_base(), outStream.time_base()));
            pkt.pos(-1);

            if (avformat.av_interleaved_write_frame(outputFormatContext, pkt) < 0) {
                // System.err.println("Warning: Error writing frame");
                break;
            }

            avcodec.av_packet_unref(pkt);
        }

        avformat.av_write_trailer(outputFormatContext);
        avformat.avformat_close_input(inputFormatContext);
        if ((outputFormatContext.oformat().flags() & avformat.AVFMT_NOFILE) == 0) {
            avformat.avio_closep(outputFormatContext.pb());
        }
        avformat.avformat_free_context(outputFormatContext);

        // System.out.println("Conversion completed successfully");
    }

    public static class VideoHandler implements HttpHandler {
        private final File baseDir;

        public VideoHandler(File directory) {
            this.baseDir = directory;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // System.out.println("\nVideoHandler request: " + exchange.getRequestURI());

            try {
                String uriPath = exchange.getRequestURI().getPath();
                String videoFileName = uriPath.replaceFirst("/videos/", "");
                // System.out.println("Requested video file: " + videoFileName);

                File file = new File(baseDir, videoFileName);
                // System.out.println("Looking for file at: " + file.getAbsolutePath());

                if (!file.exists()) {
                    // System.err.println("File not found: " + file.getAbsolutePath());
                    exchange.sendResponseHeaders(404, -1);
                    return;
                }

                if (!file.isFile()) {
                    // System.err.println("Path is not a file: " + file.getAbsolutePath());
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }

                String contentType = Files.probeContentType(file.toPath());
                if (contentType == null || contentType.equals("application/octet-stream")) {
                    if (file.getName().toLowerCase().endsWith(".mp4")) {
                        contentType = "video/mp4";
                    } else if (file.getName().toLowerCase().endsWith(".mov")) {
                        contentType = "video/quicktime";
                    } else {
                        contentType = "application/octet-stream";
                    }
                }

                // System.out.println("Serving file with content type: " + contentType);
                exchange.getResponseHeaders().add("Content-Type", contentType);
                exchange.sendResponseHeaders(200, file.length());

                try (OutputStream os = exchange.getResponseBody();
                     InputStream is = new FileInputStream(file)) {
                    // System.out.println("Starting file transfer...");
                    is.transferTo(os);
                    // System.out.println("File transfer completed");
                }
            } catch (Exception e) {
                System.err.println("Error in VideoHandler:");
                e.printStackTrace();
                String response = "Server Error: " + e.getMessage();
                exchange.sendResponseHeaders(500, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }

    public static class HtmlPageHandler implements HttpHandler {
        private final File videoDir;

        public HtmlPageHandler(File videoDirectory) {
            this.videoDir = videoDirectory;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // System.out.println("\nHtmlPageHandler request: " + exchange.getRequestURI());

            try {
                URI requestUri = exchange.getRequestURI();
                String path = requestUri.getPath();

                if (!path.endsWith("player.html")) {
                    // System.err.println("Invalid path requested: " + path);
                    exchange.sendResponseHeaders(404, -1);
                    return;
                }

                String query = requestUri.getRawQuery();
                // System.out.println("Query string: " + query);

                if (query == null || query.isEmpty()) {
                    // System.err.println("No query parameters provided");
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }

                Map<String, String> queryParams = queryToMap(query);
                String videoFileName = queryParams.get("video");

                if (videoFileName == null || videoFileName.isEmpty()) {
                    // System.err.println("No video parameter in query");
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }

                // System.out.println("Requested video: " + videoFileName);

                if (videoFileName.contains("..") || videoFileName.contains("/") || videoFileName.contains("\\")) {
                    // System.err.println("Invalid video filename (path traversal attempt)");
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }

                File requestedMov = new File(videoDir, videoFileName);
                // System.out.println("Looking for video at: " + requestedMov.getAbsolutePath());

                if (!requestedMov.exists() || !requestedMov.isFile()) {
                    // System.err.println("Video file not found");
                    exchange.sendResponseHeaders(404, -1);
                    return;
                }

                String baseName = videoFileName;
                if (baseName.toLowerCase().endsWith(".mov")) {
                    baseName = baseName.substring(0, baseName.length() - 4);
                }
                File mp4File = new File(videoDir, baseName + ".mp4");
                // System.out.println("MP4 output path: " + mp4File.getAbsolutePath());


                try {
                    if (mp4File.exists()) {
                        boolean deleted = mp4File.delete();
                        if (!deleted) {
                            throw new IOException("Failed to delete existing MP4 file: " + mp4File.getName());
                        }
                    }
                    convertMovToMp4(requestedMov, mp4File);
                    // System.out.println("Conversion successful");
                } catch (Exception e) {
                    System.err.println("Conversion failed:");
                    e.printStackTrace();
                    String response = "Conversion Error: " + e.getMessage();
                    exchange.sendResponseHeaders(500, response.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                    return;
                }

                String videoUrl = "/videos/" + mp4File.getName();
                String contentType = "video/mp4";

                // System.out.println("Generating HTML player page for: " + videoUrl);

                String html = "<!DOCTYPE html>"
                        + "<html lang=\"en\">"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Playing: " + mp4File.getName() + "</title>"
                        + "<style>"
                        + "body {"
                        + "  margin: 0;"
                        + "  height: 100vh;"
                        + "  display: flex;"
                        + "  justify-content: center;"
                        + "  align-items: center;"
                        + "  background-color: #000;"
                        + "}"
                        + "</style>"
                        + "</head>"
                        + "<body>"
                        + "<video width=\"960\" controls autoplay>"
                        + "<source src=\"" + videoUrl + "\" type=\"" + contentType + "\">"
                        + "Your browser does not support the video tag."
                        + "</video>"
                        + "<div style=\"position: absolute; top: 20px; left: 20px; color: white;\">"
                        + "Playing: " + mp4File.getName() + ""
                        + "</div>"
                        + "</body>"
                        + "</html>";

                byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
                // System.out.println("HTML response sent successfully");
            } catch (Exception e) {
                System.err.println("Error in HtmlPageHandler:");
                e.printStackTrace();
                String response = "Server Error: " + e.getMessage();
                exchange.sendResponseHeaders(500, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }

        private Map<String, String> queryToMap(String query) {
            Map<String, String> result = new HashMap<>();
            if (query == null) return result;

            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 0) {
                    String key = URLDecoder.decode(entry[0], StandardCharsets.UTF_8);
                    String value = entry.length > 1 ? URLDecoder.decode(entry[1], StandardCharsets.UTF_8) : "";
                    result.put(key, value);
                }
            }
            return result;
        }
    }
}
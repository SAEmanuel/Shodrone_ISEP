//import com.sun.net.httpserver.HttpServer;
//import com.sun.net.httpserver.HttpHandler;
//import com.sun.net.httpserver.HttpExchange;
//
//import java.io.*;
//import java.net.InetSocketAddress;
//import java.nio.file.Files;
//
//
//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.ffmpeg.global.avformat;
//import org.bytedeco.ffmpeg.global.avutil;
//import org.bytedeco.ffmpeg.avcodec.*;
//import org.bytedeco.ffmpeg.avformat.*;
//import org.bytedeco.javacpp.PointerPointer;
//import utils.StartupMessageServer;
//
//public class VideoServer {
//
//    public static void main(String[] args) throws IOException {
//        int port = 8000;
//
//        File currentDir = new File(System.getProperty("user.dir"));
//        File videosDir = new File(currentDir, "videos");
//        if (!videosDir.exists() || !videosDir.isDirectory()) {
//            File parent = currentDir.getParentFile();
//            if (parent != null) {
//                videosDir = new File(parent, "videos");
//            }
//        }
//        System.out.println("Usando pasta de vídeos: " + videosDir.getAbsolutePath());
//
//        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
//        server.createContext("/videos", new VideoHandler(videosDir));
//        server.createContext("/www", new HtmlPageHandler(videosDir));
//        server.setExecutor(null);
//
//        StartupMessageServer.displayStartupMessage(port);
//        server.start();
//
//        System.out.println("Server iniciado na porta " + port);
//        System.out.println("Abra: http://localhost:" + port + "/www/drone_script_1_drones.html");
//    }
//
//    // Conversão direta .mov → .mp4 usando Bytedeco (sem ProcessBuilder)
//    private static void convertMovToMp4(File inputFile, File outputFile) throws IOException {
//        System.out.println("Convertendo via Bytedeco: " + inputFile.getName());
//
//        AVFormatContext inputFormatContext = new AVFormatContext(null);
//        if (avformat.avformat_open_input(inputFormatContext, inputFile.getAbsolutePath(), null, null) < 0) {
//            throw new IOException("Não foi possível abrir arquivo de entrada.");
//        }
//
//        if (avformat.avformat_find_stream_info(inputFormatContext, (PointerPointer<?>) null) < 0) {
//            throw new IOException("Não foi possível encontrar informações do stream.");
//        }
//
//        AVFormatContext outputFormatContext = new AVFormatContext(null);
//        avformat.avformat_alloc_output_context2(outputFormatContext, null, null, outputFile.getAbsolutePath());
//        if (outputFormatContext == null) {
//            throw new IOException("Não foi possível criar contexto de saída.");
//        }
//
//        int streamCount = inputFormatContext.nb_streams();
//        for (int i = 0; i < streamCount; i++) {
//            AVStream inStream = inputFormatContext.streams(i);
//            AVCodecParameters inCodecPar = inStream.codecpar();
//
//            AVStream outStream = avformat.avformat_new_stream(outputFormatContext, null);
//            if (outStream == null) {
//                throw new IOException("Não foi possível criar stream de saída.");
//            }
//
//            avcodec.avcodec_parameters_copy(outStream.codecpar(), inCodecPar);
//            outStream.codecpar().codec_tag(0);
//        }
//
//        if ((outputFormatContext.oformat().flags() & avformat.AVFMT_NOFILE) == 0) {
//            AVIOContext pb = new AVIOContext(null);
//            if (avformat.avio_open(pb, outputFile.getAbsolutePath(), avformat.AVIO_FLAG_WRITE) < 0) {
//                throw new IOException("Não foi possível abrir arquivo de saída.");
//            }
//            outputFormatContext.pb(pb);
//        }
//
//        if (avformat.avformat_write_header(outputFormatContext, (PointerPointer<?>) null) < 0) {
//            throw new IOException("Erro ao escrever cabeçalho.");
//        }
//
//        AVPacket pkt = new AVPacket();
//        while (avformat.av_read_frame(inputFormatContext, pkt) >= 0) {
//            AVStream inStream = inputFormatContext.streams(pkt.stream_index());
//            AVStream outStream = outputFormatContext.streams(pkt.stream_index());
//
//            pkt.pts(avutil.av_rescale_q_rnd(pkt.pts(), inStream.time_base(), outStream.time_base(), avutil.AV_ROUND_NEAR_INF | avutil.AV_ROUND_PASS_MINMAX));
//            pkt.dts(avutil.av_rescale_q_rnd(pkt.dts(), inStream.time_base(), outStream.time_base(), avutil.AV_ROUND_NEAR_INF | avutil.AV_ROUND_PASS_MINMAX));
//            pkt.duration(avutil.av_rescale_q(pkt.duration(), inStream.time_base(), outStream.time_base()));
//            pkt.pos(-1);
//
//            if (avformat.av_interleaved_write_frame(outputFormatContext, pkt) < 0) {
//                System.err.println("Erro ao gravar pacote.");
//                break;
//            }
//
//            avcodec.av_packet_unref(pkt);
//        }
//
//        avformat.av_write_trailer(outputFormatContext);
//        avformat.avformat_close_input(inputFormatContext);
//        if ((outputFormatContext.oformat().flags() & avformat.AVFMT_NOFILE) == 0) {
//            avformat.avio_closep(outputFormatContext.pb());
//        }
//        avformat.avformat_free_context(outputFormatContext);
//
//        System.out.println("Conversão concluída com sucesso para: " + outputFile.getName());
//    }
//
//    static class VideoHandler implements HttpHandler {
//        private final File baseDir;
//
//        public VideoHandler(File directory) {
//            this.baseDir = directory;
//            System.out.println("VideoHandler criado com base em: " + directory.getAbsolutePath());
//        }
//
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            System.out.println("VideoHandler: nova requisição em " + exchange.getRequestURI());
//            String uriPath = exchange.getRequestURI().getPath();
//            String videoFileName = uriPath.replaceFirst("/videos/", "");
//            System.out.println("-> Arquivo solicitado: " + videoFileName);
//
//            File file = new File(baseDir, videoFileName);
//            if (!file.exists() || !file.isFile()) {
//                System.out.println("-> Arquivo NÃO encontrado: " + file.getAbsolutePath());
//                exchange.sendResponseHeaders(404, -1);
//                return;
//            }
//
//            String contentType = Files.probeContentType(file.toPath());
//            if (contentType == null || contentType.equals("application/octet-stream")) {
//                if (file.getName().toLowerCase().endsWith(".mp4")) {
//                    contentType = "video/mp4";
//                } else if (file.getName().toLowerCase().endsWith(".mov")) {
//                    contentType = "video/quicktime";
//                } else {
//                    contentType = "application/octet-stream";
//                }
//            }
//
//            exchange.getResponseHeaders().add("Content-Type", contentType);
//            exchange.sendResponseHeaders(200, file.length());
//            System.out.println("-> Servindo " + videoFileName + " (" + contentType + ", " + file.length() + " bytes)");
//
//            try (OutputStream os = exchange.getResponseBody();
//                 InputStream is = new FileInputStream(file)) {
//                is.transferTo(os);
//            }
//        }
//    }
//
//    static class HtmlPageHandler implements HttpHandler {
//        private final File videoDir;
//
//        public HtmlPageHandler(File videoDirectory) {
//            this.videoDir = videoDirectory;
//            System.out.println("HtmlPageHandler criado com base em: " + videoDirectory.getAbsolutePath());
//        }
//
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            System.out.println("HtmlPageHandler: nova requisição em " + exchange.getRequestURI());
//            String path = exchange.getRequestURI().getPath();
//            if (!path.endsWith(".html")) {
//                System.out.println("-> Caminho inválido (não termina em .html)");
//                exchange.sendResponseHeaders(404, -1);
//                return;
//            }
//
//            String htmlFileName = path.substring(path.lastIndexOf('/') + 1);
//            String videoBaseName = htmlFileName.substring(0, htmlFileName.length() - 5);
//            System.out.println("-> Pedida página para vídeo: " + videoBaseName);
//
//            File videoFileMp4 = new File(videoDir, videoBaseName + ".mp4");
//            File videoFileMov = new File(videoDir, videoBaseName + ".mov");
//
//            String videoUrl;
//            String contentType;
//
//            if (videoFileMp4.exists() && videoFileMp4.isFile()) {
//                videoUrl = "/videos/" + videoFileMp4.getName();
//                contentType = "video/mp4";
//                System.out.println("-> Usando vídeo para a página: " + videoUrl + " com tipo: " + contentType);
//            } else if (videoFileMov.exists() && videoFileMov.isFile()) {
//                // Converte .mov para .mp4 se ainda não existir
//                try {
//                    convertMovToMp4(videoFileMov, videoFileMp4);
//                    videoUrl = "/videos/" + videoFileMp4.getName();
//                    contentType = "video/mp4";
//                    System.out.println("-> Conversão para .mp4 concluída com sucesso.");
//                } catch (Exception e) {
//                    System.out.println("-> Erro na conversão:");
//                    e.printStackTrace();
//                    exchange.sendResponseHeaders(500, -1);
//                    return;
//                }
//            } else {
//                System.out.println("-> Arquivo de vídeo não encontrado para base: " + videoBaseName);
//                exchange.sendResponseHeaders(404, -1);
//                return;
//            }
//
//            String html = "<!DOCTYPE html>"
//                    + "<html lang=\"en\">"
//                    + "<head>"
//                    + "<meta charset=\"UTF-8\">"
//                    + "<title>Playing: " + videoBaseName + "</title>"
//                    + "<style>"
//                    + "body {"
//                    + "  margin: 0;"
//                    + "  height: 100vh;"
//                    + "  display: flex;"
//                    + "  justify-content: center;"
//                    + "  align-items: center;"
//                    + "  background-color: #000;"
//                    + "}"
//                    + "</style>"
//                    + "</head>"
//                    + "<body>"
//                    + "<video width=\"960\" controls autoplay>"
//                    + "<source src=\"" + videoUrl + "\" type=\"" + contentType + "\">"
//                    + "Your browser does not support the video tag."
//                    + "</video>"
//                    + "</body>"
//                    + "</html>";
//
//
//            byte[] bytes = html.getBytes("UTF-8");
//            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
//            exchange.sendResponseHeaders(200, bytes.length);
//            System.out.println("-> Enviando página HTML para " + videoBaseName);
//
//            try (OutputStream os = exchange.getResponseBody()) {
//                os.write(bytes);
//            }
//        }
//    }
//}

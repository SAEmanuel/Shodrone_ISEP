package utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class VideoServer {

    public static void main(String[] args) throws IOException {
        System.out.println("Starting VideoServer...");
        int port = 8000;

        File currentDir = new File(System.getProperty("user.dir"));
        File videosDir = new File(currentDir, "videos");

        if (!videosDir.exists() || !videosDir.isDirectory()) {
            File parent = currentDir.getParentFile();
            if (parent != null) {
                videosDir = new File(parent, "videos");
            }
        }

        if (!videosDir.exists() || !videosDir.isDirectory()) {
            System.err.println("ERROR: Videos directory not found at: " + videosDir.getAbsolutePath());
            System.exit(1);
        }

        System.out.println("Using videos directory: " + videosDir.getAbsolutePath());

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/videos", new VideoHandler(videosDir));
            server.createContext("/www", new HtmlPageHandler(videosDir));
            server.setExecutor(null);
            server.start();

            String filename = "drone_script_1_drones";

            System.out.println("Server started on port " + port);
            System.out.println("Example URLs:");
            System.out.println(" - http://localhost:" + port + "/www/player.html?video=" + filename + ".mov");
            System.out.println(" - http://localhost:" + port + "/videos/" + filename + ".mov");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static class VideoHandler implements HttpHandler {
        private final File baseDir;

        public VideoHandler(File directory) {
            this.baseDir = directory;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                String uriPath = exchange.getRequestURI().getPath();
                String videoFileName = uriPath.replaceFirst("/videos/", "");

                File file = new File(baseDir, videoFileName);
                if (!file.exists() || !file.isFile()) {
                    exchange.sendResponseHeaders(404, -1);
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

                exchange.getResponseHeaders().add("Content-Type", contentType);
                exchange.sendResponseHeaders(200, file.length());

                try (OutputStream os = exchange.getResponseBody();
                     InputStream is = new FileInputStream(file)) {
                    is.transferTo(os);
                }
            } catch (Exception e) {
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
            try {
                URI requestUri = exchange.getRequestURI();
                String path = requestUri.getPath();

                if (!path.endsWith("player.html")) {
                    exchange.sendResponseHeaders(404, -1);
                    return;
                }

                String query = requestUri.getRawQuery();
                if (query == null || query.isEmpty()) {
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }

                Map<String, String> queryParams = queryToMap(query);
                String videoFileName = queryParams.get("video");

                if (videoFileName == null || videoFileName.isEmpty()
                        || videoFileName.contains("..") || videoFileName.contains("/") || videoFileName.contains("\\")) {
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }

                File requestedFile = new File(videoDir, videoFileName);
                if (!requestedFile.exists() || !requestedFile.isFile()) {
                    exchange.sendResponseHeaders(404, -1);
                    return;
                }

                String html = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">"
                        + "<title>Video Player</title><style>body{margin:0;height:100vh;"
                        + "display:flex;justify-content:center;align-items:center;background:#000;}"
                        + "</style></head><body>"
                        + "<video width=\"960\" controls autoplay>"
                        + "<source src=\"/videos/" + requestedFile.getName() + "\" type=\"video/mp4\">"
                        + "Your browser does not support the video tag."
                        + "</video></body></html>";

                byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } catch (Exception e) {
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

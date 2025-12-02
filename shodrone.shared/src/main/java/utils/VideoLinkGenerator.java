package utils;

public class VideoLinkGenerator {

    private final String baseUrl;

    public VideoLinkGenerator(String baseUrl) {
        if (!baseUrl.endsWith("/")) baseUrl += "/";
        this.baseUrl = baseUrl;
    }

    /**
     * Generate a link to the video player HTML page (e.g. www/sample.html).
     *
     * @param videoFileName the video file name (must end with .mov)
     * @return URL to the video player page for this video
     */
    public String generateLink(String videoFileName) {
        if (!videoFileName.toLowerCase().endsWith(".mov")) {
            throw new IllegalArgumentException("Only .mov files supported.");
        }
        String baseName = videoFileName.substring(0, videoFileName.length() - 4);
        return baseUrl + "www/" + baseName + ".html";
    }
}

package controller.showproposal;

public class VideoLinkGenerator {

    private final String videoServerBaseUrl;

    public VideoLinkGenerator(String videoServerBaseUrl) {
        this.videoServerBaseUrl = videoServerBaseUrl;
    }

    public String generateLink(long videoId) {
        return videoServerBaseUrl + "?id=" + videoId;
    }

    // Test example
    public static void main(String[] args) {
        VideoLinkGenerator generator = new VideoLinkGenerator("http://10.8.0.80:8080/video");
        long exampleVideoId = 52L;
        String link = generator.generateLink(exampleVideoId);
        System.out.println("Generated video link: " + link);
    }
}

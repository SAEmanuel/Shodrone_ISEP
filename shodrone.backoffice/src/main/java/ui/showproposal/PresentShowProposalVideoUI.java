package ui.showproposal;

import controller.showproposal.PresentShowProposalVideoController;
import domain.entity.ShowProposal;
import domain.valueObjects.Video;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import utils.Utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class PresentShowProposalVideoUI implements Runnable {

    private final PresentShowProposalVideoController controller;

    public PresentShowProposalVideoUI() {
        this.controller = new PresentShowProposalVideoController();
        // Initialize JavaFX toolkit once (needed for MediaPlayer even if no UI shown immediately)
        new JFXPanel();
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("VIDEO OF SHOW PROPOSAL");
        Utils.dropLines(3);

            List<ShowProposal> listOfProposals = controller.getAllProposals();

            if(listOfProposals==null || listOfProposals.isEmpty()){
                Utils.printFailMessage("❌ No Show Proposals available. Add them first and then try again!");
                Utils.waitForUser();
                return;
            }

            int selectedProposalIndex = Utils.showAndSelectIndexCustomOptions(listOfProposals, "Select one Show Proposal");
            ShowProposal selectedProposal = listOfProposals.get(selectedProposalIndex);
            Utils.printSuccessMessage("\nSelected Proposal with ID : " + selectedProposal.identity());

            Optional<Video> video = controller.getVideoOfShowProposal(selectedProposal);

            if (video.isPresent()) {
                try {
                    playVideo(video.get().getContent());
                } catch (IOException e) {
                    Utils.printFailMessage("\n❌ Something when wrong playing the video!");
                }
                Utils.printSuccessMessage("\n✔️ Video successfully presented!");
            } else {
                Utils.printFailMessage("\n❌ No video found for the selected proposal!");
            }
        Utils.waitForUser();
    }


    private void playVideo(byte[] videoBytes) throws IOException {
        if (videoBytes == null || videoBytes.length == 0) {
            throw new IOException("Video bytes are empty or null");
        }

        Path tempMp4File = Files.createTempFile("showproposal-video-", ".mp4");
        Files.write(tempMp4File, videoBytes);
        tempMp4File.toFile().deleteOnExit();

        // Try system default player first
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(tempMp4File.toFile());
                //System.out.println("Opened video with system player.");
                return; // Success, no need to launch JavaFX player
            } catch (Exception e) {
                System.err.println("❌ Failed to open video with system player: " + e.getMessage());
            }
        }

        // If system player fails, launch JavaFX video player as fallback
        System.out.println("⚠\uFE0F Launching JavaFX video player fallback...");
        VideoPlayerApp.videoFile = tempMp4File.toFile();
        Application.launch(VideoPlayerApp.class);
    }

    public static class VideoPlayerApp extends Application {
        public static File videoFile;

        @Override
        public void start(Stage stage) {
            if (videoFile == null || !videoFile.exists()) {
                Utils.printFailMessage("❌ Video file could not be found!");
                Platform.exit();
                return;
            }

            Media media = new Media(videoFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);

            StackPane root = new StackPane(mediaView);
            Scene scene = new Scene(root, 800, 600);

            stage.setTitle("Show Proposal Video Player");
            stage.setScene(scene);
            stage.show();

            mediaPlayer.play();

            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.dispose();
                stage.close();
                cleanup();
                Platform.exit();
            });

            stage.setOnCloseRequest(event -> {
                mediaPlayer.stop();
                mediaPlayer.dispose();
                cleanup();
                Platform.exit();
            });
        }

        private void cleanup() {
            if (videoFile.exists() && !videoFile.delete()) {
                Utils.printFailMessage("⚠\uFE0F Video file could not be deleted!");
            }
        }
    }
}

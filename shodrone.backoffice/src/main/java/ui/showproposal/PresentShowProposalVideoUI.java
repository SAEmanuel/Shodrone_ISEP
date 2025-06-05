package ui.showproposal;

import controller.showproposal.PresentShowProposalVideoController;
import domain.entity.ShowProposal;
import domain.valueObjects.Video;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import utils.NativeOutputSilencer;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class PresentShowProposalVideoUI implements Runnable {

    private final PresentShowProposalVideoController controller;

    public PresentShowProposalVideoUI() {
        controller = new PresentShowProposalVideoController();
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("VIDEO OF SHOW PROPOSAL");
        Utils.dropLines(3);

        List<ShowProposal> listOfProposals = controller.getAllProposals();
        int selectedProposalIndex = Utils.showAndSelectIndexCustomOptions(listOfProposals, "Select one Show Proposal");
        ShowProposal selectedProposal = listOfProposals.get(selectedProposalIndex);
        Utils.printSuccessMessage("\nSelected Proposal with ID : " + selectedProposal.identity());

        Optional<Video> video = controller.getVideoOfShowProposal(selectedProposal);

        if (video.isPresent()) {
            try {
                // Step 1: Write original MOV to temp file
                Path movTempFile = Files.createTempFile("video", ".mov");
                Files.write(movTempFile, video.get().getContent());

                // Step 2: Convert MOV -> MP4 with FFmpeg
                Path mp4TempFile = Files.createTempFile("video-converted", ".mp4");
                convertMovToMp4(movTempFile, mp4TempFile);

                // Step 3: Play the MP4 video on the Swing EDT
                SwingUtilities.invokeLater(() -> playVideo(mp4TempFile, movTempFile, mp4TempFile));
            } catch (IOException | InterruptedException e) {
                // silently ignore errors to suppress output
            }

            Utils.printSuccessMessage("\n✔️ Video successfully presented!");
        } else {
            Utils.printFailMessage("\n✖️ Something went wrong presenting the Show Proposal Video!");
        }
        Utils.waitForUser();
    }

    private void convertMovToMp4(Path movFile, Path mp4File) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg",
                "-y",
                "-i", movFile.toAbsolutePath().toString(),
                "-c:v", "libx264",
                "-profile:v", "high",
                "-pix_fmt", "yuv420p",
                "-c:a", "aac",
                "-preset", "fast",
                "-movflags", "+faststart",
                mp4File.toAbsolutePath().toString()
        );
        // Suppress ffmpeg output by redirecting both stdout and stderr to null
        pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
        pb.redirectError(ProcessBuilder.Redirect.DISCARD);

        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("FFmpeg conversion failed with exit code " + exitCode);
        }
    }

    private void playVideo(Path mp4File, Path movFileToDelete, Path mp4FileToDelete) {
        // 🔇 Suppress native stderr output temporarily
        NativeOutputSilencer.mute();

        EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

        JFrame frame = new JFrame("Video Player");
        frame.setLayout(new BorderLayout());
        frame.add(mediaPlayerComponent, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        mediaPlayerComponent.mediaPlayer().media().play(
                mp4File.toAbsolutePath().toString(),
                "--avcodec-hw=none",              // Disable hardware decoding
                "--vout=drawable",                // Use software rendering
                "--no-video-title-show",
                "--no-xlib",                      // Disable Xlib extensions
                "--file-caching=3000"
        );


        mediaPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                try {
                    Files.deleteIfExists(movFileToDelete);
                    Files.deleteIfExists(mp4FileToDelete);
                } catch (IOException ignored) {}
                NativeOutputSilencer.restore(); // ✅ restore stderr
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                NativeOutputSilencer.restore(); // ✅ restore even on error
            }
        });
    }


}

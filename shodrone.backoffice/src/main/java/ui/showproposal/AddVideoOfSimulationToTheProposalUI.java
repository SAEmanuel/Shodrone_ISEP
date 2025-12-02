package ui.showproposal;

import com.sun.net.httpserver.HttpServer;
import controller.showproposal.AddVideoOfSimulationToTheProposalController;
import domain.entity.ShowProposal;
import domain.valueObjects.*;
import javafx.application.Application;
import utils.Utils;
import utils.VideoServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public class AddVideoOfSimulationToTheProposalUI implements Runnable {

    private final AddVideoOfSimulationToTheProposalController controller;
    private static final int EXIT = -1;

    public AddVideoOfSimulationToTheProposalUI() {
        controller = new AddVideoOfSimulationToTheProposalController();
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("ADD VIDEO TO SHOW PROPOSAL");
        Utils.dropLines(3);

        List<ShowProposal> listOfProposals = controller.getAllProposals();

        if (listOfProposals != null && !listOfProposals.isEmpty()) {
            HttpServer server = null;
            try {
                Utils.printAlterMessage("Before continuing be sure you have the pretended file in the path:\n shodrone.shared/src/main/resources/domain/valueObjects\n");
                String videoFile = Utils.rePromptWhileInvalid("Enter the video file name: ", String::new);
                String javafxModules = "javafx.controls,javafx.fxml,javafx.swing";

                // Detect OS classifier for JavaFX jars (adjust if needed)
                String osClassifier = detectOSClassifier();

                // JavaFX version used in your Maven dependencies
                String javafxVersion = "20";

                // Build path to JavaFX jars in Maven local repo
                String userHome = System.getProperty("user.home");
                String javafxBasePath = userHome + "/.m2/repository/org/openjfx/";

                // Compose full module path by joining javafx modules jars
                StringBuilder modulePathBuilder = new StringBuilder();
                String[] modules = {"javafx-base", "javafx-controls", "javafx-graphics", "javafx-fxml", "javafx-swing"};
                for (String mod : modules) {
                    String jarPath = String.format("%s%s/%s/%s-%s-%s.jar",
                            javafxBasePath,
                            mod,
                            javafxVersion,
                            mod,
                            javafxVersion,
                            osClassifier);
                    File jarFile = new File(jarPath);
                    if (!jarFile.exists()) {
                        throw new RuntimeException("JavaFX jar not found: " + jarPath);
                    }
                    if (modulePathBuilder.length() > 0) {
                        modulePathBuilder.append(File.pathSeparator);
                    }
                    modulePathBuilder.append(jarFile.getAbsolutePath());
                }
                String modulePath = modulePathBuilder.toString();

                ProcessBuilder pb = new ProcessBuilder(
                        "java",
                        "--module-path", modulePath,
                        "--add-modules", javafxModules,
                        "-cp", System.getProperty("java.class.path"),
                        "utils.Drone3DSimulation",
                        videoFile
                );
                pb.inheritIO(); // Show simulation logs in console

                Process process = pb.start();
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    Utils.printFailMessage("❌ Video file could not be found!");
                    return;
                }

                String fileName;

                if (videoFile.toLowerCase().endsWith(".txt")) {
                    fileName = videoFile.substring(0, videoFile.length() - 4);
                } else {
                    fileName = videoFile;
                }

                File file = new File("videos", "drone_" + fileName + ".mov");
                if (!file.exists()) {
                    Utils.printFailMessage("❌ Video could not be found at: " + file.getAbsolutePath());
                    return;
                }

                byte[] content = Files.readAllBytes(file.toPath());
                Optional<Video> video = Optional.of(new Video(content));

                int port = 8000;

                File currentDir = new File(System.getProperty("user.dir"));
                File videosDir = new File(currentDir, "videos");
                if (!videosDir.exists() || !videosDir.isDirectory()) {
                    File parent = currentDir.getParentFile();
                    if (parent != null) {
                        videosDir = new File(parent, "videos");
                    }
                }

                server = HttpServer.create(new InetSocketAddress(port), 0);
                server.createContext("/videos", new VideoServer.VideoHandler(videosDir));
                server.createContext("/www", new VideoServer.HtmlPageHandler(videosDir));
                server.setExecutor(null);
                server.start();

                Utils.dropLines(2);
                System.out.println("Open: http://localhost:" + port + "/www/player.html?video=drone_" + fileName + ".mov");
                Utils.dropLines(5);

                Optional<ShowProposal> result = Optional.empty();

                int selectedProposalIndex = Utils.showAndSelectIndexCustomOptions(listOfProposals, "Select one Show Proposal");

                if (selectedProposalIndex == EXIT) {
                    Utils.dropLines(2);
                    Utils.printFailMessage("❌ No show proposal selected.");
                    return;
                }

                ShowProposal selectedProposal = listOfProposals.get(selectedProposalIndex);
                Utils.printSuccessMessage("\nSelected Proposal with ID : " + selectedProposal.identity());

                result = controller.addVideoToShowProposal(selectedProposal, video.get());
                if (result.isEmpty()) {
                    Utils.printFailMessage("\n❌ Something went wrong saving the Show Proposal!");
                } else {
                    Utils.printSuccessMessage("\n✔️ Video added successfully to proposal!");
                }
            }catch (IOException e) {
                System.err.println("❌ Error reading the video file!");
            } catch (Exception e) {
                System.err.println("❌ Unexpected error: " + e.getMessage());
            } finally {
                Utils.waitForUser();
                if (server != null) {
                    server.stop(0);
                    //System.out.println("Servidor HTTP encerrado.");
                }
            }
        } else{
            Utils.printFailMessage("\n❌ No Show Proposals available. Add them first and then try again!");
            Utils.waitForUser();
        }
    }

    private static String detectOSClassifier() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return "win";
        } else if (osName.contains("mac")) {
            return "mac";
        } else if (osName.contains("linux")) {
            return "linux";
        } else {
            throw new RuntimeException("❌ Unsupported OS for JavaFX: " + osName);
        }
    }
}

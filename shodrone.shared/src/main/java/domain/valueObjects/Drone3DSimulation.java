package domain.valueObjects;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.*;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Drone3DSimulation extends Application {

    private static double[][][] dronePositions;  // [frame][drone][x,y,z]
    private static int NUM_FRAMES;
    private static int NUM_DRONES;

    private int currentFrame = 0;
    private final List<Sphere> droneSpheres = new ArrayList<>();

    private static final int TOTAL_VIDEO_FRAMES = 200; // limit video length
    private int savedFrames = 0;

    private SubScene subScene;
    private AnimationTimer timer;

    private static String droneFileName;

    // Directory to save PNG frames
    private final File framesDir = new File("frames");

    public static void main(String[] args) {
        // Disable noisy JavaFX logging
        Logger logger = Logger.getLogger("com.sun.javafx.application.PlatformImpl");
        logger.setLevel(Level.OFF);

        // Load drone positions from resource file
        loadDroneDataFromCustomFormat("/domain/valueObjects/script_1_drones.txt");

        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Prepare frames directory
        if (!framesDir.exists()) {
            framesDir.mkdirs();
        }

        Group root = new Group();
        Group cameraPivot = new Group();

        // Setup camera with perspective projection
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-300);

        cameraPivot.getChildren().add(camera);
        root.getChildren().add(cameraPivot);

        // Camera rotation transforms
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        cameraPivot.getTransforms().addAll(rotateY, rotateX);

        final double[] mouseOldX = new double[1];
        final double[] mouseOldY = new double[1];

        // Lighting setup
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(0);
        light.setTranslateY(-100);
        light.setTranslateZ(-100);
        root.getChildren().add(light);

        AmbientLight ambient = new AmbientLight(Color.color(0.3, 0.3, 0.3));
        root.getChildren().add(ambient);

        // Add a red sphere at origin for reference
        Sphere originSphere = new Sphere(2);
        originSphere.setTranslateX(0);
        originSphere.setTranslateY(0);
        originSphere.setTranslateZ(0);
        originSphere.setMaterial(new PhongMaterial(Color.RED));
        root.getChildren().add(originSphere);

        // Create drone spheres with sky blue material
        for (int i = 0; i < NUM_DRONES; i++) {
            Sphere drone = new Sphere(6);
            drone.setMaterial(new PhongMaterial(Color.SKYBLUE));
            droneSpheres.add(drone);
            root.getChildren().add(drone);
        }

        // Create 3D SubScene
        subScene = new SubScene(root, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.BLACK);
        subScene.setCamera(camera);

        Group mainRoot = new Group(subScene);
        Scene scene = new Scene(mainRoot, 800, 600);
        stage.setTitle("3D Drone Simulation");
        stage.setScene(scene);
        stage.show();

        // Mouse controls for camera rotation on right button drag
        scene.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {
                mouseOldX[0] = event.getSceneX();
                mouseOldY[0] = event.getSceneY();
            }
        });

        scene.setOnMouseDragged(event -> {
            if (event.isSecondaryButtonDown()) {
                double deltaX = event.getSceneX() - mouseOldX[0];
                double deltaY = event.getSceneY() - mouseOldY[0];
                double sensitivity = 0.5;

                rotateY.setAngle(rotateY.getAngle() + deltaX * sensitivity);
                rotateX.setAngle(clamp(rotateX.getAngle() - deltaY * sensitivity, -90, 90));

                mouseOldX[0] = event.getSceneX();
                mouseOldY[0] = event.getSceneY();
            }
        });

        // Scroll wheel zoom
        scene.setOnScroll(event -> {
            double delta = event.getDeltaY();
            double zoomSpeed = 50.0;
            camera.setTranslateZ(camera.getTranslateZ() + delta * zoomSpeed * -0.01);
        });

        // Animation timer to update frames & save PNGs
        timer = new AnimationTimer() {
            private long lastUpdate = 0;
            private final long frameDurationNanos = 40_000_000; // ~25 FPS

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= frameDurationNanos) {
                    if (savedFrames < TOTAL_VIDEO_FRAMES) {
                        updateFrame();
                        captureFrameAndSavePNG();
                        lastUpdate = now;
                    } else {
                        stop();
                        finalizeAndCreateVideo();
                    }
                }
            }
        };

        timer.start();

        stage.setOnCloseRequest(event -> {
            //System.out.println("Window close requested, stopping timer and finalizing video...");
            if (timer != null) timer.stop();
            finalizeAndCreateVideo();
        });
    }

    private void finalizeAndCreateVideo() {
        //System.out.println("All frames saved: " + savedFrames);
        //System.out.println("Starting video encoding with FFmpeg...");

        try {
            runFFmpegEncoding();
            //System.out.println("Video encoding finished successfully.");
        } catch (Exception e) {
            //System.err.println("Failed to run FFmpeg: " + e.getMessage());
            e.printStackTrace();
        }

        Platform.exit();
        //System.exit(0);
    }

    private void updateFrame() {
        if (NUM_FRAMES == 0 || NUM_DRONES == 0) return;

        if (currentFrame >= NUM_FRAMES) {
            currentFrame = 0;
        }

        double scale = 10;

        for (int i = 0; i < NUM_DRONES; i++) {
            double[] pos = dronePositions[currentFrame][i];
            Sphere drone = droneSpheres.get(i);
            drone.setTranslateX(pos[0] * scale);
            drone.setTranslateY(-pos[1] * scale); // invert Y axis
            drone.setTranslateZ(pos[2] * scale);
            drone.setVisible(true);
        }

        currentFrame++;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private void captureFrameAndSavePNG() {
        WritableImage snapshot = new WritableImage((int) subScene.getWidth(), (int) subScene.getHeight());
        subScene.snapshot(null, snapshot);

        File frameFile = new File(framesDir, String.format("frame_%04d.png", savedFrames));

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", frameFile);
            savedFrames++;
            //System.out.println("Saved PNG frame #" + savedFrames);
        } catch (IOException e) {
            //System.err.println("Failed to save PNG frame #" + savedFrames + ": " + e.getMessage());
        }
    }

    private void runFFmpegEncoding() throws IOException, InterruptedException {
        // Requires ffmpeg installed and in PATH
        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg",
                "-y", // overwrite output file if exists
                "-r", "25", // input frame rate
                "-i", "frames/frame_%04d.png", // input file pattern
                "-c:v", "libx264",
                "-pix_fmt", "yuv420p",  // <-- changed here to preserve color fidelity
                "drone_" + droneFileName + ".mov"
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println("[FFmpeg] " + line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("FFmpeg exited with error code: " + exitCode);
        }
    }

    /**
     * Loads drone positions from a custom formatted text file resource.
     * Format example:
     * 0 - 10x10x10
     * 0.0 1.0 2.0
     * ...
     *
     * @param resourcePath path within the resources folder
     */
    public static void loadDroneDataFromCustomFormat(String resourcePath) {
        droneFileName = resourcePath;

        resourcePath = "/domain/valueObjects/" + resourcePath + ".txt";
        Map<Integer, List<double[]>> droneFramesMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Drone3DSimulation.class.getResourceAsStream(resourcePath)))) {

            if (br == null) {
                System.err.println("Resource not found: " + resourcePath);
                NUM_FRAMES = 0;
                NUM_DRONES = 0;
                return;
            }

            String line;
            Integer currentDrone = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.matches("\\d+\\s*-\\s*\\d+x\\d+x\\d+")) {
                    currentDrone = Integer.parseInt(line.split("\\s*-\\s*")[0]);
                    droneFramesMap.putIfAbsent(currentDrone, new ArrayList<>());
                } else {
                    if (currentDrone == null) {
                        continue;
                    }
                    String[] parts = line.split("\\s+");
                    if (parts.length == 3) {
                        try {
                            double x = Double.parseDouble(parts[0]);
                            double y = Double.parseDouble(parts[1]);
                            double z = Double.parseDouble(parts[2]);
                            droneFramesMap.get(currentDrone).add(new double[]{x, y, z});
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (droneFramesMap.isEmpty()) {
            NUM_FRAMES = 0;
            NUM_DRONES = 0;
            return;
        }

        NUM_DRONES = droneFramesMap.size();
        int maxFrames = droneFramesMap.values().stream().mapToInt(List::size).max().orElse(0);
        NUM_FRAMES = maxFrames;
        NUM_FRAMES = maxFrames;

        dronePositions = new double[NUM_FRAMES][NUM_DRONES][3];

        for (int d = 0; d < NUM_DRONES; d++) {
            List<double[]> frames = droneFramesMap.get(d);
            for (int f = 0; f < NUM_FRAMES; f++) {
                if (f < frames.size()) {
                    dronePositions[f][d] = frames.get(f);
                } else {
                    dronePositions[f][d] = new double[]{0, 0, 0};
                }
            }
        }
    }
}

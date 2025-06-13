package utils;

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
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jcodec.api.SequenceEncoder;
import org.jcodec.common.Format;
import org.jcodec.common.Codec;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Picture;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Rational;

import static more.ColorfulOutput.ANSI_RESET;

public class Drone3DSimulation extends Application {
    private static double[][][] dronePositions;
    private static int NUM_FRAMES, NUM_DRONES;
    private int currentFrame = 0;
    private final List<Sphere> droneSpheres = new ArrayList<>();
    private static final int TOTAL_VIDEO_FRAMES = 200;
    private int savedFrames = 0;
    private SubScene subScene;
    private AnimationTimer timer;
    private static String droneFileName;
    private final File framesDir = new File("frames");

    public static void main(String[] args) {
        final String ANSI_BOLD = "\033[1m";
        final String ANSI_RED = "\033[31m";
        final String ANSI_RESET = "\033[0m";

        try {
            loadDroneDataFromCustomFormat(args[0]);
            // Check if data loaded properly, e.g. NUM_DRONES > 0
            if (NUM_DRONES == 0 || NUM_FRAMES == 0) {
                Utils.printFailMessage(ANSI_BOLD + "X" + ANSI_RESET + ANSI_RED + " No drone data loaded or file not found." + ANSI_RESET);
                Platform.exit();
            }
            launch(args);
        } catch (Exception e) {
            Utils.printFailMessage(ANSI_BOLD + "X" + ANSI_RESET + ANSI_RED + " Failed to load drone data!" + ANSI_RESET);
            Platform.exit();
        }
    }


    @Override
    public void start(Stage stage) {
        try{
            if (!framesDir.exists()) framesDir.mkdirs();

            Group root = new Group(), cameraPivot = new Group();
            PerspectiveCamera camera = new PerspectiveCamera(true);
            camera.setNearClip(0.1); camera.setFarClip(10000); camera.setTranslateZ(-300);
            cameraPivot.getChildren().add(camera); root.getChildren().add(cameraPivot);

            Rotate rotateX = new Rotate(0, Rotate.X_AXIS), rotateY = new Rotate(0, Rotate.Y_AXIS);
            cameraPivot.getTransforms().addAll(rotateY, rotateX);
            final double[] mx = new double[1], my = new double[1];

            PointLight light = new PointLight(Color.WHITE);
            light.setTranslateY(-100); light.setTranslateZ(-100);
            root.getChildren().addAll(light, new AmbientLight(Color.gray(0.3)));

            Sphere origin = new Sphere(2);
            origin.setMaterial(new PhongMaterial(Color.RED));
            root.getChildren().add(origin);

            for (int i = 0; i < NUM_DRONES; i++) {
                Sphere drone = new Sphere(6);
                drone.setMaterial(new PhongMaterial(Color.SKYBLUE));
                droneSpheres.add(drone);
                root.getChildren().add(drone);
            }

            subScene = new SubScene(root, 800, 600, true, SceneAntialiasing.BALANCED);
            subScene.setFill(Color.BLACK);
            subScene.setCamera(camera);

            Scene scene = new Scene(new Group(subScene), 800, 600);
            stage.setTitle("3D Drone Simulation");
            stage.setScene(scene);
            stage.show();

            scene.setOnMousePressed(e -> {
                if (e.isSecondaryButtonDown()) {
                    mx[0] = e.getSceneX();
                    my[0] = e.getSceneY();
                }
            });
            scene.setOnMouseDragged(e -> {
                if (e.isSecondaryButtonDown()) {
                    double dx = e.getSceneX() - mx[0];
                    double dy = e.getSceneY() - my[0];
                    rotateY.setAngle(rotateY.getAngle() + dx * 0.5);
                    rotateX.setAngle(Math.max(-90, Math.min(90, rotateX.getAngle() - dy * 0.5)));
                    mx[0] = e.getSceneX();
                    my[0] = e.getSceneY();
                }
            });
            scene.setOnScroll(e -> camera.setTranslateZ(camera.getTranslateZ() - e.getDeltaY() * 0.5));

            timer = new AnimationTimer() {
                private long last = 0;
                private final long frameNanos = 40_000_000; // ~25fps

                @Override
                public void handle(long now) {
                    if (now - last >= frameNanos) {
                        if (savedFrames < TOTAL_VIDEO_FRAMES) {
                            updateFrame();
                            captureFrame();
                            last = now;
                        } else {
                            stop();
                            finalizeAndCreateVideo();
                        }
                    }
                }
            };
            timer.start();

            stage.setOnCloseRequest(e -> {
                if (timer != null) timer.stop();
                finalizeAndCreateVideo();
            });
        } catch (Exception e) {
            Utils.printFailMessage("❌ An error occurred in JavaFX application.");
            Platform.exit();
        }

    }

    private void updateFrame() {
        if (NUM_FRAMES == 0 || NUM_DRONES == 0) return;
        if (currentFrame >= NUM_FRAMES) currentFrame = 0;

        double scale = 10;
        for (int i = 0; i < NUM_DRONES; i++) {
            double[] pos = dronePositions[currentFrame][i];
            Sphere s = droneSpheres.get(i);
            s.setTranslateX(pos[0] * scale);
            s.setTranslateY(-pos[1] * scale);
            s.setTranslateZ(pos[2] * scale);
            s.setVisible(true);
        }
        currentFrame++;
    }

    private void captureFrame() {
        WritableImage wi = new WritableImage((int) subScene.getWidth(), (int) subScene.getHeight());
        subScene.snapshot(null, wi);
        File out = new File(framesDir, String.format("frame_%04d.png", savedFrames));
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", out);
            savedFrames++;
        } catch (IOException e) {
            Utils.printFailMessage("❌ Failed to save frame #" + savedFrames + ": " + e);
            Platform.exit();
        }
    }

    private void finalizeAndCreateVideo() {
        Utils.printAlterMessage("Saved " + savedFrames + " frames. Encoding video...");
        // Run encoding in a separate thread to avoid blocking JavaFX UI thread
        new Thread(() -> {
            try {
                encodeVideo();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Platform.runLater(() -> {
                //Utils.printAlterMessage("Exiting application...");
                Platform.exit();
            });
        }).start();
    }


    private void encodeVideo() throws IOException {
        String videosPath = new File(System.getProperty("user.dir"), "videos").getAbsolutePath();
        new File(videosPath).mkdirs();  // Make sure the folder exists
        File outFile = new File(videosPath, "drone_" + droneFileName + ".mov");

        try (SeekableByteChannel ch = NIOUtils.writableFileChannel(outFile.getAbsolutePath())) {
            SequenceEncoder enc = new SequenceEncoder(
                    ch,
                    new Rational(25, 1),
                    Format.MOV,
                    Codec.H264,
                    Codec.AAC
            ); // 5‑arg constructor required :contentReference[oaicite:1]{index=1}

            for (int i = 0; i < savedFrames; i++) {
                File f = new File(framesDir, String.format("frame_%04d.png", i));
                BufferedImage img = ImageIO.read(f);
                Picture pic = bufferedImageToPicture(img);
                enc.encodeNativeFrame(pic);
            }
            enc.finish();
        }
        Utils.printAlterMessage("Video created at: " + outFile.getAbsolutePath());
    }

    private static Picture bufferedImageToPicture(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        // Convert source image to TYPE_3BYTE_BGR to ensure consistent byte order
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.getGraphics().drawImage(src, 0, 0, null);

        Picture picture = Picture.create(width, height, ColorSpace.RGB);
        byte[] dstData = picture.getPlaneData(0);

        // Get raw BGR bytes from the BufferedImage
        byte[] srcData = ((java.awt.image.DataBufferByte) img.getRaster().getDataBuffer()).getData();

        // Convert BGR bytes to RGB for JCodec
        for (int i = 0, j = 0; i < srcData.length; i += 3, j += 3) {
            // BGR order in srcData -> RGB order in dstData
            dstData[j] = srcData[i + 2];       // R
            dstData[j + 1] = srcData[i + 1];   // G
            dstData[j + 2] = srcData[i];       // B
        }

        return picture;
    }

    public static void loadDroneDataFromCustomFormat(String baseName) {
        String resourcePath;

        if (baseName.toLowerCase().endsWith(".txt")) {
            droneFileName = baseName.substring(0, baseName.length() - 4);
            resourcePath = "/domain/valueObjects/" + baseName;
        } else {
            droneFileName = baseName;
            resourcePath = "/domain/valueObjects/" + droneFileName + ".txt";
        }

        Map<Integer, List<double[]>> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Drone3DSimulation.class.getResourceAsStream(resourcePath)))) {
            if (br == null) {
                System.err.println("Resource not found: " + resourcePath);
                Platform.exit();
                NUM_FRAMES = NUM_DRONES = 0;
                return;
            }
            String line;
            Integer curr = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.matches("\\d+\\s*-\\s*\\d+x\\d+x\\d+")) {
                    curr = Integer.valueOf(line.split("\\s*-\\s*")[0]);
                    map.putIfAbsent(curr, new ArrayList<>());
                } else if (curr != null) {
                    String[] parts = line.split("\\s+");
                    if (parts.length == 3) {
                        try {
                            double x = Double.parseDouble(parts[0]);
                            double y = Double.parseDouble(parts[1]);
                            double z = Double.parseDouble(parts[2]);
                            map.get(curr).add(new double[]{x, y, z});
                        } catch (NumberFormatException ignored) { }
                    }
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }

        if (map.isEmpty()) {
            NUM_FRAMES = NUM_DRONES = 0;
            return;
        }
        NUM_DRONES = map.size();
        NUM_FRAMES = map.values().stream().mapToInt(List::size).max().orElse(0);
        dronePositions = new double[NUM_FRAMES][NUM_DRONES][3];
        for (int d = 0; d < NUM_DRONES; d++) {
            List<double[]> frames = map.get(d);
            for (int f = 0; f < NUM_FRAMES; f++) {
                dronePositions[f][d] = (f < frames.size()) ? frames.get(f) : new double[]{0,0,0};
            }
        }
    }
}

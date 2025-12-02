import com.google.gson.Gson;
import network.SimulationRequestDTO;
import network.SimulationResultDTO;
import utils.Utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class Simulation {
    private static final int PORT = 9091;
    private static final Path SCRIPTS_DIR = Path.of("shodrone.simulator/src/main/java/simulator/scripts");
    private static final Path REPORTS_DIR = Path.of("shodrone.simulator/src/main/java/simulator/reports");

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("🚁 Simulator is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        handleClient(socket);
                    } catch (IOException e) {
                        System.err.println("❌ Error handling client: " + e.getMessage());
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("❌ Failed to start simulator: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Gson gson = new Gson();
        String inputJson = in.readLine();
        SimulationRequestDTO request = gson.fromJson(inputJson, SimulationRequestDTO.class);

        String safeName = request.getProposalName().replaceAll("\\s+", "_");
        String scriptFilename = safeName + ".txt";
        String reportFilename = safeName + "_report.txt";

        Files.createDirectories(SCRIPTS_DIR);
        Files.createDirectories(REPORTS_DIR);

        Path scriptPath = SCRIPTS_DIR.resolve(scriptFilename);
        try (FileWriter fw = new FileWriter(scriptPath.toFile())) {
            for (String line : request.getDslScripts()) {
                fw.write(line + System.lineSeparator());
            }
        }
        Utils.printSuccessMessage("Script saved: " + scriptFilename);


        Path reportPath = REPORTS_DIR.resolve(reportFilename);
        System.out.println("⏳ Waiting for report: " + reportFilename);
        while (!Files.exists(reportPath)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }

        String result = extractFinalStatusFromReport(reportPath);
        SimulationResultDTO resultDTO = new SimulationResultDTO(request.getProposalId(), result);
        out.println(gson.toJson(resultDTO));
        System.out.println("📄 Report status: " + result);

    }

    private static String extractFinalStatusFromReport(Path reportPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(reportPath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Final Status")) {
                    if (line.contains("APPROVED")) return "PASS";
                    if (line.contains("FAILED")) return "FAIL";
                }
            }
        } catch (IOException e) {
            System.err.println("⚠️ Failed to read report: " + e.getMessage());
        }
        return "FAIL";
    }
}
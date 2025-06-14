package handler;

import com.google.gson.Gson;
import domain.entity.ShowProposal;
import domain.valueObjects.ShowProposalStatus;
import network.SimulationRequestDTO;
import network.SimulationResultDTO;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;
import utils.Utils;
import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class HandleSimulation {
    private static final int SIMULATOR_PORT = 9091;
    private static final String SIMULATOR_HOST = "10.8.101.185";  //check ip

    public static void handle(Socket socketFromBackoffice) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socketFromBackoffice.getInputStream()))
        ) {
            Gson gson = new Gson();
            String inputJson = in.readLine();

            SimulationRequestDTO request = gson.fromJson(inputJson, SimulationRequestDTO.class);
            System.out.println("📥 Received proposal from backoffice: " + request.getProposalId());


            RepositoryProvider.setUseInMemory(false);
            ShowProposalRepository repo = RepositoryProvider.showProposalRepository();

            Optional<ShowProposal> optProposal = repo.findByID(request.getProposalId());

            if (optProposal.isEmpty()) {
                Utils.printFailMessage("❌ Proposal not found: " + request.getProposalId());
                return;
            }

            ShowProposal proposal = optProposal.get();
            proposal.setStatus(ShowProposalStatus.WAITING_IN_SIMULATOR);
            repo.saveInStore(proposal);

            try (
                    Socket simulatorSocket = new Socket(SIMULATOR_HOST, SIMULATOR_PORT);
                    PrintWriter outToSimulator = new PrintWriter(simulatorSocket.getOutputStream(), true);
                    BufferedReader inFromSimulator = new BufferedReader(new InputStreamReader(simulatorSocket.getInputStream()))
            ) {
                outToSimulator.println(inputJson);
                System.out.println("🚀 Sent proposal to simulator...");

                String resultJson = inFromSimulator.readLine();
                SimulationResultDTO resultDTO = gson.fromJson(resultJson, SimulationResultDTO.class);

                Utils.printSuccessMessage("Simulator returned: " + resultDTO.getResult());

                ShowProposal finalProposal = repo.findByID(resultDTO.getProposalId()).orElseThrow();
                finalProposal.setStatus("PASS".equalsIgnoreCase(resultDTO.getResult())
                        ? ShowProposalStatus.PASSED_SIMULATION
                        : ShowProposalStatus.FAILED_SIMULATION);
                repo.saveInStore(finalProposal);

            } catch (IOException e) {
                Utils.printFailMessage("❌ Failed to communicate with simulator: " + e.getMessage());
            }

        } catch (Exception e) {
            Utils.printFailMessage("❌ Error handling simulation: " + e.getMessage());
        }
    }
}
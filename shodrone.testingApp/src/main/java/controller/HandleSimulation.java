package controller;

import com.google.gson.Gson;
import domain.valueObjects.ShowProposalStatus;
import network.SimulationRequestDTO;
import network.SimulationResultDTO;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;
import domain.entity.ShowProposal;
import utils.Utils;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class HandleSimulation {
    public static void handle(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            Gson gson = new Gson();
            String input = in.readLine();

            SimulationRequestDTO request = gson.fromJson(input, SimulationRequestDTO.class);
            System.out.println("📡 Received simulation request for proposal: " + request.getProposalId());

            FileWriter fw = new FileWriter("sim_input.dsl");
            for (String code : request.getDslScripts()) {
                fw.write(code + System.lineSeparator());
            }
            fw.close();

            Thread.sleep(2000);
            String result = Math.random() > 0.2 ? "PASS" : "FAIL";

            out.println(gson.toJson(new SimulationResultDTO(request.getProposalId(), result)));

            ShowProposalRepository repo = RepositoryProvider.showProposalRepository();
            Optional<ShowProposal> proposalOpt = repo.findByID(request.getProposalId());
            if (proposalOpt.isPresent()) {
                ShowProposal p = proposalOpt.get();
                p.setStatus(result.equals("PASS") ? ShowProposalStatus.PASSED_SIMULATION : ShowProposalStatus.FAILED_SIMULATION);
                repo.saveInStore(p);
            }

        } catch (Exception e) {
            Utils.printFailMessage("❌ Error during simulation handling: " + e.getMessage());
        }
    }
}
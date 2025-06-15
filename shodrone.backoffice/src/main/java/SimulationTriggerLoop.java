import com.google.gson.Gson;
import domain.entity.ShowProposal;
import network.SimulationRequestDTO;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;
import utils.Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

public class SimulationTriggerLoop {

    private final ShowProposalRepository repo = RepositoryProvider.showProposalRepository();

    public void run() {
        while (true) {
            try {
                Optional<List<ShowProposal>> proposals = repo.getStandbyProposals();

                if (proposals.isEmpty()) {
                    return;
                }

                for (ShowProposal proposal : proposals.get()) {
                    sendToSimulator(proposal);
                }

                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendToSimulator(ShowProposal proposal) {
        try (Socket socket = new Socket("10.9.23.21", 9090);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            SimulationRequestDTO requestDTO = new SimulationRequestDTO(
                    proposal.identity(),
                    proposal.getNameProposal().toString(),
                    proposal.getScript(),
                    proposal.getStatus()
            );

            String json = new Gson().toJson(requestDTO);
            out.println(json);

        } catch (IOException e) {
            System.err.println(e.getMessage() + "... Turn Testing App on!");
            System.exit(1);
        }
    }
}
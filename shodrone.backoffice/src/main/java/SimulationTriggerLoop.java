import domain.entity.ShowProposal;
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

                if(proposals.isEmpty()) {
                    return;
                }

                for (ShowProposal proposal : proposals.get()) {
                    sendToSimulator(proposal);
                }

                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendToSimulator(ShowProposal proposal) {
        try (Socket socket = new Socket("localhost", 9090);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println(proposal.getNameProposal().toString());
            out.println(proposal.identity());
            out.println(String.join("\n", proposal.getScript()));

        } catch (IOException e) {
            System.out.println("❌ Erro ao comunicar com simulador: " + e.getMessage());
        }
    }
}
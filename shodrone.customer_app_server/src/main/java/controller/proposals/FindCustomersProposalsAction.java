package controller.proposals;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.entity.Costumer;
import domain.entity.ShowProposal;
import domain.valueObjects.NIF;
import network.ObjectDTO;
import network.ShowProposalDTO;
import persistence.RepositoryProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static more.ColorfulOutput.ANSI_BRIGHT_PURPLE;
import static more.ColorfulOutput.ANSI_RESET;
import static more.TextEffects.BOLD;

public class FindCustomersProposalsAction {
    public static void findCustomersProposalsAction(BufferedReader in, PrintWriter out, Gson gson) {
        try {
            System.out.printf("%s%sWaiting for client connection...%s%n", BOLD, ANSI_BRIGHT_PURPLE, ANSI_RESET);

            String receivedEmail = in.readLine();
            Type objectDTOType = new TypeToken<ObjectDTO<NIF>>() {
            }.getType();
            ObjectDTO<NIF> objectDTO = gson.fromJson(receivedEmail, objectDTOType);

            NIF nifCustomer = objectDTO.getObject();
            Optional<Costumer> customerToAssociatedNIF = RepositoryProvider.costumerRepository().findByNIF(nifCustomer);

            if (customerToAssociatedNIF.isPresent()) {
                Optional<List<ShowProposal>> listOfProposalsOptional = RepositoryProvider.showProposalRepository().findAllCostumerProposals(customerToAssociatedNIF.get());

                if (listOfProposalsOptional.isPresent()) {
                    List<ShowProposal> proposals = listOfProposalsOptional.get();
                    int numberOfProposals = proposals.size();

                    ObjectDTO<Integer> objectDTOToSendInformative = ObjectDTO.of(numberOfProposals);
                    String objectDTOJsonToSendInformative = gson.toJson(objectDTOToSendInformative);
                    out.println(objectDTOJsonToSendInformative);

                    for (int i = 0; i < numberOfProposals; i++) {
                        ShowProposalDTO proposalDTO = ShowProposalDTO.fromEntity(proposals.get(i));
                        ObjectDTO<ShowProposalDTO> objectDTOToSend = ObjectDTO.of(proposalDTO);
                        String objectDTOJsonToSend = gson.toJson(objectDTOToSend);
                        out.println(objectDTOJsonToSend);
                    }

                } else {
                    int numberOfProposals = -1;
                    ObjectDTO<Integer> objectDTOToSendInformative = ObjectDTO.of(numberOfProposals);
                    String objectDTOJsonToSendInformative = gson.toJson(objectDTOToSendInformative);
                    out.println(objectDTOJsonToSendInformative);
                }
                System.out.printf("%s%sAction finished - Get Customer of Representative - %s%s\n", BOLD, ANSI_BRIGHT_PURPLE, nifCustomer, ANSI_RESET);
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}

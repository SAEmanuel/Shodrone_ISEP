package network;


import domain.entity.ShowProposal;
import domain.valueObjects.ShowProposalStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShowProposalDTO {
    private Long id;
    private String name;
    private Long costumerID;
    private List<String> text;
    private ShowProposalStatus status;
    private String feedback;


    public ShowProposalDTO() {
    }

    public ShowProposalDTO(Long id, String name, Long costumerID, List<String> text, ShowProposalStatus status) {
        this.id = id;
        this.name = name;
        this.costumerID = costumerID;
        this.text = text;
        this.status = status;
    }

    public static ShowProposalDTO fromEntity(ShowProposal proposal) {
        return new ShowProposalDTO(
                proposal.identity(),
                proposal.getNameProposal().name(),
                proposal.getShowRequest().getCostumer().identity(),
                proposal.getText(),
                proposal.getStatus()
        );
    }


    @Override
    public String toString() {
        return String.format("Proposal [%s]", name);
    }
}

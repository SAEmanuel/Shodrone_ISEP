package network;


import domain.entity.ShowProposal;
import domain.valueObjects.ShowProposalStatus;
import lombok.Setter;

import java.util.List;

public class ShowProposalDTO {
    private Long id;
    private Long costumerID;
    private List<String> text;
    @Setter
    private ShowProposalStatus status;
    @Setter
    private String feedback;


    public ShowProposalDTO() {
    }

    public ShowProposalDTO(Long id, Long costumerID, List<String> text, ShowProposalStatus status) {
        this.id = id;
        this.costumerID = costumerID;
        this.text = text;
        this.status = status;
    }

    public static ShowProposalDTO fromEntity(ShowProposal proposal) {
        return new ShowProposalDTO(
                proposal.identity(),
                proposal.getShowRequest().getCostumer().identity(),
                proposal.getText(),
                proposal.getStatus()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getCostumerID() {
        return costumerID;
    }

    public List<String> getText() {
        return text;
    }

    public ShowProposalStatus getStatus() {
        return status;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return String.format("Proposal [%d]", id);
    }
}

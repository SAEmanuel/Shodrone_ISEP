package network;


import domain.entity.ShowProposal;

import java.util.List;

public class ShowProposalDTO {
    private Long id;
    private Long costumerID;
    private List<String> text;


    public ShowProposalDTO() {
    }

    public ShowProposalDTO(Long id, Long costumerID, List<String> text) {
        this.id = id;
        this.costumerID = costumerID;
        this.text = text;
    }

    public static ShowProposalDTO fromEntity(ShowProposal proposal) {
        return new ShowProposalDTO(
                proposal.identity(),
                proposal.getShowRequest().getCostumer().identity(),
                proposal.getText()
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
}

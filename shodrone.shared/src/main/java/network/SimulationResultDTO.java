package network;

public class SimulationResultDTO {
    private Long proposalId;
    private String result;

    public SimulationResultDTO(Long proposalId, String result) {
        this.proposalId = proposalId;
        this.result = result;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public String getResult() {
        return result;
    }
}
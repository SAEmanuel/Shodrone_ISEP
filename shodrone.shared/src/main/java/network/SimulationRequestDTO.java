package network;

import java.util.List;

public class SimulationRequestDTO {
    private Long proposalId;
    private List<String> dslScripts;

    public SimulationRequestDTO(Long proposalId, List<String> dslScripts) {
        this.proposalId = proposalId;
        this.dslScripts = dslScripts;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public List<String> getDslScripts() {
        return dslScripts;
    }
}

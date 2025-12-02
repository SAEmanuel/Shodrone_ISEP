package network;

import domain.valueObjects.ShowProposalStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SimulationRequestDTO {
    private Long proposalId;
    private String proposalName;
    private List<String> dslScripts;
    private ShowProposalStatus status;

    public SimulationRequestDTO(Long proposalId, String proposalName, List<String> dslScripts, ShowProposalStatus status) {
        this.proposalId = proposalId;
        this.proposalName = proposalName;
        this.dslScripts = dslScripts;
        this.status = status;
    }
}
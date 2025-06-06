package controllers;

import controller.showproposal.ListShowProposalController;
import domain.entity.ShowProposal;
import domain.valueObjects.ShowProposalStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListShowProposalControllerTest {

    private ListShowProposalController controller;
    private ShowProposalRepository repositoryMock;

    @BeforeEach
    void setUp() {
        repositoryMock = Mockito.mock(ShowProposalRepository.class);
        RepositoryProvider.injectShowProposalRepository(repositoryMock);

        controller = new ListShowProposalController();
    }

    @Test
    void testGetAllSentAcceptedProposals_ReturnsEligibleProposals() {
        ShowProposal proposal1 = new ShowProposal();
        proposal1.setStatus(ShowProposalStatus.CUSTOMER_APPROVED);

        ShowProposal proposal2 = new ShowProposal();
        proposal2.setStatus(ShowProposalStatus.CUSTOMER_APPROVED);

        ShowProposal proposal3 = new ShowProposal();
        proposal3.setStatus(ShowProposalStatus.REJECTED);

        List<ShowProposal> allProposals = Arrays.asList(proposal1, proposal2, proposal3);

        when(repositoryMock.getAllProposals()).thenReturn(Optional.of(allProposals));

        List<ShowProposal> result = controller.getAllSentAcceptedProposals();

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.getStatus() == ShowProposalStatus.CUSTOMER_APPROVED));
        verify(repositoryMock).getAllProposals();
    }

    @Test
    void testGetAllSentAcceptedProposals_NoEligibleProposals_ThrowsException() {
        ShowProposal proposal1 = new ShowProposal();
        proposal1.setStatus(ShowProposalStatus.REJECTED);

        ShowProposal proposal2 = new ShowProposal();
        proposal2.setStatus(ShowProposalStatus.CREATED);

        List<ShowProposal> allProposals = Arrays.asList(proposal1, proposal2);

        when(repositoryMock.getAllProposals()).thenReturn(Optional.of(allProposals));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.getAllSentAcceptedProposals();
        });

        assertEquals("No Show Proposal's in CUSTOMER APPROVED status were found on the system.", exception.getMessage());
        verify(repositoryMock).getAllProposals();
    }

    @Test
    void testGetAllSentAcceptedProposals_NoProposalsInRepo_ThrowsException() {
        when(repositoryMock.getAllProposals()).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.getAllSentAcceptedProposals();
        });

        assertEquals("No Show Proposal's were found on the system.", exception.getMessage());
        verify(repositoryMock).getAllProposals();
    }
}


package controllers;

import controller.showproposal.AcceptShowProposalController;
import controller.showproposal.VerifyShowProposalStatusController;
import domain.entity.ShowProposal;
import domain.valueObjects.ShowProposalStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AcceptShowProposalControllerTest {

    private AcceptShowProposalController controller;
    private ShowProposalRepository repositoryMock;
    private VerifyShowProposalStatusController statusControllerMock;

    @BeforeEach
    void setUp() {
        repositoryMock = Mockito.mock(ShowProposalRepository.class);
        statusControllerMock = Mockito.mock(VerifyShowProposalStatusController.class);

        RepositoryProvider.injectShowProposalRepository(repositoryMock);

        controller = new AcceptShowProposalController();
        controller.showProposalStatusController = statusControllerMock;
    }


    @Test
    void testMarkShowProposalAsAccepted_Success() {
        ShowProposal proposal = new ShowProposal();
        proposal.setStatus(ShowProposalStatus.CUSTOMER_APPROVED);

        when(statusControllerMock.wasShowProposalSent(proposal)).thenReturn(true);
        when(repositoryMock.saveInStore(proposal)).thenReturn(Optional.of(proposal));

        Optional<ShowProposal> result = controller.markShowProposalAsAccepted(proposal);

        assertTrue(result.isPresent());
        assertEquals(ShowProposalStatus.COLLABORATOR_APPROVED, proposal.getStatus());
        verify(repositoryMock).saveInStore(proposal);
    }

    @Test
    void testMarkShowProposalAsAccepted_NotEligible() {
        ShowProposal proposal = new ShowProposal();
        proposal.setStatus(ShowProposalStatus.STAND_BY);

        when(statusControllerMock.wasShowProposalSent(proposal)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            controller.markShowProposalAsAccepted(proposal);
        });

        assertEquals("❌ Cannot accept the proposal because it was not sent.", exception.getMessage());
        verify(repositoryMock, never()).saveInStore(any());
    }
}


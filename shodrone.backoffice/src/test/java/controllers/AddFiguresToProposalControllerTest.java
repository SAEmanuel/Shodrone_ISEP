package controllers;

import controller.showproposal.AddFiguresToProposalController;
import domain.entity.Figure;
import domain.entity.ShowProposal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddFiguresToProposalControllerTest {

    private AddFiguresToProposalController controller;
    private ShowProposalRepository mockRepo;

    @BeforeEach
    void setUp() {
        mockRepo = mock(ShowProposalRepository.class);
        RepositoryProvider.injectShowProposalRepository(mockRepo);
        controller = new AddFiguresToProposalController();
    }

    @Test
    void getAllProposals_ShouldReturnList_WhenProposalsExist() {
        List<ShowProposal> mockProposals = Arrays.asList(mock(ShowProposal.class));
        when(mockRepo.getAllProposals()).thenReturn(Optional.of(mockProposals));

        List<ShowProposal> result = controller.getAllProposals();
        assertEquals(mockProposals.size(), result.size());
        verify(mockRepo).getAllProposals();
    }

    @Test
    void getAllProposals_ShouldThrowException_WhenEmpty() {
        when(mockRepo.getAllProposals()).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> controller.getAllProposals());
        assertEquals("No Show Proposal's were found on the system.", exception.getMessage());
    }

    @Test
    void saveNewImagesInProposal_ShouldSaveAndReturnProposal() {
        ShowProposal proposal = mock(ShowProposal.class);
        List<Figure> newFigures = Arrays.asList(mock(Figure.class), mock(Figure.class));
        when(mockRepo.saveInStore(proposal)).thenReturn(Optional.of(proposal));

        Optional<ShowProposal> result = controller.saveNewImagesInProposal(proposal, newFigures);

        assertTrue(result.isPresent());
        verify(proposal).setSequenceFigues(newFigures);
        verify(mockRepo).saveInStore(proposal);
    }

    @Test
    void saveNewImagesInProposal_ShouldReturnEmptyIfRepoFails() {
        ShowProposal proposal = mock(ShowProposal.class);
        List<Figure> figures = Collections.emptyList();
        when(mockRepo.saveInStore(proposal)).thenReturn(Optional.empty());

        Optional<ShowProposal> result = controller.saveNewImagesInProposal(proposal, figures);

        assertTrue(result.isEmpty());
        verify(proposal).setSequenceFigues(figures);
        verify(mockRepo).saveInStore(proposal);
    }
}

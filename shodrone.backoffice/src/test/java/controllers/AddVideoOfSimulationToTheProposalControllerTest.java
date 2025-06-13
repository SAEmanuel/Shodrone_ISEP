package controllers;

import controller.showproposal.AddVideoOfSimulationToTheProposalController;
import domain.entity.ShowProposal;
import domain.valueObjects.Video;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddVideoOfSimulationToTheProposalControllerTest {

    @Test
    void testGetAllProposalsReturnsList() {
        AddVideoOfSimulationToTheProposalController controller = new AddVideoOfSimulationToTheProposalController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);
        List<ShowProposal> mockProposals = Arrays.asList(mock(ShowProposal.class), mock(ShowProposal.class));

        when(mockRepo.getAllProposals()).thenReturn(Optional.of(mockProposals));

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            List<ShowProposal> result = controller.getAllProposals();

            assertNotNull(result);
            assertEquals(2, result.size());
        }
    }

    @Test
    void testGetAllProposalsReturnsNullWhenEmpty() {
        AddVideoOfSimulationToTheProposalController controller = new AddVideoOfSimulationToTheProposalController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);

        when(mockRepo.getAllProposals()).thenReturn(Optional.empty());

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            List<ShowProposal> result = controller.getAllProposals();

            assertNull(result);
        }
    }

    @Test
    void testAddVideoToShowProposalSuccess() {
        AddVideoOfSimulationToTheProposalController controller = new AddVideoOfSimulationToTheProposalController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);
        ShowProposal mockProposal = mock(ShowProposal.class);
        Video mockVideo = mock(Video.class);

        when(mockRepo.saveInStore(mockProposal)).thenReturn(Optional.of(mockProposal));

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            Optional<ShowProposal> result = controller.addVideoToShowProposal(mockProposal, mockVideo);

            assertTrue(result.isPresent());
            assertEquals(mockProposal, result.get());

            verify(mockProposal).editVideo(mockVideo);
            verify(mockRepo).saveInStore(mockProposal);
        }
    }

    @Test
    void testAddVideoToShowProposalFailsOnSave() {
        AddVideoOfSimulationToTheProposalController controller = new AddVideoOfSimulationToTheProposalController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);
        ShowProposal mockProposal = mock(ShowProposal.class);
        Video mockVideo = mock(Video.class);

        when(mockRepo.saveInStore(mockProposal)).thenReturn(Optional.empty());

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            Optional<ShowProposal> result = controller.addVideoToShowProposal(mockProposal, mockVideo);

            assertFalse(result.isPresent());
            verify(mockProposal).editVideo(mockVideo);
            verify(mockRepo).saveInStore(mockProposal);
        }
    }
}

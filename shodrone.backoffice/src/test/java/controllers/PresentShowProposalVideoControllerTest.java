package controllers;

import controller.showproposal.PresentShowProposalVideoController;
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

class PresentShowProposalVideoControllerTest {

    @Test
    void testGetAllProposalsReturnsList() {
        // Arrange
        PresentShowProposalVideoController controller = new PresentShowProposalVideoController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);
        List<ShowProposal> mockList = Arrays.asList(mock(ShowProposal.class), mock(ShowProposal.class));

        when(mockRepo.getAllProposals()).thenReturn(Optional.of(mockList));

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            // Act
            List<ShowProposal> result = controller.getAllProposals();

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());
        }
    }

    @Test
    void testGetAllProposalsReturnsNullIfEmpty() {
        PresentShowProposalVideoController controller = new PresentShowProposalVideoController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);

        when(mockRepo.getAllProposals()).thenReturn(Optional.empty());

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            List<ShowProposal> result = controller.getAllProposals();

            assertNull(result);
        }
    }

    @Test
    void testGetVideoOfShowProposalReturnsVideo() {
        PresentShowProposalVideoController controller = new PresentShowProposalVideoController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);
        ShowProposal mockProposal = mock(ShowProposal.class);
        Video mockVideo = mock(Video.class);

        when(mockRepo.getVideoBytesByShowProposal(mockProposal)).thenReturn(Optional.of(mockVideo));

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            Optional<Video> result = controller.getVideoOfShowProposal(mockProposal);

            assertTrue(result.isPresent());
            assertEquals(mockVideo, result.get());
        }
    }

    @Test
    void testGetVideoOfShowProposalReturnsEmpty() {
        PresentShowProposalVideoController controller = new PresentShowProposalVideoController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);
        ShowProposal mockProposal = mock(ShowProposal.class);

        when(mockRepo.getVideoBytesByShowProposal(mockProposal)).thenReturn(Optional.empty());

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            Optional<Video> result = controller.getVideoOfShowProposal(mockProposal);

            assertFalse(result.isPresent());
        }
    }
}

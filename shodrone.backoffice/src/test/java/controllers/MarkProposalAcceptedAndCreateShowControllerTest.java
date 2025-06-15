package controllers;

import controller.show.AcceptProposalAndCreateShowController;
import domain.entity.Costumer;
import domain.entity.Show;
import domain.entity.ShowProposal;
import domain.entity.ShowRequest;
import domain.valueObjects.Address;
import domain.valueObjects.Location;
import domain.valueObjects.ShowProposalStatus;
import domain.valueObjects.ShowStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.ShowProposalRepository;
import persistence.ShowRepository;
import persistence.ShowRequestRepository;
import persistence.RepositoryProvider;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarkProposalAcceptedAndCreateShowControllerTest {

    private AcceptProposalAndCreateShowController controller;
    private ShowProposalRepository showProposalRepository;
    private ShowRequestRepository showRequestRepository;
    private ShowRepository showRepository;
    private ShowProposal proposal;
    private ShowRequest request;
    private Show show;

    @BeforeEach
    void setUp() {
        showProposalRepository = mock(ShowProposalRepository.class);
        showRequestRepository = mock(ShowRequestRepository.class);
        showRepository = mock(ShowRepository.class);

        RepositoryProvider.injectShowProposalRepository(showProposalRepository);
        RepositoryProvider.injectShowRequestRepository(showRequestRepository);
        RepositoryProvider.injectShowRepository(showRepository);

        proposal = mock(ShowProposal.class);
        when(proposal.identity()).thenReturn(123L);
        when(proposal.getStatus()).thenReturn(ShowProposalStatus.CUSTOMER_APPROVED);

        request = mock(ShowRequest.class);
        Costumer mockCostumer = mock(Costumer.class);
        when(mockCostumer.identity()).thenReturn(99L);
        when(request.getCostumer()).thenReturn(mockCostumer);

        when(proposal.getShowRequest()).thenReturn(request);

        Address address = new Address("Rua Teste", "Lisboa", "1234-567", "Portugal");
        Location location = new Location(address, 38.7169, -9.1399, "Show no centro");

        show = new Show(
                proposal,
                location,
                LocalDateTime.of(2025, 7, 1, 21, 0),
                50,
                Duration.ofMinutes(10),
                ShowStatus.PLANNED,
                99L
        );
    }


    @Test
    void shouldThrowIfProposalNotSent() {
        when(proposal.getStatus()).thenReturn(ShowProposalStatus.STAND_BY);

        controller = new AcceptProposalAndCreateShowController();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            controller.acceptProposalAndCreateShow(proposal);
        });

        assertTrue(exception.getMessage().contains("was not sent"));
    }

    @Test
    void shouldThrowIfShowRequestNotFound() {
        when(showRequestRepository.findById(any())).thenReturn(Optional.empty());

        controller = new AcceptProposalAndCreateShowController();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            controller.acceptProposalAndCreateShow(proposal);
        });

        assertTrue(exception.getMessage().contains("ShowRequest"));
    }

    @Test
    void shouldThrowIfDuplicateShowExists() {
        when(showRequestRepository.findById(any())).thenReturn(Optional.of(request));
        when(showRepository.findDuplicateShow(any(), any(), anyLong())).thenReturn(Optional.of(mock(Show.class)));

        controller = new AcceptProposalAndCreateShowController();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            controller.acceptProposalAndCreateShow(proposal);
        });

        assertTrue(exception.getMessage().contains("already exists"));
    }

    @Test
    void shouldThrowIfProposalNotInRepositoryAnymore() {
        when(showRequestRepository.findById(any())).thenReturn(Optional.of(request));
        when(showRepository.findDuplicateShow(any(), any(), anyLong())).thenReturn(Optional.empty());
        when(showProposalRepository.findByID(any())).thenReturn(Optional.empty());

        controller = new AcceptProposalAndCreateShowController();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            controller.acceptProposalAndCreateShow(proposal);
        });

        assertTrue(exception.getMessage().contains("Proposal not found"));
    }

    @Test
    void shouldCreateShowSuccessfully() {
        when(showRequestRepository.findById(any())).thenReturn(Optional.of(request));
        when(showRepository.findDuplicateShow(any(), any(), anyLong())).thenReturn(Optional.empty());
        when(showProposalRepository.findByID(any())).thenReturn(Optional.of(proposal));
        when(showRepository.saveInStore(any())).thenReturn(Optional.of(show));

        controller = new AcceptProposalAndCreateShowController();

        Optional<Show> createdShow = controller.acceptProposalAndCreateShow(proposal);

        assertTrue(createdShow.isPresent());
        assertEquals(ShowStatus.PLANNED, createdShow.get().getStatus());

        verify(showProposalRepository, times(1)).saveInStore(proposal);
    }
}

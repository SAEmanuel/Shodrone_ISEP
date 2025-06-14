package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.Show;
import domain.valueObjects.Address;
import domain.valueObjects.Location;
import domain.valueObjects.ShowStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InMemoryShowRepositoryTest {

    private InMemoryShowRepository repository;
    private Costumer mockCostumer;
    private Location mockLocation;
    private Show sampleShow;

    @BeforeEach
    void setUp() {
        repository = new InMemoryShowRepository();
        mockCostumer = mock(Costumer.class);
        when(mockCostumer.identity()).thenReturn(1L);

        mockLocation = new Location(new Address("Ste","Vity","4000-787","Portugal"),12,12,"Description");


                sampleShow = new Show(
                null,
                mockLocation,
                LocalDateTime.of(2025, 6, 30, 21, 0),
                10,
                Duration.ofMinutes(15),
                ShowStatus.PLANNED,
                mockCostumer.identity()
        );
    }

    @Test
    void testSaveInStoreAssignsId() {
        assertNull(sampleShow.identity());
        Optional<Show> result = repository.saveInStore(sampleShow);

        assertTrue(result.isPresent());
        assertNotNull(result.get().identity());
    }

    @Test
    void testGetAllReturnsAllSavedShows() {
        repository.saveInStore(sampleShow);
        Show secondShow = new Show(
                null,
                new Location(new Address("Ste","Vity","4000-787","Portugal"),12,12,"Description"),
                LocalDateTime.of(2025, 7, 10, 22, 0),
                15,
                Duration.ofMinutes(20),
                ShowStatus.PLANNED,
                1L
        );
        repository.saveInStore(secondShow);

        List<Show> all = repository.getAll();
        assertEquals(2, all.size());
    }

    @Test
    void testFindByIdReturnsCorrectShow() {
        Show saved = repository.saveInStore(sampleShow).get();
        Optional<Show> found = repository.findById(saved.identity());

        assertTrue(found.isPresent());
        assertEquals(saved.identity(), found.get().identity());
    }

    @Test
    void testFindByIdReturnsEmptyForNonExistentId() {
        Optional<Show> result = repository.findById(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByCostumerReturnsCorrectList() {
        repository.saveInStore(sampleShow);
        Optional<List<Show>> result = repository.findByCostumer(mockCostumer);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(sampleShow.identity(), result.get().get(0).identity());
    }

    @Test
    void testFindByCostumerReturnsEmptyIfNone() {
        Costumer other = mock(Costumer.class);
        when(other.identity()).thenReturn(999L);

        Optional<List<Show>> result = repository.findByCostumer(other);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindDuplicateShowReturnsMatch() {
        repository.saveInStore(sampleShow);

        Optional<Show> duplicate = repository.findDuplicateShow(
                sampleShow.getLocation(),
                sampleShow.getShowDate(),
                sampleShow.getCustomerID()
        );

        assertTrue(duplicate.isPresent());
    }

    @Test
    void testFindDuplicateShowReturnsEmptyIfNoMatch() {
        repository.saveInStore(sampleShow);

        Optional<Show> result = repository.findDuplicateShow(
                new Location(new Address("Rua","Braga","4000-787","Portugal"),12,12,"Description"),
                sampleShow.getShowDate(),
                sampleShow.getCustomerID()
        );

        assertTrue(result.isEmpty());
    }
}

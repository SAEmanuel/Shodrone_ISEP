package persistence.inmemory;

import domain.entity.*;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DslMetadata;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryShowRequestRepositoryTest {

    private InMemoryShowRequestRepository repository;
    private Costumer costumer1;
    private Costumer costumer2;
    private ShowRequest request1;
    private ShowRequest request2;

    @BeforeEach
    void setUp() {
        repository = new InMemoryShowRequestRepository();

        costumer1 = new Costumer(1L,
                Name.valueOf("Jorge"),
                EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
                new PhoneNumber("912861312"),
                new NIF("123456789"),
                new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
        );

        costumer2 = new Costumer(2L,
                Name.valueOf("Romeu Mends"),
                EmailAddress.valueOf("maria.silva@shodrone.app"),
                new PhoneNumber("923456789"),
                new NIF("286500850"),
                new Address("Flower Street", "Lisbon", "1100-045", "Portugal")
        );

        Description description = new Description("Amazing drone show!");
        Location location = new Location(
                new Address("Rua", "Porto", "4444-888", "Portugal"),
                12, 12, "iasjdiasjdiasdjasid"
        );

        List<String> dslLines = List.of(
                "DSL version 1.1.1;",
                "DroneType DroneX;",
                "Position a = (1,2,3);",
                "Line l(a,a,DroneX);"
        );
        DslMetadata metadata = new DslMetadata("DroneX", dslLines);
        Map<String, DslMetadata> dslVersions = new HashMap<>();
        dslVersions.put("1.1.1", metadata);

        FigureCategory category = new FigureCategory(
                new Name("Category"),
                new Description("Sample category"),
                new Email("creator@shodrone.app")
        );

        Figure figure = new Figure(
                new Name("Airplane"),
                new Description("Airplane figure"),
                category,
                FigureAvailability.PUBLIC,
                FigureStatus.ACTIVE,
                dslVersions,
                costumer1
        );

        request1 = new ShowRequest(1L,
                LocalDateTime.now(),
                ShowRequestStatus.PENDING,
                "John Doe",
                costumer1,
                List.of(figure),
                description,
                location,
                LocalDateTime.now().plusHours(1),
                10,
                Duration.ofMinutes(15)
        );

        request2 = new ShowRequest(2L,
                LocalDateTime.now(),
                ShowRequestStatus.PENDING,
                "Jorge Doe",
                costumer2,
                List.of(figure),
                description,
                location,
                LocalDateTime.now().plusHours(1),
                10,
                Duration.ofMinutes(15)
        );
    }

    @Test
    void testSaveInStoreAssignsIdAndStoresEntity() {
        Optional<ShowRequest> saved = repository.saveInStore(request1);
        assertTrue(saved.isPresent());
        assertNotNull(saved.get().identity());
        assertEquals(1L, saved.get().identity());
    }

    @Test
    void testSaveInStoreReturnsEmptyForNull() {
        Optional<ShowRequest> result = repository.saveInStore(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllReturnsAllSavedRequests() {
        repository.saveInStore(request1);
        repository.saveInStore(request2);
        List<ShowRequest> all = repository.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(request1));
        assertTrue(all.contains(request2));
    }

    @Test
    void testFindByIdReturnsCorrectEntity() {
        ShowRequest saved = repository.saveInStore(request1).get();
        Optional<ShowRequest> found = repository.findById(saved.identity());
        assertTrue(found.isPresent());
        assertEquals(saved.identity(), found.get().identity());
    }

    @Test
    void testFindByIdReturnsEmptyForUnknownId() {
        Optional<ShowRequest> found = repository.findById(999L);
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindByCostumerReturnsCorrectRequests() {
        repository.saveInStore(request1);
        repository.saveInStore(request2);
        Optional<List<ShowRequest>> found = repository.findByCostumer(costumer1);
        assertTrue(found.isPresent());
        assertEquals(1, found.get().size());
        assertEquals(costumer1.identity(), found.get().get(0).getCostumer().identity());
    }

    @Test
    void testFindByCostumerReturnsEmptyForUnknownCostumer() {
        repository.saveInStore(request1);
        Costumer unknown = new Costumer(3L,
                Name.valueOf("Paulo Xu"),
                EmailAddress.valueOf("carlos.ferreira@shodrone.app"),
                new PhoneNumber("934567890"),
                new NIF("248367080"),
                new Address("Central Avenue", "Braga", "4700-123", "Portugal")
        );
        Optional<List<ShowRequest>> found = repository.findByCostumer(unknown);
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindByCostumerReturnsEmptyIfNull() {
        Optional<List<ShowRequest>> result = repository.findByCostumer(null);
        assertTrue(result.isEmpty());
    }
}
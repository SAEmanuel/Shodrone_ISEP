package domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import eapli.framework.general.domain.model.EmailAddress;
import domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.valueObjects.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

class ShowRequestTest {

    private ShowRequest showRequest;
    private Costumer costumer;
    private Description description;
    private Location location;
    private Figure figure;

    private final Costumer customer2 = new Costumer(
            Name.valueOf("Maria Silva"),
            EmailAddress.valueOf("maria.silva@shodrone.app"),
            new PhoneNumber("923456789"),
            new NIF("286500850"),
            new Address("Flower Street", "Lisbon", "1100-045", "Portugal")
    );

    @BeforeEach
    void setUp() {
        costumer = new Costumer(
                Name.valueOf("John Doe"),
                EmailAddress.valueOf("john.doe@example.com"),
                new PhoneNumber("912345678"),
                new NIF("900000007"),
                new Address("Rua Exemplo", "Lisboa", "1234-567", "Portugal")
        );

        description = new Description("Amazing drone show!");
        location = new Location(
                new Address("Rua", "Porto", "4444-888", "Portugal"),
                12, 12, "iasjdiasjdiasdjasid"
        );

        Map<String, List<String>> fakeDslVersions = new HashMap<>();
        fakeDslVersions.put("v1", Arrays.asList("LINE(1,2)", "CIRCLE(3)"));

        figure = new Figure(
                new domain.valueObjects.Name("Airplane"),
                new Description("Airplane figure"),
                null,
                FigureAvailability.PUBLIC,
                FigureStatus.ACTIVE,
                fakeDslVersions,
                null
        );

        showRequest = new ShowRequest(
                2L,
                LocalDateTime.now(),
                ShowRequestStatus.PENDING,
                "John Doe",
                costumer,
                List.of(figure),
                description,
                location,
                LocalDateTime.now().plusHours(1),
                10,
                Duration.ofMinutes(15)
        );
    }

    @Test
    void testShowRequestCreation() {
        assertNotNull(showRequest);
        assertEquals("John Doe", showRequest.getSubmissionAuthor());
        assertEquals(ShowRequestStatus.PENDING, showRequest.getStatus());
        assertNotNull(showRequest.getCostumer());
        assertFalse(showRequest.getFigures().isEmpty());
    }

    @Test
    void testShowRequestIdentity() {
        showRequest = new ShowRequest(
                LocalDateTime.now(),
                ShowRequestStatus.PENDING,
                "John Doe",
                costumer,
                List.of(figure),
                description,
                location,
                LocalDateTime.now().plusHours(1),
                10,
                Duration.ofMinutes(15)
        );

        assertNull(showRequest.identity()); // ID is null before persisting
    }

    @Test
    void testEqualsSameObject() {
        ShowRequest anotherRequest = showRequest;
        assertEquals(showRequest, anotherRequest);
    }

    @Test
    void testEqualsDifferentObject() {
        Map<String, List<String>> dsl = new HashMap<>();
        dsl.put("x", List.of("test"));

        ShowRequest anotherRequest = new ShowRequest(
                1L,
                LocalDateTime.now(),
                ShowRequestStatus.PENDING,
                "Jane Doe",
                customer2,
                List.of(new Figure(
                        new domain.valueObjects.Name("Airplane"),
                        new Description("Airplane figure"),
                        null,
                        FigureAvailability.PUBLIC,
                        FigureStatus.ACTIVE,
                        dsl,
                        null)),
                new Description("Another Show!"),
                new Location(
                        new Address("Rua", "Porto", "4444-888", "Portugal"),
                        12, 12, "iasjdiasjdiasdjasid"
                ),
                LocalDateTime.now().plusHours(2),
                15,
                Duration.ofMinutes(20)
        );

        assertNotEquals(showRequest, anotherRequest);
    }

    @Test
    void testShowRequestHashCode() {
        Map<String, List<String>> dsl = new HashMap<>();
        dsl.put("x", List.of("test"));

        ShowRequest anotherRequest = new ShowRequest(
                1L,
                LocalDateTime.now(),
                ShowRequestStatus.PENDING,
                "Jane Doe",
                customer2,
                List.of(new Figure(
                        new domain.valueObjects.Name("Airplane"),
                        new Description("Airplane figure"),
                        null,
                        FigureAvailability.PUBLIC,
                        FigureStatus.ACTIVE,
                        dsl,
                        null)),
                new Description("Another Show!"),
                new Location(
                        new Address("Rua", "Porto", "4444-888", "Portugal"),
                        12, 12, "iasjdiasjdiasdjasid"
                ),
                LocalDateTime.now().plusHours(2),
                15,
                Duration.ofMinutes(20)
        );

        assertNotEquals(showRequest.hashCode(), anotherRequest.hashCode());
    }

    @Test
    void testShowRequestStatus() {
        assertEquals(ShowRequestStatus.PENDING, showRequest.getStatus());

        showRequest = new ShowRequest(
                1L,
                LocalDateTime.now(),
                ShowRequestStatus.APPROVED,
                "John Doe",
                costumer,
                List.of(figure),
                description,
                location,
                LocalDateTime.now().plusHours(1),
                10,
                Duration.ofMinutes(15)
        );

        assertEquals(ShowRequestStatus.APPROVED, showRequest.getStatus());
    }

    @Test
    void testShowDate() {
        assertNotNull(showRequest.getShowDate());
        assertTrue(showRequest.getShowDate().isAfter(LocalDateTime.now()));
    }

    @Test
    void testShowRequestDuration() {
        assertEquals(Duration.ofMinutes(15), showRequest.getShowDuration());
    }
}
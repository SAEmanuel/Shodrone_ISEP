package domain.entity;

import domain.valueObjects.Address;
import domain.valueObjects.Location;
import domain.valueObjects.ShowStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShowTest {

    private ShowProposal mockProposal;
    private Location mockLocation;
    private LocalDateTime showDate;
    private Duration duration;
    private Show show;

    @BeforeEach
    void setUp() {
        mockProposal = new ShowProposal(); // Mock or real instance as needed
        mockLocation = new Location(new Address("Stret","Porto","4444-777","Portugal"),12,12,"Description");
        showDate = LocalDateTime.of(2025, 10, 1, 21, 0);
        duration = Duration.ofMinutes(30);

        show = new Show(mockProposal, mockLocation, showDate, 50, duration, ShowStatus.PLANNED, 1001L);
    }

    @Test
    void testConstructorInitializesCorrectly() {
        assertEquals(mockProposal, show.getShowProposalAcceptedID());
        assertEquals(mockLocation, show.getLocation());
        assertEquals(showDate, show.getShowDate());
        assertEquals(50, show.getNumberOfDrones());
        assertEquals(duration, show.getShowDuration());
        assertEquals(ShowStatus.PLANNED, show.getStatus());
        assertEquals(1001L, show.getCustomerID());
        assertNotNull(show.getSequenceFigures());
        assertTrue(show.getSequenceFigures().isEmpty());
    }

    @Test
    void testSetAndGetSequenceFigures() {
        Figure figure1 = new Figure();
        Figure figure2 = new Figure();
        show.setSequenceFigures(List.of(figure1, figure2));
        assertEquals(2, show.getSequenceFigures().size());
    }

    @Test
    void testIdentityReturnsNullInitially() {
        assertNull(show.identity()); // Since ID is generated post-persist
    }

    @Test
    void testSameAsAndEquals() {
        Show other = new Show(mockProposal, mockLocation, showDate, 50, duration, ShowStatus.PLANNED, 1001L);
        other.setShowID(10L);
        show.setShowID(10L);

        assertTrue(show.sameAs(other));
        assertEquals(show, other);
        assertEquals(show.hashCode(), other.hashCode());
    }

    @Test
    void testEqualsWithDifferentObject() {
        Show other = new Show(mockProposal, mockLocation, showDate, 40, duration, ShowStatus.COMPLETED, 1002L);
        other.setShowID(11L);
        show.setShowID(10L);

        assertFalse(show.equals(other));
        assertFalse(show.sameAs(other));
    }

    @Test
    void testToStringContainsImportantInfo() {
        show.setShowID(20L);
        String output = show.toString();
        System.out.println(output);
        assertTrue(output.contains("showID=20"));
        assertTrue(output.contains("location="));
        assertTrue(output.contains("showDate="));
        assertTrue(output.contains("numberOfDrones=50"));
        assertTrue(output.contains("status=Planned"));
    }
}

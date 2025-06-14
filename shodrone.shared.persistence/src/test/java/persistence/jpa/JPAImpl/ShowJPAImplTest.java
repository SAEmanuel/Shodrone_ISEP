package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.entity.Show;
import domain.valueObjects.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowJPAImplTest {

    private EntityManager mockEntityManager;
    private TypedQuery<Show> typedQuery;
    private ShowJPAImpl showRepository;

    @BeforeEach
    void setUp() {
        mockEntityManager = mock(EntityManager.class);
        typedQuery = mock(TypedQuery.class);

        showRepository = new ShowJPAImpl();
        showRepository.injectEntityManagerForTest(mockEntityManager);
    }

    @Test
    void saveInStore_ShouldReturnSavedEntity() {
        Show show = mock(Show.class);
        ShowJPAImpl repo = spy(showRepository);
        doReturn(show).when(repo).add(any(Show.class));

        Optional<Show> result = repo.saveInStore(show);
        assertTrue(result.isPresent());
        assertEquals(show, result.get());
    }

    @Test
    void saveInStore_ShouldReturnEmptyIfNullEntity() {
        Optional<Show> result = showRepository.saveInStore(null);
        assertTrue(result.isEmpty());
    }


    @Test
    void findByCostumer_ShouldReturnEmptyIfNoneFound() {
        Costumer costumer = mock(Costumer.class);
        when(costumer.identity()).thenReturn(1L);
        when(mockEntityManager.createQuery(anyString(), eq(Show.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("costumerId"), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        Optional<List<Show>> result = showRepository.findByCostumer(costumer);
        assertTrue(result.isEmpty());
    }

    @Test
    void findDuplicateShow_ShouldReturnDuplicateIfFound() {
        Location location = mock(Location.class);
        LocalDateTime dateTime = LocalDateTime.now();
        Long customerID = 1L;

        when(mockEntityManager.createQuery(anyString(), eq(Show.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("location", location)).thenReturn(typedQuery);
        when(typedQuery.setParameter("showDate", dateTime)).thenReturn(typedQuery);
        when(typedQuery.setParameter("customerID", customerID)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(mock(Show.class)));

        Optional<Show> result = showRepository.findDuplicateShow(location, dateTime, customerID);
        assertFalse(result.isPresent());
    }

    @Test
    void findDuplicateShow_ShouldReturnEmptyIfNoneFound() {
        Location location = mock(Location.class);
        LocalDateTime dateTime = LocalDateTime.now();
        Long customerID = 1L;

        when(mockEntityManager.createQuery(anyString(), eq(Show.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("location", location)).thenReturn(typedQuery);
        when(typedQuery.setParameter("showDate", dateTime)).thenReturn(typedQuery);
        when(typedQuery.setParameter("customerID", customerID)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        Optional<Show> result = showRepository.findDuplicateShow(location, dateTime, customerID);
        assertTrue(result.isEmpty());
    }
}

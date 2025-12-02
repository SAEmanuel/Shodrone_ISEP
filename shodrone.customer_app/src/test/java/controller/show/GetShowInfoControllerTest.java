package controller.show;

import controller.customerRepresentative.FindCustomerOfRepresentativeController;
import controller.network.AuthenticationController;
import domain.valueObjects.NIF;
import network.ShowDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetShowInfoControllerTest {

    private AuthenticationController authController;
    private GetShowInfoController controller;
    private FindCustomerOfRepresentativeController customerController;
    private FindShows4CustomerController showsController;

    private final String testEmail = "rep@example.com";
    private final NIF testNIF = new NIF("123456789");
    private final List<ShowDTO> showList = List.of(new ShowDTO(), new ShowDTO());

    @BeforeEach
    void setUp() {
        authController = mock(AuthenticationController.class);
        controller = new GetShowInfoController(authController);

        // Manually inject the inner controllers via reflection to allow mocking
        customerController = mock(FindCustomerOfRepresentativeController.class);
        showsController = mock(FindShows4CustomerController.class);

        try {
            var customerField = GetShowInfoController.class.getDeclaredField("findCustomerOfRepresentativeController");
            var showsField = GetShowInfoController.class.getDeclaredField("findShows4CustomerController");
            customerField.setAccessible(true);
            showsField.setAccessible(true);
            customerField.set(controller, customerController);
            showsField.set(controller, showsController);
        } catch (Exception e) {
            fail("Reflection setup failed: " + e.getMessage());
        }
    }

    @Test
    void testGetCustomerNIFOfTheRepresentativeAssociatedSuccess() {
        when(customerController.getCustomerIDbyHisEmail(testEmail)).thenReturn(Optional.of(testNIF));

        NIF result = controller.getCustomerNIFOfTheRepresentativeAssociated(testEmail);

        assertNotNull(result);
        assertEquals(testNIF, result);
        verify(customerController).getCustomerIDbyHisEmail(testEmail);
    }

    @Test
    void testGetCustomerNIFOfTheRepresentativeAssociatedNotFound() {
        when(customerController.getCustomerIDbyHisEmail(testEmail)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                controller.getCustomerNIFOfTheRepresentativeAssociated(testEmail));

        assertTrue(exception.getMessage().contains("✖ No customer was found"));
    }

    @Test
    void testGetShowsForCustomerSuccess() {
        when(showsController.getShows4Customer(testNIF)).thenReturn(Optional.of(showList));

        Optional<List<ShowDTO>> result = controller.getShowsForCustomer(testNIF);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        verify(showsController).getShows4Customer(testNIF);
    }

    @Test
    void testGetShowsForCustomerNotFound() {
        when(showsController.getShows4Customer(testNIF)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                controller.getShowsForCustomer(testNIF));

        assertTrue(exception.getMessage().contains("✖ No shows found for customer"));
    }
}

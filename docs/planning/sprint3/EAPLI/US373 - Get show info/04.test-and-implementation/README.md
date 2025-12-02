# US373 – Get Show Info

## 1. Overview

**As a Customer**,  
I want to get the details of a show (scheduled or in the past),  
**so that I can view information like drone models, figures, duration, and other details.**

This feature enables customer representatives to retrieve a customer’s shows and associated metadata by linking a representative’s email with the customer’s NIF and querying the show repository.

---

## 2. Tests

The test suite for **US373** validates the `GetShowInfoController`, ensuring:

- Correct retrieval of a customer's NIF using their representative's email.
- Accurate fetching of a customer's show list.
- Proper handling of "not found" and empty result cases with appropriate exception handling.

```java
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
```

---

### 2.1. Controller: `GetShowInfoControllerTest`

The test class uses **mocked controllers** to simulate underlying behavior and **reflection** to inject dependencies into the `GetShowInfoController`.


## 3. Implementation Summary
### 3.1. Controller: GetShowInfoController
Responsible for:
- Mapping representative emails to customer NIFs.

- Retrieving a list of shows for a given customer.

## ✅ Test: Retrieve NIF by Email (Success)
```java
@Test
void testGetCustomerNIFOfTheRepresentativeAssociatedSuccess() {
when(customerController.getCustomerIDbyHisEmail(testEmail)).thenReturn(Optional.of(testNIF));

    NIF result = controller.getCustomerNIFOfTheRepresentativeAssociated(testEmail);

    assertNotNull(result);
    assertEquals(testNIF, result);
    verify(customerController).getCustomerIDbyHisEmail(testEmail);
}
```

## ❌ Test: NIF Not Found
```java
@Test
void testGetCustomerNIFOfTheRepresentativeAssociatedNotFound() {
    when(customerController.getCustomerIDbyHisEmail(testEmail)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
            controller.getCustomerNIFOfTheRepresentativeAssociated(testEmail));

    assertTrue(exception.getMessage().contains("✖ No customer was found"));
}
```

## ✅ Test: Fetch Shows for NIF (Success)
```java
@Test
void testGetShowsForCustomerSuccess() {
    when(showsController.getShows4Customer(testNIF)).thenReturn(Optional.of(showList));

    Optional<List<ShowDTO>> result = controller.getShowsForCustomer(testNIF);

    assertTrue(result.isPresent());
    assertEquals(2, result.get().size());
    verify(showsController).getShows4Customer(testNIF);
}
```

## ❌ Test: No Shows Found
```java
@Test
void testGetShowsForCustomerNotFound() {
    when(showsController.getShows4Customer(testNIF)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
            controller.getShowsForCustomer(testNIF));

    assertTrue(exception.getMessage().contains("✖ No shows found for customer"));
}
```

## 4. Involved Components

| Component                              | Responsibility                                            |
|--------------------------------------|-----------------------------------------------------------|
| `AuthenticationController`            | Provides email identity of logged-in user.                |
| `FindCustomerOfRepresentativeController` | Maps representative email to customer NIF.                 |
| `FindShows4CustomerController`       | Retrieves customer shows from database.                    |
| `ShowDTO`                            | Data Transfer Object encapsulating show info (e.g., figures, drones, duration). |

---

## 5. Design Considerations

- **Reflection** is used in tests to inject private dependencies — this improves test isolation without altering controller design.
- Error handling ensures that missing customers or shows are clearly reported.
- Loose coupling through delegation to helper controllers keeps responsibilities separated.

---

## 6. Observations

- The `GetShowInfoControllerTest` thoroughly verifies both positive and negative paths.
- Shows are returned in a form suitable for presentation (`ShowDTO`).
- Designed for easy extension to support show filtering, sorting, or exporting in the future.
- Well-aligned with clean architecture: clear separation of concerns between auth, customer linkage, and data retrieval.

---

## 7. Summary

- **User Story**: US373 – "Get Show Info"
- **Feature**: Allows customer reps to query past or scheduled shows, including metadata like figures, drone models, and duration.
- **Controller Tested**: `GetShowInfoController`
- **Test Coverage**: 100% for expected functional paths
- **Result**: Ensures customers receive accurate, relevant show information through robust, fail-safe logic.



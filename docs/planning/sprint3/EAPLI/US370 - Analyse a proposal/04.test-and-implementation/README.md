# US213 - List users

## 4. Tests

This section documents the unit tests and validation strategies used to verify the correctness of the user listing functionality, ensuring that user information is correctly retrieved, status is properly resolved, and edge cases (such as missing users) are handled gracefully.

### **Test Cases**

1. **Unit Test: List Returns All Users with Correct Status**
   * **Description**: Verifies that a list of users is returned and each user is displayed with the correct active/inactive status.
   * **Expected Outcome**: Two entries are returned, one marked as ACTIVE, the other as INACTIVE.
   * **Test**:
   ```java
   @Test
   void testGetAllUsersWithStatusShowsCorrectFormat() {
       User user1 = mock(User.class);
       when(user1.getId()).thenReturn(new Email("john@shodrone.app"));
       when(user1.getName()).thenReturn("John");
       when(user1.isActive()).thenReturn(true);

       User user2 = mock(User.class);
       when(user2.getId()).thenReturn(new Email("jane@shodrone.app"));
       when(user2.getName()).thenReturn("Jane");
       when(user2.isActive()).thenReturn(false);

       when(mockUserRepo.findAll()).thenReturn(List.of(user1, user2));

       List<String> result = controller.getAllUsersWithStatus();

       assertEquals(2, result.size());
       assertTrue(result.get(0).contains("ACTIVE") || result.get(1).contains("ACTIVE"));
       assertTrue(result.get(0).contains("INACTIVE") || result.get(1).contains("INACTIVE"));
   }
   ```

2. **Unit Test: No Users Found**
   * **Description**: Confirms that the system handles an empty list of users without errors.
   * **Expected Outcome**: The result is an empty list.
   * **Test**:
   ```java
   @Test
   void testGetAllUsersWithStatusWhenNoUsersExist() {
   when(mockUserRepo.findAll()).thenReturn(List.of());

        List<String> result = controller.getAllUsersWithStatus();

        assertTrue(result.isEmpty());
   }
   ```

### Screenshot

![Unit Tests for ListUserControllerTest](img/ListUserControllerTest.png)

## 5. Construction (Implementation)

**Controller**: `ListUserController`  
The `ListUserController` handles the retrieval of all users registered in the system and formats them with their active/inactive status, interfacing between the UI and the persistence layer.

**Responsibilities**

- Retrieves all domain users using the `UserRepository`.
- Formats each user as a string in the form `email | name | ACTIVE/INACTIVE`.
- Handles scenarios where no users exist gracefully, returning an empty list.

**DDD Principles**

- The `User` aggregate provides access to state via `isActive()`, `getId()`, and `getName()`, without exposing internal implementation.
- The controller acts as an **Application Service**, coordinating the use case logic and returning the necessary information to the UI without containing domain logic itself.
- Role-based access control is not enforced at this level.

**Persistence Layer**

- `UserRepository`: Provides access to all registered domain users through the `findAll()` method.
- Repository access is abstracted via `RepositoryProvider`, allowing seamless switching between in-memory and JPA implementations.

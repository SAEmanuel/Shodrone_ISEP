## US230 - Register Show Request

## 4. **Tests**

This section is intended to document **unit tests**, **integration tests**, or any other **validation procedures** developed for this User Story.

### **Test Cases**

1. **Unit Test: Customer Identity Initially Null**

    * **Description**: Verifies if the `Customer` identifier is `null` initially.
    * **Scenario**: Create a new `Customer` and check the identity before persistence.
    * **Expected Outcome**: The `Customer` identifier should be `null` initially.
    * **Test**:

   ```java
   @Test
   void testIdentityInitiallyNull() {
       assertNull(customer.identity());
   }
   ```

2. **Unit Test: Customer Name**

    * **Description**: Verifies if the `Customer`'s name is correctly assigned.
    * **Scenario**: Create a `Customer` with a specific name and check if it matches.
    * **Expected Outcome**: The `Customer`'s name should match the provided value.
    * **Test**:

   ```java
   @Test
   void testName() {
       assertEquals(name, customer.name());
   }
   ```

3. **Unit Test: Customer Email**

    * **Description**: Verifies if the `Customer`'s email is correctly assigned.
    * **Scenario**: Create a `Customer` with a specific email and check if it matches.
    * **Expected Outcome**: The `Customer`'s email should match the provided value.
    * **Test**:

   ```java
   @Test
   void testEmail() {
       assertEquals(email, customer.email());
   }
   ```

4. **Unit Test: Customer Phone Number**

    * **Description**: Verifies if the `Customer`'s phone number is correctly assigned.
    * **Scenario**: Create a `Customer` with a specific phone number and check if it matches.
    * **Expected Outcome**: The `Customer`'s phone number should match the provided value.
    * **Test**:

   ```java
   @Test
   void testPhoneNumber() {
       assertEquals(phone, customer.phoneNumber());
   }
   ```

5. **Unit Test: Customer NIF**

    * **Description**: Verifies if the `Customer`'s NIF is correctly assigned.
    * **Scenario**: Create a `Customer` with a specific NIF and check if it matches.
    * **Expected Outcome**: The `Customer`'s NIF should match the provided value.
    * **Test**:

   ```java
   @Test
   void testNif() {
       assertEquals(nif, customer.nif());
   }
   ```

6. **Unit Test: Customer Address**

    * **Description**: Verifies if the `Customer`'s address is correctly assigned.
    * **Scenario**: Create a `Customer` with a specific address and check if it matches.
    * **Expected Outcome**: The `Customer`'s address should match the provided value.
    * **Test**:

   ```java
   @Test
   void testAddress() {
       assertEquals(address, customer.address());
   }
   ```

7. **Unit Test: Equals with Same NIF**

    * **Description**: Verifies if two `Customer` objects with the same NIF are considered equal.
    * **Scenario**: Create two `Customer` objects with the same NIF and check if they are equal.
    * **Expected Outcome**: The `equals` method should return `true` for `Customer` objects with the same NIF.
    * **Test**:

   ```java
   @Test
   void testEqualsSameNIF() {
       Customer other = new Customer(Name.valueOf("Another", "Name"),
               EmailAddress.valueOf("another@example.com"),
               new PhoneNumber("934567890"),
               new NIF("900000007"),
               new Address("Another Street", "Porto", "9999-999", "Portugal"));

       assertEquals(customer, other);
   }
   ```

8. **Unit Test: Not Equals with Different NIF**

    * **Description**: Verifies if two `Customer` objects with different NIFs are considered different.
    * **Scenario**: Create two `Customer` objects with different NIFs and check if they are different.
    * **Expected Outcome**: The `equals` method should return `false` for `Customer` objects with different NIFs.
    * **Test**:

   ```java
   @Test
   void testNotEqualsDifferentNIF() {
       Customer other = new Customer(Name.valueOf("Different", "Person"),
               EmailAddress.valueOf("different@example.com"),
               new PhoneNumber("912861312"),
               new NIF("245716084"),
               new Address("Another Street", "Braga", "8888-888", "Portugal"));

       assertNotEquals(customer, other);
   }
   ```

9. **Unit Test: Hash Code Consistent with Equals**

    * **Description**: Verifies if the hash code of two `Customer` objects with the same data is the same.
    * **Scenario**: Create two `Customer` objects with the same data and check if their hash codes are the same.
    * **Expected Outcome**: The `hashCode` should be consistent with the `equals` method.
    * **Test**:

   ```java
   @Test
   void testHashCodeConsistentWithEquals() {
       Customer other = new Customer(name, email, phone, nif, address);
       assertEquals(customer, other);
       assertEquals(customer.hashCode(), other.hashCode());
   }
   ```

10. **Unit Test: To String Contains Name and Email**

    * **Description**: Verifies if the `Customer`'s `toString` method contains the name and email of the customer.
    * **Scenario**: Create a `Customer` and check if `toString` contains the correct name and email.
    * **Expected Outcome**: The `toString` method should include the customer's name and email.
    * **Test**:

    ```java
    @Test
    void testToStringContainsNameAndEmail() {
        String result = customer.toString();
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("john.doe@example.com"));
    }
    ```

### Screenshots

![Unit Tests for Show Request](img/UnitTestShowRequest.png)
![Unit Tests for Show Request Repository](img/InMemoryShowRequestRepositoryTests.png)

## 5. **Construction (Implementation)**

This section describes the implementation logic for the **Show Request Registration**, based on **Domain-Driven Design (DDD)**, **SOLID**, and **GoF** design patterns.

### **Controller**: `RegisterShowRequestController`

The **Controller** is responsible for orchestrating the show request registration process. It acts as an entry point for the user interface, coordinating the interaction between different components such as the **UI** and **Services**.

* **Responsibilities**:

    * Receives user inputs, including customer selection and associated figures, as well as show details (such as date and number of drones).
    * Validates the inputs, including checking the customer's status (active or VIP) and data conformity.
    * Calls **factories** to create necessary entities, like the `ShowRequest`, and coordinates their persistence.

  **SOLID Principle**:

    * **Single Responsibility Principle (SRP)**: The controller has a single responsibility — orchestrating the process of registration, without managing validation logic or persistence, which is delegated to other components.

  **DDD Principle**:

    * The **Controller** layer should not know the internal business logic. It simply invokes **Domain Services** that contain the logic.

### **Services/Factories**:

#### **FactoryProvider.getShowRequestFactory()**

The **Factory** (`ShowRequestFactory`) is responsible for creating the `ShowRequest` entity. This allows the object creation process to be isolated from the controller and business logic. Using factories promotes the **Dependency Inversion Principle (DIP)**, as the controller does not depend on specific implementations for creating objects but rather on an interface that abstracts the creation.

* **Responsibilities**:

    * Create instances of `ShowRequest` with the provided data.
    * Encapsulate the logic of creating the `ShowRequest`, allowing for more flexible initialization.

  **SOLID Principle**:

    * **Factory Pattern**: The factory abstracts the creation of the entity, promoting flexibility and reusability.

#### **RepositoryProvider.showRequestRepository()**

The **Repository** layer is responsible for persistence and retrieval of data. The **Repository** pattern provides an abstraction over the persistence layer, making it easier to change storage technologies without impacting the business logic.

* **Responsibilities**:

    * Persist show requests to the database.
    * Retrieve show requests as needed for querying or viewing.

  **SOLID Principle**:

    * **Interface Segregation Principle (ISP)**: The repository exposes methods that meet the domain needs, without overwhelming it with irrelevant operations.

  **GoF Pattern**:

    * **Repository Pattern**: Provides an abstraction layer over data storage, making the application more flexible and easier to test.

### **Implementation Strategy**

The implementation of the show request registration process follows a logical sequence, ensuring data consistency and integrity:

1. **Customer and Associated Figures Selection**:

    * The user selects an active or VIP customer and associated figures to the customer. Only **public** or **exclusive** figures are allowed.

2. **Validations**:

    * The system performs a series of validations:

        * **Active or VIP Customer**: Only customers with active or VIP status can register show requests.
        * **Future Date**: The show date must be in the future.
        * **Positive Drones**: The number of drones must be positive.
        * **Public or Exclusive Figures**: Validation of associated figures, ensuring they are in the correct state.

   **SOLID Principle**:

    * **Open/Closed Principle (OCP)**: The system should be **open for extension** (new types of validation can be added) but **closed for modification** (there is no need to modify existing code to add new validations).

3. **Creation of `ShowRequest`**:

    * Once inputs are validated, the controller calls the **ShowRequestFactory** to create a `ShowRequest` instance with the provided data (customer, figures, date, etc.).

4. **Persistence via Repository**:

    * The `ShowRequest` is persisted in the repository. The repository can use an in-memory or JPA implementation, depending on the application's context (development or production mode).

   **SOLID Principle**:

    * **Dependency Inversion Principle (DIP)**: The service layer (controller) depends on abstractions (interfaces), not concrete implementations. This makes the system more flexible and easier to test.

5. **Confirmation of Registration**:

    * After successful registration, the system displays a confirmation message along with a summary of the show request.

### **Patterns Used**

* **Factory Pattern**: Used for creating `ShowRequest` and `Location` objects. The factory encapsulates the complexity of object creation, making maintenance and testing easier.
* **Repository Pattern**: The repository layer abstracts the data access logic, allowing the application to handle domain entities without worrying about storage implementation.

### **DDD Principles Applied**

* **Bounded Context**: The process of registering a show request is a well-defined context, where all business rules and validations are defined. Each part of the system (customer, figures, date, etc.) has a clear meaning within this context.
* **Entities**: `ShowRequest` is an entity with its own identity and persistence.
* **Value Objects**: `ClientStatus`, `Figure`, and other attributes that don’t have their own identity are treated as value objects.
* **Aggregates**: `ShowRequest` can be considered an **aggregate root**, ensuring the consistency of related data, such as

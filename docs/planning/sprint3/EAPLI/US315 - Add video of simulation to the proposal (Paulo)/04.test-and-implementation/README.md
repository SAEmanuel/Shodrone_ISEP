# US315 - Add Video of Simulation to the Proposal

## 1. Overview

**User Story**:  
As a **CRM Collaborator**, I want to **add a video of the simulated show** so the **customer can have a preview** of the show.

This feature enables CRM collaborators to attach a video preview to an existing show proposal. It supports updating a show proposal entity with a simulation video and ensures the data is stored persistently.

---

## 2. Tests

Unit tests ensure correct behavior of the controller when:

- Retrieving all show proposals.
- Adding a video to a specific show proposal.
- Handling empty proposal lists or failed saves.

```java
package controller.showproposal;

import domain.entity.ShowProposal;
import domain.valueObjects.Video;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddVideoOfSimulationToTheProposalControllerTest {

    @Test
    void testGetAllProposalsReturnsList() {
        AddVideoOfSimulationToTheProposalController controller = new AddVideoOfSimulationToTheProposalController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);
        List<ShowProposal> mockProposals = Arrays.asList(mock(ShowProposal.class), mock(ShowProposal.class));

        when(mockRepo.getAllProposals()).thenReturn(Optional.of(mockProposals));

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            List<ShowProposal> result = controller.getAllProposals();

            assertNotNull(result);
            assertEquals(2, result.size());
        }
    }

    @Test
    void testGetAllProposalsReturnsNullWhenEmpty() {
        AddVideoOfSimulationToTheProposalController controller = new AddVideoOfSimulationToTheProposalController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);

        when(mockRepo.getAllProposals()).thenReturn(Optional.empty());

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            List<ShowProposal> result = controller.getAllProposals();

            assertNull(result);
        }
    }

    @Test
    void testAddVideoToShowProposalSuccess() {
        AddVideoOfSimulationToTheProposalController controller = new AddVideoOfSimulationToTheProposalController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);
        ShowProposal mockProposal = mock(ShowProposal.class);
        Video mockVideo = mock(Video.class);

        when(mockRepo.saveInStore(mockProposal)).thenReturn(Optional.of(mockProposal));

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            Optional<ShowProposal> result = controller.addVideoToShowProposal(mockProposal, mockVideo);

            assertTrue(result.isPresent());
            assertEquals(mockProposal, result.get());

            verify(mockProposal).editVideo(mockVideo);
            verify(mockRepo).saveInStore(mockProposal);
        }
    }

    @Test
    void testAddVideoToShowProposalFailsOnSave() {
        AddVideoOfSimulationToTheProposalController controller = new AddVideoOfSimulationToTheProposalController();
        ShowProposalRepository mockRepo = mock(ShowProposalRepository.class);
        ShowProposal mockProposal = mock(ShowProposal.class);
        Video mockVideo = mock(Video.class);

        when(mockRepo.saveInStore(mockProposal)).thenReturn(Optional.empty());

        try (MockedStatic<RepositoryProvider> mockedStatic = mockStatic(RepositoryProvider.class)) {
            mockedStatic.when(RepositoryProvider::showProposalRepository).thenReturn(mockRepo);

            Optional<ShowProposal> result = controller.addVideoToShowProposal(mockProposal, mockVideo);

            assertFalse(result.isPresent());
            verify(mockProposal).editVideo(mockVideo);
            verify(mockRepo).saveInStore(mockProposal);
        }
    }
}
```


## 3. Construction (Implementation)
### 3.1 Controller
Handles business logic for updating a show proposal with a simulation video.

```java
package controller.showproposal;

import domain.entity.ShowProposal;
import domain.valueObjects.Video;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;

import java.util.List;
import java.util.Optional;

public class AddVideoOfSimulationToTheProposalController {

    private final ShowProposalRepository repository;

    public AddVideoOfSimulationToTheProposalController() {
        this.repository = RepositoryProvider.showProposalRepository();
    }

    /**
     * Retrieves all show proposals.
     * @return List of proposals or null if none exist.
     */
    public List<ShowProposal> getAllProposals() {
        return repository.getAllProposals().orElse(null);
    }

    /**
     * Adds a simulation video to a given proposal.
     * @param proposal The proposal to update.
     * @param video The simulation video to attach.
     * @return Optional updated proposal.
     */
    public Optional<ShowProposal> addVideoToShowProposal(ShowProposal proposal, Video video) {
        proposal.editVideo(video);
        return repository.saveInStore(proposal);
    }
}
```

## 4. Repository Interface

```java
public interface ShowProposalRepository {
    Optional<List<ShowProposal>> getAllProposals();
    Optional<ShowProposal> saveInStore(ShowProposal proposal);
}
```

## 5. Summary of Classes Involved

| Layer          | Class Name                                       | Responsibility                                           |
|----------------|--------------------------------------------------|-----------------------------------------------------------|
| Controller     | `AddVideoOfSimulationToTheProposalController`    | Orchestrates logic for adding video to a proposal        |
| Entity         | `ShowProposal`                                   | Represents a show proposal, editable with video          |
| Value Object   | `Video`                                          | Encapsulates the video content for the show              |
| Repository     | `ShowProposalRepository`                         | Interface for persisting and retrieving show proposals    |
| Provider       | `RepositoryProvider`                             | Supplies repository instances via static methods         |

---

## 6. Integration and Usage

- The **user interface** (GUI or CLI) prompts the CRM Collaborator to select a `ShowProposal`.
- The collaborator adds a simulation video through a form or upload tool.
- The **controller** `AddVideoOfSimulationToTheProposalController` receives the input.
- It calls `editVideo(video)` on the selected `ShowProposal`.
- It then saves the updated proposal using `ShowProposalRepository.saveInStore()`.
- The outcome is returned as `Optional<ShowProposal>` to confirm success or failure.

---

## 7. Observations

- **Separation of concerns** is respected: domain logic stays inside entities, persistence logic in repositories, and orchestration in the controller.
- Uses **Optional<T>** to clearly handle success/failure scenarios without null ambiguity.
- **MockedStatic** and **Mockito** are leveraged effectively for robust, isolated unit testing.
- Design is easily extendable for future enhancements like validations, video format checks, or notification triggers.
- Could be further improved with **error logging**, **user feedback integration**, or **file size/type validation**.

---



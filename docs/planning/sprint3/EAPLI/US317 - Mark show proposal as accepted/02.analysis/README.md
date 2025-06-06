# US210 - Authentication and authorization

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt 

![Domain Model](svg/us317-domain-model.svg)

### 2.2. Other Remarks
* Proposal State Validation:
  Only proposals in the CUSTOMER_APPROVED state are eligible for acceptance.
  The wasShowProposalSent(...) method from VerifyShowProposalStatusController verifies this eligibility.

* Status Transition:
  If valid, the proposal’s status is updated to COLLABORATOR_APPROVED via setStatus(...).

* Persistence Layer:
  The updated proposal is persisted using updateInStoreProposal(...) from the ShowProposalRepository.

* Access Control:
  Only authenticated users with the CRM Collaborator role are presented with this functionality.
  Access is enforced by offering this option only in the CRMCollaboratorUI menu.
  (Assumes that user authentication and role loading are already handled via EAPLI's AuthService.)

* User Feedback:
  The UI informs the user whether the operation was successful or failed using Utils.printSuccessMessage(...) or printFailMessage(...).

* Error Handling:
  If a proposal is not eligible (already accepted, rejected, or not sent), the system throws an IllegalStateException with an appropriate message. The user is notified accordingly.

* UI and Controller Separation:
  Presentation logic is isolated in AcceptShowProposalUI, which delegates business logic to AcceptShowProposalController.
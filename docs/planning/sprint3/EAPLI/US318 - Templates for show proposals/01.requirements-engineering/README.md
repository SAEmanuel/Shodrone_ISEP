# US318 – Templates for Show Proposals

## 1. Requirements Engineering

### 1.1. User Story Description

As a CRM Manager, I want to be able to configure the template that formats the document to be sent to the customer, so that proposals follow a consistent and professional format.

---

### 1.2. Customer Specifications and Clarifications

- **Only users with the CRM Manager role can configure or edit proposal templates.**
- **The template defines the format of the proposal document sent to the customer** (e.g., email body, PDF, etc.).
- **The template must support dynamic placeholders/fields** (e.g., `${customerName}`, `${showDate}`, `${showLocation}`, `${figures}`, `${drones}`, `${video}`) that will be automatically filled with request/proposal data.
- **The system must validate the syntax and presence of required fields in the template before accepting it** (using the ANTLR plugin).
- **Templates can be edited, but can only be used if they pass validation.**
- **The system must allow multiple templates to be saved, with one set as the “active” default.**
- **Template change history must be recorded (who changed it, when).**
- **Only validated templates are available for use in proposal generation.**
- **The template must be flexible to support future types of proposals.**

---

### 1.3. Acceptance Criteria

- [x] Only the CRM Manager can create/edit templates.
- [x] The system validates the template syntax and required fields before accepting it.
- [x] The template supports dynamic placeholders for all necessary fields.
- [x] It is not possible to activate an invalid template.
- [x] Change history is recorded (user, date/time).
- [x] The active template is used by default for proposal generation.
- [x] The system displays success or error messages after each operation.
- [x] The template can be edited and revalidated at any time.

> **Note:** These acceptance criteria will be checked off as they are addressed and implemented during development and testing.

---

### 1.4. Found Out Dependencies

- **Depends on the user authentication and authorization module.**
- **Depends on the template validation plugin (ANTLR).**
- **Depends on the show request and proposal management module.**
- **Integration with the change/audit history system.**

---

### 1.5. Input and Output Data

**Input Data:**

- **Template content (String or text file) with placeholders.**
- **Information about the user who creates/edits the template.**

**Output Data:**

- **Confirmation of template accepted and validated, or**
- **Error message indicating syntax issues or missing required fields.**
- **Change history (who, when, what).**

---

### 1.6. System Sequence Diagram (SSD)

![Requirements-Engineering](svg/us318-system-sequence-diagram-System_Sequence_Diagram.svg)

---

### 1.7. Other Relevant Remarks

- **The system should be prepared for template internationalization/localization.**
- **It should be possible to preview the template with sample data before activation.**
- **The template can be used to generate different document formats (text, HTML, PDF).**
- **Validation should be extensible to support new dynamic fields in the future.**
- **Template changes do not affect proposals already sent previously.**

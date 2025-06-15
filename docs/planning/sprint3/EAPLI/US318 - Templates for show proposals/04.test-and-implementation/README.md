# US318 - Templates for ShowProposals

## 4. Tests

### Domain Layer – `ProposalTemplate`

- **testCreationWithValidData**  
  Verifies that a `ProposalTemplate` instance is correctly created with valid name, description, and content.

- **testChangeName**  
  Confirms that the name of the template can be updated successfully.

- **testChangeDescription**  
  Ensures the description is updated correctly.

- **testChangeContent**  
  Checks that the script content (text list) can be modified.

- **testHasIdentityWithMatchingId**  
  Tests `hasIdentity()` using a matching string-formatted ID.

- **testEqualsAndHashCode**  
  Validates `equals()` and `hashCode()` behavior based on `id` and `name`.

- **testSameAsEqualsEquivalentObject**  
  Ensures `sameAs()` returns true for logically equal objects.

- **testToStringWithAndWithoutDescription**  
  Validates the `toString()` output formatting when description is provided or set to default ("Not provided!").

---

### Infrastructure Layer – `InMemoryProposalTemplateRepository`

- **testSaveSuccess**  
  Confirms a template with a unique name is saved successfully.

- **testSaveDuplicateReturnsEmpty**  
  Ensures that saving a duplicate name returns `Optional.empty()`.

- **testFindByNameSuccess**  
  Checks that a template can be found by its name (case-insensitive).

- **testFindByNameNotFound**  
  Returns empty when no template is found by name.

- **testFindAllSortedAlphabetically**  
  Asserts that all stored templates are retrieved and sorted alphabetically by name.

---

## 5. Implementation – Key Methods

### UI Layer

**RegisterProposalTemplateUI**
- `public void run()`
    - Orchestrates the entire template registration flow, collecting name, description, and content, and displaying success/error messages.

### Application Layer

**RegisterProposalTemplateController**
- `public Optional<ProposalTemplate> registerProposalTemplate(Name name, Description description, List<String> text, List<String> missing)`
    - Validates template content using the plugin.
    - Persists the template if valid.

### Domain Layer

**ProposalTemplate**
- `public ProposalTemplate(Name name, Description description, List<String> text)`
    - Main aggregate constructor.
- `public void changeName(Name name)`
- `public void changeContent(List<String> text)`
- `public void changeDescription(Description description)`

### Infrastructure/Validation Layer

**TemplatePlugin**
- `public List<String> validate()`
    - Validates the template, returning missing required fields.
- `public boolean isValid()`
    - Indicates if the template is valid.

**ProposalTemplateRepository**
- `Optional<ProposalTemplate> save(ProposalTemplate template)`
    - Persists the template, ensuring name uniqueness.
- `List<ProposalTemplate> findAll()`
- `Optional<ProposalTemplate> findByName(String name)`

---

You can use these methods in the implementation section, focusing on the main flow of US318.
If you need test examples or helper methods, just ask!

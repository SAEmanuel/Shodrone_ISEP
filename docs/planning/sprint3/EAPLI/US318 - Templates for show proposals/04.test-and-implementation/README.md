# US318 - Templates for ShowProposals

## 4. Tests


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

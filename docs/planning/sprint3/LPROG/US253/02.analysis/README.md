# US253 – Configuration of a Drone’s Language Using ANTLR Grammar

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt

![Domain Model](svg/us253-domain-model.svg)

Este diagrama apresenta o modelo de domínio centrado na **configuração da linguagem de programação** para diferentes **modelos de drones**, utilizando gramáticas definidas com **ANTLR**. O objetivo é permitir que **cada modelo de drone** seja associado a uma linguagem formal específica para interpretação de ficheiros de entrada (`input.txt`) com instruções operacionais.

Este trecho do modelo destaca os seguintes elementos principais:

- **DroneLanguageConfiguration** (`<<AggregateRoot>>`): Representa a configuração da linguagem de um modelo de drone, definida por uma gramática ANTLR.
- **DroneModel**: Entidade que identifica um tipo específico de drone ao qual será associada uma linguagem.
- **ANTLRGrammar**: Contém o conteúdo da gramática ANTLR, incluindo regras léxicas e sintáticas.
- **InputScript**: Representa o ficheiro de entrada (`input.txt`) que será validado conforme a gramática associada.
- **ValidationPipeline**: Mecanismo responsável por compilar a gramática, gerar os ficheiros de código, compilar o parser e executar a análise do ficheiro de entrada.
- **MakefileAutomation**: Suporte técnico que encapsula o processo de compilação e execução via `make`, garantindo repetibilidade e integração com o sistema.

#### **Explanation of the model elements**

- **DroneLanguageConfiguration** (`<<AggregateRoot>>`): Representa a configuração da linguagem de um modelo de drone. Contém:
  - `antlrGrammar`: Texto da gramática ANTLR (e.g., `DroneOne.g4`).
  - `startRule`: Regra inicial da gramática (normalmente `program`).
  - `inputScript`: Caminho ou conteúdo do ficheiro `input.txt` a ser validado.
- **DroneModel**: Identificador do modelo de drone. Cada modelo pode ter **exatamente uma gramática associada**.
- **ANTLRGrammar**:
  - `grammarName`: Nome da gramática (e.g., `DroneOne`).
  - `parserRules`: Regras sintáticas da linguagem (e.g., `instruction`, `variable`).
  - `lexerRules`: Tokens reconhecidos (e.g., `FLOAT`, `SEMICOLON`, etc.).
- **InputScript**: Conteúdo de entrada a ser analisado pelo parser gerado. Pode incluir comandos específicos como `Move(<p1>, <v1>);`.
- **ValidationPipeline**:
  - Usa `antlr-4.13.1-complete.jar` para gerar código.
  - Compila os ficheiros com `javac`.
  - Executa o parser e analisa o ficheiro `input.txt`.
  - Retorna sucesso ou lista de erros de parsing.
- **MakefileAutomation**:
  - Encapsula o processo com targets como `antlr`, `compile`, `run`, `tests`.
  - Permite integração CI/CD ou execução manual.

---

### 2.2. Other Remarks

Este modelo enfatiza a abordagem modular e automatizada para definição de linguagens por modelo de drone, destacando:

- **Separação de responsabilidades**: A gramática é definida separadamente da sua execução e análise.
- **Extensibilidade**: A gramática pode ser modificada ou substituída conforme evoluem os requisitos operacionais do drone.
- **Testabilidade**: O sistema permite validar a gramática com exemplos (`input.txt`) de forma automática.
- **Automação**: Através do `Makefile`, todas as etapas são executadas de forma consistente, desde a geração da gramática até à execução dos testes.
- **Reusabilidade**: A mesma gramática pode ser reaproveitada em diferentes ambientes de simulação ou execução real.

Este modelo fornece a fundação necessária para garantir que cada drone modelado possua uma linguagem formal clara e testável, assegurando que o comportamento esperado em cenários reais seja corretamente especificado e validado via gramática.


# US253 – Configuration of a Drone’s Language Using ANTLR Grammar

## 3. Design

### 3.1. Design Overview

O design para a configuração da linguagem de drones via gramática ANTLR baseia-se em princípios modulares, automação por Makefile e separação clara entre definição da linguagem e sua execução.

Cada modelo de drone pode ter associada uma gramática ANTLR específica, que define como instruções para aquele modelo devem ser escritas e interpretadas. Esta gramática é compilada usando o ANTLR para gerar um parser que analisa um ficheiro `input.txt`, contendo comandos ou variáveis que controlam o comportamento do drone.

Principais responsabilidades do design incluem:

- A gramática ANTLR (`.g4`) define formalmente as instruções e estruturas aceites para cada modelo de drone.
- O `Makefile` automatiza todo o ciclo: geração de código com ANTLR, compilação do parser com `javac`, execução com `java`, e análise do ficheiro `input.txt`.
- O parser gerado processa `input.txt` e valida se as instruções estão em conformidade com a gramática.
- A modularidade permite que cada gramática esteja isolada por modelo de drone, garantindo suporte a múltiplos tipos de linguagem operacional.
- O sistema pode ser estendido para aplicar validações semânticas, interpretar árvores de análise ou integrar com simuladores/reais.

Este design promove robustez, automação e escalabilidade, garantindo que instruções para drones sejam formalmente validadas antes da execução.

---

### 3.2. Sequence Diagram(s)

![Sequence Diagram - Drone Language Configuration and Validation](svg/us253-sequence-diagram-full.svg)

O diagrama de sequência ilustra o fluxo desde a configuração da linguagem até à validação da entrada:

- O utilizador desenvolve ou associa uma gramática `.g4` a um modelo de drone.
- Um ficheiro `input.txt` com instruções para o drone é preparado.
- O `Makefile` é executado para compilar a gramática e o parser gerado.
- O sistema usa o parser para analisar o conteúdo de `input.txt`.
- Se o ficheiro for válido, a análise continua para camadas superiores (e.g., execução em simulação).
- Se ocorrerem erros, o utilizador é notificado com mensagens claras e localizadas no input.

---

### 3.3. Design Patterns (if any)

- **Parser Pattern**: Utilizado através da gramática ANTLR, transforma `input.txt` em árvore de parsing (parse tree).
- **Validation Pipeline**: A pipeline de validação separa parsing de interpretação, permitindo reutilização e modularidade.
- **Makefile Automation Pattern**: Centraliza todas as etapas de geração, compilação e execução de testes num único ponto.
- **Strategy Pattern**: Permite que diferentes drones usem diferentes estratégias de parsing, trocando dinamicamente a gramática aplicada.
- **Extensible Grammar Design**: A gramática pode ser estendida com novas instruções, tipos de variáveis ou comportamentos.
- **Tooling Integration**: Estrutura preparada para integração futura com editores de código, IDEs ou simuladores de drones.

Este design garante uma base sólida para a validação automatizada de comandos de drones, através de linguagens formais específicas, altamente reutilizáveis e facilmente auditáveis.

# US340 – Parsing and Validation of Figure Description Language Using ANTLR

## 3. Design

### 3.1. Design Overview

O design para o parsing e validação da linguagem de descrição de figuras (Figure Description Language) baseia-se na integração do ANTLR para definição formal da gramática, geração automática de parser, e uso de visitor pattern para construção do modelo de domínio.

Principais componentes e responsabilidades do design incluem:

- A gramática ANTLR (`Figures.g4`) define formalmente a sintaxe e estrutura da linguagem de descrição.
- O `Makefile` automatiza a geração do lexer e parser, compilação dos fontes Java e execução da aplicação que lê um ficheiro `input.txt` contendo o código DSL.
- O lexer (`FiguresLexer`) tokeniza o código de entrada, enquanto o parser (`FiguresParser`) constrói a árvore de análise sintática (parse tree).
- Um visitor (`DSLFigureVisitor`) percorre a árvore para construir o modelo de domínio (`DSLFigureModel`), que representa os drones, posições, velocidades, formas, blocos de movimento, etc.
- O sistema suporta a validação sintática e semântica da linguagem, gerando relatórios e mensagens de erro úteis para o utilizador.
- A modularidade permite extensão da linguagem e integração com interfaces gráficas ou sistemas de simulação de drones.

Este design assegura que o código da linguagem de descrição de figuras seja validado e interpretado de forma robusta e extensível, com uma arquitetura clara e automatizada.

---

### 3.2. Sequence Diagram(s)

![Sequence Diagram - DSL Parsing and Validation](svg/us340-sequence-diagram-full.svg)

O diagrama de sequência mostra o fluxo completo da análise da linguagem:

- O utilizador (Drone Tech) submete um ficheiro DSL (`input.txt`) ao sistema.
- O sistema encaminha a requisição para o plugin responsável pela análise da linguagem.
- O plugin utiliza o lexer para tokenizar o código.
- Os tokens são passados ao parser, que gera a árvore de análise.
- O visitor percorre a árvore, construindo o modelo do domínio.
- O resultado da análise (modelo ou erros) é retornado ao sistema.
- O sistema fornece feedback detalhado ao utilizador, indicando sucesso ou falhas na validação.

---

### 3.3. Design Patterns

- **Parser Pattern**: Uso da gramática ANTLR para transformar o código DSL num parse tree estruturado.
- **Visitor Pattern**: Aplicado para percorrer a árvore de análise e construir o modelo de domínio rico.
- **Pipeline de Validação**: Separação clara entre tokenização, parsing e validação/interpretação.
- **Makefile Automation Pattern**: Centraliza a geração do lexer/parser, compilação e execução numa pipeline automatizada.
- **Modular Plugin Design**: Permite encapsular lógica de parsing num componente reutilizável e extensível.
- **Extensible DSL Design**: A gramática e o modelo podem ser facilmente ampliados para suportar novas funcionalidades.

Este design fortalece a robustez, a escalabilidade e a manutenção da ferramenta de parsing da linguagem de descrição de figuras, facilitando o desenvolvimento e validação de cenários para drones.


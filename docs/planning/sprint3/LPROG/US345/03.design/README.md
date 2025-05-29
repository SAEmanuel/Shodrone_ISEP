# US345 – Drone Language Plugin Deployment and Configuration

## 3. Design

### 3.1. Design Overview

O design para o sistema de plugins de linguagens de drone baseia-se em uma arquitetura modular e extensível, utilizando gramáticas ANTLR para análise sintática e validação semântica dos programas de drone.

O fluxo inicia com o código fonte do programa drone submetido ao plugin correspondente à linguagem alvo. Cada plugin encapsula uma gramática ANTLR, responsável pela tokenização, parsing e validação da sintaxe e semântica do código.

Principais componentes e responsabilidades incluem:
- **DroneLanguagePlugin:** Interface que define o contrato para análise dos programas, via o método `analyzeProgram(String code)`, que executa parsing e validação semântica.
- **ANTLRGrammar:** Define formalmente a linguagem do drone, incluindo regras léxicas e sintáticas para o lexer e parser ANTLR.
- **DroneOnePlugin:** Implementação concreta do plugin para uma linguagem drone específica, que cria lexer, parser, adiciona listeners para tratamento de erros e usa o visitor `DroneOneValidator` para validação semântica detalhada.
- **DroneOneValidator:** Visitor que percorre a árvore sintática produzida pelo parser, realizando validações semânticas específicas como verificação de variáveis, expressões, tuplas, arrays e instruções.
- **ValidationResult:** Objeto que encapsula o resultado da análise, indicando sucesso ou falha e incluindo mensagens detalhadas.
- **PluginManager:** Gerencia o ciclo de vida dos plugins, selecionando o plugin correto para a linguagem do código submetido.
- **ErrorReporting:** Responsável por apresentar mensagens claras e localizadas para o usuário, facilitando a identificação e correção de erros.

Este design promove:
- Modularidade para permitir a coexistência e evolução independente de plugins para diferentes linguagens.
- Uso rigoroso de gramáticas ANTLR para garantir precisão na análise sintática.
- Separação clara entre parsing e validação semântica para facilitar manutenção e extensão.
- Feedback robusto e detalhado para usuários e ferramentas consumidoras.
- Facilidade de integração em fluxos automatizados e ambientes de desenvolvimento.

### 3.2. Sequence Diagram(s)

![Sequence Diagram - Drone Language Plugin Analysis](svg/us345-sequence-diagram-full.svg)

O diagrama de sequência demonstra o processo típico de análise e validação de um programa drone:

- O sistema ou usuário submete o código fonte do programa drone.
- O **PluginManager** seleciona o plugin adequado para a linguagem do código.
- O plugin instancia um lexer ANTLR para tokenização e um parser para análise sintática.
- O parser gera a árvore sintática, reportando erros sintáticos por meio de listeners especializados.
- Em caso de sucesso sintático, o plugin utiliza o visitor **DroneOneValidator** para análise semântica da árvore.
- O visitor percorre nós da árvore, validando variáveis, expressões, instruções e emitindo erros semânticos se necessário.
- O plugin retorna um **ValidationResult** indicando sucesso ou falha, junto com mensagens de erro ou confirmação.
- Mensagens são apresentadas ao usuário para correção ou confirmação da validade do programa.

### 3.3. Design Patterns

- **Strategy Pattern:** Cada linguagem drone é representada por um plugin que implementa a interface comum `DroneLanguagePlugin`, permitindo a seleção dinâmica do plugin correto.
- **Visitor Pattern:** O visitor `DroneOneValidator` implementa a validação semântica, separando lógica de validação da estrutura da árvore sintática.
- **Factory Pattern:** O **PluginManager** atua como fábrica e orquestrador dos plugins, instanciando e configurando conforme a linguagem.
- **Observer Pattern:** Uso de listeners ANTLR para captura e tratamento de erros sintáticos durante o parsing.
- **Pipeline Pattern:** A análise segue etapas distintas e sequenciais: tokenização, parsing, validação semântica, e geração de resultados, facilitando extensibilidade.
- **Error Reporting Pattern:** Mecanismos especializados para coleta e apresentação de erros, assegurando feedback claro e localizado.

Este design assegura um sistema robusto, modular e preparado para evolução, garantindo validação precisa e feedback eficaz para múltiplas linguagens de programação de drones.

# US110 - Domain model

## 1. Requirements Engineering

### 1.1. User Story Description

As Project Manager, I want the team to elaborate a Domain Model using DDD.

### 1.2. Customer Specifications and Clarifications

**From the specifications document and client meetings:**

>None.

**From forum:**

> **Question:** Segue-se as dúvidas que surgiram ao meu grupo na leitura do documento:
>- Qual a informação necessária para a "maintenance" de um drone(data da manutenção, data da próxima manutenção,...);
>- O que quer dizer com "role" do drone em um show, é algo que tem valores específicos? Se sim quais esses valores;
>- Qual a informação necessária no "show proposal"?
>- "Upon acceptance of the show proposal by the client, the show is scheduled by the CRM team. This probably involves some negotiation with the customer. The date and time are stored in the system." A data e tempo neste excerto estão relacionadas com o show, ou seja, a data em que ele irá ser realizado, ou com o show proposal, ou seja, quando este foi aceite? ?
>
> **Answer:** Em relação à manutenção, normalmente inclui manutenção preventiva e reparação. No caso das reparações, são desplotadas por falhas. A manutenção preventiva é programada.
Quando se envia um equipamento para reparar (devido a falha) é necessário haver uma descrição da falha/problema, não?
Relativamente ao papel de um drone num show, assumindo que se está a referir à secção 3.1.3, não percebi a questão. Cada drone tem o seu papel no show, executando um conjunto de movimentos. Quer atribuir um identificador específico a cada drone no show? Se isso facilitar, não tenho nada contra. Desde que não seja um utilizador do sistema a fazê-lo. Um show pode ter centenas de drones. Teria de ser um processo automático.
Relativamente à show proposal, sinceramente, estava à espera que os grupos apresentassem propostas concretas. Aliás, há user stories para especificar e validar a show proposal.
Quanto à última questão, será mesmo uma questão bem pensada? A data em que a proposta foi aceite deve ser registada no sistema? Óbvio! Passa pela cabeça de alguém não fazer isso?! Aliás, a data de criação de um show request também deve ser registada, etc. Mesmo que não seja parte dos requisitos, registam-se instantes de criação de muitos registos. Os SGBD até têm tipos de dados e mecanismos específicos para criarem registos temporais (timestamp).
Mas o texto refere-se especificamente à data de agendamente do show? Parece-me bastante claro...

> **Question:** Um representante pode atender vários clientes, ou cada cliente tem seus próprios representantes, sem compartilhamento entre eles?
> 
> **Answer:** Simplifiquemos: os representantes não são partilhados entre clientes. Não encontro user stories que apontem noutro sentido.


> **Question:** Uma figura pode ter várias versões. De que forma o cliente pretende que seja feito o registo destas diferentes versões?
>- Cada figura tem o seu indicativo de versão e são vistas como "figuras diferentes" ou todas as versões precisam ficar agregadas à primeira versão dessa figura criada e todas têm o mesmo nome?
>
> **Answer:** Do ponto de vista da Shodrone isso apenas poderá ter reflexos na UI. Por exemplo, ao pesquisar por uma figura poderem aparecer todas as versões dessa mesma figura. Mas isso nada tem a ver com a implementação no backend.
O cliente não tem qualquer opinião sobre o assunto.


> **Question:** Gostaríamos de esclarecer o seguinte(Referente à informação Figure 1 de Main Use cases na secção de Show Management nos use cases "Generate show proposal and send to client" e " Submit show request"):
É necessário que um Show Request tenha associado o colaborador que o criou? Da mesma forma, um Show Proposal deve estar sempre ligado ao CRM Manager que o gerou?
>
> **Answer:** Normalmente regista-se essa informação. Note-se que a Show Proposal não é gerada por um CRM Manager.

> **Question:** É referido que um show request tem um número pedido de drones (tentativa). Este número é o número total de drones ou é necessário perceber número total por tipo/modelo de drone pedido?
>
> **Answer:** Faz sentido que seja o número total. Caso contrário, seria indicado que tinha os modelos e o número de drones de cada modelo. Não é?


> **Question:** Boa tarde, eu e o meu grupo estamos a ter uns problemas em compreender o que o "representante" significa, a nossa leitura leva-nos a entender que o "cliente" será uma empresa, e o "representante" seria alguém pertencente à empresa que irá comunicar/interagir com a Shodrone.
>
> **Answer:** Será bsicamente isso.


> **Question:** É possível que um Request inclua a execução de várias figuras em simultâneo?
Estávamos a pensar realizar transações, ou seja, após cada figura ser executada, os drones voltariam à sua posição inicial, ficando prontos para a próxima figura. Este raciocínio está correto?
O Customer Representative interage diretamente com a aplicação ou faz o pedido através de um colaborador da Shodrone? Gostaríamos de perceber melhor essa interação, para sabermos quem efetivamente introduz o pedido no sistema.
Quanto aos Exclusive Requirements, como devemos implementá-los? O cliente pode apenas pedir pequenas alterações como por exemplo as luzes ou aparência dos drones, ou também pode requisitar figuras totalmente diferentes das existentes no catálogo de figuras?
>
> **Answer:** 
>1. Não.
>2. Talvez quisesse dizer "transições". Esse assunto será aprofundado no sprint 2.
>3. O diagrama de casos de uso (figura 1) parece-me claro. O representante do cliente só interage com a CustomerApp (figura 2).
>4. Não se pergunta ao cliente como a equipa deve implementar uma funcionalidade. E lamento, mas não encontrei "Exclusive Requirements" no enunciado.


> **Question:** Quais são os estados possíveis que um Drone se pode encontrar? Visto que o mesmo pode ser removido do inventário, estar ativo, em manutenção, isso seriam todos estados que devemos considerar?
>
> **Answer:** Esses 3 estados parecem-me bem. Ainda que, tendo em vista que estamos a falar de manutenção, o estado "avariado" também faz sentido.


> **Question:** Quando numa proposta há uma nova figura, esta é enviada para o Show Designer que a cria e submete no sistema. Se esta imagem for "pública" deve ser automaticamente adicionada ao catálogo após a sua submissão?
>
> **Answer:** Todas as figuras devem ser adicionadas ao catálogo, independentemente de serem públicas ou não.


> **Question:** Em alguma situação, as figuras "privadas" podem aparecer no catálogo?
>
> **Answer:** Claro, mas só podem ser usadas em "proposals" do cliente que tem direitos de reserva sobre elas.


> **Question:** Que informações tem um proposal template e o que diferente um template do outro?
Sempre que o CRM collaborator cria uma proposta é obrigado a escolher um template antes?
>
> **Answer:** Acho que está claro que uma proposta é baseada numa template. Criar uma proposta sem escolher a template não faz sentido.

> **Question:** Qual a relevância deste relatório para o negócio?
>
> **Answer:** O relatório dá-nos o resultado do teste. E claro que isso será crítico para o negócio. Não passa pela cabeça de ninguém enviar a um cliente uma proposta de um show que não passa o teste de funcionamento.

> **Question:** Já foi dito que para o show request e show proposal faz sentido guardar que colaborador que o criou no sistema. No entanto surgiu-nos a dúvida de como deveríamos guardar esta informação. Há algum tipo de identificação de colaborador? Se sim, que regras existem para esta identificação? Se não, apenas o email do colaborador chegaria para o identificar?
>
> **Answer:** Todos os utilizadores devem ter um ID, garantidamente. Estava mal se não tivessem.
O que deve ser esse ID é outra questão. Aparentemente, na UI deve ser usado o email. Internamente, o senhor é que sabe.

> **Question:** Gostaria de saber se showRequest e showProposal são diferentes, isto é, se showProposal é apenas uma confirmação do showRequest, ou se é algo do género de uma contraproposta.
Por exemplo, caso no showRequest seja pedido 100 drones, é possível que a showProposal tenha apenas 80?
>
> **Answer:** São diferentes.

### 1.3. Acceptance Criteria

* **AC01:** NFR11 – High-level language (DSL) and drones’ language analysis/validation

### 1.4. Found out Dependencies
* None.


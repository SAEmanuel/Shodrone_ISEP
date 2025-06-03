grammar showProposal_pt;

proposal         : header corpo assinatura anexo lista_drones lista_figuras EOF ;

header           : EXMOS_SENHORES EMPRESA MORADA_OU_NOME VAT REF PROPOSTA_SHOW ;

corpo            : INICIO descricao fim ;

descricao        : DESCRICAO+ ;

fim              : FIM1 FIM2 FIM3 ;

assinatura       : MORADA_OU_NOME CRM_MANAGER ;

anexo            : TITULO_ANEXO LOCAL DATA_FIELD HORA_FIELD DURACAO_FIELD ;

lista_drones     : TITULO_DRONES_L drone_info ;

drone_info       : DRONE_INFO+ ;

lista_figuras    : TITULO_FIGURAS_L figuras_info;

figuras_info     : FIGURAS_INFO+ ;

EXMOS_SENHORES   : 'Exmos. Senhores' ;

EMPRESA : '[' EMPRESA_TEXTO TIPO_EMPRESA ']' ;
fragment EMPRESA_TEXTO : ~']'+? ;
fragment TIPO_EMPRESA
    : ' Lda'
    | ' Limitada'
    | ' Unipessoal Lda'
    | ' Unipessoal Limitada'
    | ' S.A.'
    | ' Sociedade Anónima'
    | ' SGPS'
    | ' S.U.Q.'
    | ' S.C.'
    | ' S.N.C.'
    ;

VAT : '[' [0-9]+ ']' ;

MORADA_OU_NOME : '[' PREENCHIMENTO ']' ;
fragment PREENCHIMENTO : (~[\]] | WS)+ ;

REF : 'Referência' ' [#' NUMERO ']' ' / ' '[' DATA_PT ']' ;
fragment NUMERO : [0-9]+ ;
fragment DATA_PT : DIA '/' MES '/' ANO ;
fragment DIA : [0-2][0-9] | '3'[01] ;
fragment MES : '0'[1-9] | '1'[0-2] ;
fragment ANO : [0-9][0-9][0-9][0-9] ;

PROPOSTA_SHOW : 'Proposta de Show' ;

INICIO: 'A Shodrone tem o prazer de submeter à V/ apreciação uma proposta' ~[\r\n]+ ;

FIM1: 'Estando certos que seremos alvo da V/ preferência.' ;
FIM2: 'Subscrevemo-nos ao dispor.' ;
FIM3: 'Melhores cumprimentos,' ;

CRM_MANAGER : 'CRM Manager';

TITULO_ANEXO: 'Anexo – Detalhes do Show' ' [#' NUMERO ']' ;
LOCAL: 'Local de realização –' WS? LOCAL_FIELD ;
DATA_FIELD: 'Data –' WS? '[' DATA_PT ']' ;
HORA_FIELD: 'Hora –' WS? '[' HORA_TEXTO ']' ;
DURACAO_FIELD: 'Duração –' WS? DURACAO_TEXTO ' minutos' ;

fragment LOCAL_FIELD : (~[\r\n\]])+ ;
fragment HORA_TEXTO : [0-2][0-9] ':' [0-5][0-9] ;
fragment DURACAO_TEXTO : '[' [0-9]* ']' ;

TITULO_DRONES_L: '#Lista de drones utilizados';
DRONE_INFO : '[' ~[\r\n]+ ']' ' – ' '[#' [0-9]+ ']' ' unidades.';

TITULO_FIGURAS_L : '#Lista de figuras';
FIGURAS_INFO : '[' ~[\r\n]+ ']' ' – ' '[' ~[\r\n]+ ']';

DESCRICAO: ~[\r\n]+ ;

WS : [ \t\r\n]+ -> skip ;

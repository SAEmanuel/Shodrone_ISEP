grammar Figures;

program: statement* EOF;

statement:
    droneType
    | variable
    | instantiation
    | segment
    | functionCall SEMICOLON
    | pauseStmt
    ;

droneType:
    TYPE IDENTIFIER SEMICOLON
    ;

variable:
    'Position' IDENTIFIER '=' vector SEMICOLON
    | 'Velocity' IDENTIFIER '=' expr SEMICOLON
    | 'Distance' IDENTIFIER '=' expr SEMICOLON
    ;

instantiation
    : TYPE IDENTIFIER LPAREN argList? RPAREN SEMICOLON
    ;

segment
    : 'before' segmentBody 'endbefore'
    | 'group' segmentBody 'endgroup'
    | 'after' segmentBody 'endafter'
    ;

segmentBody
    : statement*
    ;

pauseStmt
    : 'pause' LPAREN INT RPAREN SEMICOLON
    ;

functionCall
    : IDENTIFIER '.' IDENTIFIER LPAREN argList? RPAREN
    | IDENTIFIER '.' IDENTIFIER
    ;

argList:
    arguement
    | arguement COMMA argList
    ;

arguement:
    IDENTIFIER
    | vector
    | exprPosition
    | expr
    | COLOR
    ;

exprPosition:
    vector
    | vector '+' exprPosition
    | vector '-' exprPosition
    ;

vector:
    LPAREN number COMMA number COMMA number RPAREN;

number:
    INT
    | FLOAT;

expr:
    literal
    | literal op=('*'|'/') expr
    | literal op=('+'|'-') expr
    ;

literal:
    INT
    | FLOAT
    | CONSTANT;


FLOAT: '-'? [0-9]+ '.' [0-9]+;
INT: '-'? [0-9]+;

COLOR: 'RED' | 'GREEN' | 'YELLOW';
CONSTANT: 'PI' | 'e';

TYPE: [A-Z][a-zA-Z]*;
IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;

OPERATOR:
    '+'
    |'-'
    |'*'
    |'/';

LPAREN: '(';
RPAREN: ')';
COMMA: ',';
SEMICOLON: ';';

WS: [ \t\r\n]+ -> skip;

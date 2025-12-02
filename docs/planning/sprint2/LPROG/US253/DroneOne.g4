grammar DroneOne;

program: (variable | instruction)* EOF;

 /* ---------- VARIABLES  ------------- */
variable:
    type_Var SEMICOLON;

type_Var :
    position
    | vector
    | linearVelocity
    | angularVelocity
    | distance
    | time;

position :  'Position' IDENTIFIER EQUALS (tupleEpression | array);
vector:  'Vector' IDENTIFIER EQUALS tupleEpression;
linearVelocity:  'LinearVelocity' IDENTIFIER EQUALS expression;
angularVelocity:  'AngularVelocity' IDENTIFIER EQUALS expression;
distance: 'Distance' IDENTIFIER EQUALS expression;
time: 'Time' IDENTIFIER EQUALS timeExpression;

tupleEpression :
        tuple  tupleEpression
        | tuple MINUS tupleEpression
        | tuple PLUS tupleEpression
        | tuple;

expression:
    VALUE
    | expression PLUS expression
    | expression MINUS expression
    | expression STAR expression
    | expression DIVIDE expression;

timeExpression:
    INT
    | INT STAR timeExpression
    | INT PLUS timeExpression
    | INT MINUS timeExpression
    | INT DIVIDE timeExpression;

tuple: LPAREN VALUE COMMA VALUE COMMA VALUE RPAREN;

array: LPAREN tuple (COMMA tuple)* RPAREN;

 /* ---------- INSTRUCTIONS  ------------- */
instruction:
    IDENTIFIER LPAREN argumentList? RPAREN SEMICOLON;

argumentList:
    VARIABLE
    | VARIABLE COMMA argumentList;

VARIABLE: '<' [a-zA-Z_][a-zA-Z0-9_ ]* '>';

VALUE:
    CONSTANT
    | INT
    | FLOAT;

CONSTANT:
    [Pp][Ii];

FLOAT
    : DIGIT+ '.' DIGIT+ ( [eE] [+-]? DIGIT+ )?
    | '.' DIGIT+ ( [eE] [+-]? DIGIT+ )?
    | DIGIT+ [eE] [+-]? DIGIT+;

INT
    : MINUS? DIGIT+;

DIGIT
    : [0-9];

// Lexer rules for symbols that were causing the errors
EQUALS: '=';
PLUS: '+';
MINUS: '-';
STAR: '*';
DIVIDE: '/';
LPAREN: '(';
RPAREN: ')';
COMMA: ',';
SEMICOLON: ';';

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;

// Skip whitespace and comments
WS: [ \t\r\n]+ -> skip;

// Skip other descriptive lines
DESCRIPTION_LINE 
    : [A-Z][a-zA-Z0-9 ,.-]+ ('\r'? '\n' | '\n') -> skip;

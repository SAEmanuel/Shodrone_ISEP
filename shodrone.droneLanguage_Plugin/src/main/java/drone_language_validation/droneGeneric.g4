grammar droneGeneric;

program: statement* EOF;

// Statements
statement
    : variableDeclaration
    | instruction
    ;

// Variable Declaration
variableDeclaration
    : type ID '=' expression ';'
    ;

// Type Definitions (both DroneOne and DroneTwo types)
type
    : 'Position' | 'Point'
    | 'Vector'
    | 'LinearVelocity'
    | 'AngularVelocity'
    | 'Distance'
    | 'Time'
    ;

// Instructions
instruction
    : 'takeOff' '(' expression ',' expression ')' ';'
    | 'land' '(' expression ')' ';'
    | 'move' '(' expression ',' expression ')' ';'
    | 'move' '(' expression ',' expression ',' expression ')' ';'
    | 'movePath' '(' expression ',' expression ')' ';'
    | 'moveCircle' '(' expression ',' expression ',' expression ')' ';'
    | 'hoover' '(' expression ')' ';'
    | 'lightsOn' '(' expression? ')' ';'
    | 'lightsOff' '('? ')' ';'
    | 'blink' '(' expression ')' ';'
    ;

// Expressions (with precedence and no left-recursion)
expression
    : <assoc=right> expression op=('*'|'/') expression       # ExpressionMulDiv
    | <assoc=right> expression op=('+'|'-') expression       # ExpressionAddSub
    | 'PI' '/' INT                                           # ExpressionPiDiv
    | vectorExpr                                             # ExpressionVector
    | tupleExpr                                              # ExpressionTuple
    | arrayOfTuples                                          # ExpressionArray
    | floatLiteral                                           # ExpressionFloat
    | INT                                                    # ExpressionInt
    | ID                                                     # ExpressionVarRef
    ;


tupleExpr
    : '(' floatLiteral ',' floatLiteral ',' floatLiteral ')'
    ;

arrayOfTuples
    : '(' tupleExpr (',' tupleExpr)* ')'
    ;

vectorExpr
    : tupleExpr '-' tupleExpr
    ;

// Literals and Identifiers
ID: [a-zA-Z_][a-zA-Z0-9_]*;

fragment DIGIT: [0-9];
INT: '-'? DIGIT+;

FLOAT: '-'? DIGIT+ '.' DIGIT* | '-'? '.' DIGIT+;

fragment EXPONENT: ('e'|'E') ('+'|'-')? DIGIT+;
floatLiteral: FLOAT (EXPONENT)?;

// Skip
WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;

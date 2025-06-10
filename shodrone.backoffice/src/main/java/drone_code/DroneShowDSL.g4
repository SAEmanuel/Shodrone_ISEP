grammar DroneShowDSL;

dslFile
    : 'DSL' 'version' VERSION ';'
      droneTypeDecl
      variableDecl*
      shapeDecl+
      commandBlock*
      EOF
    ;

droneTypeDecl
    : 'DroneType' ID ';'
    ;

variableDecl
    : positionDecl
    | velocityDecl
    | distanceDecl
    ;

positionDecl
    : 'Position' ID '=' vector ';'
    ;

velocityDecl
    : 'Velocity' ID '=' expr ';'
    ;

distanceDecl
    : 'Distance' ID '=' expr ';'
    ;

shapeDecl
    : ('Line' | 'Rectangle' | 'Circle' | 'Circumference') ID '(' exprList ')' ';'
    ;

commandBlock
    : beforeBlock
    | groupBlock
    | afterBlock
    | pauseCommand
    | simpleCommand
    ;

groupBlock
    : 'group' command* 'endgroup'
    ;

beforeBlock
    : 'before' (command | groupBlock)* 'endbefore'
    ;

afterBlock
    : 'after' (command | groupBlock)* 'endafter'
    ;

pauseCommand
    : 'pause' '(' expr ')' ';'
    ;

simpleCommand
    : command
    ;

command
    : ID '.' ID ( '(' exprArguments? ')' )? ';'
    ;

exprArguments
    : expr (',' expr)*
    ;

exprList
    : expr (',' expr)*
    ;

expr
    : atom
    | '-' expr
    | expr ('*' | '/') expr
    | expr ('+' | '-') expr
    ;

atom
    : NUMBER
    | 'PI'
    | 'PI' '/' NUMBER
    | ID
    | vector
    ;

vector
    : '(' expr ',' expr ',' expr ')'
    ;

VERSION : [0-9]+ '.' [0-9]+ '.' [0-9]+ ;
ID      : [a-zA-Z_][a-zA-Z0-9_]* ;
NUMBER  : [0-9]+ ('.' [0-9]+)? ;

WS      : [ \t\r\n]+ -> skip ;
COMMENT : '//' ~[\r\n]* -> skip ;

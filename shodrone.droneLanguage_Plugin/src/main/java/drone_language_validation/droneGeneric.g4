grammar droneGeneric;

program
  : header section_types section_variables section_instructions EOF
  ;

header
  : ID 'programming' 'language' 'version' VERSION_NUMBER
  ;

section_types
  : 'Types' ID* // tipos livres, um por linha
  ;

section_variables
  : 'Variables' variable_declaration*
  ;

variable_declaration
  : ID ID '=' expression ';'
  ;

section_instructions
  : 'Instructions' instruction*
  ;

instruction
  : ID '(' param_list? ')' ';'
  ;

param_list
  : expression (',' expression)*
  ;

expression
  : vector
  | array_literal
  | NUMBER
  | ID
  | expression op=('*' | '/' | '+' | '-') expression
  | '(' expression ')'
  ;

vector
  : '(' expression ',' expression ',' expression ')'
  | vector '-' vector
  ;

array_literal
  : '(' vector (',' vector)* ')'
  ;


VERSION_NUMBER : [0-9]+ '.' [0-9]+ '.' [0-9]+ ;
ID             : [a-zA-Z_][a-zA-Z0-9_]* ;
NUMBER         : '-'? [0-9]+ ('.' [0-9]+)? ;
WS             : [ \t\r\n]+ -> skip ;
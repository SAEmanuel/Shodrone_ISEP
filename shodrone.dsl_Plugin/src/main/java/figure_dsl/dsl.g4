grammar dsl;

dsl
  : version
    drone_model
    variable_declaration*
    element_definition*
    block_statement*
    unscoped_statement*
  ;

version
  : 'DSL' 'version' VERSION_NUMBER ';'
  ;

drone_model
  : 'DroneType' ID ';'
  ;

variable_declaration
  : position_declaration
  | velocity_declaration
  | distance_declaration
  ;

position_declaration
  : 'Position' ID '=' vector ';'
  ;

velocity_declaration
  : 'Velocity' ID '=' expression ';'
  ;

distance_declaration
  : 'Distance' ID '=' expression ';'
  ;

vector
  : '(' expression ',' expression ',' expression ')'
  ;

element_definition
  : ID ID '(' parameter_list? ')' ';'
  ;

parameter_list
  : parameter (',' parameter)*
  ;

parameter
  : ID
  | vector
  | expression
  ;

block_statement
  : block_type statement+ end_block_type
  ;

unscoped_statement
  : statement
  | pause_statement
  ;

block_type
  : 'before'
  | 'after'
  | 'group'
  ;

end_block_type
  : 'endbefore'
  | 'endafter'
  | 'endgroup'
  ;

statement
  : ID '.' method ';'
  | group_statement_block
  ;

group_statement_block
  : 'group' statement+ 'endgroup'
  ;

method
  : 'move' '(' vector ',' expression ',' ID ')'
  | 'rotate' '(' rotate_param ',' rotate_param ',' rotate_param ',' rotate_param ')'
  | 'lightsOn' '(' parameter_list ')'
  | 'lightsOff()'
  ;

rotate_param
  : vector
  | expression
  ;

pause_statement
  : 'pause' '(' expression ')' ';'
  ;

expression
  : NUMBER
  | ID
  | expression ('*' | '/' | '+' | '-') expression
  | '(' expression ')'
  ;

VERSION_NUMBER : [0-9]+ '.' [0-9]+ '.' [0-9]+ ;
ID             : [a-zA-Z_][a-zA-Z0-9_]* ;
NUMBER         : '-'? [0-9]+ ('.' [0-9]+)? ;
WS             : [ \t\r\n]+ -> skip ;
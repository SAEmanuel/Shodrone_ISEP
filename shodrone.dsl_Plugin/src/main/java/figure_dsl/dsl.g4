grammar dsl;

// The main entry rule of the DSL parser.
// Defines the expected structure of a DSL file:
// version declaration, exactly one drone model,
// zero or more variable declarations, element definitions,
// block statements, and unscoped statements.
dsl
  : version
    drone_model
    variable_declaration*
    element_definition*
    block_statement*
    unscoped_statement*
  ;

// Version declaration rule.
// Requires the keywords 'DSL' 'version', followed by a version number and a semicolon.
version
  : 'DSL' 'version' VERSION_NUMBER ';'
  ;

// Drone model declaration rule.
// Specifies the drone type using 'DroneType' keyword followed by an identifier and semicolon.
drone_model
  : 'DroneType' ID ';'
  ;

// Variable declaration can be one of the following three types.
variable_declaration
  : position_declaration
  | velocity_declaration
  | distance_declaration
  ;

// Position variable declaration.
// Uses keyword 'Position', an identifier, equals sign, a 3D vector, and semicolon.
position_declaration
  : 'Position' ID '=' vector ';'
  ;

// Velocity variable declaration.
// Uses keyword 'Velocity', an identifier, equals sign, an expression, and semicolon.
velocity_declaration
  : 'Velocity' ID '=' expression ';'
  ;

// Distance variable declaration.
// Uses keyword 'Distance', an identifier, equals sign, an expression, and semicolon.
distance_declaration
  : 'Distance' ID '=' expression ';'
  ;

// Vector definition.
// Represents a 3-component vector using parentheses and three expressions separated by commas.
vector
  : '(' expression ',' expression ',' expression ')'
  ;

// Element definition rule.
// Defines an element by two identifiers (e.g., type and name),
// followed by optional parameters enclosed in parentheses, ending with semicolon.
element_definition
  : ID ID '(' parameter_list? ')' ';'
  ;

// Parameter list rule.
// A comma-separated list of one or more parameters.
parameter_list
  : parameter (',' parameter)*
  ;

// Parameter can be:
// - an identifier,
// - a vector,
// - or an expression.
parameter
  : ID
  | vector
  | expression
  ;

// Block statement rule.
// Defines blocks like 'before', 'after', or 'group' that contain one or more statements,
// and end with a corresponding end block keyword.
block_statement
  : block_type statement+ end_block_type
  ;

// Unscoped statements that are not inside blocks.
// Can be a general statement or a pause statement.
unscoped_statement
  : statement
  | pause_statement
  ;

// Block type keywords.
block_type
  : 'before'
  | 'after'
  | 'group'
  ;

// End block keywords matching the block type.
end_block_type
  : 'endbefore'
  | 'endafter'
  | 'endgroup'
  ;

// Statement rule.
// Either a method call on an ID (like object.method()) ending with semicolon,
// or a group of statements inside 'group' ... 'endgroup'.
statement
  : ID '.' method ';'
  | group_statement_block
  ;

// Group statement block.
// A 'group' keyword followed by one or more statements, then closed by 'endgroup'.
group_statement_block
  : 'group' statement+ 'endgroup'
  ;

// Method calls supported in this DSL.
// Four options:
// 1. move method with vector, expression, and ID parameters
// 2. rotate method with four rotate parameters
// 3. lightsOn method with parameter list
// 4. lightsOff method with no parameters
method
  : 'move' '(' vector ',' expression ',' ID ')'
  | 'rotate' '(' rotate_param ',' rotate_param ',' rotate_param ',' rotate_param ')'
  | 'lightsOn' '(' parameter_list ')'
  | 'lightsOff()'
  ;

// Rotate parameters can be either a vector or an expression.
rotate_param
  : vector
  | expression
  ;

// Pause statement.
// The keyword 'pause' followed by an expression in parentheses and semicolon.
pause_statement
  : 'pause' '(' expression ')' ';'
  ;

// Expression rule supporting:
// - numbers
// - identifiers
// - binary arithmetic operations (+, -, *, /)
// - grouped expressions inside parentheses
expression
  : NUMBER
  | ID
  | expression ('*' | '/' | '+' | '-') expression
  | '(' expression ')'
  ;

// Lexer rules for tokens:

// Version number token, format like: 1.0.0
VERSION_NUMBER : [0-9]+ '.' [0-9]+ '.' [0-9]+ ;

// Identifier token: starts with letter or underscore, followed by letters, digits or underscores
ID             : [a-zA-Z_][a-zA-Z0-9_]* ;

// Number token: optional leading minus sign, digits, optional decimal part
NUMBER         : '-'? [0-9]+ ('.' [0-9]+)? ;

// Whitespace: spaces, tabs, newlines, skipped by the lexer
WS             : [ \t\r\n]+ -> skip ;

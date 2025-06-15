grammar template; // Defines the grammar name as 'template'

/*
 * Parser Rules
 */
template: (field | TEXT)* EOF;
// The main rule: a template consists of zero or more 'field' or 'TEXT' tokens,
// followed by EOF (end of file).

field: FIELD;
// A 'field' is simply a FIELD token (defined in the lexer rules below).

/*
 * Lexer Rules
 */
FIELD: '${' FIELD_NAME '}';
// A FIELD is a placeholder of the form '${field_name}', where 'field_name'
// follows the FIELD_NAME rule (i.e., letters, digits, or underscores).

TEXT: ~[$]+ | '$' ~[{]+;
// TEXT matches any sequence of characters not including '$',
// OR a '$' followed by any character except '{' (e.g., "$x").
// This prevents partial field patterns like "$x" from being misinterpreted as fields.

FIELD_NAME: [a-zA-Z_][a-zA-Z0-9_]*;
// A valid field name must start with a letter or underscore,
// and may be followed by letters, digits, or underscores (similar to variable names in many languages).

WS: [ \t\r\n]+ -> skip;
// Whitespace (spaces, tabs, newlines) is ignored/skipped during parsing.

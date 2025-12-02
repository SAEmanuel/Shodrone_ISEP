grammar template;

template: (field | TEXT)* EOF;

field: FIELD;

FIELD: '${' FIELD_NAME '}';

TEXT: ~[$]+ | '$' ~[{]+;

FIELD_NAME: [a-zA-Z_][a-zA-Z0-9_]*;

WS: [ \t\r\n]+ -> skip;
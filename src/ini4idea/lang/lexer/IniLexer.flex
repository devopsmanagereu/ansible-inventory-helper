package ini4idea.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import ini4idea.lang.IniTokenTypes;

%%

%class _IniLexer
%implements FlexLexer
%unicode
%public

%type IElementType

%function advance

%eof{ return;
%eof}

EOL= \n | \r | \r\n
COMMENTCONTENT=[^\r\n]*
EZCOMMENT=#{COMMENTCONTENT}
ZENDCOMMENT=;{COMMENTCONTENT}
COMMENT={EZCOMMENT} | {ZENDCOMMENT}

VALUE_CHARACTER=\".*\" | [^\n\r\f\t\=]+
KEY_CHARACTER=\".*\" | [^\n\r\f\ \t\=]+

WHITESPACE=[\ \t\f]+
SECTION=\[[^\]]+\]
EQUAL=\=
LEFT=[a-zA-z\]\[\.\_1-9]+
RIGHT=\".*\" | [^\n\r\f\ \t\=]+

%state IN_VALUE
%state IN_KEY_VALUE_SEPARATOR

%%

{EOL}        { return IniTokenTypes.EOL; }
{COMMENT}    { return IniTokenTypes.COMMENT; }
{WHITESPACE} { return IniTokenTypes.WHITESPACE; }
{SECTION}    { return IniTokenTypes.SECTION; }

<YYINITIAL> {KEY_CHARACTER}+             { yybegin(IN_KEY_VALUE_SEPARATOR); return IniTokenTypes.KEY_CHARACTERS; }
<IN_KEY_VALUE_SEPARATOR> {EQUAL} { yybegin(IN_VALUE); return IniTokenTypes.EQUAL; }
<IN_VALUE> {VALUE_CHARACTER}+            { yybegin(YYINITIAL); return IniTokenTypes.VALUE_CHARACTERS; }

<IN_KEY_VALUE_SEPARATOR> {WHITESPACE}*  { yybegin(YYINITIAL); return IniTokenTypes.WHITESPACE; }
<IN_VALUE> {EOL}{WHITESPACE}*     { yybegin(YYINITIAL); return IniTokenTypes.WHITESPACE; }
.            { return IniTokenTypes.BAD_CHARACTER; }

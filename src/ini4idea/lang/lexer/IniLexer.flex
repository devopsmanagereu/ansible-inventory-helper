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

VALUE_CHARACTER=[^\n\r\f\t\=\ ][^\n\r\f\t\=]*{EOL}?
KEY_CHARACTER=[^\n\r\f\ \t\=]+

WHITESPACE=[\ \t\f]+
SECTION=\[[^\]]+\]
EQUAL=\=

%state IN_VALUE
%state IN_KEY_VALUE_SEPARATOR

%%

//{EOL}        { return IniTokenTypes.EOL; }
{COMMENT}    { return IniTokenTypes.COMMENT; }
{WHITESPACE} { return IniTokenTypes.WHITESPACE; }
{SECTION}    { return IniTokenTypes.SECTION; }

<YYINITIAL> {
                {KEY_CHARACTER}             { yybegin(IN_KEY_VALUE_SEPARATOR); return IniTokenTypes.KEY_CHARACTERS; }
                {EOL}+ { return IniTokenTypes.EOL; }
             }
<IN_KEY_VALUE_SEPARATOR> {  {EQUAL} { yybegin(IN_VALUE); return IniTokenTypes.EQUAL; }
                            {EOL} { yybegin(YYINITIAL); return IniTokenTypes.EOL; }
                          }
<IN_VALUE> {    {EOL} { yybegin(YYINITIAL); return IniTokenTypes.EOL; }
                {VALUE_CHARACTER}            { yybegin(YYINITIAL); return IniTokenTypes.VALUE_CHARACTERS; }
            }

.            { return IniTokenTypes.BAD_CHARACTER; }

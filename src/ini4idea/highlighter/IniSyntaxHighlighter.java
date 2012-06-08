package ini4idea.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import ini4idea.lang.IniTokenTypes;
import ini4idea.lang.lexer.IniLexer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Konstantin Ulitin
 */
public class IniSyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<IElementType, TextAttributesKey>();

    @NotNull
    public static Map<IElementType, TextAttributesKey> getTextAttributesMap() {
        return ATTRIBUTES;
    }

    static {
        fillMap(ATTRIBUTES,
                TextAttributesKey.createTextAttributesKey("INI.COMMENT", SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes()),
                IniTokenTypes.COMMENT);
        fillMap(ATTRIBUTES,
                TextAttributesKey.createTextAttributesKey("INI.SECTION", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()),
                IniTokenTypes.SECTION);
        fillMap(ATTRIBUTES,
                TextAttributesKey.createTextAttributesKey("INI.KEY", SyntaxHighlighterColors.STRING.getDefaultAttributes()),
                IniTokenTypes.KEY_CHARACTERS);
        fillMap(ATTRIBUTES,
                TextAttributesKey.createTextAttributesKey("INI.VALUE", SyntaxHighlighterColors.STRING.getDefaultAttributes()),
                IniTokenTypes.VALUE_CHARACTERS);
        fillMap(ATTRIBUTES,
                TextAttributesKey.createTextAttributesKey("INI.EQUALS", SyntaxHighlighterColors.OPERATION_SIGN.getDefaultAttributes()),
                IniTokenTypes.EQUAL);
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new IniLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(ATTRIBUTES.get(tokenType));
    }
}

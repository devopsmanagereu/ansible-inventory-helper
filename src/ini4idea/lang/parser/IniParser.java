package ini4idea.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import ini4idea.IniElementTypes;
import ini4idea.lang.IniTokenTypes;
import org.jetbrains.annotations.NotNull;

//import com.intellij.packageDependencies.ui.Marker;

/**
 * @author Konstantin Ulitin
 */
public class IniParser implements PsiParser {

    @NotNull
    @Override
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        // TODO modify parser to include single key character into section, not full directive
        PsiBuilder.Marker rootMarker = builder.mark();
        while (parseSection(builder)) {
        }
        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

    /**
     * @param builder Psi builder
     * @return false if EOF was reached
     */
    private boolean parseSection(PsiBuilder builder) {
        if (builder.eof()) {
            return false;
        }
        while (builder.getTokenType() != IniTokenTypes.SECTION && !builder.eof()) {
            // TODO Assert tpkentype == EOL
            builder.advanceLexer();
        }

        PsiBuilder.Marker section = builder.mark();
        // TODO assert builder.getTokenType() == IniTokenTypes.SECTION
        builder.advanceLexer();
        while (parseAssign(builder)) {
        }
        section.done(IniTokenTypes.SECTION);

        return true;
    }

    private boolean parseAssign(PsiBuilder builder) {
        if (builder.getTokenType() == IniTokenTypes.EOL) {
            builder.advanceLexer();
            return true;
        }

        if (builder.getTokenType() != IniTokenTypes.KEY_CHARACTERS) {
            return false;
        }

        PsiBuilder.Marker assign = builder.mark();

        PsiBuilder.Marker lval = builder.mark();
        while (parseKey(builder)) {
        }
        lval.done(IniTokenTypes.LVAL);

        if (builder.getTokenType() != IniTokenTypes.EQUAL) {
            /*assign.drop();
            return false;*/
            assign.drop();
            builder.advanceLexer();
            return true;
        }

        builder.advanceLexer();

        PsiBuilder.Marker rval = builder.mark();
        while (parseValue(builder)) {
        }
        rval.done(IniTokenTypes.RVAL);

//        assign.done(IniTokenTypes.ASSIGN);
        assign.done(IniElementTypes.DIRECTIVE);

        // TODO assert builder.getTokenType() == IniTokenTypes.EOL
//        builder.advanceLexer();

        return true;
    }

    private boolean parseKey(PsiBuilder builder) {
        if (builder.getTokenType() != IniTokenTypes.KEY_CHARACTERS) {
            return false;
        }

        PsiBuilder.Marker str = builder.mark();
        builder.advanceLexer();
        str.done(IniTokenTypes.KEY_CHARACTERS);

        return true;
    }

    private boolean parseValue(PsiBuilder builder) {
/*
        if (builder.getTokenType() == IniTokenTypes.EOL){
            builder.advanceLexer();
            return false;
        }
*/

        if (builder.getTokenType() != IniTokenTypes.VALUE_CHARACTERS) {
            return false;
        }

        PsiBuilder.Marker str = builder.mark();
        builder.advanceLexer();
        str.done(IniTokenTypes.VALUE_CHARACTERS);

        return true;
    }


}

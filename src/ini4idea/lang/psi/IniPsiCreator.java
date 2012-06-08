package ini4idea.lang.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import ini4idea.IniElementTypes;
import ini4idea.lang.IniTokenTypes;
import ini4idea.lang.psi.stubs.DirectiveImpl;
import org.jetbrains.annotations.NotNull;

/**
 * @author Konstantin Ulitin
 */
public class IniPsiCreator {

    @NotNull
    public static PsiElement createElement(@NotNull ASTNode node) {
        final IElementType astNodeType = node.getElementType();
        // TODO try to make some enum for here
        if (astNodeType == IniElementTypes.DIRECTIVE) {
            return new DirectiveImpl(node);
        }
        if (astNodeType == IniTokenTypes.SECTION) {
            return new IniSectionImpl(node);
        } else if (astNodeType == IniTokenTypes.ASSIGN) {
            return new IniAssign(node);
        } else if (astNodeType == IniTokenTypes.LVAL) {
            return new IniKeyImpl(node);
        } else if (astNodeType == IniTokenTypes.RVAL) {
            return new IniValue(node);
        }
        return new ASTWrapperPsiElement(node);
    }
}

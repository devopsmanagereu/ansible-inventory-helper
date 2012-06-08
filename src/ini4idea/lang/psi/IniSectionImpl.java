package ini4idea.lang.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import ini4idea.lang.IniTokenTypes;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Konstantin Ulitin
 */
public class IniSectionImpl extends ASTWrapperPsiElement implements IniSection {
    private static final Logger LOG = Logger.getInstance("#com.intellij.ini4idea.lang.psi.IniSectionImpl");

    public IniSectionImpl(@org.jetbrains.annotations.NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    public String getName() {
        PsiElement child = this.getFirstChild();
        assert child != null;
        if (child.getText() == null) {
            LOG.error("No child in section: " + this.getText() + " of class " + this.getClass());
        }
        return child.getText().replaceAll("\\[|\\]", "");
    }

    @Override
    public PsiReference getReference() {
        return new IniSectionReference(this);
    }

    @NotNull
    @Override
    public PsiElement setName(@NonNls @NotNull final String name) throws IncorrectOperationException {
        IniSectionImpl section = (IniSectionImpl) IniElementFactory.createSection(getProject(), name);
        ASTNode keyNode = getNode().findChildByType(IniTokenTypes.SECTION);
        ASTNode newKeyNode = section.getNode().findChildByType(IniTokenTypes.SECTION);
        LOG.assertTrue(newKeyNode != null);
        if (keyNode == null) {
            getNode().addChild(newKeyNode);
        } else {
            getNode().replaceChild(keyNode, newKeyNode);
        }
        return this;
    }

}

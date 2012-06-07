package ini4idea.lang.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexei Vasin
 */
public class IniKeyImpl extends ASTWrapperPsiElement {
    private static final Logger LOG = Logger.getInstance("#com.intellij.ini4idea.lang.psi.IniKeyImpl");

    public IniKeyImpl(@org.jetbrains.annotations.NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    public String getName() {
        return this.getText();
    }

    @Override
    public PsiReference getReference() {
        return new IniKeyReference(this);
    }
}

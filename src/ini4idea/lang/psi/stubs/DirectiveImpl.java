package ini4idea.lang.psi.stubs;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Alexei Vasin
 */
public class DirectiveImpl extends DirectiveStubElementImpl<DirectiveStub> implements Directive {
    private ASTNode myNode;

    public DirectiveImpl(@NotNull ASTNode node) {
        super(node);
        myNode = node;
    }

    public DirectiveImpl(@NotNull final DirectiveStub stub, @NotNull final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    @NotNull
    @Override
    public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
        return this.setName(name);
    }


    @Override
    public String getKey() {
        PsiElement child = this.getFirstChild();
        assert child != null;
        return child.getText();
//        return this.getName();
    }

    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Override
            public String getPresentableText() {
                if (myNode != null) {
                    return myNode.getText();
                } else {
                    return null;
                }
            }

            @Override
            public String getLocationString() {
                return null;
            }

            @Override
            public Icon getIcon(boolean open) {
                return null;
            }
        };
    }
}

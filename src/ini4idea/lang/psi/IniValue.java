package ini4idea.lang.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;

import javax.swing.*;

/**
 * @author Konstantin Ulitin
 */
public class IniValue extends ASTWrapperPsiElement {
    private ASTNode myNode;

    public IniValue(@org.jetbrains.annotations.NotNull ASTNode node) {
        super(node);
        myNode = node;
    }

    public String getName() {
        return myNode.getText();
    }

    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Override
            public String getPresentableText() {
                return myNode.getText();
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

package ini4idea.formatting;

import com.intellij.formatting.ASTBlock;
import com.intellij.formatting.Alignment;
import com.intellij.formatting.Indent;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexei Vasin
 */
public abstract class IniBlock implements ASTBlock {

    private ASTNode myNode;
    private Indent myIndent;

    public IniBlock(@NotNull final ASTNode node, @NotNull final Indent indent) {
        myNode = node;
        myIndent = indent;
    }

    @NotNull
    @Override
    public TextRange getTextRange() {
        return myNode.getTextRange();
    }

    @Override
    public Indent getIndent() {
        return myIndent;
    }

    @Override
    public Alignment getAlignment() {
        return null;
    }

    @Override
    public boolean isIncomplete() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public ASTNode getNode() {
        return myNode;
    }
}
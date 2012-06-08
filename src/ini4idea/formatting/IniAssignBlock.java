package ini4idea.formatting;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import ini4idea.lang.IniTokenTypes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexei Vasin
 */
public class IniAssignBlock extends IniBlock {
    public IniAssignBlock(@NotNull ASTNode node) {
        super(node, Indent.getNoneIndent());
    }

    @NotNull
    @Override
    public List<Block> getSubBlocks() {
        List<Block> result = new ArrayList<Block>();
        for (ASTNode node : getNode().getChildren(null)) {
            if (node.getElementType() != IniTokenTypes.WHITESPACE)// || node.getElementType() == IniTokenTypes.ASSIGN) {
            {
                result.add(new IniLeafBlock(node));
            }
        }
        return result;
    }

    @Override
    public Wrap getWrap() {
        //return Wrap.createWrap(WrapType.ALWAYS, false);
        return null;
    }

    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        return Spacing.createSpacing(1, 1, 0, false, 0);
    }

    @NotNull
    @Override
    public ChildAttributes getChildAttributes(int newChildIndex) {
        return new ChildAttributes(getIndent(), null);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}

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
public class IniFileBlock extends IniBlock {
    public IniFileBlock(ASTNode fileNode) {
        super(fileNode, Indent.getNoneIndent());
    }

    @NotNull
    @Override
    public List<Block> getSubBlocks() {
        final ArrayList<Block> blocks = new ArrayList<Block>();
        for (ASTNode node : getNode().getChildren(null)) {
            if (node.getElementType() == IniTokenTypes.SECTION) {
                blocks.add(new IniSectionBlock(node, Indent.getNoneIndent()));
            }
        }
        return blocks;
    }

    @Override
    public Wrap getWrap() {
        return Wrap.createWrap(WrapType.ALWAYS, false);
    }

    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        return Spacing.createSpacing(0, 0, 1, false, 0);
    }

    @NotNull
    @Override
    public ChildAttributes getChildAttributes(int newChildIndex) {
        return new ChildAttributes(Indent.getNoneIndent(), null);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}

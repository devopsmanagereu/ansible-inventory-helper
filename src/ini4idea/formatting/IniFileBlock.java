package ini4idea.formatting;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import ini4idea.lang.IniTokenTypes;
import ini4idea.lang.psi.stubs.DirectiveStubElementType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexei Vasin
 */
public class IniFileBlock extends IniBlock {
    public IniFileBlock(@NotNull ASTNode fileNode) {
        super(fileNode, Indent.getNoneIndent());
    }

    @NotNull
    @Override
    public List<Block> getSubBlocks() {
        final ArrayList<Block> blocks = new ArrayList<Block>();
        for (ASTNode node : getNode().getChildren(null)) {
            if (node.getElementType() == IniTokenTypes.SECTION || node.getElementType() == IniTokenTypes.COMMENT) {
                blocks.add(new IniSectionBlock(node, Indent.getNoneIndent()));
            } else if (node.getElementType() == IniTokenTypes.LVAL) {
                blocks.add(new IniLeafBlock(node));
            } else if (node.getElementType() instanceof DirectiveStubElementType) {
                blocks.add(new IniAssignBlock(node));
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
        IniBlock block1 = (IniBlock) child1;
        IniBlock block2 = (IniBlock) child2;

        if (block1.getNode().getElementType() == IniTokenTypes.COMMENT && block2.getNode().getElementType() == IniTokenTypes.SECTION) {
            return Spacing.createSpacing(0, 0, 2, false, 10);
        }
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

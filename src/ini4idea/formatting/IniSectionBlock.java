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
public class IniSectionBlock extends IniBlock {
    public IniSectionBlock(ASTNode node, Indent indent) {
        super(node, indent);
    }

    @NotNull
    @Override
    public List<Block> getSubBlocks() {
        List<Block> result = new ArrayList<Block>();
        for (ASTNode node : getNode().getChildren(null)) {
            if (node.getElementType() == IniTokenTypes.ASSIGN) {
                result.add(new IniAssignBlock(node));
            } else if (node.getElementType() != IniTokenTypes.STRING) {
                result.add(new IniLeafBlock(node));
            }

            /*if (node.getElementType() != IniTokenTypes.WHITESPACE && node.getElementType() != IniTokenTypes.EOL){
              result.add(new IniLeafBlock(node));
            }*/

        }

        int pos = 0;
        for (int i = result.size() - 1; i >= 0; i--) {
            IniBlock b = (IniBlock) result.get(i);

            if (b.getNode().getElementType() != IniTokenTypes.EOL) {
                pos = i;
                break;
            }
        }

        List<Block> subResult = result.subList(0, pos + 2);

        return subResult;
    }

    @Override
    public Wrap getWrap() {
        return Wrap.createWrap(WrapType.ALWAYS, false);
        //return null;
    }

    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        /*IniBlock block1 = (IniBlock)child1;
 IniBlock block2 = (IniBlock)child2;
 if ((block1.getNode().getElementType() == IniTokenTypes.COMMENT && block2.getNode().getElementType() == IniTokenTypes.SECTION))
   return Spacing.createSpacing(0, 0, 1, false, 0);*/
        //return Spacing.createSpacing(0, 0, 1, false, 0);
        return Spacing.createSpacing(0, 0, 0, false, 0);
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

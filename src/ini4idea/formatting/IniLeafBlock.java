package ini4idea.formatting;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Alexei Vasin
 */
public class IniLeafBlock extends IniBlock {
    public IniLeafBlock(@NotNull ASTNode node) {
        super(node, Indent.getAbsoluteNoneIndent());
    }

    @NotNull
    @Override
    public List<Block> getSubBlocks() {
        return Collections.emptyList();
    }

    @Override
    public Wrap getWrap() {
        return null;
    }

    @Override
    public Spacing getSpacing(Block child1, Block child2) {
//        return Spacing.createSpacing(0, 0, 0, false, 0);
        return null;
    }

    @NotNull
    @Override
    public ChildAttributes getChildAttributes(int newChildIndex) {
        return new ChildAttributes(Indent.getNoneIndent(), null);
//        return null;
    }
}

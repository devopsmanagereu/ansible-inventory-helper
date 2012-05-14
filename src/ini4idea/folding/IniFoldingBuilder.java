package ini4idea.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import ini4idea.lang.IniTokenTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Konstantin Ulitin
 */
public class IniFoldingBuilder implements FoldingBuilder {

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode node, @NotNull Document document) {
        ASTNode[] sections = node.getChildren(IniTokenTypes.SECTIONS);
        FoldingDescriptor[] descriptors = new FoldingDescriptor[sections.length];
        for (int i = 0; i < sections.length; i++) {
            ASTNode section = sections[i];
            //TODO don't understand why one symbol is added to folding region
            //TODO or exclude EOLs from Psi
            //TODO add multiline comments folding
//            if (section.getFirstChildNode() != null)
            descriptors[i] = new FoldingDescriptor(section, section.getTextRange().grown(-1));
/*
            if (section.getFirstChildNode() != null){
                descriptors[i]  = new FoldingDescriptor(section, section.getTextRange());
            }
*/
        }
        return descriptors;
    }

    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        try {
            return node.findChildByType(IniTokenTypes.SECTION).getText();
        } catch (NullPointerException e) {
            // TODO logger.error section doesn't contain header
            return null;
        }
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}

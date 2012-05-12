package ini4idea.structureView;

import com.intellij.ide.util.treeView.smartTree.ActionPresentation;
import com.intellij.ide.util.treeView.smartTree.ActionPresentationData;
import com.intellij.ide.util.treeView.smartTree.Filter;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.psi.PsiElement;
import com.intellij.util.PlatformIcons;
import ini4idea.lang.IniTokenTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexei Vasin
 */
public class IniSectionsFilter implements Filter {
    private static final String FILTER_NAME = "Show Directives in Sections";

    @Override
    public boolean isVisible(TreeElement treeNode) {
        if (treeNode instanceof IniStructureViewElement) {
            IniStructureViewElement element = (IniStructureViewElement) treeNode;
            PsiElement e = (PsiElement) element.getValue();

            if (e.getNode().getElementType() == IniTokenTypes.ASSIGN) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isReverted() {
        return true;
    }

    @NotNull
    @Override
    public ActionPresentation getPresentation() {
        return new ActionPresentationData(FILTER_NAME, null, PlatformIcons.PACKAGE_ICON);
        //return null
    }

    @NotNull
    @Override
    public String getName() {
        return FILTER_NAME;
    }
}

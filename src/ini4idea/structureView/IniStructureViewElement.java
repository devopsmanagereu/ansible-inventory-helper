package ini4idea.structureView;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.Iconable;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.PlatformIcons;
import ini4idea.lang.IniTokenTypes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexei Vasin
 */
public class IniStructureViewElement implements StructureViewTreeElement {
    protected PsiElement myElement;


    IniStructureViewElement(final PsiElement elem) {
        myElement = elem;
    }

    @Override
    public void navigate(boolean requestFocus) {
        ((Navigatable) myElement).navigate(requestFocus);
    }

    @Override
    public Object getValue() {
        return myElement;
    }

    @Override
    public boolean canNavigate() {
        return ((Navigatable) myElement).canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return ((Navigatable) myElement).canNavigateToSource();
    }

    public boolean isSection(PsiElement e) {
        return e.getNode().getElementType() == IniTokenTypes.SECTION;
    }

    public boolean isDirective(PsiElement e) {
        return e.getNode().getElementType() == IniTokenTypes.ASSIGN;
    }


    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Override
            public String getPresentableText() {
                if (myElement instanceof PsiFile) {
                    return ((PsiFile) myElement).getName();
                }
                if (isDirective(myElement)) {
                    return myElement.getFirstChild().getText();
                }
                if (isSection(myElement)) {
                    String sectionName = myElement.getFirstChild().getText().replaceAll("\\[|\\]", "");
                    return sectionName;
                }
                return null;
            }

            @Override
            public String getLocationString() {
                return null;
            }

            @Override
            public Icon getIcon(boolean open) {
                //
                if (isSection(myElement)) {
                    return PlatformIcons.ADD_ICON;
                } else if (isDirective(myElement)) {
                    return PlatformIcons.CLASS_ICON;
                } else {
                    return myElement.getIcon(Iconable.ICON_FLAG_OPEN);
                }
            }
        };
    }

    @Override
    public TreeElement[] getChildren() {
        final List<TreeElement> result = new ArrayList<TreeElement>();

        if (myElement instanceof PsiFile) {
            final PsiElement arr[] = myElement.getChildren();

            for (PsiElement elem : arr) {
                if (isSection(elem)) {
                    result.add(new IniStructureViewElement(elem));
                }
            }
        } else if (isSection(myElement)) {
            final PsiElement arr[] = myElement.getChildren();

            for (PsiElement elem : arr) {
                if (isDirective(elem)) {
                    result.add(new IniStructureViewElement(elem));
                }
            }
        }
        return (result.isEmpty()) ? EMPTY_ARRAY : result.toArray(new TreeElement[result.size()]);
    }
}

package ini4idea.structureView;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.TextEditorBasedStructureViewModel;
import com.intellij.ide.util.treeView.smartTree.Filter;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.psi.PsiElement;
import ini4idea.lang.IniTokenTypes;
import ini4idea.lang.psi.IniFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexei Vasin
 */
public class IniStructureViewModel extends TextEditorBasedStructureViewModel implements StructureViewModel.ExpandInfoProvider, StructureViewModel.ElementInfoProvider {
    @NotNull
    private final IniFile myIniFile;
    private Filter[] myFilters = new Filter[]{new IniSectionsFilter()};

    @NotNull
    public Sorter[] getSorters() {
        return mySorters;
    }

    public boolean shouldEnterElement(final Object element) {
        return true;
    }

    @Override
    protected boolean isSuitable(PsiElement element) {
        return true;
    }

    private Sorter[] mySorters = new Sorter[]{Sorter.ALPHA_SORTER};

    protected IniStructureViewModel(@NotNull IniFile psiFile) {
        super(psiFile);
        myIniFile = psiFile;
    }

    @NotNull
    @Override
    public StructureViewTreeElement getRoot() {
        return new IniStructureViewElement(myIniFile);
    }

    @NotNull
    @Override
    public Filter[] getFilters() {
        return myFilters;
    }

    @Override
    public boolean isAutoExpand(@NotNull StructureViewTreeElement element) {
        PsiElement e = (PsiElement) element.getValue();
        if (e.getNode().getElementType() == IniTokenTypes.SECTION) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isSmartExpand() {
        return true;
    }

    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(@NotNull StructureViewTreeElement element) {
        PsiElement e = (PsiElement) element.getValue();
        if (e.getNode().getElementType() == IniTokenTypes.ASSIGN) {
            return true;
        }
        return false;
    }
}

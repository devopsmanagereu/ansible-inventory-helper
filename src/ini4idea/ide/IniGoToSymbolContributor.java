package ini4idea.ide;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.ArrayUtil;
import ini4idea.lang.psi.DirectiveKeyIndex;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author Alexei Vasin
 */
public class IniGoToSymbolContributor implements ChooseByNameContributor {

    @NotNull
    @Override
    public String[] getNames(@NotNull Project project, boolean includeNonProjectItems) {
        final ArrayList<String> names = new ArrayList<String>();
        names.addAll(StubIndex.getInstance().getAllKeys(DirectiveKeyIndex.KEY, project));
        return ArrayUtil.toStringArray(names);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(@NotNull String name, String pattern, @NotNull Project project, boolean includeNonProjectItems) {
        final ArrayList<PsiElement> items = new ArrayList<PsiElement>();
        items.addAll(StubIndex.getInstance().get(DirectiveKeyIndex.KEY, name, project, GlobalSearchScope.allScope(project)));
        return items.toArray(new NavigationItem[items.size()]);
    }
}

package ini4idea.ide;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.util.ArrayUtil;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Alexei Vasin
 */
public class IniContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        Collection<String> result = new THashSet<String>();
        result.addAll(IniIndex.getSymbolNames(project));
        return ArrayUtil.toStringArray(result);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        Collection<NavigationItem> result = new THashSet<NavigationItem>();
        result.addAll(IniIndex.getItemsByName(name, project));
        return result.toArray(new NavigationItem[result.size()]);
    }
}

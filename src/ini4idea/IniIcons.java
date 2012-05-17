package ini4idea;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author Alexei Vasin
 */
public interface IniIcons {
    Icon directiveIcon = IconLoader.getIcon("/icons/directive.png");
    Icon sectionIcon = IconLoader.getIcon("/icons/list_of_directives.png");
}

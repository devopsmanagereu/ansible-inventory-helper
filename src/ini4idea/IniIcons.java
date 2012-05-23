package ini4idea;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author Alexei Vasin
 */
public interface IniIcons {
    Icon directiveIcon = IconLoader.getIcon("/icons/ini_directive.png");
    Icon sectionIcon = IconLoader.getIcon("/icons/ini_list_of_directives.png");
}

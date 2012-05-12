package ini4idea.lang;

import com.intellij.lang.refactoring.NamesValidator;
import com.intellij.openapi.project.Project;

/**
 * @author Alexei Vasin
 */
public class IniValidator implements NamesValidator {
    @Override
    public boolean isKeyword(String name, Project project) {
        return true;
    }

    @Override
    public boolean isIdentifier(String name, Project project) {
        return true;
    }
}

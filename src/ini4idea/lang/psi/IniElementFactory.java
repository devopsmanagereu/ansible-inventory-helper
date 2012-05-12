package ini4idea.lang.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import ini4idea.file.IniFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexei Vasin
 */
public class IniElementFactory {
    @NotNull
    public static IniSection createSection(@NotNull Project project, @NonNls @NotNull String name) {
        String text = "[" + name + "]";
        final IniFile dummy = createIniFile(project, text);
        final PsiElement childElement = dummy.getFirstChild();
        return (IniSection) childElement;
    }

    @NotNull
    public static IniFile createIniFile(@NotNull Project project, @NonNls @NotNull String text) {
        @NonNls String filename = "dummy." + IniFileType.INSTANCE.getDefaultExtension();
        return (IniFile) PsiFileFactory.getInstance(project)
                .createFileFromText(filename, IniFileType.INSTANCE, text);
    }
}

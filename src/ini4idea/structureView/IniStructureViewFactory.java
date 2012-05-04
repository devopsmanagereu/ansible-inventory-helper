package ini4idea.structureView;

import com.intellij.ide.structureView.StructureView;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import ini4idea.lang.psi.IniFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexei Vasin
 */
public class IniStructureViewFactory implements PsiStructureViewFactory {
    @Override
    public StructureViewBuilder getStructureViewBuilder(final PsiFile psiFile) {
        return new StructureViewBuilder() {
            @NotNull
            @Override
            public StructureView createStructureView(FileEditor fileEditor, Project project) {
                return new IniStructureViewComponent(project, (IniFile) psiFile, fileEditor);
            }
        };
    }
}

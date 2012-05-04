package ini4idea.structureView;

import com.intellij.ide.structureView.newStructureView.StructureViewComponent;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import ini4idea.lang.psi.IniFile;

/**
 * @author Alexei Vasin
 */
public class IniStructureViewComponent extends StructureViewComponent {
    private final IniFile myIniFile;

    public IniStructureViewComponent(Project project, IniFile file, FileEditor editor) {
        super(editor, new IniStructureViewModel(file), project);
        myIniFile = file;
    }
}

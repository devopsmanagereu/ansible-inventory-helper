package ini4idea;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.IStubFileElementType;
import ini4idea.lang.psi.stubs.DirectiveStubElementType;

/**
 * @author Alexei Vasin
 */
public interface IniElementTypes {
    IniLanguage LANG = IniLanguage.INSTANCE;

    IFileElementType FILE = new IStubFileElementType(LANG);
    IStubElementType DIRECTIVE = new DirectiveStubElementType();
}

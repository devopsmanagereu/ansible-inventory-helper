package ini4idea.lang.parser;

import com.intellij.psi.tree.IElementType;
import ini4idea.file.IniFileType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Konstantin Ulitin
 */
public class IniElementType extends IElementType {
    public IniElementType(@NotNull String debugName) {
        super(debugName, IniFileType.INSTANCE.getLanguage());
    }
}

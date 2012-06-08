package ini4idea.lang.psi.stubs;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import ini4idea.IniLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexei Vasin
 */
public class DirectiveStubElementImpl<T extends StubElement> extends StubBasedPsiElementBase<T> {
    public DirectiveStubElementImpl(@NotNull final T stub, @NotNull IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DirectiveStubElementImpl(@NotNull final ASTNode node) {
        super(node);
    }

    @NotNull
    public Language getLanguage() {
        return IniLanguage.INSTANCE;
    }
}
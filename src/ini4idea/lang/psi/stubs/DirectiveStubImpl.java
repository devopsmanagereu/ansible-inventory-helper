package ini4idea.lang.psi.stubs;

import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import ini4idea.IniElementTypes;

/**
 * @author Alexei Vasin
 */
public class DirectiveStubImpl extends StubBase<Directive> implements DirectiveStub {
    private final String myKey;

    public DirectiveStubImpl(final StubElement parent, final String key) {
        super(parent, IniElementTypes.DIRECTIVE);
        myKey = key;
    }

    @Override
    public String getKey() {
        return myKey;
    }
}

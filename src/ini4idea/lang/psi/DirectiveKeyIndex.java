package ini4idea.lang.psi;

import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndexKey;
import ini4idea.lang.psi.stubs.Directive;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexei Vasin
 */
public class DirectiveKeyIndex extends StringStubIndexExtension<Directive> {
    public static final StubIndexKey<String, Directive> KEY = StubIndexKey.createIndexKey("directive.index");

//    private static final DirectiveKeyIndex ourInstance = new DirectiveKeyIndex();

/*
    public static DirectiveKeyIndex getInstance() {
        return ourInstance;
    }
*/

    @NotNull
    public StubIndexKey<String, Directive> getKey() {
        return KEY;
    }
}
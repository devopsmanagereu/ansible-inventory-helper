package ini4idea.lang.psi.stubs;

import com.intellij.psi.stubs.StubElement;

/**
 * @author Alexei Vasin
 */
public interface DirectiveStub extends StubElement<Directive> {
    String getKey();
}

package ini4idea.lang.psi.stubs;

import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.StubBasedPsiElement;

/**
 * @author Alexei Vasin
 */
public interface Directive extends PsiNamedElement, StubBasedPsiElement<DirectiveStub>, NavigatablePsiElement {
    String getName();

    String getKey();
}

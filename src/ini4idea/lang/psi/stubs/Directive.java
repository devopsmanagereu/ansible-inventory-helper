package ini4idea.lang.psi.stubs;

import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.annotations.Nullable;

/**
 * @author Alexei Vasin
 */
public interface Directive extends PsiNamedElement, StubBasedPsiElement<DirectiveStub>, NavigatablePsiElement {
    @Nullable
    String getName();

    String getKey();
}

package ini4idea.lang.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.util.PsiElementFilter;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexei Vasin
 */
public class IniSimpleReference implements PsiReference {
//    private final Logger LOG = Logger.getInstance("#com.intellij.ini4idea.lang.psi");

    private IniSectionImpl mySection;

    public IniSimpleReference(IniSectionImpl section) {
        mySection = section;
    }

    @Override
    public PsiElement getElement() {
        return mySection.getFirstChild();
    }

    @Override
    public TextRange getRangeInElement() {
        return new TextRange(0, mySection.getTextLength());
    }


    public IniSectionImpl resolve() {
        final PsiElementProcessor.FindFilteredElement<IniSectionImpl> processor = new PsiElementProcessor.FindFilteredElement<IniSectionImpl>(new PsiElementFilter() {
            public boolean isAccepted(PsiElement element) {
                if (element instanceof IniSectionImpl) {
                    String elementName = ((IniSectionImpl) element).getName();//element.getFirstChild().getText().replaceAll("\\[|\\]", "");
                    String sectionName = mySection.getName();//mySection.getFirstChild().getText().replaceAll("\\[|\\]", "");

                    if (elementName.equals(sectionName)) {
                        return false;
                    }

                    if (sectionName.contains(":")) {
                        String inheritedSectionName = sectionName.substring(sectionName.indexOf(":") + 1);
//                        inherited.replaceAll("\\s*", "");
                        inheritedSectionName = inheritedSectionName.substring(inheritedSectionName.lastIndexOf(" ") + 1);

                        if (inheritedSectionName.equals(elementName)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        PsiTreeUtil.processElements(mySection.getContainingFile(), processor);
/*
        if (processor.getFoundElement() instanceof IniSectionImpl) {
            return processor.getFoundElement();
        }
*/
        if (processor.getFoundElement() != null) {
            return processor.getFoundElement();
        }
        return null;
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        return mySection.getText();
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        String newName = mySection.getName().substring(0, mySection.getName().indexOf(":") + 1);
        newName += " " + newElementName;
        mySection.setName(newName);
        return mySection;
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        return mySection;
    }

    @Override
    public boolean isReferenceTo(@NotNull PsiElement element) {
        IniSectionImpl resolved = resolve();
        if (resolved != null) {
            return resolved.equals(element);
        }
        return false;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public boolean isSoft() {
        return false;
    }
}

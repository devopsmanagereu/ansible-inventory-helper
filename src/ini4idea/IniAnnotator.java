package ini4idea;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.hash.HashSet;
import ini4idea.lang.IniTokenTypes;
import ini4idea.lang.psi.IniFile;
import ini4idea.lang.psi.IniKeyImpl;
import ini4idea.lang.psi.IniSectionImpl;
import ini4idea.lang.psi.stubs.DirectiveImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author Alexei Vasin
 */
public class IniAnnotator implements Annotator {


    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof IniFile) {
            Set<String> sectionSet = new HashSet<String>();
            Annotation annotation;
            PsiElement children[] = element.getChildren();

            for (PsiElement elem : children) {
                if (elem instanceof IniSectionImpl) {
                    PsiElement child = elem.getFirstChild();
                    //assert child != null;
                    if (child != null) {
                        String sectionKey = child.getText();

                        if (sectionSet.contains(sectionKey)) {
                            annotation = holder.createWarningAnnotation(child, "Duplicate section");
                            annotation.registerFix(new DuplicateSectionIntention(child));
                        } else {
                            sectionSet.add(sectionKey);
                        }

                        PsiElement sectionChildren[] = elem.getChildren();
                        Set<String> s = new HashSet<String>();

                        for (final PsiElement sectionElement : sectionChildren) {
                            if (sectionElement instanceof IniKeyImpl || sectionElement instanceof DirectiveImpl) {
                                PsiElement e = sectionElement.getFirstChild();

                                assert e != null;
                                String key = e.getText();

                                if (s.contains(key)) {
                                    annotation = holder.createWarningAnnotation(e, "Duplicate key in the same section");

                                    annotation.registerFix(new DuplicateDirectiveIntention(sectionElement));


                                } else {
                                    s.add(key);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static class DuplicateDirectiveIntention implements IntentionAction {

        private PsiElement myElement;

        private DuplicateDirectiveIntention(PsiElement myElement) {
            this.myElement = myElement;
        }

        @NotNull
        @Override
        public String getText() {
            return "Remove Duplicate Directive";
        }

        @NotNull
        @Override
        public String getFamilyName() {
            return getText();
        }

        @Override
        public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
            return true;
        }

        @Override
        public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
            TextRange textRange;
            if (myElement.getNode().getElementType() == IniTokenTypes.SECTION) {
                textRange = myElement.getParent().getTextRange();
            } else {
                textRange = myElement.getTextRange();
            }
            if (textRange != null) {
                Document document = PsiDocumentManager.getInstance(project).getDocument(myElement.getContainingFile());
                document.deleteString(textRange.getStartOffset(), textRange.getEndOffset());
            }
        }

        @Override
        public boolean startInWriteAction() {
            return true;
        }
    }

    private static class DuplicateSectionIntention extends DuplicateDirectiveIntention {

        private DuplicateSectionIntention(PsiElement myElement) {
            super(myElement);
        }

        @NotNull
        @Override
        public String getText() {
            return "Remove Duplicate Section";
        }

    }

}

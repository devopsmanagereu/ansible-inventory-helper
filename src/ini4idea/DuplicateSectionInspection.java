package ini4idea;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.SmartList;
import com.intellij.util.containers.hash.HashSet;
import ini4idea.lang.psi.IniFile;
import ini4idea.lang.psi.IniSectionImpl;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static com.intellij.codeInspection.ProblemHighlightType.GENERIC_ERROR_OR_WARNING;

/**
 * @author Alexei Vasin
 */
public class DuplicateSectionInspection extends IniSuppressableInspectionBase {
    private static final Logger LOG = Logger.getInstance("#ini4idea.DuplicateSectionInspection");

    @Override
    public ProblemDescriptor[] checkFile(@NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
        if (!(file instanceof IniFile)) return null;

        final List<ProblemDescriptor> descriptors = new SmartList<ProblemDescriptor>();
        Set<String> sectionSet = new HashSet<String>();
        PsiElement children[] = file.getChildren();

        for (PsiElement elem : children) {
            if (elem instanceof IniSectionImpl) {
                PsiElement child = elem.getFirstChild();
                assert child != null;
                String sectionKey = child.getText();

                if (sectionSet.contains(sectionKey)) {
                    TextRange textRange = child.getTextRange();
                    if (textRange != null) {
                        descriptors.add(manager.createProblemDescriptor(child, textRange, "Duplicate Section", GENERIC_ERROR_OR_WARNING, true, RemoveDuplicateSectionFix.INSTANCE));
                    }

                } else {
                    sectionSet.add(sectionKey);
                }
            }
        }

        return descriptors.toArray(new ProblemDescriptor[descriptors.size()]);
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return IniBundle.message("ini.duplicate.section.inspection.display.name");
    }

    @NotNull
    @Override
    public String getShortName() {
        return "DuplicateSectionInFile";
    }

    private static class RemoveDuplicateSectionFix implements LocalQuickFix {
        private static final RemoveDuplicateSectionFix INSTANCE = new RemoveDuplicateSectionFix();

        @NotNull
        public String getName() {
            return "Remove Duplicate Section";
        }

        @NotNull
        public String getFamilyName() {
            return getName();
        }

        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiElement element = descriptor.getPsiElement();
            TextRange textRange = element.getParent().getTextRange();
            if (textRange != null) {
                Document document = PsiDocumentManager.getInstance(project).getDocument(element.getContainingFile());
                //TextRange docRange = textRange.shiftRight(element.getTextRange().getStartOffset());
                document.deleteString(textRange.getStartOffset(), textRange.getEndOffset());
            }
        }
    }

}

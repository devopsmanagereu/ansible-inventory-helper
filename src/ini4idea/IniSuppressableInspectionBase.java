package ini4idea;

import com.intellij.codeInsight.CodeInsightUtilBase;
import com.intellij.codeInspection.CustomSuppressableInspectionTool;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.SuppressIntentionAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import ini4idea.lang.psi.IniFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexei Vasin
 */
public abstract class IniSuppressableInspectionBase extends LocalInspectionTool implements CustomSuppressableInspectionTool {
    private static final Logger LOG = Logger.getInstance("#com.intellij.lang.properties.PropertySuppressableInspectionBase");

    @NotNull
    public String getGroupDisplayName() {
        return IniBundle.message("ini.files.inspection.group.display.name");
    }

    public SuppressIntentionAction[] getSuppressActions(final PsiElement element) {
        return new SuppressIntentionAction[]{new SuppressForFile(getShortName())};
    }

    public boolean isSuppressedFor(PsiElement element) {
        return false;
    }

    private static class SuppressForFile extends SuppressIntentionAction {
        private final String shortName;

        public SuppressForFile(String shortName) {
            this.shortName = shortName;
        }

        @NotNull
        public String getText() {
            return IniBundle.message("duplicate.section.suppress.for.file");
        }

        @NotNull
        public String getFamilyName() {
            return IniBundle.message("duplicate.section.suppress.for.file");
        }

        public boolean isAvailable(@NotNull final Project project, final Editor editor, @NotNull final PsiElement element) {
            return element.isValid() && element.getContainingFile() instanceof IniFile;
        }

        public void invoke(@NotNull final Project project, final Editor editor, @NotNull final PsiElement element) throws IncorrectOperationException {
            final PsiFile file = element.getContainingFile();
            if (!CodeInsightUtilBase.prepareFileForWrite(file)) return;

            @NonNls final Document doc = PsiDocumentManager.getInstance(project).getDocument(file);
            LOG.assertTrue(doc != null, file);

            doc.insertString(0, "# suppress inspection \"" +
                    shortName +
                    "\" for whole file\n");
        }
    }
}

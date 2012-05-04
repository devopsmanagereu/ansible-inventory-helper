package ini4idea.formatting;

import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexei Vasin
 */
public class IniFormattingModelBuilder implements FormattingModelBuilder {
    @NotNull
    @Override
    public FormattingModel createModel(PsiElement psiElement, CodeStyleSettings settings) {
        ASTNode node = psiElement.getNode();
        assert node != null;
        PsiFile containingFile = psiElement.getContainingFile();
        ASTNode fileNode = containingFile.getNode();
        assert fileNode != null;
        IniBlock block = new IniFileBlock(fileNode);
        return FormattingModelProvider.createFormattingModelForPsiFile(containingFile, block, settings);
    }

    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}

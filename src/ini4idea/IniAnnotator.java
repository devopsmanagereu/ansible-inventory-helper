package ini4idea;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.hash.HashSet;
import ini4idea.lang.psi.IniAssign;
import ini4idea.lang.psi.IniFile;
import ini4idea.lang.psi.IniSection;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author Alexei Vasin
 */
public class IniAnnotator implements Annotator {


    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof IniFile) {
            Set<String> sectionSet = new HashSet<String>();

            PsiElement children[] = element.getChildren();

            for (PsiElement elem : children) {
                if (elem instanceof IniSection) {
                    String sectionKey = elem.getFirstChild().getText();

                    if (sectionSet.contains(sectionKey)) {
                        holder.createWarningAnnotation(elem.getFirstChild(), "Duplicate section");
                    } else {
                        sectionSet.add(sectionKey);
                    }

                    PsiElement sectionChildren[] = elem.getChildren();
                    Set<String> s = new HashSet<String>();

                    for (PsiElement sectionElement : sectionChildren) {
                        if (sectionElement instanceof IniAssign) {
                            PsiElement e = sectionElement.getFirstChild();

                            String key = e.getText();

                            if (s.contains(key)) {
                                holder.createWarningAnnotation(e, "Duplicate key in the same section");
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

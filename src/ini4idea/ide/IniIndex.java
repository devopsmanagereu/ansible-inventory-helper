package ini4idea.ide;

import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Consumer;
import com.intellij.util.indexing.*;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import ini4idea.file.IniFileType;
import ini4idea.lang.IniTokenTypes;
import ini4idea.lang.psi.IniFile;
import ini4idea.lang.psi.IniValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexei Vasin
 */
public class IniIndex extends ScalarIndexExtension<String> {
    private static final Logger LOG = Logger.getInstance("#ini4idea.ide.IniIndex");

    private static final int INDEX_VERSION = 1;

    public static final ID<String, Void> NAME = ID.create("IniIndex");
    private final EnumeratorStringDescriptor myKeyDescriptor = new EnumeratorStringDescriptor();
    private final FileBasedIndex.InputFilter myInputFilter = new FileBasedIndex.InputFilter() {
        @Override
        public boolean acceptInput(@NotNull VirtualFile file) {
            return file.getFileType() == IniFileType.INSTANCE;
        }
    };

    @NotNull
    @Override
    public ID<String, Void> getName() {
        return NAME;
    }

    public static String getName(@NotNull PsiElement element) {
        PsiElement child = element.getFirstChild();
        assert child != null;
        String name = child.getText();
        if (name == null) {
            LOG.error("Element name shouldn't be null");
        } else {
            return name;
        }
        return "";
    }

    @NotNull
    @Override
    public DataIndexer<String, Void, FileContent> getIndexer() {
        return new DataIndexer<String, Void, FileContent>() {
            @NotNull
            @Override
            public Map<String, Void> map(@NotNull FileContent inputData) {
                String className;
                Map<String, Void> result = new HashMap<String, Void>();
                try {
                    className = inputData.getContentAsText().toString();
                    String a[] = className.split("\n");
                    for (String s : a) {
                        if (s.contains("=")) {
                            result.put(s.substring(0, s.indexOf("=")), null);
                        }
                    }
                } catch (Exception e) {
                    // ignore
                }
                return result;
            }
        };
    }

    public static void process(@NotNull IniFile file, @NotNull final Consumer<PsiElement> consumer) {
        processChildrenRecursively(file, consumer);
    }


    public static boolean isKeyNode(@NotNull PsiElement element) {
        return (element instanceof IniValue && element.getNode().getElementType() == IniTokenTypes.LVAL);
    }

    public static void processChildrenRecursively(@NotNull PsiElement element, @NotNull final Consumer<PsiElement> consumer) {
        PsiElement[] children = element.getChildren();
        if (children.length == 0) return;
        for (PsiElement child : children) {
            if (isKeyNode(child)) {
                consumer.consume(child);
            }
            processChildrenRecursively(child, consumer);
        }

    }

    @NotNull
    public static Collection<NavigationItem> getItemsByName(@NotNull final String name, @NotNull Project project) {
        Collection<VirtualFile> files = FileBasedIndex.getInstance().getContainingFiles(NAME, name, GlobalSearchScope.projectScope(project));
        final Collection<NavigationItem> result = new ArrayList<NavigationItem>();
        for (VirtualFile vFile : files) {
            PsiFile file = PsiManager.getInstance(project).findFile(vFile);
            assert file != null;
            if (!(file.getFileType() == IniFileType.INSTANCE)) {
                continue;
            }
            process((IniFile) file, new Consumer<PsiElement>() {
                @Override
                public void consume(@NotNull PsiElement element) {
                    if (getName(element).contains(name)) {
                        result.add((NavigationItem) element);
                    }
                }
            });
        }
        return result;
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return myKeyDescriptor;
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return myInputFilter;
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

    @Override
    public int getVersion() {
        return INDEX_VERSION;
    }

    @NotNull
    public static Collection<String> getSymbolNames(@NotNull Project project) {
        Collection<String> result = FileBasedIndex.getInstance().getAllKeys(NAME, project);
        return result;
    }
}

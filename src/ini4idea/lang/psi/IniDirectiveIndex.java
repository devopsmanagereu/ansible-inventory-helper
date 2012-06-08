package ini4idea.lang.psi;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.indexing.*;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import ini4idea.file.IniFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexei Vasin
 */
public class IniDirectiveIndex extends ScalarIndexExtension<String> {
    private static final Logger LOG = Logger.getInstance("#ini4idea.lang.psi.IniDirectiveIndex");
    @NonNls
    public static final ID<String, Void> NAME = ID.create("IniDirectiveIndex");
    private final EnumeratorStringDescriptor myKeyDescriptor = new EnumeratorStringDescriptor();
    private final MyInputFilter myInputFilter = new MyInputFilter();
    private final MyDataIndexer myDataIndexer = new MyDataIndexer();

    @NotNull
    @Override
    public ID<String, Void> getName() {
        return NAME;
    }

    @NotNull
    @Override
    public DataIndexer<String, Void, FileContent> getIndexer() {
        return myDataIndexer;
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
        return 1;
    }

    private static class MyDataIndexer implements DataIndexer<String, Void, FileContent> {
        @Override
        @NotNull
        public Map<String, Void> map(@NotNull final FileContent inputData) {
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
                LOG.error(e.toString());
            }
            return result;
/*
            if (className != null) {
                return Collections.singletonMap(className, null);
            }
*/
//            return Collections.emptyMap();
        }
    }

    private static class MyInputFilter implements FileBasedIndex.InputFilter {
        @Override
        public boolean acceptInput(@NotNull final VirtualFile file) {
            return file.getFileType() == IniFileType.INSTANCE;
        }
    }

/*
    public static Collection<NavigationItem> getItemsByName(final String name, Project project) {
        Collection<VirtualFile> files = FileBasedIndex.getInstance().getContainingFiles(NAME, name, GlobalSearchScope.projectScope(project));
        final Collection<NavigationItem> result = new ArrayList<NavigationItem>();
        for (VirtualFile vFile : files) {
            PsiFile file = PsiManager.getInstance(project).findFile(vFile);
            if (!(file instanceof IniFile)) {
                continue;
            }
            process((IniFile)file, new Consumer<PsiElement>() {
                @Override
                public void consume(PsiElement element) {
                    if (name.equals(getName(element))) {
                        result.add((NavigationItem) element);
                    }                     }
            }, true);
        }
        return result;
    }
*/

}

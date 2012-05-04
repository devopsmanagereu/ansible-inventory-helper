package ini4idea.file;

import com.intellij.openapi.fileTypes.ExtensionFileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author Konstantin Ulitin
 */
public class IniFileTypeFactory extends FileTypeFactory {

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        //consumer.consume(IniFileType.INSTANCE, IniFileType.DEFAULT_EXTENSION);
        //new ExtensionFileNameMatcher(HaxeFileType.DEFAULT_EXTENSION)
        consumer.consume(IniFileType.INSTANCE, new ExtensionFileNameMatcher("ini"));/* {
      @NotNull
      @Override
      public String getPresentableString() {
        return "ini";
      }
    });*/
    }
}

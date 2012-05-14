package ini4idea.highlighter;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.psi.tree.IElementType;
import ini4idea.lang.IniTokenTypes;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;

/**
 * @author Alexei Vasin
 */
public class IniColorSettingsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] ATTRS;

    static {
        final Map<IElementType, TextAttributesKey> m = IniSyntaxHighlighter.getTextAttributesMap();
        ATTRS = new AttributesDescriptor[]{
                new AttributesDescriptor("Directive Key", m.get(IniTokenTypes.KEY_CHARACTERS)),
                new AttributesDescriptor("Directive Value", m.get(IniTokenTypes.VALUE_CHARACTERS)),
                new AttributesDescriptor("Section", m.get(IniTokenTypes.SECTION)),
                new AttributesDescriptor("Comment", m.get(IniTokenTypes.COMMENT)),
                new AttributesDescriptor("Equal", m.get(IniTokenTypes.EQUAL))
        };
    }

    @NotNull
    public String getDisplayName() {
        //return OptionsBundle.message("properties.options.display.name");
        return "Ini";
    }

    public Icon getIcon() {
//    return PropertiesFileType.FILE_ICON;
        return null;
    }

    @NotNull
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRS;
    }

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    public SyntaxHighlighter getHighlighter() {
        return new IniSyntaxHighlighter();
    }

    @NotNull
    public String getDemoText() {
        return "# Comment on keys and values\n" +
                "include_path = \".:/usr/local/lib/php\"\n" +
                "[SECTION]\n" +
                ";Comment\n"
                ;
    }

    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }
}

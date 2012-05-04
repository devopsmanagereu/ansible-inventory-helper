package com.jetbrains.ini4idea;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import ini4idea.lang.IniTokenTypes;
import ini4idea.lang.lexer.IniLexer;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.intellij.psi.impl.source.tree.TreeUtil.getTokenText;
import static junit.framework.Assert.assertTrue;

/**
 * @author Alexei Vasin
 */
public class IniLexerTest {
    public String getSubPath() {
        return "/plugins/ini4idea/test/com/jetbrains/ini4idea/testData/";
    }

    public void testOneFile(String testFileName, IElementType token) {
        IniLexer lexer = new IniLexer();

        String testPath = PathManager.getHomePath() + getSubPath() + testFileName;

        lexer.start(getFileText(testPath));
        String result = "";
        while (true) {
            IElementType tokenType = lexer.getTokenType();
            if (tokenType == null) {
                break;
            }
            if (tokenType != TokenType.NEW_LINE_INDENT && tokenType != TokenType.WHITE_SPACE) {
                assertTrue(tokenType == token);
            }

            String tokenText = getTokenText(lexer);
            String tokenTypeName = tokenType.toString();
            String line = tokenTypeName + " ('" + tokenText + "')\n";
            result += line;
            lexer.advance();
        }
    }

    @Test
    public void testComments() {
        testOneFile("zendcomment.ini", IniTokenTypes.COMMENT);
        testOneFile("ezcomment.ini", IniTokenTypes.COMMENT);
    }

    @Test
    public void testSections() {
        testOneFile("section.ini", IniTokenTypes.SECTION);
    }

    @Test
    public void testString() {
        testOneFile("string.ini", IniTokenTypes.STRING);
    }

    public static String getFileText(final String filePath) {
        try {
            return FileUtil.loadFile(new File(filePath));
        } catch (IOException e) {
            System.out.println(filePath);
            throw new RuntimeException(e);
        }
    }
}

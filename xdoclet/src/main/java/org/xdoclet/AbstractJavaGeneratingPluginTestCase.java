package org.xdoclet;

import java.io.*;

import com.thoughtworks.qdox.junit.APITestCase;
import org.generama.MetadataProvider;
import org.generama.astunit.ASTTestCase;
import org.generama.tests.AbstractPluginTestCase;
import antlr.RecognitionException;
import antlr.TokenStreamException;

/**
 * Baseclass for testing generation of Java sources. Uses QDox'
 * APITestCase internally to compare equality of Java Sources.
 *
 * TODO move to Generama, this will be needed for RDBMSTableMetadataProvider too.
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public abstract class AbstractJavaGeneratingPluginTestCase extends AbstractPluginTestCase {

    protected final void compare(Reader expected, Reader actual) throws RecognitionException, TokenStreamException, IOException {
        char[] expectedChars = toCharArray(expected);
        char[] actualChars = toCharArray(actual);

        APITestCase.assertEquals(new CharArrayReader(expectedChars), new CharArrayReader(actualChars));
        ASTTestCase.assertEquals(new CharArrayReader(expectedChars), new CharArrayReader(actualChars));
    }

    private char[] toCharArray(Reader reader) throws IOException {
        CharArrayWriter expectedMemory = new CharArrayWriter();
        char[] buffer = new char[1024];
        int read;
        while( (read = reader.read(buffer)) != -1 ) {
            expectedMemory.write(buffer, 0, read);
        }
        return expectedMemory.toCharArray();
    }

    protected MetadataProvider createMetadataProvider() throws Exception {
        return new QDoxMetadataProvider(getTestSource());
    }

    protected Reader getTestSource() throws IOException {
        return null;
    }
}

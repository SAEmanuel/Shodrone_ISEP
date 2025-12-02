package domain.valueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DSLTest {
//
//    // ---- Constructor and factory method tests ----
//
//    /**
//     * Test that a valid file name is accepted and stored correctly.
//     */
//    @Test
//    void testValidFileNameCreation() {
//        DSL dsl = new DSL("input.txt");
//        assertEquals("input.txt", dsl.fileName());
//    }
//
//    /**
//     * Test that the factory method valueOf creates an equivalent DSL object.
//     */
//    @Test
//    void testValueOfCreatesEquivalentObject() {
//        DSL dsl1 = new DSL("file.pdf");
//        DSL dsl2 = DSL.valueOf("file.pdf");
//        assertEquals(dsl1, dsl2);
//    }
//
//    /**
//     * Test that null or empty file names throw an exception.
//     */
//    @Test
//    void testEmptyFileNameThrowsException() {
//        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new DSL(null));
//        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new DSL(""));
//
//        assertTrue(exception1.getMessage().contains("File name should neither be null nor empty"));
//        assertTrue(exception2.getMessage().contains("File name should neither be null nor empty"));
//    }
//
//    /**
//     * Test that invalid file names (missing extension or invalid chars) throw an exception.
//     */
//    @Test
//    void testInvalidFileNameFormatThrowsException() {
//        String[] invalidFileNames = {"file", "file.", ".txt", "file@name.txt", "file name", "file/name.txt"};
//        for (String invalid : invalidFileNames) {
//            Exception e = assertThrows(IllegalArgumentException.class, () -> new DSL(invalid));
//            assertTrue(e.getMessage().contains("Invalid file name format"));
//        }
//    }
//
//    // ---- Behavior tests ----
//
//    /**
//     * Test the extension method correctly returns the file extension.
//     */
//    @Test
//    void testExtensionReturnsCorrectExtension() {
//        DSL dsl = new DSL("document.PDF");
//        assertEquals("pdf", dsl.extension());
//
//        DSL dsl2 = new DSL("archive.tar.gz");
//        assertEquals("gz", dsl2.extension());
//
//        DSL dsl3 = new DSL("noextension.txt");
//        assertEquals("txt", dsl3.extension());
//    }
//
//    /**
//     * Test that toString returns the original file name string.
//     */
//    @Test
//    void testToStringReturnsFileName() {
//        DSL dsl = new DSL("report.docx");
//        assertEquals("report.docx", dsl.toString());
//    }
//
//    /**
//     * Test equals and hashCode methods for value equality.
//     */
//    @Test
//    void testEqualsAndHashCode() {
//        DSL dsl1 = new DSL("sample.txt");
//        DSL dsl2 = new DSL("sample.txt");
//        DSL dsl3 = new DSL("other.txt");
//
//        assertEquals(dsl1, dsl2);
//        assertNotEquals(dsl1, dsl3);
//
//        assertEquals(dsl1.hashCode(), dsl2.hashCode());
//        assertNotEquals(dsl1.hashCode(), dsl3.hashCode());
//    }
}

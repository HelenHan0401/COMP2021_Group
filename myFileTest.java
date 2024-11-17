package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class myFileTest {

    private Document testDocument;

    @BeforeEach
    public void setUp() {
        // Initialize a Document instance for testing
        testDocument = new Document("testFile.txt", "txt",  "This is a test document.");
    }

    @Test
    public void testGetFullName() {
        assertEquals("testFile.txt", testDocument.getFullName());
    }

    @Test
    public void testSetName() {
        testDocument.setName("newFile.txt");
        assertEquals("newFile.txt", testDocument.getFullName());
    }

    @Test
    public void testIsDocument() {
        assertTrue(testDocument.isDocument());
    }


    @Test
    public void testGetType() {
        assertEquals("txt", testDocument.getType());
    }

    @Test
    public void testGetContent() {
        assertEquals("This is a test document.", testDocument.getContent());
    }
}
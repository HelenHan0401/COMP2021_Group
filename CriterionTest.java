package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CriterionTest {

    private TestCriterion testCriterion;
    private myFile testFile;

    @BeforeEach
    public void setUp() {
        // Initialize the test criterion with a sample name and attribute
        testCriterion = new TestCriterion("testCriterion", "type");

        // Create a test file
        testFile = new myFile("testFile.txt", "test", 60, "Content of the file.") {
            @Override
            void setName(String newName) {

            }

            @Override
            String getFullName() {
                return "";
            }

            @Override
            boolean isDocument() {
                return false;
            }

            @Override
            int getFileSize() {
                return 0;
            }

            @Override
            String getType() {
                return "";
            }

            @Override
            String getContent() {
                return "";
            }
        }; // Type is "test"
    }

    @Test
    public void testToString() {
        String expected = "Criterion: testCriterion for attribute type";
        assertEquals(expected, testCriterion.ToString());
    }


    @Test
    public void testEvaluateFalse() {
        testFile = new myFile("testFile.txt", "pdf", 60, "Content of the file.") {
            @Override
            void setName(String newName) {

            }

            @Override
            String getFullName() {
                return "";
            }

            @Override
            boolean isDocument() {
                return false;
            }

            @Override
            int getFileSize() {
                return 0;
            }

            @Override
            String getType() {
                return "";
            }

            @Override
            String getContent() {
                return "";
            }
        }; // Type is now "pdf"
        assertFalse(testCriterion.Evaluate(testFile)); // Should return false since type is not "test"
    }

    @Test
    public void testGetAttributeName() {
        assertEquals("type", testCriterion.getAttributeName("testCriterion"));
    }
}
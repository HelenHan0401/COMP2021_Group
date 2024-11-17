package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NegationCriteriaTest {

    private NegationCriteria negationCriteria;
    private SimpleCriterion simpleCriterion;
    private myFile testFile;

    @BeforeEach
    public void setUp() {
        // Create a SimpleCriterion that evaluates based on a hypothetical attribute
        simpleCriterion = new SimpleCriterion("sizeCriteria", "size", ">", "50");
        // Create a NegationCriteria based on the SimpleCriterion
        negationCriteria = new NegationCriteria("notSizeCriteria", simpleCriterion);
        // Create a test file to evaluate against
        testFile = new myFile("testFile.txt", "txt", 30, "Content of the file.") {
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
        }; // Size less than 50
    }

    @Test
    public void testToString() {
        String expected = "! (size > 50)";
        assertEquals(expected, negationCriteria.ToString());
    }

    @Test
    public void testEvaluateTrue() {
        assertTrue(negationCriteria.Evaluate(testFile));
    }

    @Test
    public void testEvaluateFalse() {
        // Change the test file to satisfy the simple criterion
        testFile = new myFile("testFile.txt", "txt", 60, "Content of the file.") {
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
        };
    }
}
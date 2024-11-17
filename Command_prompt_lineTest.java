package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class CommandPromptLineTest {

    private CVFS cvfs;

    @BeforeEach
    public void setUp() {
        cvfs = new CVFS();
    }

    @Test
    public void testNewDisk() {
        // Simulate input for creating a new disk
        String input = "newDisk 1000\nquit\n"; // Include quit to end the loop
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check if the disk was created
        assertNotNull(cvfs.getCurDisk());
        assertEquals(1000, cvfs.getCurDisk().getMaxDiskSize());

        System.setIn(stdin); // Restore original System.in
    }

    // Test for invalid disk size (negative number)
    @Test
    public void testInvalidDiskSizeNegative() {
        String input = "newDisk -500\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertNull(cvfs.getCurDisk());

        System.setIn(stdin);
    }

    // Test for invalid disk size (non-integer)
    @Test
    public void testInvalidDiskSizeNonInteger() {
        String input = "newDisk abc\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertNull(cvfs.getCurDisk());

        System.setIn(stdin);
    }

    @Test
    public void testNewDoc() {
        cvfs.newDisk(1000); // Create a disk first
        String input = "newDoc testDoc txt Document content here.\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check if the document was created
        assertEquals(1, cvfs.getCurDir().getFileList().size());
        assertEquals("testDoc", cvfs.getCurDir().getFileList().get(0).getFullName());

        System.setIn(stdin);
    }


    // Test for invalid file type in newDoc
    @Test
    public void testNewDocWithInvalidFileType() {
        cvfs.newDisk(1000);
        String input = "newDoc invalidDoc.txt invalidType Content\nquit\n"; // Invalid file type
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals(0, cvfs.getCurDir().getFileList().size());

        System.setIn(stdin);
    }

    // Test for valid and invalid file names
    @Test
    public void testNewDocWithVariousFileNames() {
        cvfs.newDisk(1000);
        String input = "newDoc validDoc txt Content\nnewDoc invalid*Name txt Content\nquit\n"; // Mixed valid and invalid
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals(1, cvfs.getCurDir().getFileList().size());
        assertEquals("validDoc", cvfs.getCurDir().getFileList().get(0).getFullName());

        System.setIn(stdin);
    }

    // Test for renaming a file
    @Test
    public void testRenameFile() {
        cvfs.newDisk(1000);
        cvfs.getCurDir().newDoc(cvfs, "original", "txt", "Content");

        String input = "rename original newName\nlist\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals(1, cvfs.getCurDir().getFileList().size());
        assertEquals("newName", cvfs.getCurDir().getFileList().get(0).getFullName());

        System.setIn(stdin);
    }

    // Test for renaming a file to an invalid name
    @Test
    public void testRenameFileToInvalidName() {
        cvfs.newDisk(1000);
        cvfs.getCurDir().newDoc(cvfs, "validName.txt", "txt", "Content");

        String input = "rename validName.txt invalid*Name.txt\nquit\n"; // Invalid name
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals(1, cvfs.getCurDir().getFileList().size());

        System.setIn(stdin);
    }


    @Test
    public void testNewDir() {
        cvfs.newDisk(1000); // Create a disk first
        String input = "newDir newFolder\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check if the directory was created
        assertEquals(1, cvfs.getCurDir().getFileList().size());
        assertEquals("newFolder", cvfs.getCurDir().getFileList().get(0).getFullName());
        System.setIn(stdin);
    }


    @Test
    public void testDelete() {
        cvfs.newDisk(1000);
        cvfs.getCurDir().newDoc(cvfs, "docToDelete.txt", "txt", "Content");

        String input = "delete docToDelete.txt\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check if the document was deleted
        assertEquals(0, cvfs.getCurDir().getFileList().size());

        System.setIn(stdin);
    }

    @Test
    public void testRList() {
        cvfs.newDisk(1000);
        cvfs.getCurDir().newDir(cvfs, "subDir");

        String input = "rList\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // The actual output would be printed to the console, we can't assert on it directly here

        System.setIn(stdin);
    }


    @Test
    public void testDeleteNonExistentFile() {
        cvfs.newDisk(1000);
        String input = "delete nonExistentFile.txt\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check no files are deleted
        assertEquals(0, cvfs.getCurDir().getFileList().size());

        System.setIn(stdin);
    }

    @Test
    public void testRename() {
        cvfs.newDisk(1000);
        cvfs.getCurDir().newDoc(cvfs, "oldName", "txt", "Content");

        String input = "rename oldName newName\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals("newName", cvfs.getCurDir().getFileList().get(0).getFullName());

        System.setIn(stdin);
    }

    @Test
    public void testSaveAndLoadFunctionality() {
        cvfs.newDisk(1000);
        cvfs.getCurDir().newDoc(cvfs, "docToSave", "txt", "Content to save");

        String savePath = "testSavePath"; // Mock save path
        String input = "save " + savePath + "\nload " + savePath + "\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check that files are still there after saving and loading
        assertEquals(1, cvfs.getCurDir().getFileList().size());
        assertEquals("docToSave", cvfs.getCurDir().getFileList().get(0).getFullName());

        System.setIn(stdin);
    }

    @Test
    public void testRenameNonExistentFile() {
        cvfs.newDisk(1000);
        String input = "rename nonExistent.txt newName.txt\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check that no files were renamed
        assertEquals(0, cvfs.getCurDir().getFileList().size());

        System.setIn(stdin);
    }

    @Test
    public void testChangeDir() {
        cvfs.newDisk(1000);
        cvfs.getCurDir().newDir(cvfs, "subDir");

        String input = "changeDir subDir\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals("subDir", cvfs.getCurDir().getFullName());

        System.setIn(stdin);
    }

    // Test for changeDir with non-existing directory
    @Test
    public void testChangeDirToNonExisting() {
        cvfs.newDisk(1000);
        String input = "changeDir nonExistingDir\nquit\n"; // Non-existing directory
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals("root", cvfs.getCurDir().getFullName()); // Assuming "root" is the default

        System.setIn(stdin);
    }

    @Test
    public void testList() {
        cvfs.newDisk(1000);
        cvfs.getCurDir().newDoc(cvfs, "file1.txt", "txt", "Content");
        cvfs.getCurDir().newDoc(cvfs, "file2.txt", "txt", "Content");

        String input = "list\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // The actual output would be printed to the console, we can't assert on it directly here

        System.setIn(stdin);
    }

    @Test
    public void testInvalidCommands() {
        String input = "invalidCommand\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // No changes expected; just ensure it runs without errors
        System.setIn(stdin);
    }

    @Test
    public void testNewSimpleCri() {
        cvfs.newDisk(1000);
        cvfs.getCurDir().newDoc(cvfs, "simpleDoc txt", "txt", "Simple content");

        String input = "search simpleDoc\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Validate that the search was successful
        // Check console output if captured, or assert state as needed

        System.setIn(stdin);
    }

    // Test for criteria with invalid names
    @Test
    public void testNewSimpleCriteriaWithInvalidName() {
        String input = "newDisk 1000\nnewSimpleCri invalidCriName attrName contains value\nquit\n"; // Invalid criteria name
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Ensure criteria isn't added
        // Check criteria size if applicable

        System.setIn(stdin);
    }

    // Test for criteria with valid name
    @Test
    public void testNewSimpleCriteriaWithValidName() {
        String input = "newDisk 1000\nnewSimpleCri validCri attrName contains value\nquit\n"; // Valid criteria
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check that criteria was added successfully
        // Assert criteria size or content if applicable

        System.setIn(stdin);
    }

    @Test
    public void testNegationSearch() {
        cvfs.newDisk(1000);
        cvfs.getCurDir().newDoc(cvfs, "existingDoc.txt", "txt", "Content here");

        String input = "search nonexistentDoc\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Ensure the search completes without finding the file
        // Handle output validation if required

        System.setIn(stdin);
    }

    // Test for invalid negation criteria (non-existing criteria)
    @Test
    public void testInvalidNewNegationNonExisting() {
        String input = "newDisk 1000\nnewNegation negCri2 nonExistingCri\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check that the negation was not added due to non-existing criteria
        // assertFalse(cvfs.criteriaExists("negCri2"));

        System.setIn(stdin);
    }

    // Test for invalid negation criteria name
    @Test
    public void testInvalidNewNegationName() {
        String input = "newDisk 1000\nnewSimpleCri validCri attr1 contains value\nnewNegation invalid*Name validCri\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check that the negation was not added due to invalid name
        // assertFalse(cvfs.criteriaExists("invalid*Name"));

        System.setIn(stdin);
    }

    // Test for valid binary criteria creation
    @Test
    public void testValidNewBinaryCri() {
        String input = "newDisk 1000\nnewSimpleCri criA attrA contains valueA\nnewSimpleCri criB attrB contains valueB\nnewBinaryCri binCri1 criA && criB\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check that the binary criteria was added successfully
        // Adjust the assertion based on how you check for added criteria
        // assertTrue(cvfs.criteriaExists("binCri1"));

        System.setIn(stdin);
    }

    // Test for invalid binary criteria (non-existing criteria)
    @Test
    public void testInvalidNewBinaryCriNonExisting() {
        String input = "newDisk 1000\nnewBinaryCri binCri2 nonExistingCri1 && nonExistingCri2\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check that the binary criteria was not added due to non-existing criteria
        // assertFalse(cvfs.criteriaExists("binCri2"));

        System.setIn(stdin);
    }

    // Test for invalid binary criteria name
    @Test
    public void testInvalidNewBinaryCriName() {
        String input = "newDisk 1000\nnewSimpleCri validCri1 attr1 contains value1\nnewSimpleCri validCri2 attr2 contains value2\nnewBinaryCri invalid*Name validCri1 || validCri2\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check that the binary criteria was not added due to invalid name
        // assertFalse(cvfs.criteriaExists("invalid*Name"));

        System.setIn(stdin);
    }

    // Test for invalid logical operator in binary criteria
    @Test
    public void testInvalidBinaryCriLogicOp() {
        String input = "newDisk 1000\nnewSimpleCri cri1 attr1 contains value1\nnewSimpleCri cri2 attr2 contains value2\nnewBinaryCri binCri3 cri1 invalidOp cri2\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check that the binary criteria was not added due to invalid logical operator
        // assertFalse(cvfs.criteriaExists("binCri3"));

        System.setIn(stdin);
    }

    @Test
    public void testQuit() {
        String input = "quit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // No assertion needed, just ensure it can quit without exception
        System.setIn(stdin);
    }

    @Test
    public void testCheckIsValidOp() {
        assertFalse(Command_prompt_line.checkIsValidOp("invalidOp", "."));
    }

    // Test for checkIsValidFileType
    @Test
    public void testCheckIsValidFileType() {
        assertTrue(Command_prompt_line.checkIsValidFileType("txt"));
        assertFalse(Command_prompt_line.checkIsValidFileType("pdf"));
        assertFalse(Command_prompt_line.checkIsValidFileType("invalidType"));
    }

    // Test for checkIsValidFileName
    @Test
    public void testCheckIsValidFileName() {
        assertFalse(Command_prompt_line.checkIsValidFileName("invalidFileName*?.txt")); // Invalid characters
        assertFalse(Command_prompt_line.checkIsValidFileName("tooLongFileName" + "a".repeat(250) + ".txt")); // Too long
    }

    // Test for isValidLogicOp
    @Test
    public void testIsValidLogicOp() {
        assertTrue(Command_prompt_line.isValidLogicOp("&&"));
        assertTrue(Command_prompt_line.isValidLogicOp("||"));
        assertFalse(Command_prompt_line.isValidLogicOp("NOT")); // Assuming NOT is not supported
        assertFalse(Command_prompt_line.isValidLogicOp("invalidOp"));
    }

    // Test for valid rSearch with existing criteria
    @Test
    public void testValidRSearchWithExistingCriteria() {
        String input = "newDisk 1000\nnewSimpleCri searchCri attr1 contains value\nnewDoc doc1.txt txt Content\nrSearch searchCri\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Check that the rSearch worked correctly
        // Adjust the assertion based on expected output or state after search
        // For example: assertTrue(cvfs.getSearchResult().contains("doc1.txt"));

        System.setIn(stdin);
    }

    // Test for rSearch with non-existing criteria
    @Test
    public void testRSearchWithNonExistingCriteria() {
        String input = "newDisk 1000\nrSearch nonExistingCri\nquit\n"; // Non-existing criteria
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Ensure appropriate response when criteria does not exist
        // e.g., assertEquals("Error: Invalid criteria name.", output);

        System.setIn(stdin);
    }

    // Test for rSearch without any criteria
    @Test
    public void testRSearchWithoutCriteria() {
        String input = "newDisk 1000\nrSearch \nquit\n"; // Empty criteria name
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Ensure appropriate response when no criteria is provided
        // e.g., assertEquals("Error: Invalid command.", output);

        System.setIn(stdin);
    }

    // Test for checkIsValidOp with valid operators for "name"
    @Test
    public void testCheckIsValidOpName() {
        assertTrue(Command_prompt_line.checkIsValidOp("name", "contains"));
        assertFalse(Command_prompt_line.checkIsValidOp("name", "equals"));
    }

    // Test for checkIsValidOp with valid operators for "type"
    @Test
    public void testCheckIsValidOpType() {
        assertTrue(Command_prompt_line.checkIsValidOp("type", "equals"));
        assertFalse(Command_prompt_line.checkIsValidOp("type", "contains"));
    }

    // Test for checkIsValidOp with valid operators for "size"
    @Test
    public void testCheckIsValidOpSize() {
        assertTrue(Command_prompt_line.checkIsValidOp("size", ">"));
        assertFalse(Command_prompt_line.checkIsValidOp("size", "<="));
        assertFalse(Command_prompt_line.checkIsValidOp("size", "contains"));
    }

    // Test for checkIsValidOp with invalid attribute
    @Test
    public void testCheckIsValidOpInvalidAttribute() {
        assertFalse(Command_prompt_line.checkIsValidOp("invalidAttr", "contains"));
    }

    // Test for checkIsValidOp with invalid operator
    @Test
    public void testCheckIsValidOpInvalidOperator() {
        assertFalse(Command_prompt_line.checkIsValidOp("size", "invalidOp"));
    }

    // Test for creating a directory with an existing name
    @Test
    public void testNewDirWithExistingName() {
        String input = "newDisk 1000\nnewDir existDir\nnewDir existDir\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals(1, cvfs.getCurDir().getFileList().size());  // Ensure only one directory is created
//        assertEquals("existDir", cvfs.getCurDir().getFileList().get(0));

        System.setIn(stdin);
    }


    // Test for valid newDir creation
    @Test
    public void testValidNewDir() {
        String input = "newDisk 1000\nnewDir testDir\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals(1, cvfs.getCurDir().getFileList().size());

        System.setIn(stdin);
    }

    // Test for newDir with an empty name
    @Test
    public void testNewDirWithEmptyName() {
        String input = "newDisk 1000\nnewDir \nquit\n"; // Empty directory name
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Expect an error message
        // Check that the directory was not created
        assertEquals(0, cvfs.getCurDir().getFileList().size());
        System.setIn(stdin);
    }

    // Test for changeDir to an existing subdirectory
    @Test
    public void testChangeDirToExistingSubDir() {
        String input = "newDisk 1000\nnewDir mySubDir\nchangeDir mySubDir\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals("mySubDir", cvfs.getCurDir().getFullName());

        System.setIn(stdin);
    }

    // Test for changeDir to a non-existing subdirectory
    @Test
    public void testChangeDirToNonExistingSubDir() {
        String input = "newDisk 1000\nchangeDir nonExistingDir\nquit\n"; // Non-existing directory
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals("root", cvfs.getCurDir().getFullName()); // Assuming "root" is the default

        System.setIn(stdin);
    }

    // Test for changeDir to parent directory
    @Test
    public void testChangeDirToParent() {
        String input = "newDisk 1000\nnewDir subDir\nchangeDir subDir\nchangeDir ..\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals("root", cvfs.getCurDir().getFullName()); // Verify back to root

        System.setIn(stdin);
    }

    // Test for changeDir with empty name
    @Test
    public void testChangeDirWithEmptyName() {
        String input = "newDisk 1000\nchangeDir \nquit\n"; // Empty name
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals("root", cvfs.getCurDir().getFullName()); // Should remain in root

        System.setIn(stdin);
    }

    // Test for changeDir with special characters
    @Test
    public void testChangeDirWithSpecialCharacters() {
        String input = "newDisk 1000\nnewDir specialDir\nchangeDir specialDir\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals("specialDir", cvfs.getCurDir().getFullName());

        System.setIn(stdin);
    }

    // Test for creating a deep directory structure
    @Test
    public void testDeepDirectoryCreation() {
        String input = "newDisk 1000\nnewDir level1\nchangeDir level1\nnewDir level2\nchangeDir level2\nnewDir level3\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals("level2", cvfs.getCurDir().getFullName());

        System.setIn(stdin);
    }

    // Test for changeDir back to root after deep navigation
    @Test
    public void testChangeDirBackToRoot() {
        String input = "newDisk 1000\nnewDir level1\nchangeDir level1\nnewDir level2\nchangeDir ..\nchangeDir ..\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        assertEquals("root", cvfs.getCurDir().getFullName()); // Verify back to root

        System.setIn(stdin);
    }
}

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

        //assertEquals("subDir", cvfs.getCurDir().getCurrentDirectoryName());

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
    public void testNewSimpleCriteria() {
        cvfs.newDisk(1000);
        cvfs.getCurDir().newDoc(cvfs, "simpleDoc.txt", "txt", "Simple content");

        String input = "search simpleDoc\nquit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // Validate that the search was successful
        // Check console output if captured, or assert state as needed

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
        assertFalse(Command_prompt_line.checkIsValidOp("invalidOp","."));
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

}

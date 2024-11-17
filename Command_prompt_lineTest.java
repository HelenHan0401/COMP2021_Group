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
    public void testQuit() {
        String input = "quit\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Command_prompt_line.run(cvfs);

        // No assertion needed, just ensure it can quit without exception
        System.setIn(stdin);
    }
}
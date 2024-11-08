package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayList;
import java.util.List;

class Directory extends File {
    String name;
    List<Document> files;
    ArrayList<File> fileList = new ArrayList<File>(); //arrayList of all files in the directory

    Directory(String dirName) {
        this.name = dirName;
        CVFS.curDisk.updateDiskSize(40);//update diskSize, !!A check of diskSize before creating files are needed
    }


    void newDoc(String name, String type, String content) {//REQ(2)
        if (CVFS.curDisk.getDiskSize() + 40 + content.length() * 2 > CVFS.curDisk.getMaxDiskSize()) {
            System.out.println("Insufficient space. Creation failed.");
            return;
        }

        Document doc = new Document(name, type, content);//create new doc
        this.fileList.add(doc);//add reference to new doc into fileList
    }

    void newDir(String name) {//REQ(3)
        if (CVFS.curDisk.getDiskSize() + 40 > CVFS.curDisk.getMaxDiskSize()) {
            System.out.println("Insufficient space. Creation failed.");
            return;
        }

        Directory Dir = new Directory(name);//create new dir
        this.fileList.add(Dir);//add reference to new dir into fileList
    }

    void delete(String fileName) {//REQ(4)
        for (File i : this.fileList) {
            if (i.name.equals(fileName)) {
                this.fileList.remove(fileList.indexOf(i));
                CVFS.curDisk.updateDiskSize(-4);
                return;
            }
        }

        System.out.println("Error: File not found in current directory. Deletion failed.");
    }

    void rename(String oldFileName, String newFileName) {//REQ(5)
        for (File i : this.fileList) {
            if (i.name.equals(oldFileName)) {
                i.name = newFileName;
                return;
            }
        }

        System.out.println("Error: File not found in current directory. Rename failed.");
    }

    @Override
    String getFullName() {
        return this.name;
    }

    @Override
    boolean isDocument() {//REQ(10)
        return false;
    }

    void list() {//REQ(7)
        for (File i : this.fileList) {
            System.out.println(i.getFullName());
        }
    }

    void rList() {//REQ(8)

    }

    List<Document> getFiles() { // REC(13)
        return files;
    }

}

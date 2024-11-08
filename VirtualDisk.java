package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.*;

class VirtualDisk implements Serializable {
    Directory root;

    VirtualDisk() {
        this.root = new Directory("root");
    }

    Directory getRoot() {
        return root;
    }

//-------------------------------------------------------------------
//
//    void search(String criName) {
//        List<Document> matchingFiles = new ArrayList<>();
//        searchHelper(root, criName, matchingFiles);
//        int totalSize = matchingFiles.stream().mapToInt(Document::getSize).sum();
//        matchingFiles.forEach(System.out::println);
//    }

    //-------------------------------------------------------------------
// REC(15)
    public void save(String path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        }
    }

    //-------------------------------------------------------------------
// REC(16)
    public static VirtualDisk load(String path) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (VirtualDisk) ois.readObject();
        }
    }
}
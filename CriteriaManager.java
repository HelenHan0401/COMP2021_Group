package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CriteriaManager {
    private List<SimpleCriterion> criteriaList;
    private hk.edu.polyu.comp.comp2021.cvfs.model.VirtualDisk VirtualDisk;

    public CriteriaManager() {
        this.criteriaList = new ArrayList<>();
        this.criteriaMap = new HashMap<>();
    }

    public void addCriteria(SimpleCriterion criteria) {
        criteriaList.add(criteria);
    }

    //---------------------------------------------------------------
    //REC(11)
    private Map<String, CompositeCriteria> criteriaMap;

    public void addNegationCriteria(String criName, String existingCriName) {

        CompositeCriteria existingCriteria = criteriaMap.get(existingCriName);
        if (existingCriteria != null) {
            criteriaMap.put(criName, new NegationCriteria(criName, existingCriteria));
        } else {
            throw new IllegalArgumentException("Criteria " + existingCriName + " does not exist.");
        }
    }

    public void addBinaryCriteria(String criName, String criName1, String logicOp, String criName2) {
        CompositeCriteria criteria1 = criteriaMap.get(criName1);
        CompositeCriteria criteria2 = criteriaMap.get(criName2);
        if (criteria1 != null && criteria2 != null) {
            criteriaMap.put(criName, new BinaryCriteria(criName, criteria1, logicOp, criteria2));
        } else {
            throw new IllegalArgumentException("One or both criteria do not exist.");
        }
    }

    //-------------------------------------------------------------------
    // REC(12)
    public void printAllCriteria() {
        for (SimpleCriterion criteria : criteriaList) {
            System.out.println(criteria);
        }
    }

    //-------------------------------------------------------------------
    // REC(13)
    public void search(String criName, File directory) {
        CompositeCriteria criteria = criteriaMap.get(criName);
        if (criteria == null) {
            throw new IllegalArgumentException("Criteria " + criName + " does not exist.");
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("No files found in the directory.");
            return;
        }
        long totalSize = 0;
        int fileCount = 0;
        for (File file : files) {
            if (file.isFile() && criteria.matches(file)) {
                System.out.println(file.getAbsolutePath());
                totalSize += file.length();
                fileCount++;
            }
        }
        System.out.println("Total files: " + fileCount);
        System.out.println("Total size: " + totalSize + " bytes");
    }

    //---------------------------------------------------------------
//REC(14)
    public void rSearch(String criName, File directory) {
        CompositeCriteria criteria = criteriaMap.get(criName);
        if (criteria == null) {
            throw new IllegalArgumentException("Criteria " + criName + " does not exist.");
        }
        List<File> matchingFiles = new ArrayList<>();
        searchFiles(directory, criteria, matchingFiles);
        long totalSize = 0;
        for (File file : matchingFiles) {
            totalSize += file.length();
            System.out.println(file.getAbsolutePath());
        }
        System.out.println("Total files: " + matchingFiles.size());
        System.out.println("Total size: " + totalSize + " bytes");
    }


    private void searchFiles(File directory, CompositeCriteria criteria, List<File> matchingFiles) {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    searchFiles(file, criteria, matchingFiles);
                } else {
                    if (criteria.matches(file)) {
                        matchingFiles.add(file);
                    }
                }
            }
        }
    }

    //-------------------------------------------------------------------
// REC(15)
    public void save(String path) throws IOException {
        VirtualDisk.save(path);
    }

    //-------------------------------------------------------------------
//REC(16)
    public void load(String path) throws IOException, ClassNotFoundException {
        this.VirtualDisk = VirtualDisk.load(path);
    }
}
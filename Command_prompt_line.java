//for user input

package hk.edu.polyu.comp.comp2021.cvfs.model;//read input from use

import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.util.Scanner;

public class Command_prompt_line {
    public static void choose(CVFS cvfs) throws IOException {

        Scanner scanner = new Scanner(System.in);
        String choice = "0";
        while (!choice.equals("quit")) {
            choice = scanner.next();
            switch (choice) {
                //task1
                case "newDisk":
                    int size = scanner.nextInt();
                    ;
                    scanner.nextLine(); //remove \n in the scanner
                    cvfs.newDisk(size);
                    break;
                //task2
                case "newDoc":
                    String docName = scanner.next();
                    if (!checkIsValidFileName(docName)) {
                        System.out.println("The file name must be within 10 characters and only letter and digit");
                        scanner.nextLine();
                        break;
                    }

                    String docType = scanner.next();
                    if (!checkIsValidFileType(docType)) {
                        System.out.println("The file types must be txt, java, html and css");
                        scanner.nextLine();
                        break;
                    }
                    String docContent = scanner.nextLine().trim();
                    cvfs.curDir.newDoc(docName, docType, docContent);

                    break;
                //task 3
                case "newDir":
                    String dirName = scanner.next();
                    if (!checkIsValidFileName(dirName)) {
                        System.out.println("The file name must be within 10 characters and only letter and digit");
                        break;
                    }
                    scanner.nextLine();
                    cvfs.curDir.newDir(dirName);

                    break;
                //task 4
                case "delete":
                    String fileName = scanner.next();
                    scanner.nextLine();

                    break;
                //task 5
                case "rename":
                    String oldFileName = scanner.next();
                    String newFileName = scanner.next();
                    if (!checkIsValidFileName(newFileName)) {
                        System.out.println("The file name must be within 10 characters and only letter and digit");
                        break;
                    }
                    scanner.nextLine();

                    break;
                //task 6
                case "changeDir":
                    String newdir = scanner.next();
                    scanner.nextLine();

                    break;
                //task 7
                case "list":
                    cvfs.curDir.list();

                    break;
                //task 8
                case "rList":

                    break;
                //task 9
                case "newSimpleCri":
                    String newSimpleCri = scanner.next();
                    String criName = scanner.next();
                    String attrName = scanner.next();
                    String op = scanner.next();
                    String val = scanner.next();
                    new SimpleCriterion(criName, attrName, op, val);
                    scanner.nextLine();
                    break;
                //task 10
                case "IsDocument":
                    String isDocument = scanner.next();
                    scanner.nextLine();
                    break;
                //task 11
                case "newNegation":
                    String criName1 = scanner.next();
                    String criName2 = scanner.next();
                    CriteriaManager negation_manager = new CriteriaManager();
                    negation_manager.addNegationCriteria(criName1,criName2);
                    scanner.nextLine();
                    break;
                case "newBinaryCri":
                    String binCriName1 = scanner.next();
                    String binCriName2 = scanner.next();
                    String logicOp = scanner.next();
                    String binCriName3 = scanner.next();
                    CriteriaManager binary_manager = new CriteriaManager();
                    binary_manager.addBinaryCriteria(binCriName1,binCriName2,logicOp,binCriName3);
                    scanner.nextLine();
                    break;
                //task 12
                case "printAllCriteria":
                    String printAllCriteria = scanner.next();
                    CriteriaManager print = new CriteriaManager();
                    print.printAllCriteria();
                    scanner.nextLine();
                    break;
                //task 13
                case "search":
                    String searchName = scanner.next();
                    java.io.File workingDirectory = new java.io.File(searchName) {
                        String getFullName() {
                            return "";
                        }

                        boolean isDocument() {
                            return false;
                        }
                    };
                    CriteriaManager search = new CriteriaManager();
                    search.search(searchName, workingDirectory);
                    scanner.nextLine();

                    break;
                //task 14
                case "rSearch":
                    String rRriName = scanner.next();
                    CriteriaManager rSearch = new CriteriaManager();
                    rSearch.rSearch(rRriName, new File("."));
                    scanner.nextLine();
                    break;
                //task 15
                case "save":
                    String savePath = scanner.next();
                    CriteriaManager save = new CriteriaManager();
                    save.save(savePath);
                    scanner.nextLine();

                    break;
                //task 16
                case "load":
                    String loadPath = scanner.next();
                    CriteriaManager load = new CriteriaManager();
                    load.save(loadPath);
                    scanner.nextLine();
                    scanner.nextLine();

                    break;
                //task 17
                case "quit":
                    String quit = scanner.next();
                    String command = scanner.nextLine();
                    if (command.equalsIgnoreCase("quit")) {
                        System.out.println("Terminating the system...");
                        System.exit(0);
                    }
                default:
                    if (!choice.equals("quit")) {
                        System.out.println("Wrong input!");
                    }
                    scanner.nextLine();
            }
        }
    }

    public static boolean checkIsValidFileName(String name) {
        if (name.length() > 10) return false; //file name cant exist 10 characters

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isDigit(c) || Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIsValidFileType(String type) {
        if (type.equals("txt") || type.equals("java") || type.equals("css") || type.equals("html")) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        CVFS cvfs = new CVFS();
//        choose(cvfs);

    }
}
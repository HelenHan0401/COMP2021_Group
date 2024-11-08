package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.File;

public class BinaryCriteria extends CompositeCriteria {//REC(11)-BinaryCriteria
    //    private CompositeCriteria criteria1;
//    private CompositeCriteria criteria2;
//    String criteria1;
//    String criteria2;
//    private String logicOp;
//
//    public BinaryCriteria(String criName, String criteria1, String logicOp, String criteria2) {
//        super(criName);
//        this.criteria1 = criteria1;
//        this.criteria2 = criteria2;
//        this.logicOp = logicOp;
//    }
    private CompositeCriteria criteria1;
    private CompositeCriteria criteria2;
    private String logicOp;

    public BinaryCriteria(String criName, CompositeCriteria criteria1, String logicOp, CompositeCriteria criteria2) {
        super(criName);
        this.criteria1 = criteria1;
        this.criteria2 = criteria2;
        this.logicOp = logicOp;
    }

    @Override
    public String toString() {
        return "(" + criteria1.toString() + " " + logicOp + " " + criteria2.toString() + ")";
    }

    @Override
    public boolean matches(File file) {
        return false;
    }
}

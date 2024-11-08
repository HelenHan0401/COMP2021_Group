package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.File;

public class NegationCriteria extends CompositeCriteria { //REC(11)-NegationCriteria
    //    private CompositeCriteria criteria;
//    String criteria;
//    public NegationCriteria(String criName, String criteria) {
//        super(criName);
//        this.criteria = criteria;
//    }
    private CompositeCriteria criteria;

    public NegationCriteria(String criName, CompositeCriteria criteria) {
        super(criName);
        this.criteria = criteria;
    }


    @Override
    public String toString() {
        return "NOT (" + criteria.toString() + ")";
    }

    @Override
    public boolean matches(File file) {
        return false;
    }
}

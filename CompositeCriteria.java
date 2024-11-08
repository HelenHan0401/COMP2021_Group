package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.File;

public abstract class CompositeCriteria {
    protected String criName;

    public CompositeCriteria(String criName) {
        this.criName = criName;
    }

    public abstract String toString();

    //
//    public boolean matches(File file) {
//        if(!file.equals(criName)) {
//            return false;
//        }
//        return true;
//    }
    public abstract boolean matches(File file);
}


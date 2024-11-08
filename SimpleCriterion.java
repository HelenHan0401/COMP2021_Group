package hk.edu.polyu.comp.comp2021.cvfs.model;

class SimpleCriterion { // REC(9)
    String criName;
    String attrName;
    String op;
    String val;
    int intVal;

    public SimpleCriterion(String criName, String attrName, String op, String val) {
        this.criName = criName;
        this.attrName = attrName;
        this.op = op;
        this.val = val;
        if (op.matches("[><>=<=!=]")) {
            this.intVal = Integer.parseInt(val);
        }
    }

    public boolean evaluate(File file) {
        if (attrName.equals("name")) {
            return op.equals("contains") && file.name.contains(val);
        } else if (attrName.equals("type")) {
            return op.equals("equals") && file.type.equals(val);
        } else if (attrName.equals("size")) {
            int fileSize = file.size;
            switch (op) {
                case ">":
                    return fileSize > intVal;
                case "<":
                    return fileSize < intVal;
                case ">=":
                    return fileSize >= intVal;
                case "<=":
                    return fileSize <= intVal;
                case "==":
                    return fileSize == intVal;
                case "!=":
                    return fileSize != intVal;
            }
        }
        return false;
    }
}
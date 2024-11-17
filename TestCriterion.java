package hk.edu.polyu.comp.comp2021.cvfs.model;

class TestCriterion extends Criterion {
    private String attributeName;

    public TestCriterion(String criName, String attributeName) {
        this.criName = criName;
        this.attributeName = attributeName;
    }

    @Override
    public String ToString() {
        return "Criterion: " + criName + " for attribute " + attributeName;
    }

    @Override
    boolean Evaluate(myFile file) {
        // This is a mock implementation, as Evaluation logic should be defined as per actual requirements.
        return "test".equals(file.getType()); // Example condition
    }

    @Override
    public String getAttributeName(String criName) {
        return this.attributeName; // Overriding to return the attribute name for testing
    }
}
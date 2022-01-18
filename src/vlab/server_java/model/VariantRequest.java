package vlab.server_java.model;

public class VariantRequest {

    private int complexity;

    public VariantRequest() {}

    public VariantRequest(int complexity) {
        this.complexity = complexity;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

}

package vlab.server_java.model;

import vlab.server_java.common.Classifier;

import java.util.List;

public class VariantSolution {

    private List<DataPoint> dataPoints;
    private Classifier cls;

    public VariantSolution() {}

    public VariantSolution(List<DataPoint> dataPoints, Classifier cls) {
        this.dataPoints = dataPoints;
        this.cls = cls;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public Classifier getCls() {
        return cls;
    }

    public void setCls(Classifier cls) {
        this.cls = cls;
    }

}

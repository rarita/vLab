package vlab.server_java.model;

import vlab.server_java.StaticUtilsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VariantResponse {

    private static final int DEFAULT_DIM_LIMIT = 20;
    private static final Random random = StaticUtilsProvider.getRandomInstance();

    private List<DataPoint> dataPoints;
    private DataPoint pointToClassify;
    private int k;

    public static VariantResponse random(int qty) {

        final List<DataPoint> pts = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            pts.add(DataPoint.random(DEFAULT_DIM_LIMIT));
        }

        final DataPoint pointToClassify = DataPoint.random(DEFAULT_DIM_LIMIT);
        pointToClassify.setCls(null);
        int k = random.nextInt(qty / 2);
        if (k < 3) {
            k += 2;
        }

        return new VariantResponse(pts, pointToClassify, k);

    }

    public VariantResponse() {}

    public VariantResponse(List<DataPoint> dataPoints, DataPoint pointToClassify, int k) {
        this.dataPoints = dataPoints;
        this.pointToClassify = pointToClassify;
        this.k = k;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public DataPoint getPointToClassify() {
        return pointToClassify;
    }

    public void setPointToClassify(DataPoint pointToClassify) {
        this.pointToClassify = pointToClassify;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

}

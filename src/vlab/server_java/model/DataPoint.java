package vlab.server_java.model;

import vlab.server_java.StaticUtilsProvider;
import vlab.server_java.common.Classifier;

import java.util.Random;

public class DataPoint {

    private static final Random random = StaticUtilsProvider.getRandomInstance();

    private int x;
    private int y;
    private Classifier cls;

    public static DataPoint random(int dim) {
        return new DataPoint(
                random.nextInt(dim) * ((random.nextBoolean()) ? 1 : -1),
                random.nextInt(dim) * ((random.nextBoolean()) ? 1 : -1),
                Classifier.values()[random.nextInt(Classifier.values().length)]
        );
    }

    public DataPoint(int x, int y, Classifier cls) {
        this.x = x;
        this.y = y;
        this.cls = cls;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Classifier getCls() {
        return cls;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCls(Classifier cls) {
        this.cls = cls;
    }

}

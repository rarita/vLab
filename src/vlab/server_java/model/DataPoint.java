package vlab.server_java.model;

import vlab.server_java.StaticUtilsProvider;
import vlab.server_java.common.Classifier;

import java.util.Objects;
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

    public DataPoint() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataPoint dataPoint = (DataPoint) o;
        return x == dataPoint.x &&
                y == dataPoint.y &&
                cls == dataPoint.cls;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, cls);
    }

}

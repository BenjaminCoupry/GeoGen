package Utils.Perlin;

public class PerlinParams {
    private int nbOctaves;
    private double f0;
    private double Attenuation;
    private int seed;
    private double shift;
    private double min;
    private double max;


    public PerlinParams(int nbOctaves, double f0, double attenuation, int seed, double shift, double min, double max) {
        this.nbOctaves = nbOctaves;
        this.f0 = f0;
        Attenuation = attenuation;
        this.seed = seed;
        this.shift = shift;
        this.min = min;
        this.max = max;
    }

    public int getNbOctaves() {
        return nbOctaves;
    }

    public void setNbOctaves(int nbOctaves) {
        this.nbOctaves = nbOctaves;
    }

    public double getF0() {
        return f0;
    }

    public void setF0(double f0) {
        this.f0 = f0;
    }

    public double getAttenuation() {
        return Attenuation;
    }

    public void setAttenuation(double attenuation) {
        Attenuation = attenuation;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public double getShift() {
        return shift;
    }

    public void setShift(double shift) {
        this.shift = shift;
    }
    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
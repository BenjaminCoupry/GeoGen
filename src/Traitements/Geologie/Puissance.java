package Traitements.Geologie;

import Utils.HeightMaps.MapFunc;

public class Puissance implements MapFunc {
    double pow;

    public Puissance(double pow) {
        this.pow = pow;
    }

    @Override
    public double evaluate(int x, int y, double val) {
        return Math.signum(val)*Math.pow(Math.abs(val),pow);
    }
}

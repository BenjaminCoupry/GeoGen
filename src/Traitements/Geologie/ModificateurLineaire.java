package Traitements.Geologie;

import Utils.HeightMaps.MapFunc;

public class ModificateurLineaire implements MapFunc {
    double k;
    double a;

    public ModificateurLineaire(double k, double a) {
        this.k = k;
        this.a = a;
    }

    @Override
    public double evaluate(int x, int y, double val) {
        return val*k+a;
    }
}

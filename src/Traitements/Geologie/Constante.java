package Traitements.Geologie;

import Utils.HeightMaps.MapGen;

public class Constante implements MapGen {
    double val;

    public Constante(double val) {
        this.val = val;
    }

    @Override
    public double evaluate(int x, int y) {
        return val;
    }
}

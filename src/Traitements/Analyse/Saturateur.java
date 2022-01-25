package Traitements.Analyse;

import Utils.HeightMaps.MapFunc;

public class Saturateur implements MapFunc {
    double seuil_min;
    double seuil_max;

    public Saturateur(double seuil_min, double seuil_max) {
        this.seuil_min = seuil_min;
        this.seuil_max = seuil_max;
    }


    @Override
    public double evaluate(int x, int y, double val) {
        return Math.max(seuil_min,Math.min(seuil_max,val));
    }
}

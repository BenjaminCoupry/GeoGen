package Traitements.Analyse;

import Utils.HeightMaps.MapFunc;

public class Seuillage implements MapFunc {
    double seuil_min;
    double seuil_max;

    public Seuillage(double seuil_min, double seuil_max) {
        this.seuil_min = seuil_min;
        this.seuil_max = seuil_max;
    }


    @Override
    public double evaluate(int x, int y, double val) {
        if(val>seuil_min && val<seuil_max)
        {
            return 1;
        }else
        {
            return 0;
        }
    }

}

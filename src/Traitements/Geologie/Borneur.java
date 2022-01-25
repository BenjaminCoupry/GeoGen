package Traitements.Geologie;

import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.MapCalc;

public class Borneur implements MapCalc {
    double min;
    double max;

    public Borneur(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public double evaluate(int x, int y, HeightMap context) {
        double k = (context.getValeur(x,y)-context.getMin())/(context.getMax()-context.getMin());
        return min + k*(max-min);
    }
}

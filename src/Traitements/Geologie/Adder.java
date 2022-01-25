package Traitements.Geologie;

import Utils.HeightMaps.MapComb;

public class Adder implements MapComb {

    @Override
    public double combine(int x, int y, double val1, double val2) {
        return val1+val2;
    }
}

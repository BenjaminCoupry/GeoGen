package Traitements.Statistique;

import Utils.HeightMaps.MapFunc;

import java.util.Random;

public class Spawner implements MapFunc {

    Random r;

    public Spawner(int seed) {
        r = new Random(seed);
    }

    @Override
    public double evaluate(int x, int y, double val) {
        if(r.nextDouble()<val)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}

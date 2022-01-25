package Traitements.Geologie;

import Utils.HeightMaps.MapGen;
import Utils.Perlin.PerlinGenerator;
import Utils.Perlin.PerlinParams;

public class GeologiePerlin implements MapGen {
    PerlinParams params;

    public GeologiePerlin(PerlinParams params) {
        this.params = params;
    }
    @Override
    public double evaluate(int x, int y) {
        return PerlinGenerator.perlin2D(x,y,params);
    }
}

package Utils.HeightMaps.Manipulateur;

import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.MapComb;

public class Combineur extends Manipulateur{
    MapComb mapComb;
    Manipulateur m1;
    Manipulateur m2;

    public Combineur(MapComb mapComb, Manipulateur m1, Manipulateur m2) {
        super();
        this.mapComb = mapComb;
        this.m1 = m1;
        this.m2 = m2;
    }

    @Override
    public HeightMap generer(int nx, int ny)
    {
        HeightMap hm1 = m1.generer(nx,ny);
        HeightMap hm2 = m2.generer(nx,ny);
        HeightMap hmc =  combineHeightMap(hm1,hm2,mapComb);
        return traiter(hmc);
    }
}

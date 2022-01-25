package Utils.HeightMaps.Manipulateur;

import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.MapComb;
import Utils.HeightMaps.MapGen;

public class Generateur extends Manipulateur{
    MapGen generateur;

    public Generateur(MapGen generateur) {
        super();
        this.generateur = generateur;
    }

    @Override
    public HeightMap generer(int nx, int ny)
    {
        HeightMap hm = generateHeightMap(nx,ny,generateur);
        return traiter(hm);
    }
}

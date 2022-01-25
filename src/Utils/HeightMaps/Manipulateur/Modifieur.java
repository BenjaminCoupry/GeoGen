package Utils.HeightMaps.Manipulateur;

import Utils.HeightMaps.HeightMap;

public class Modifieur extends Manipulateur{
    HeightMap map;

    public Modifieur(HeightMap map) {
        super();
        this.map = map;
    }

    @Override
    public HeightMap generer(int nx, int ny) {
        return traiter(map);
    }
}

package Utils.HeightMaps.Manipulateur;

import Utils.HeightMaps.HeightMap;

public class Suiveur extends Manipulateur{
    Manipulateur base;

    public Suiveur(Manipulateur base) {
        super();
        this.base = base;
    }

    @Override
    public HeightMap generer(int nx, int ny) {
        HeightMap hm1 = base.generer(nx,ny);
        return traiter(hm1);
    }
}

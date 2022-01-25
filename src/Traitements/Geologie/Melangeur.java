package Traitements.Geologie;

import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.Manipulateur.Manipulateur;
import Utils.HeightMaps.MapComb;

public class Melangeur implements MapComb {
    HeightMap facteur;

    public Melangeur( HeightMap facteur) {
        this.facteur = facteur;
    }
    public Melangeur( Manipulateur facteur, int nx, int ny) {
        this.facteur = facteur.generer(nx,ny);
    }

    @Override
    public double combine(int x, int y, double val1, double val2) {
        double k = facteur.getValeur(x,y);
        double ksmooth = (1.0 - Math.cos(k * Math.PI)) / 2.0;
        return ksmooth*val1 + (1.0-ksmooth)*val2;
    }
}

package Traitements.Geologie;

import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.Manipulateur.Manipulateur;
import Utils.HeightMaps.MapComb;

public class MelangeurSature implements MapComb {
    HeightMap facteur;
    double hist;

    public MelangeurSature(HeightMap facteur,double hist) {
        this.facteur = facteur;
        this.hist = hist;
    }
    public MelangeurSature(Manipulateur facteur,double hist, int nx, int ny) {
        this.facteur = facteur.generer(nx,ny);
        this.hist = hist;
    }

    @Override
    public double combine(int x, int y, double val1, double val2) {
        double beta = 0.5*(1-1/hist);
        double k = Math.max(0,Math.min(1,hist*(facteur.getValeur(x,y)-beta)));
        double ksmooth = (1.0 - Math.cos(k * Math.PI)) / 2.0;
        return ksmooth*val1 + (1.0-ksmooth)*val2;
    }
}

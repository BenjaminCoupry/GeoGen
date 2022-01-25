package Utils.Textureur;

import Utils.HeightMaps.HeightMap;

import java.awt.*;

public class ColorMap {
    ProfilCouleur profil;
    HeightMap probaMap;

    public ColorMap(ProfilCouleur profil, HeightMap probaMap) {
        this.profil = profil;
        this.probaMap = probaMap;
    }

    public ProfilCouleur getProfil() {
        return profil;
    }

    public void setProfil(ProfilCouleur profil) {
        this.profil = profil;
    }

    public HeightMap getProbaMap() {
        return probaMap;
    }

    public void setProbaMap(HeightMap probaMap) {
        this.probaMap = probaMap;
    }
    public int getNx()
    {
        return probaMap.getNx();
    }
    public int getNy()
    {
        return probaMap.getNy();
    }
    public Color getColor(int x, int y)
    {
        return profil.getCouleur(x,y);
    }
    public double getProba(int x, int y)
    {
        return probaMap.getValeur(x,y);
    }
}

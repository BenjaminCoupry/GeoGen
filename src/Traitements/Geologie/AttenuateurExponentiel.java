package Traitements.Geologie;

import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.Manipulateur.Manipulateur;
import Utils.HeightMaps.MapFunc;

public class AttenuateurExponentiel implements MapFunc {
    HeightMap attenuation;
    double facteurAttenuation;

    public AttenuateurExponentiel(HeightMap attenuation, double facteurAttenuation) {
        this.attenuation = attenuation;
        this.facteurAttenuation = facteurAttenuation;
    }
    public AttenuateurExponentiel(Manipulateur attenuation, int nx, int ny, double facteurAttenuation) {
        this.attenuation = attenuation.generer(nx,ny);
        this.facteurAttenuation = facteurAttenuation;
    }


    @Override
    public double evaluate(int x, int y, double val) {
        if(val>0) {
            double att = Math.max(0,attenuation.getValeur(x, y));
            double facteur = Math.exp(-att * facteurAttenuation);
            return Math.min(1,facteur)*val;
        }else
        {
            return val;
        }
    }
}

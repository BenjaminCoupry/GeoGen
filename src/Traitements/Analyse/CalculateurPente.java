package Traitements.Analyse;

import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.MapCalc;

public class CalculateurPente implements MapCalc {
    @Override
    public double evaluate(int x, int y, HeightMap context) {
        double dx = 0.5*(context.getValeur(x+1,y)- context.getValeur(x-1,y));
        double dy = 0.5*(context.getValeur(x,y+1)- context.getValeur(x,y-1));
        return Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
    }
}

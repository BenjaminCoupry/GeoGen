package Traitements.Analyse;

import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.MapCalc;

public class CalculateurEnsoleillement implements MapCalc {
    @Override
    public double evaluate(int x, int y, HeightMap context) {
        double tetamaxneg = 0;
        double tetamaxpos = 0;
        for(int i = 0;i<context.getNx();i++)
        {
            int dx = Math.abs(i-x);
            double dy = context.getValeur(i,y)-context.getValeur(x,y);
            double teta = Math.atan2(dy,dx);
            if(i<x)
            {
                if(teta>tetamaxneg)
                {
                    tetamaxneg = teta;
                }
            }else if(i>x)
            {
                if(teta>tetamaxpos)
                {
                    tetamaxpos = teta;
                }
            }
        }
        return Math.PI-tetamaxneg-tetamaxpos;
    }
}

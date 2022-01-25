package Traitements.Statistique;

import Utils.HeightMaps.MapAgglo;

import java.util.List;

public class Selecteur implements MapAgglo {

    @Override
    public double agglomerer(int x, int y, List<Double> valeurs) {
        for(int i=0;i<valeurs.size();i++)
        {
            if(valeurs.get(i)>0)
            {
                return i+1;
            }
        }
        return 0;
    }
}

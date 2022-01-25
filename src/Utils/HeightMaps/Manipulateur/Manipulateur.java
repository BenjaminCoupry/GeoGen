package Utils.HeightMaps.Manipulateur;

import Utils.HeightMaps.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Manipulateur {
    List<TraitementHeightMap> traitements;

    public Manipulateur() {
        this.traitements = new LinkedList<>();
    }
    public Manipulateur addTraitement(TraitementHeightMap thm)
    {
        traitements.add(thm);
        return this;
    }

    public static HeightMap generateHeightMap(int nx, int ny, MapGen generateur)
    {
        HeightMap result = new HeightMap(nx,ny);
        for(int i=0;i<result.getNx();i++)
        {
            for(int j=0;j<result.getNy();j++)
            {
                result.setValeur(i,j,generateur.evaluate(i,j));
            }
        }
        return result;
    }
    public static HeightMap combineHeightMap(HeightMap hm1, HeightMap hm2, MapComb comb)
    {
        HeightMap result = new HeightMap(hm1.getNx(),hm1.getNy());
        for(int i=0;i<hm1.getNx();i++)
        {
            for(int j=0;j<hm1.getNy();j++)
            {
                double val1 = hm1.getValeur(i,j);
                double val2 = hm2.getValeur(i,j);
                result.setValeur(i,j, comb.combine(i,j,val1,val2));
            }
        }
        return result;
    }
    public static HeightMap aggloHeightMap(List<HeightMap> hms, MapAgglo comb)
    {
        HeightMap result = new HeightMap(hms.get(0).getNx(),hms.get(0).getNy());
        for(int i=0;i<hms.get(0).getNx();i++)
        {
            for(int j=0;j<hms.get(0).getNy();j++)
            {
                int finalI = i;
                int finalJ = j;
                List<Double> valeurs = hms.stream().map(hm->hm.getValeur(finalI, finalJ)).collect(Collectors.toList());
                result.setValeur(i,j, comb.agglomerer(i,j,valeurs));
            }
        }
        return result;
    }
    public static HeightMap transformHeightMap(HeightMap hm, MapFunc fonction)
    {
        HeightMap result = new HeightMap(hm.getNx(),hm.getNy());
        for(int i=0;i<hm.getNx();i++)
        {
            for(int j=0;j<hm.getNy();j++)
            {
                double val = hm.getValeur(i,j);
                result.setValeur(i,j,fonction.evaluate(i,j,val));
            }
        }
        return result;
    }
    public static HeightMap calculateHeightMap(HeightMap hm, MapCalc calculateur)
    {
        HeightMap result = new HeightMap(hm.getNx(),hm.getNy());
        for(int i=0;i<hm.getNx();i++)
        {
            for(int j=0;j<hm.getNy();j++)
            {
                result.setValeur(i,j,calculateur.evaluate(i,j,hm));
            }
        }
        return result;
    }
    public static HeightMap appliquerTraitement(HeightMap hm, TraitementHeightMap thm)
    {
        System.out.println(thm.getClass().getName());
        if(thm instanceof MapFunc)
        {
            return transformHeightMap(hm,(MapFunc) thm);
        }
        else if (thm instanceof MapCalc)
        {
            return calculateHeightMap(hm,(MapCalc) thm);
        }
        else
        {
            return null;
        }
    }
    public HeightMap traiter( HeightMap input)
    {
        for(TraitementHeightMap thm : traitements)
        {
            input = appliquerTraitement(input,thm);
        }
        return input;
    }



    public List<TraitementHeightMap> getTraitements() {
        return traitements;
    }

    public abstract HeightMap generer(int nx, int ny);
}

package Utils.Erosion;

import Traitements.Geologie.Constante;
import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.Manipulateur.Generateur;
import Utils.HeightMaps.Manipulateur.Manipulateur;

import java.io.IOException;
import java.util.Random;

public class ErosionHydro {
    public static void eroder(HeightMap hm, int nbDrop, int seed)
    {
        Random r = new Random(seed);
        Manipulateur mp = new Generateur(new Constante(0));
        HeightMap passage = mp.generer(hm.getNx(),hm.getNy());
        for(int i=0;i<nbDrop;i++)
        {
            Goutte g = new Goutte(hm.getNx()*r.nextDouble() ,hm.getNy()*r.nextDouble(),r.nextDouble());
            boolean vivante = true;
            while (vivante)
            {
                boolean minloc = g.step(hm,0.5,10,1,0.02,0.2);
                vivante = g.estVivante();
                if(minloc) {
                    passage.setValeur((int)g.x,(int)g.y,2);
                }else
                {
                    passage.setValeur((int)g.x,(int)g.y,g.eau);
                }


                //System.out.println(g.eau+" : "+g.sediment + " ("+ (int)g.x+" "+(int)g.y+")->"+g.vitesse);
            }
            if(i%100000==0)
            {
                System.out.println(i);
                try {
                    hm.toImage("L:/APP/Erosion/MNT"+i);
                    passage.toImage("L:/APP/Erosion/MNT"+i+"chem");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

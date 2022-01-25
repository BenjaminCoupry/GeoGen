package Utils.Textureur;

import Utils.Perlin.PerlinGenerator;
import Utils.Perlin.PerlinParams;

import java.awt.*;
import java.util.Random;


public class ProfilCouleur {
    Color couleurBase;
    PerlinParams variabiliteSat;
    PerlinParams variabiliteVal;

    public ProfilCouleur(Color couleurBase, PerlinParams variabiliteSat, PerlinParams variabiliteVal) {
        this.couleurBase = couleurBase;
        this.variabiliteSat = variabiliteSat;
        this.variabiliteVal = variabiliteVal;
    }
    public ProfilCouleur(Color couleurBase,int seed, PerlinParams variabilite) {
        Random r = new Random(seed);
        this.couleurBase = couleurBase;
        this.variabiliteSat = new PerlinParams(variabilite.getNbOctaves(),variabilite.getF0(),
                variabilite.getAttenuation(),r.nextInt(),variabilite.getShift(),
                variabilite.getMin(),variabilite.getMax());
        this.variabiliteVal = new PerlinParams(variabilite.getNbOctaves(),variabilite.getF0(),
                variabilite.getAttenuation(),r.nextInt(),variabilite.getShift(),
                variabilite.getMin(),variabilite.getMax());
    }


    public Color getCouleur(int x, int y)
    {
         float[] hsb = Color.RGBtoHSB(couleurBase.getRed(),couleurBase.getGreen(),couleurBase.getBlue(),null);
         double ds = PerlinGenerator.perlin2D(x,y,variabiliteSat);
         double dv = PerlinGenerator.perlin2D(x,y, variabiliteVal);
         Color rgb = Color.getHSBColor(hsb[0],
                 (float)(Math.max(0,Math.min(1,hsb[1]+ds))),
                 (float)(Math.max(0,Math.min(1,hsb[2]+dv))));
         return rgb;
    }
}

package EcoPresets.Presets;

import EcoPresets.GeoPreset;
import EcoPresets.GeoSet;
import Traitements.Analyse.DistanceMasque;
import Traitements.Analyse.Seuillage;
import Traitements.Geologie.*;
import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.Manipulateur.Combineur;
import Utils.HeightMaps.Manipulateur.Generateur;
import Utils.HeightMaps.Manipulateur.Manipulateur;
import Utils.Perlin.PerlinParams;

import java.util.Random;

public class Montagne implements GeoPreset {
    int taille;

    public Montagne(int taille) {
        this.taille = taille;
    }

    @Override
    public GeoSet generer(int seed) {
        Random r = new Random(seed);
        Manipulateur mntGenCretes = new Generateur(new GeologiePerlin(new PerlinParams(10,0.001,0.45,r.nextInt(),15,0,1)))
                .addTraitement(new Seuillage(0.5,0.501))
                .addTraitement(new DistanceMasque(100))
                .addTraitement(new Borneur(0,1))
                .addTraitement(new ModificateurLineaire(-1,1))
                .addTraitement(new Puissance(2))
                .addTraitement(new ModificateurLineaire(4000,-1));
        //mntGenCretes.generer(taille,taille).toImage("L:/APP/crete");
        Manipulateur mSelCol = new Generateur(new GeologiePerlin(new PerlinParams(9,0.005,0.55,r.nextInt(),15,0,1)));

        Manipulateur mSelSed = new Generateur(new GeologiePerlin(new PerlinParams(9,0.02,0.55,r.nextInt(),15,-1,1)));

        Manipulateur mntGenHF = new Generateur(new GeologiePerlin(new PerlinParams(10,0.01,0.45,r.nextInt(),15,0,4500)));

        Manipulateur mntGenLF = new Generateur(new GeologiePerlin(new PerlinParams(10,0.005,0.45,r.nextInt(),15,-700,500)));
        Manipulateur mntDF = new Combineur(new MelangeurSature(mSelCol,3,taille,taille),mntGenLF,mntGenHF);
        Manipulateur mntGenHFC = new Combineur(new Adder(),mntDF,mntGenCretes);
        Manipulateur mntGen = mntGenHFC.addTraitement(new AttenuateurExponentiel(mSelSed,taille,taille,2));
        HeightMap hm =  mntGen.generer(taille,taille);
        return new GeoSet(hm);
    }
}

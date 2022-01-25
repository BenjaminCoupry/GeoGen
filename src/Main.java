import EcoPresets.*;
import EcoPresets.Presets.Montagne;
import EcoPresets.Presets.VillageMontagne;
import Traitements.Analyse.*;
import EcoPresets.Presets.Pyrenees;
import Traitements.Geologie.*;
import Traitements.Statistique.Selecteur;
import Utils.HeightMaps.*;
import Utils.HeightMaps.Manipulateur.Combineur;
import Utils.HeightMaps.Manipulateur.Generateur;
import Utils.HeightMaps.Manipulateur.Manipulateur;
import Utils.Perlin.PerlinParams;
import Utils.Textureur.TextureMap;
import Utils.pathfinding.Grid2d;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        int taille = 1000;
        System.out.println("Calcul de l'altitude");
        Montagne mnt = new Montagne(taille);
        GeoSet gs = mnt.generer(45465);

        System.out.println("Calcul des textures");
        Pyrenees pyr = new Pyrenees(gs);
        NatureSet txm =  pyr.generer(10895);


        System.out.println("villes et routes");
        VillageMontagne villages = new VillageMontagne(txm);
        HumanSet hs = villages.generer(65544);


        txm.getTexture().coverBy(hs.getTexture());


        try {
            gs.toImages("L:/APP/testSet/mnt");
            hs.toImages("L:/APP/testSet/path");
            txm.toImages("L:/APP/testSet/pyr");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

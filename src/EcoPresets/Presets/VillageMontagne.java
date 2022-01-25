package EcoPresets.Presets;

import EcoPresets.HumanPreset;
import EcoPresets.HumanSet;
import EcoPresets.NatureSet;
import Traitements.Analyse.DistanceMasque;
import Traitements.Analyse.Seuillage;
import Traitements.Analyse.SeuillageDoux;
import Traitements.Geologie.*;
import Traitements.Statistique.Spawner;
import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.Manipulateur.Combineur;
import Utils.HeightMaps.Manipulateur.Generateur;
import Utils.HeightMaps.Manipulateur.Manipulateur;
import Utils.HeightMaps.Manipulateur.Modifieur;
import Utils.Perlin.PerlinParams;
import Utils.Routes.ConnexionManager;
import Utils.Routes.RoadNetwork;
import Utils.Textureur.ColorMap;
import Utils.Textureur.ProfilCouleur;
import Utils.Textureur.TextureMap;
import Utils.pathfinding.Grid2d;

import java.awt.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class VillageMontagne implements HumanPreset {

    NatureSet nature;
    HeightMap villages;
    HeightMap routes;

    public VillageMontagne(NatureSet nature) {
        this.nature = nature;
    }

    @Override
    public HumanSet generer(int seed) {
        Random r = new Random(seed);
        PerlinParams ppcol = new PerlinParams(9,0.4,0.55,r.nextInt(),15,-0.8,0.8);
        PerlinParams ppcolroutes = new PerlinParams(9,0.01,0.55,r.nextInt(),15,-0.1,0.1);
        int nx = nature.getAltitude().getNx();
        int ny = nature.getAltitude().getNy();
        HeightMap villagesSpot = new Modifieur(nature.getProbaChamp()).addTraitement(new ModificateurLineaire(0.00005,0))
                .addTraitement(new Spawner(r.nextInt())).generer(nx,ny);
        genererConnexions(nx,ny,r,villagesSpot);
        Manipulateur mvill = new Modifieur(villagesSpot).addTraitement(new DistanceMasque(40)).addTraitement(new Borneur(0,1))
                .addTraitement(new ModificateurLineaire(-1,1));
        Manipulateur perlinVille = new Generateur(new GeologiePerlin(new PerlinParams(9,0.1,0.55,r.nextInt(),15,0.6,1)));
        Manipulateur selVille = new Combineur(new Multiplicateur(),mvill,perlinVille).addTraitement(new SeuillageDoux(0.7,1,0.1));
        villages = selVille.generer(nx,ny);
        TextureMap tx = new TextureMap();
        tx.addCouche(new ColorMap(new ProfilCouleur(Color.BLACK,r.nextInt(),ppcolroutes),routes));
        tx.addCouche(new ColorMap(new ProfilCouleur(Color.GRAY,r.nextInt(),ppcol),villages));
        return new HumanSet(villages,routes,tx);
    }
    private void genererConnexions(int nx, int ny, Random r, HeightMap villagesSpot)
    {
        routes = new Generateur(new Constante(0)).generer(nx,ny);
        List<Point> village = villagesSpot.find();
        Grid2d map2d = new Grid2d(nature.getAltitude().getValeurs(), true);

        RoadNetwork rn = new RoadNetwork(0.1,0.3,45,village,r,map2d);
        rn.generateNetwork();

        for(Point p : rn.getRoads())
        {
            routes.setValeur(p.x,p.y,1);
        }

    }

}

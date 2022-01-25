package EcoPresets.Presets;

import EcoPresets.EcoPreset;
import EcoPresets.GeoSet;
import EcoPresets.NatureSet;
import Traitements.Analyse.*;
import Traitements.Geologie.*;
import Traitements.Statistique.Selecteur;
import Traitements.Statistique.Spawner;
import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.Manipulateur.*;
import Utils.Perlin.PerlinParams;
import Utils.Textureur.ColorMap;
import Utils.Textureur.ProfilCouleur;
import Utils.Textureur.TextureMap;


import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Pyrenees implements EcoPreset {
    HeightMap altitude;
    int seed;
    private Random r;


    HeightMap eau;
    ColorMap colorEau;
    HeightMap pente;
    HeightMap probaArbresNord;
    ColorMap colorArbreNord;
    Manipulateur popArbresNord;

    HeightMap probaArbresSud;
    ColorMap colorArbreSud;
    Manipulateur popArbresSud;

    HeightMap probaBois;
    ColorMap colorBois;
    Manipulateur popArbresBois;

    HeightMap arbreType;


    HeightMap probaPlage;
    ColorMap colorPlage;
    HeightMap probaChamp;
    ColorMap colorChamp;
    HeightMap probaAlpages;
    ColorMap colorAlpages;
    HeightMap probaNeige;
    ColorMap colorNeige;
    HeightMap probaRoche;
    ColorMap colorRoche;

    public Pyrenees(GeoSet altitude) {
        this.altitude = altitude.getAltitude();
    }
    @Override
    public NatureSet generer(int seed)
    {
        this.seed = seed;
        r = new Random(seed);
        int nx = altitude.getNx();
        int ny = altitude.getNy();
        PerlinParams ppcol = new PerlinParams(9,0.01,0.55,r.nextInt(),15,-0.5,0.5);

        Manipulateur mcot = new Modifieur(altitude);
        mcot.addTraitement(new CalculateurCoteaux());

        Manipulateur mharb = new Modifieur(altitude);
        mharb.addTraitement(new SeuillageDoux(1000,1500,500));



        Manipulateur mpente = new Modifieur(altitude);
        mpente.addTraitement(new CalculateurPente());

        pente = mpente.generer(nx,ny);
        System.out.println(pente.getMax());
        System.out.println(pente.getMin());

        try {
            pente.toImage("L:/APP/pente");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Manipulateur mhhum = new Modifieur(altitude);
        mhhum.addTraitement(new Seuillage(10,1000));
        Manipulateur mpentehum = new Modifieur(pente).addTraitement(new Seuillage(-0.1,200));
        Manipulateur mhum = new Combineur(new Multiplicateur(),mhhum,mpentehum);

        probaChamp = mhum.generer(nx,ny);
        colorChamp = new ColorMap(new ProfilCouleur(Color.ORANGE.darker().darker().darker(),r.nextInt(),ppcol),probaChamp);

        Manipulateur boismap = new Generateur(new GeologiePerlin(new PerlinParams(9,0.01,0.55,r.nextInt(),15,0,1)));
        boismap.addTraitement(new SeuillageDoux(0.7,1,0.2));
        Manipulateur probabois = new Combineur(new Multiplicateur(),boismap,mhhum);

        probaBois = probabois.generer(nx,ny);
        popArbresBois = new Modifieur(probaBois).addTraitement(new ModificateurLineaire(0.008,0)).addTraitement(new Spawner(r.nextInt()));
        colorBois = new ColorMap(new ProfilCouleur(Color.GREEN.darker().darker().darker(),r.nextInt(),ppcol),probaBois);

        Manipulateur mhneige = new Modifieur(altitude);
        mhneige.addTraitement(new SeuillageDoux(2000,Double.POSITIVE_INFINITY,50));
        Manipulateur mpenteneige = new Modifieur(pente);
        mpenteneige.addTraitement(new Seuillage(0,700));
        Manipulateur mneige = new Combineur(new Multiplicateur(),mhneige,mpenteneige);

        probaNeige = mneige.generer(nx,ny);
        colorNeige = new ColorMap(new ProfilCouleur(Color.WHITE,r.nextInt(),ppcol),probaNeige);

        Manipulateur mhalp = new Modifieur(altitude);
        mhalp.addTraitement(new SeuillageDoux(1500,2000,500));
        Manipulateur malpages =  new Combineur(new Multiplicateur(),mhalp,mpentehum);
        probaAlpages = malpages.generer(nx,ny);
        colorAlpages = new ColorMap(new ProfilCouleur(Color.GREEN.darker().darker(),r.nextInt(),ppcol),probaAlpages);

        Manipulateur mcotnord = new Suiveur(mcot);
        mcotnord.addTraitement(new SeuillageDoux(-1,0,0.3));
        Manipulateur marbnord = new Combineur(new Multiplicateur(),mcotnord,mharb);

        probaArbresNord = marbnord.generer(nx,ny);

        popArbresNord = new Modifieur(probaArbresNord).addTraitement(new ModificateurLineaire(0.006,0)).addTraitement(new Spawner(r.nextInt()));
        colorArbreNord = new ColorMap(new ProfilCouleur(Color.GREEN.darker().darker().darker().darker().darker(),r.nextInt(),ppcol),probaArbresNord);

        Manipulateur mcotsud = new Suiveur(mcot);
        mcotsud.addTraitement(new SeuillageDoux(0,1,0.3));
        Manipulateur marbsud = new Combineur(new Multiplicateur(),mcotsud,mharb);

        probaArbresSud = marbsud.generer(nx,ny);
        popArbresSud = new Modifieur(probaArbresSud).addTraitement(new ModificateurLineaire(0.007,0)).addTraitement(new Spawner(r.nextInt()));
        colorArbreSud = new ColorMap(new ProfilCouleur(Color.GREEN.darker().darker().darker().darker(),r.nextInt(),ppcol),probaArbresSud);

        Manipulateur meau = new Modifieur(altitude);
        meau.addTraitement(new Seuillage(Double.NEGATIVE_INFINITY,0));

        eau = meau.generer(nx,ny);
        colorEau = new ColorMap(new ProfilCouleur(Color.BLUE,r.nextInt(),ppcol),eau);

        Manipulateur msable = new Suiveur(meau);
        msable.addTraitement(new DistanceMasque(30));
        msable.addTraitement(new Borneur(0,1));
        msable.addTraitement(new Seuillage(0,0.08));

        probaPlage = msable.generer(nx,ny);
        colorPlage = new ColorMap(new ProfilCouleur(Color.YELLOW,r.nextInt(),ppcol),probaPlage);

        Manipulateur mroche = new Generateur(new Constante(1));
        probaRoche = mroche.generer(nx,ny);
        colorRoche = new ColorMap(new ProfilCouleur(Color.DARK_GRAY,r.nextInt(),ppcol),probaRoche);

        arbreType = new Agglomerateur(new Selecteur()).addManipulateur(popArbresBois).addManipulateur(popArbresNord)
                .addManipulateur(popArbresSud).generer(nx,ny);



        TextureMap tx = new TextureMap();
        tx.addCouche(colorRoche);
        tx.addCouche(colorChamp);
        tx.addCouche(colorAlpages);
        tx.addCouche(colorPlage);
        tx.addCouche(colorBois);
        tx.addCouche(colorArbreSud);
        tx.addCouche(colorArbreNord);
        tx.addCouche(colorNeige);
        tx.addCouche(colorEau);
        return new NatureSet(altitude,arbreType,eau,probaChamp,tx);
    }
}

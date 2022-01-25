package EcoPresets;

import Utils.HeightMaps.HeightMap;
import Utils.Textureur.ColorMap;
import Utils.Textureur.TextureMap;

import java.io.IOException;

public class NatureSet {
    HeightMap altitude;
    HeightMap arbreType;
    HeightMap eau;
    HeightMap probaChamp;
    TextureMap texture;

    public NatureSet(HeightMap altitude, HeightMap arbreType, HeightMap eau, HeightMap probaChamp, TextureMap texture) {
        this.altitude = altitude;
        this.arbreType = arbreType;
        this.eau = eau;
        this.probaChamp = probaChamp;
        this.texture = texture;
    }

    public void toImages(String path) throws IOException {
        arbreType.toImage(path+"_typeArbre");
        eau.toImage(path+"_eau");
        probaChamp.toImage(path+"_champ");
        texture.generer();
        texture.toImage(path+"_texture");
    }

    public HeightMap getAltitude() {
        return altitude;
    }

    public HeightMap getArbreType() {
        return arbreType;
    }

    public HeightMap getEau() {
        return eau;
    }

    public HeightMap getProbaChamp() {
        return probaChamp;
    }

    public TextureMap getTexture() {
        return texture;
    }
}

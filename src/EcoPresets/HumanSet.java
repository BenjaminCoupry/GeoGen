package EcoPresets;

import Utils.HeightMaps.HeightMap;
import Utils.Textureur.TextureMap;

import java.io.IOException;

public class HumanSet {
    HeightMap villages;
    HeightMap routes;
    TextureMap texture;

    public HumanSet(HeightMap villages, HeightMap routes, TextureMap texture) {
        this.villages = villages;
        this.routes = routes;
        this.texture = texture;
    }


    public void toImages(String path) throws IOException {
        villages.toImage(path+"_villages");
        routes.toImage(path+"_routes");
        texture.generer();
        texture.toImage(path+"_texture");
    }

    public HeightMap getVillages() {
        return villages;
    }

    public HeightMap getRoutes() {
        return routes;
    }

    public TextureMap getTexture() {
        return texture;
    }
}

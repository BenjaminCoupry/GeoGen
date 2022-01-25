package EcoPresets;

import Utils.HeightMaps.HeightMap;

import java.io.IOException;

public class GeoSet {
    HeightMap altitude;

    public GeoSet(HeightMap altitude) {
        this.altitude = altitude;
    }

    public HeightMap getAltitude() {
        return altitude;
    }
    public void toImages(String path) throws IOException {
        altitude.toImage(path+"_altitude");
    }
}

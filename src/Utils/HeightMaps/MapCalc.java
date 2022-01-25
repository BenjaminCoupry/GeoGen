package Utils.HeightMaps;

public interface MapCalc extends TraitementHeightMap{
    double evaluate(int x, int y, HeightMap context);
}

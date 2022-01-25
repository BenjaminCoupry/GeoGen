package Utils.HeightMaps.Manipulateur;

import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.MapAgglo;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Agglomerateur extends Manipulateur{
    MapAgglo magl;
    List<Manipulateur> manipulateurs;

    public Agglomerateur(MapAgglo magl) {
        manipulateurs = new LinkedList<>();
        this.magl = magl;
    }
    public Agglomerateur addManipulateur(Manipulateur man)
    {
        manipulateurs.add(man);
        return this;
    }

    @Override
    public HeightMap generer(int nx, int ny)
    {
        List<HeightMap> hmaps = manipulateurs.stream().map(m->m.generer(nx,ny)).collect(Collectors.toList());
        HeightMap hmc =  aggloHeightMap(hmaps,magl);
        return traiter(hmc);
    }
}

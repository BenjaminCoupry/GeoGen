package Utils.Routes;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ConnexionManager {
    int nbElem;
    boolean[][] directConnex;
    List<Integer> networkIsolated;
    List<Integer> networkConnected;
    List<Point> positions;
    Random r;

    public ConnexionManager(int networkCore, List<Point> positions, Random r)
    {
        this.r = r;
        nbElem = positions.size();
        directConnex = new boolean[nbElem][nbElem];
        networkIsolated = new LinkedList<>();
        networkConnected = new LinkedList<>();
        this.positions = positions;
        for(int i=0;i<nbElem;i++)
        {
            if(i!=networkCore) {
                networkIsolated.add(i);
            }else
            {
                networkConnected.add(i);
            }
            for(int j=0;j<nbElem;j++)
            {
                directConnex[i][j] = (i==j);
            }
        }
    }

    public void link(int i, int j, boolean direct)
    {
        directConnex[i][j]=direct || directConnex[i][j];
        directConnex[j][i]=direct || directConnex[j][i];
        if(networkIsolated.contains(i) || networkIsolated.contains(j)) {
            if (networkIsolated.contains(i)) {
                networkIsolated.remove(networkIsolated.indexOf(i));
                networkConnected.add(i);
            }
            if (networkIsolated.contains(j)) {
                networkIsolated.remove(networkIsolated.indexOf(j));
                networkConnected.add(i);
            }
        }
    }

    public int getNearestNetworkNodeNotDirectlyConnectedTo(int cible)
    {
        Comparator<Integer> comp = Comparator.comparingDouble((Integer i) -> positions.get(i).distance(positions.get(cible)));
        Optional<Integer> elem = networkConnected.stream().filter(i -> !(directConnex[i][cible])).sorted(comp).findFirst();
        if(elem.isPresent()) {
            return elem.get();
        }else
        {
            return cible;
        }
    }
    public int getNearestNotNetworkNode(int cible)
    {
        Comparator<Integer> comp = Comparator.comparingDouble((Integer i) -> positions.get(i).distance(positions.get(cible)));
        Optional<Integer> elem = networkIsolated.stream().sorted(comp).findFirst();
        if(elem.isPresent()) {
            return elem.get();
        }else
        {
            return cible;
        }
    }
    public int getRandomNotNetworkNode()
    {
        int elem = networkIsolated.get(r.nextInt(networkIsolated.size()));
        return elem;
    }
    public int getRandomNetworkNode()
    {
        int elem = networkConnected.get(r.nextInt(networkConnected.size()));
        return elem;
    }
    public int getRandomNetworkNodeNotDirectlyConnectedTo(int cible)
    {

        List<Integer> elems = networkConnected.stream().filter(i->!(directConnex[i][cible])).collect(Collectors.toList());
        int elem = elems.get(r.nextInt(elems.size()));
        return elem;
    }
    public boolean isNetworked(int i)
    {
        return networkConnected.contains(i);
    }
    public boolean isDirectlyConnected(int i, int j)
    {
        return directConnex[i][j];
    }
    public int notNetworkedNodesCount()
    {
        return networkIsolated.size();
    }

}

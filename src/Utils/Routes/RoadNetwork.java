package Utils.Routes;

import Utils.pathfinding.Grid2d;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class RoadNetwork {
    double probaRedondance;
    double probaNodeSwipe;
    ConnexionManager cm;
    Set<Point> roads;
    int safeIntersectRadius;
    List<Point> positions;
    Random r;
    Grid2d map2d;
    List<Integer> currentRoute;

    public RoadNetwork(double probaRedondance, double probaNodeSwipe, int safeIntersectRadius, List<Point> positions, Random r, Grid2d map2d) {
        this.probaRedondance = probaRedondance;
        this.probaNodeSwipe = probaNodeSwipe;
        this.safeIntersectRadius = safeIntersectRadius;
        this.positions = positions;
        this.r = r;
        this.map2d = map2d;
        cm = new ConnexionManager(r.nextInt(positions.size()),positions,r);
        roads = new HashSet<>();
        currentRoute = new LinkedList<>();
    }

    public RoadInfo getSafeRoadPart(List<Point> road)
    {
        List<Point> safeRoad = new LinkedList<>();
        for(int i =0; i<road.size();i++)
        {
            if(i<safeIntersectRadius || i>(road.size()-safeIntersectRadius) || !roads.contains(road.get(i)))
            {
                safeRoad.add(road.get(i));
            }else
            {
                System.out.println("Intersection apres "+i+ " pas");
                return new RoadInfo(false,safeRoad,road);
            }
        }
        return new RoadInfo(true,safeRoad,road);
    }

    public List<Point> getPath(int i, int j)
    {
        System.out.println("PathSearch :"+i+">"+j);
        Point start = positions.get(i);
        Point end = positions.get(j);
        List<Grid2d.MapNode> nodesGo = map2d.findPath(start.y, start.x, end.y, end.x);
        return nodesGo.stream().map(mn->new Point(mn.getY(),mn.getX())).collect(Collectors.toList());
    }

    private double distance(int i, int j)
    {
        return positions.get(i).distance(positions.get(j));
    }

    public void directConnect(int i, int j)
    {
        System.out.println("Direct Connect : "+ i+ " to "+ j);
        List<Point> path = getPath(i, j);
        roads.addAll(path);
        cm.link(i,j,true);
        currentRoute.add(j);
        printCurrentRoute();
    }

    public int softConnect(int i, int j)
    {
        List<Point> path = getPath(i, j);
        RoadInfo safeRoad = getSafeRoadPart(path);
        roads.addAll(safeRoad.getSafePath());
        int reached;
        if(safeRoad.isDirect())
        {
            cm.link(i,j, true);
            reached =  j;
        }else
        {
            int k = cm.getNearestNetworkNodeNotDirectlyConnectedTo(j);
            if(cm.isNetworked(j))
            {
                cm.link(i,j, false);
            }else
            {
                cm.link(i,k, false);
            }
            reached =  k;
        }
        System.out.println("Soft Connect : "+ i+ " to "+ j + " (reched "+reached+")");
        currentRoute.add(reached);
        printCurrentRoute();
        return reached;
    }

    public void shortcut(int i, int j, int k)
    {
        System.out.println("Shortcut : "+ i+ " to "+ j + " by "+k);
        double distDirect = distance(i, j);
        double distanceShort = distance(k, j);
        if(distDirect>distanceShort) {
            connect(k, j);
        }else
        {
            if(!cm.isNetworked(j))
            {
                directConnect(i,j);
            }
        }
    }

    public void connect(int i, int j)
    {
        System.out.println("Connect : "+ i+ " to "+ j);
        int reached = softConnect(i,j);

        if (reached != j) {
            if (reached == i) {
                directConnect(i, j);
            } else {
                shortcut(i, j, reached);
            }
        }
    }

    public void generateNetwork()
    {
        int depart = cm.getRandomNetworkNode();
        while(cm.notNetworkedNodesCount()>0)
        {
            int arrivee;
            if(r.nextDouble()>probaRedondance) {
                arrivee= cm.getRandomNotNetworkNode();
            }else
            {
                arrivee = cm.getRandomNetworkNode();
            }
            System.out.println(cm.notNetworkedNodesCount()+":"+depart + "->"+arrivee);
            currentRoute = new LinkedList<>();
            currentRoute.add(depart);
            connect(depart,arrivee);
            if(r.nextDouble()<probaNodeSwipe)
            {
                depart = arrivee;
            }
        }
    }

    public Set<Point> getRoads() {
        return roads;
    }
    public void printCurrentRoute()
    {
        String s = "";
        for(int i : currentRoute)
        {
            s = s+"->"+i;
        }
        System.out.println(s);
    }
}

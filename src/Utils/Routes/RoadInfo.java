package Utils.Routes;

import java.awt.*;
import java.util.List;

public class RoadInfo {
    boolean direct;
    List<Point> safePath;
    List<Point> path;

    public RoadInfo(boolean direct, List<Point> safePath, List<Point> path) {
        this.direct = direct;
        this.safePath = safePath;
        this.path = path;
    }


    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public List<Point> getSafePath() {
        return safePath;
    }

    public void setSafePath(List<Point> safePath) {
        this.safePath = safePath;
    }

    public List<Point> getPath() {
        return path;
    }

    public void setPath(List<Point> path) {
        this.path = path;
    }
}

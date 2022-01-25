package Utils.HeightMaps;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class HeightMap {
    private double min;
    private double max;
    private int nx;
    private int ny;
    private double[][] valeurs;

    public double[][] getValeurs() {
        return valeurs;
    }
    public int getNx() {
        return nx;
    }

    public int getNy() {
        return ny;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public HeightMap(int nx,int ny) {
        valeurs = new double[nx][ny];
        this.nx=nx;
        this.ny=ny;
        min=Double.POSITIVE_INFINITY;
        max=Double.NEGATIVE_INFINITY;
    }

    public double getValeur(int x, int y)
    {
        x = Math.max(0,Math.min(nx-1,x));
        y = Math.max(0,Math.min(ny-1,y));
        return valeurs[x][y];
    }
    public double getValeurProp(double x, double y)
    {
        return getValeur((int)(x*nx),(int)(y*ny));
    }
    public void setValeur(int x, int y, double valeur)
    {
        x = Math.max(0,Math.min(nx-1,x));
        y = Math.max(0,Math.min(ny-1,y));
        valeurs[x][y] = valeur;
        if(valeur>max)
        {
            max=valeur;
        }
        if(valeur<min)
        {
            min=valeur;
        }
    }

    public BufferedImage toImage()
    {
        BufferedImage result = new BufferedImage(nx,ny, BufferedImage.TYPE_INT_ARGB);
        for(int i=0;i<nx;i++)
        {
            for(int j=0;j<ny;j++)
            {
                double val = getValeur(i,j);
                int color = (int)(255.0*(val-min)/(max-min));
                result.setRGB(i,j, new Color(color,color,color,255).getRGB());
            }
        }
        return result;
    }
    public void toImage(String path) throws IOException {
        ImageIO.write(toImage(),"png",new File(path+".png"));
    }
    public List<Point> find()
    {
        List<Point> res = new LinkedList<>();
        for(int i=0;i<nx;i++)
        {
            for(int j=0;j<ny;j++)
            {
                double val = getValeur(i,j);
                if(val>0)
                {
                    res.add(new Point(i,j));
                }
            }
        }
        return res;
    }

}

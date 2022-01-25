package Utils.Textureur;

import Utils.HeightMaps.HeightMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TextureMap {
    List<ColorMap> couches;
    Color[][] couleurs;

    public TextureMap() {
        couches = new LinkedList<>();
    }
    public void addCouche(ColorMap cm)
    {
        couches.add(cm);
    }

    public TextureMap coverBy(TextureMap txm)
    {
        couches.addAll(txm.couches);
        return this;
    }

    public void generer()
    {
        int nx = couches.get(0).getNx();
        int ny = couches.get(0).getNy();
        couleurs = new Color[nx][ny];
        for(int i=0;i<nx;i++)
        {
            for(int j=0;j<ny;j++)
            {
                couleurs[i][j] = getColor(i,j);
            }
        }
    }
    public void brushMasque(HeightMap masque, Color c)
    {
        List<Point> valeurs = masque.find();
        for(Point p : valeurs)
        {
            couleurs[p.x][p.y] = c;
        }
    }
    public Color getColor(int x, int y)
    {
        double r=0;
        double g=0;
        double b=0;
        for(ColorMap c : couches)
        {
            Color cl = c.getColor(x,y);
            double p = c.getProba(x,y);
            r = (1-p)*r + cl.getRed()*p;
            g = (1-p)*g + cl.getGreen()*p;
            b = (1-p)*b + cl.getBlue()*p;
        }
        return new Color((int)r,(int)g,(int)b);
    }
    public BufferedImage toImage()
    {
        int nx = couches.get(0).getNx();
        int ny = couches.get(0).getNy();
        if(couleurs == null)
        {
            generer();
        }
        BufferedImage result = new BufferedImage(nx,ny, BufferedImage.TYPE_INT_RGB);
        for(int i=0;i<nx;i++)
        {
            for(int j=0;j<ny;j++)
            {
                Color cl = couleurs[i][j];
                result.setRGB(i,j, cl.getRGB());
            }
        }
        return result;
    }
    public void toImage(String path) throws IOException {
        ImageIO.write(toImage(),"png",new File(path+".png"));
    }
}

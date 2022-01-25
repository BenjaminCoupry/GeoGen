package Utils.Erosion;

import Utils.HeightMaps.HeightMap;

public class Goutte {
    double x;
    double y;
    double vitesse;
    double eau;
    double sediment;

    public Goutte(double x, double y, double eau) {
        this.x = x;
        this.y = y;
        this.eau = eau;
        vitesse= 0;
        sediment = 0;
    }

    private double getStockageMax()
    {
        return eau;
    }
    private double getVitesseErosion(double Kerosion)
    {
        return eau*vitesse*Kerosion;
    }
    public boolean evaporer(double delta)
    {
        eau -= delta;
        return eau>0;
    }
    public boolean estVivante()
    {
        return eau>0;
    }
    public void updateVitesse(double gradient, double kvitesse)
    {
        vitesse +=kvitesse*gradient;
    }
    private double getErosionValeur(double z, double znext, double Kerosion, double sigma)
    {

        if(sediment>getStockageMax())
        {
            double depot = sediment-getStockageMax();
            sediment = getStockageMax();
            return depot;
        }else
        {
            double erosionNat = getVitesseErosion(Kerosion);
            double erosionMax = Math.max(0,z-znext)*(sigma*Math.sqrt(2.0*Math.PI));
            double prelevement = Math.min(erosionNat,erosionMax);
            sediment += prelevement;
            return -prelevement;
        }
    }

    private void eroder(HeightMap map, double erosion,double sigma,int X, int Y)
    {
        int r = (int)(4*sigma);
        for (int i=X-r;i<=X+r;i++)
        {
            for (int j=Y-r;j<=Y+r;j++)
            {
                double dst = Math.sqrt(Math.pow(i-X,2)+Math.pow(j-Y,2));
                double k = Math.exp(-0.5*Math.pow(dst/sigma,2))*1.0/(sigma*Math.sqrt(2.0*Math.PI));
                map.setValeur(i,j,map.getValeur(i,j)+erosion*k);
            }
        }
    }

    public boolean step(HeightMap map,double stepLen, double Kerosion, double Kvitesse, double Kevap, double Ksigma)
    {
        int X = (int)x;
        int Y = (int)y;

        double dx = 0.5*(map.getValeur(X+1,Y)- map.getValeur(X-1,Y));
        double dy = 0.5*(map.getValeur(X,Y+1)- map.getValeur(X,Y-1));
        double norme = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        double vx = -stepLen*dx/norme;
        double vy = -stepLen*dy/norme;
        x = x+vx;
        y = y+vy;
        int XN = (int)x;
        int YN = (int)y;

        updateVitesse(norme,Kvitesse);

        double z = map.getValeur(X,Y);
        double znext = map.getValeur(XN,YN);
        if(znext>z)
        {
            double depotMax = (znext-z)*(Ksigma*Math.sqrt(2.0*Math.PI));
            eroder(map,Math.min(sediment,depotMax),Ksigma,X,Y);
            eau = -1;
            return true;
        }else{
            double erosion = getErosionValeur(z,znext,Kerosion,Ksigma);
            eroder(map,erosion,Ksigma,X,Y);
            evaporer(Kevap);
            return false;
        }


    }
}

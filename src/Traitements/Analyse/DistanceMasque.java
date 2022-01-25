package Traitements.Analyse;

import Utils.HeightMaps.HeightMap;
import Utils.HeightMaps.MapCalc;

public class DistanceMasque implements MapCalc {
    int rayonMax;
    public DistanceMasque( int rayonMax) {
        this.rayonMax = rayonMax;
    }

    private double hitRayon (int x0, int y0, int R, HeightMap context)
    {

        double rmin = Double.POSITIVE_INFINITY;
        for(int x_ = -R;x_<=R;x_++)
        {
            int x = x0+x_;
            double v0 = context.getValeur(x,y0+R);
            double v1 = context.getValeur(x,y0-R);
            if (v0 > 0.5 || v1 > 0.5){
                double r = Math.sqrt(Math.pow(x_,2)+Math.pow(R,2));
                if(r<rmin)
                {
                    rmin=r;
                }
            }
        }
        for(int y_ = -R;y_<=R;y_++)
        {
            int y = y0+y_;
            double v0 = context.getValeur(x0+R,y);
            double v1 = context.getValeur(x0-R,y);
            if (v0 > 0.5 || v1 > 0.5){
                double r = Math.sqrt(Math.pow(y_,2)+Math.pow(R,2));
                if(r<rmin)
                {
                    rmin=r;
                }
            }
        }
        return rmin;
    }


    @Override
    public double evaluate(int x, int y, HeightMap context) {
        double dmax = Math.sqrt(Math.pow(context.getNx(),2)+Math.pow(context.getNy(),2));
        double dmin=Math.min(dmax,rayonMax);
        boolean modif = false;
        int r = 0;
        double rstop = rayonMax;
        while(r<rstop)
        {
            double d = hitRayon(x,y,r,context);
            if(d<dmin)
            {
                dmin=d;
                if(!modif) {
                    rstop = Math.sqrt(2) * rstop;
                    modif = true;
                }
            }
            r++;
        }
        if(Double.isInfinite(dmin))
        {
            dmin = dmax;
        }
        return dmin/dmax;
    }
}

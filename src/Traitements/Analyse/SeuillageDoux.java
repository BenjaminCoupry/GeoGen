package Traitements.Analyse;

import Utils.HeightMaps.MapFunc;

public class SeuillageDoux implements MapFunc {
    double seuil_min;
    double seuil_max;
    double lambda;

    public SeuillageDoux(double seuil_min, double seuil_max, double lambda) {
        this.seuil_min = seuil_min;
        this.seuil_max = seuil_max;
        this.lambda = lambda;
    }


    @Override
    public double evaluate(int x, int y, double val) {
        if(val>seuil_min && val<seuil_max)
        {
            return 1;
        }else
        {
            double delta;
            if(val<seuil_min)
            {
                delta = seuil_min-val;
            }else
            {

                delta = val-seuil_max;
            }
            return Math.exp(-Math.pow(delta/lambda,2));
        }
    }

}

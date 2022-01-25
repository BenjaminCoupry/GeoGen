package Utils.Perlin;

public class PerlinGenerator {
    private static double pseudo_Alea(int N, int seed) {
        //Retourne un double pseudo aleatoire N->R
        N = N + seed * 58900;
        N = (N << 13) ^ N;
        N = (N * (N * N * 15731 + 789221)) + 1376312589;
        return 1.0 - (N & 0x7fffffff) / 1073741824.0;
    }

    private static double pseudo_Alea_R2(int x, int y, int seed) {
        //Retourne un double pseudo aleatoire N^2->R
        return pseudo_Alea(x, (int) (255655544.0 * pseudo_Alea(y, seed) + 6558));
    }

    private static double fInterp(double k) {
        //Coefficent d'interpolation en cosinus
        return (1.0 - Math.cos(k * Math.PI)) / 2.0;
    }

    private static double perlinSimple1D(double x, int seed) {
        int X0 = (int) (Math.floor(x));
        double k = x - X0;
        double interp = 1.0 - fInterp(k);
        return interp * pseudo_Alea(X0, seed) + (1 - interp) * pseudo_Alea(X0 + 1, seed);
    }

    private static double perlinSimple2D(double x, double y, int seed) {
        int X0 = (int) (Math.floor(x));
        int Y0 = (int) (Math.floor(y));
        double kx = x - X0;
        double ky = y - Y0;
        double interpx = 1.0 - fInterp(kx);
        double interpy = 1.0 - fInterp(ky);
        double V1 = interpx * pseudo_Alea_R2(X0, Y0, seed) + (1.0 - interpx) * pseudo_Alea_R2(X0 + 1, Y0, seed);
        double V2 = interpx * pseudo_Alea_R2(X0, Y0 + 1, seed) + (1.0 - interpx) * pseudo_Alea_R2(X0 + 1, Y0 + 1, seed);
        return interpy * V1 + (1.0 - interpy) * V2;
    }

    public static double perlin1D(double x, PerlinParams params) {
        double result = 0;
        double A = 1.0;
        double sommeA = 0;
        double f = params.getF0();
        for (int i = 0; i < params.getNbOctaves(); i++) {
            sommeA += A;
            double shift = params.getShift() * pseudo_Alea(38565 * i + 56644, params.getSeed());
            result += A * perlinSimple1D((x + shift) * f, params.getSeed());
            A *= params.getAttenuation();
            f *= 2;
        }
        double val = result / sommeA;
        return params.getMin() + (params.getMax() - params.getMin()) *(val+1)/2.0;
    }

    public static double perlin2D(double x, double y, PerlinParams params) {
        double result = 0;
        double A = 1.0;
        double sommeA = 0;
        double f = params.getF0();
        for (int i = 0; i < params.getNbOctaves(); i++) {
            sommeA += A;
            double shiftx = params.getShift() * pseudo_Alea(38565 * i + 56644, params.getSeed());
            double shifty = params.getShift() * pseudo_Alea(75865 * i + (int) (5644 * shiftx) + 5457177, params.getSeed());
            result += A * perlinSimple2D((x + shiftx) * f, (y + shifty) * f, params.getSeed());
            A *= params.getAttenuation();
            f *= 2;
        }
        double val = result / sommeA;
        return params.getMin() + (params.getMax() - params.getMin()) *(val+1)/2.0;
    }
}
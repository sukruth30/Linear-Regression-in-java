package reg;

interface InnerManualReg {
    double mean(double[] x);

    double beta_one(double[] x, double[] y);

    double beta_not(double[] x, double[] y);

    double predict(double beta_not, double beta_one, double x);

    double r_squared(double[] x, double[] y, double[] pred);
}

public class ManualReg implements InnerManualReg {
    public double mean(double[] x) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i];
        }
        double mean = sum / x.length;
        return mean;
    }

    public double beta_one(double[] x, double[] y) {
        double mx = mean(x);
        double my = mean(y);
        double sxx = 0;
        double syx = 0;
        for (int i = 0; i < x.length; i++) {
            double a = x[i] - mx;
            double b = y[i] - my;
            double c = a * b;
            syx += c;
            double d = a * a;
            sxx += d;
        }
        return syx / sxx;
    }

    public double beta_not(double[] x, double[] y) {
        double mx = mean(x);
        double my = mean(y);
        double beta_one = beta_one(x, y);
        return my - (beta_one * mx);
    }

    public double predict(double beta_not, double beta_one, double x) {
        double y = beta_not + (beta_one * x);
        return y;
    }

    public double r_squared(double[] x, double[] y, double[] pred) {
        double mx = mean(x);
        double my = mean(y);
        double beta_one = beta_one(x, y);
        double syy = 0;
        double sxy = 0;
        for (int i = 0; i < x.length; i++) {
            double a = x[i] - mx;
            double b = y[i] - my;
            double c = a * b;
            sxy += c;
            double d = b * b;
            syy += d;
        }
        double SSres = syy - (beta_one * sxy);
        double SSreg = 0;
        for (int i = 0; i < x.length; i++) {
            double a = pred[i] - my;
            double b = a * a;
            SSreg += b;
        }
        double SST = SSres + SSreg;
        double r_squared = 1 - (SSres / SST);
        return r_squared;
    }
}

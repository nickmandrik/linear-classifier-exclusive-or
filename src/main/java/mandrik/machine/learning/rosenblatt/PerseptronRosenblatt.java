package mandrik.machine.learning.rosenblatt;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PerseptronRosenblatt {

    private int NUM_INPUT_NEIRONS = 2;
    private int COUNT_MAX_EPOHS = 20000;
    private double[] w;
    private double[] T;

    private double[][] x = {{-8, -6}, {-8,6}, {8,-6}, {8,6}};
    private double[][] e = {{0, 1, 1, 1}, {0, 0, 0, 1}};

    private double stepAducation;
    private boolean isAdaptiveStep;


    public PerseptronRosenblatt(boolean isAdaptive, double stepAducation) {
        this.isAdaptiveStep = isAdaptive;
        this.stepAducation = stepAducation;
        w = new double[NUM_INPUT_NEIRONS];
        T = new double[NUM_INPUT_NEIRONS];
    }


    public void modulate() {
        randomInitialization();

        aducate();
    }

    public double findY(double x1, double x2, double T) {
        double y = x1 * w[0] + x2 * w[1] - T;
        return functionActivation(y);
    }

    public void aducate() {
        for(int i = 0; i < T.length; i++) {
            printStepAnswer(i);
        }
        System.out.println();

        int index;
        for(index = 0; index < COUNT_MAX_EPOHS; index++) {
            boolean isOk = true;
            for(int j = 0; j < T.length; j++) {
                for (int i = 0; i < x.length; i++) {
                    Pair<Boolean, Double> isOkStep = aducateStep(x[i], e[j][i], T[j]);
                    if (!isOkStep.getKey()) {
                        isOk = false;
                    }
                    T[j] = isOkStep.getValue();
                }
            }
            if(isOk) {
                break;
            }
        }
        for(int i = 0; i < T.length; i++) {
            printStepAnswer(i);
        }
        System.out.println();
        System.out.println(index);
    }

    public void printStepAnswer(int index) {
        System.out.print("S[" + index + "] = ");
        for(int i = 0; i < w.length; i++) {
            if(w[i] > 0) {
                System.out.print("+ ");
            }
            System.out.print(w[i] + " * x[" + i + "] ");
        }
        if(T[index] <= 0) {
            System.out.print("+ ");
        }
        System.out.println(-T[index]);
    }


    public Pair<Boolean, Double> aducateStep(double[] x, double value, double T) {
        boolean isOk = false;
        double yk = 0;
        for(int i = 0; i < w.length; i++) {
            yk += w[i]*x[i];
        }
        yk -= T;
        double y = functionActivation(yk);

        if(y == value) {
            isOk = true;
        } else {
            double step = stepAducation;
            if(isAdaptiveStep) {
                double sum = 0;
                for(int i = 0; i < x.length; i++) {
                    sum += x[i] * x[i];
                }
                step = 1. / (1 + sum);
            }
            for (int i = 0; i < w.length; i++) {
                w[i] -= step * x[i] * (y - value);
            }
            T += step * (y - value);
        }
        return new Pair<>(isOk, T);
    }

    public Double functionActivation(Double yk) {
        double y = 0;
        if(yk > 0) {
            y = 1;
        }
        return y;
    }

    public void randomInitialization() {

        for(int i = 0; i < w.length; i++) {
            w[i] = new Random().nextInt(2000);
            w[i] = (w[i] / 1000) - 1;
            System.out.println(w[i]);
        }

        for(int i = 0; i < T.length; i++) {
            T[i] = new Random().nextInt(2000);
            T[i] = (T[i] / 1000) - 1;
        }
        Arrays.sort(T);
    }

    public void setStepAducation(Double stepAducation) {
        this.stepAducation = stepAducation;
    }

    public double[] getW() {
        return w;
    }

    public double[] getT() {
        return T;
    }

}

package com.company;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;
import java.util.Random;

public class AlingmentEvoluator implements FitnessEvaluator {


    @Override
    public double getFitness(Object o , List list ) {
        List<List> candidate = (List<List>) o;
        List<List<List>> population = list;
        //Nucleotids a = A ,b = C, c = G, d = T, e = -
        double FitVal =0 ;
        String x ;
        Random r = new Random();
        int j = r.nextInt(candidate.get(0).size());
        for (int i = 0; i < 2; i++) {
            x = new StringBuilder().append(candidate.get(i).get(j)).append(candidate.get(i + 1).get(j)).toString(); //.
            if (x.equalsIgnoreCase("AA") ) {
                FitVal = FitVal + 10;
            }
            else if(x.equals("TT"))
                FitVal = FitVal + 10;
            else if (x.equals("GG"))
                FitVal = FitVal + 20;
            else if (x.equals("CC"))
                FitVal = FitVal + 20;
            else if (x.equals("--"))
                FitVal = FitVal;
            else if (x.equals("AG"))
                FitVal = FitVal - 2;
            else if(x.equals("GA"))
                FitVal = FitVal - 2;
            else if   (x.equals("TC"))
                FitVal = FitVal - 2;
            else if (x.equals("CT") )
                FitVal = FitVal - 2;
            else if (x.equals("CG"))
                FitVal = FitVal - 1;
            else if (x.equals("AC"))
                FitVal = FitVal - 1;
            else if(x.equals("GC"))
                FitVal = FitVal - 1;
            else if (x.equals("CA"))
                FitVal = FitVal - 1;
            else if (x.equals("GT"))
                FitVal = FitVal - 4;
            else if  (x.equals("TG"))
                FitVal = FitVal - 4;
            else if (x.equals("TA") )
                FitVal = FitVal - 3;
            else
                FitVal = FitVal - 3;


        }
        if (FitVal < 0 ) {
            FitVal *= -1;
            return FitVal ;
        }

        return Math.max(FitVal,0);
    }


    @Override
    public boolean isNatural() {
        return false;
    }
}

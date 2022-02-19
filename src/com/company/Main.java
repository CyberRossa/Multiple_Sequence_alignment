package com.company;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final String[] bases = new String[]{"A", "T", "G", "C"};

    private static String randomNuc(Random random) {
        return bases[random.nextInt(bases.length)];
    }

    public static ArrayList GenerateRandomCandidate(Random random, int length) {
        ArrayList<String> seq = new ArrayList<>();
        ArrayList<String> seq_1 = new ArrayList<>();
        ArrayList<String> seq_2 = new ArrayList<>();

        ArrayList<ArrayList> member = new ArrayList();
        for (int j = 0; j < length; j++) {

            seq.add(randomNuc(random));
            seq_1.add(randomNuc(random));
            seq_2.add(randomNuc(random));

        }
        member.add(seq);
        member.add(seq_1);
        member.add(seq_2);


        return member;
    }

    public static List gaps(List<List> member, int b) {
        List<Integer> x = new ArrayList<>();
        for (int i = 0; i < member.size(); i++) {
            String k = (String) member.get(b).get(i);
            if (k.equals("-")) {
                x.add(i);
            }
        }
        return x;
    }

    public static List gap_add(List<List> member, int b) {
        if (b == 0) {
            member.get(1).add("-");
            member.get(2).add("-");
        } else if (b == 1) {
            member.get(0).add("-");
            member.get(2).add("-");
        } else {
            member.get(0).add("-");
            member.get(1).add("-");
        }
        return member;
    }

    public static void main(String[] args) {
        Random rng = new MersenneTwisterRNG();
        ArrayList<ArrayList> sequencesToBeAligned = GenerateRandomCandidate(rng, 20);
        List result = evolution(sequencesToBeAligned, rng);//Member
        System.out.println("Evolution result: " +"\n" + result.get(0)+ "\n" + result.get(1) + "\n" + result.get(2));
    }

    public static List evolution(ArrayList<ArrayList> Member, Random rng) {
        Initilizer initilizer = new Initilizer(Member);

        List<EvolutionaryOperator<ArrayList>> operators = new ArrayList<EvolutionaryOperator<ArrayList>>();
        operators.add(new Mutation(new Probability(0.02)));
        operators.add(new CrossOver(new Probability(0.45)));
        operators.add(new GAPCleanMutation());

        EvolutionaryOperator<ArrayList> pipeline = new EvolutionPipeline<ArrayList>(operators);
        EvolutionEngine<ArrayList> engine = new GenerationalEvolutionEngine<ArrayList>(initilizer,pipeline,new AlingmentEvoluator() ,new RouletteWheelSelection(), rng);
        engine.addEvolutionObserver(new EvolutionLogger());
        return engine.evolve(100,1, new GenerationCount(5000));

    }

    private static class EvolutionLogger implements EvolutionObserver<ArrayList> {

        @Override
        public void populationUpdate(PopulationData<? extends ArrayList> populationData) {
            System.out.printf("Generation %d: %s\n",
                    populationData.getGenerationNumber(),
                    populationData.getBestCandidate());
            double Fit = populationData.getBestCandidateFitness();
            System.out.printf("Fitness %f :" , Fit * -1 );
        }
    }
}

package com.company;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.*;

public class Initilizer extends AbstractCandidateFactory<ArrayList> {
    private final ArrayList<ArrayList> member;

    public Initilizer(ArrayList<ArrayList> member) {
        this.member = member;
    }

    public List GenerateRandomCandidate(Random random) {
        return member;
    }


    @Override
    public ArrayList generateRandomCandidate(Random random) {
        // this.GenerateRandomCandidate()=generateRandomCandidate();
        return new ArrayList(member);
    }

    public ArrayList<ArrayList> copyList(ArrayList<ArrayList> p) {
        ArrayList np = new ArrayList();
        for (int i = 0; i < p.size(); i++) {
            ArrayList npi = new ArrayList();
            for (int j = 0; j < p.get(i).size(); j++) {
                npi.add(p.get(i).get(j));
            }
            np.add(npi);
        }
        return np;
    }

    @Override
    public List<ArrayList> generateInitialPopulation(int populationSize, Random rng) {
        ArrayList pop = new ArrayList();
        for (int i = 0; i < populationSize; i++) {
            pop.add(copyList(member));
        }

        return pop;
    }

    @Override
    public List<ArrayList> generateInitialPopulation(int populationSize, Collection<ArrayList> seedCandidates, Random rng) {
        ArrayList pop = new ArrayList();
        pop.addAll(seedCandidates);

        int candidateToCreate = populationSize - seedCandidates.size();

        for (int i = 0; i < candidateToCreate; i++) {
            pop.add(copyList(member));
        }

        return pop;
    }
}


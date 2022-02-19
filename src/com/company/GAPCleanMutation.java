package com.company;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GAPCleanMutation implements EvolutionaryOperator<ArrayList> {
    @Override
    public List<ArrayList> apply(List<ArrayList> selectedCandidates, Random random) {

        for (List<ArrayList> list : selectedCandidates) {

            for (int i = 0; i < list.get(0).size(); i++) {
                if (list.get(0).get(i) == "-" & list.get(1).get(i) == "-" & list.get(2).get(i) == "-") {
                    list.get(0).remove(i);
                    list.get(1).remove(i);
                    list.get(2).remove(i);
                }
            }
        }

        return selectedCandidates;
    }
}

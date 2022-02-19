package com.company;

import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.*;

public class Mutation implements EvolutionaryOperator<ArrayList> {
    private final NumberGenerator<Probability> mutationProbabilityGenerator;

    public Mutation(Probability mutationProbability) {//List<List> member,
        this.mutationProbabilityGenerator = new ConstantGenerator<Probability>(mutationProbability);
    }

    public void clean(List<List> list) {
        for (int i = 0; i < list.get(0).size(); i++) {
            if(
                    list.get(0).get(i) == "-" &
                            list.get(1).get(i) == "-" &
                            list.get(1).get(i) == "-") {

                list.get(0).remove(i);
                list.get(1).remove(i);
                list.get(2).remove(i);

            }
        }
    }

    private List mutateList(List<List> list, Random random) {
        int a = random.nextInt(3);
        int b = random.nextInt(3);
        int c = random.nextInt(list.size());

        List<Integer> x = Main.gaps(list, b);
        int j = x.size();

        if(j==0) {//There is no gap in the sequence so new gap can be randomly put
            a = 0;
        }

        switch (a) {
            case 0: // open gap
                list.get(b).add(c, "-");
                Main.gap_add(list, b);
                break;
            case 1:// expand gap
                int r = random.nextInt(j);
                list.get(b).add(x.get(r) + 1, "-");
                Main.gap_add(list, b);
                break;
            case 2:// delete gap
                int r_1 = random.nextInt(j);
                list.get(b).remove((int)x.get(r_1));//it was passing Integer instead of int...
                list.get(b).add("-");

                break;

        }
        return list;

    }

    @Override
    public List<ArrayList> apply(List<ArrayList> selectedCandidates, Random random) {
        List<ArrayList> mutatedPopulation = new ArrayList<>(selectedCandidates.size());
        Iterator is = selectedCandidates.iterator();

        while (is.hasNext()) {
            if (mutationProbabilityGenerator.nextValue().nextEvent(random)) {
                List list = (List) is.next();
                mutatedPopulation.add((ArrayList) this.mutateList(list, random));
            }
        }
        return mutatedPopulation;
    }
}

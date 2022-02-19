package com.company;

import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;
import org.uncommons.watchmaker.framework.operators.ListCrossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.Probability;

public class CrossOver extends AbstractCrossover<ArrayList> {


    private final NumberGenerator<Probability> crossOverProbabilityGenerator;


    public CrossOver(Probability crossoverProbability) {//int crossoverPoints, removed it, it wasnt necessary anyways
        super(1, crossoverProbability);// I can only swap one currently
        crossOverProbabilityGenerator = new ConstantGenerator<>(crossoverProbability);

    }

    protected List<List> matee(List<List> parent, List<List> parent_1, int numberOfCrossoverPoints, Random r) {
        List<List> offspring = new ArrayList(parent);
        List<List> offspring_1 = new ArrayList(parent_1);
        for (int i = 0; i < numberOfCrossoverPoints; ++i) {
            int ins = parent.size();
            int crossoverIndex = 1 + r.nextInt(ins - 1);
            int d = parent.get(crossoverIndex).size() - parent_1.get(crossoverIndex).size();
            if (d < 0) {
                for (int u = 0; u < d * (-1); u++)
                    parent_1.get(crossoverIndex).add('e');
            } else if (d > 0)
                for (int u = 0; u < d; u++)
                    parent.get(crossoverIndex).add('e');
            else {
                for (int j = 0; j < crossoverIndex; ++j) {
                    List temp = offspring.get(j);
                    offspring.set(j, offspring_1.get(j));
                    offspring_1.set(j, temp);
                }
            }

        }
        List<List> result = new ArrayList(2);
        result.add(offspring);
        result.add(offspring_1);
        return result;
    }

    @Override
    protected List<ArrayList> mate(ArrayList p1, ArrayList p2, int i, Random random) {

        if (crossOverProbabilityGenerator.nextValue().nextEvent(random)) {

            //i will always be 1 for this implementation since I am swapping a single genome from the alignment
            int cp = random.nextInt(p1.size());
            List p1GenomeToBeReplaced = (List) p1.get(cp);
            List p2GenomeToBeReplaced = (List) p2.get(cp);

            p1.set(cp, p2GenomeToBeReplaced);
            p2.set(cp, p1GenomeToBeReplaced);//Make the crossover

            if (p1GenomeToBeReplaced.size() < p2GenomeToBeReplaced.size()) {//Match lengths
                for (int j = 0; j < p2GenomeToBeReplaced.size() - p1GenomeToBeReplaced.size(); j++) {
                    p1GenomeToBeReplaced.add("-");//Add gap to p1
                    Main.gap_add(p1, cp);//Add gap to other genomes in p1 to match the size of the p2GenomeToBeReplaced
                }
            } else {
                for (int j = 0; j < p1GenomeToBeReplaced.size() - p2GenomeToBeReplaced.size(); j++) {
                    p2GenomeToBeReplaced.add("-");
                    Main.gap_add(p2, cp);
                }
            }
        }

        List offSprings = new ArrayList();
        offSprings.add(p1);
        offSprings.add(p2);

        return offSprings;
    }

    @Override
    public List<ArrayList> apply(List<ArrayList> selectedCandidates, Random rng) {
        return mate(selectedCandidates.get(0), selectedCandidates.get(1), 1, rng);
    }


}





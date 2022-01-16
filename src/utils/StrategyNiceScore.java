package utils;

import entities.Child;

import java.util.ArrayList;

public class StrategyNiceScore implements Strategy {

    /**
     *
     * @param children
     * @return
     */
    @Override
    public ArrayList<Child> doStrategy(final ArrayList<Child> children) {
        ArrayList<Child> sortedChildren = new ArrayList<>(children);
        sortedChildren.sort((o1, o2) -> {
            if (o1.getAverageScore().equals(o2.getAverageScore())) {
                return o1.getId().compareTo(o2.getId());
            } else {
                return o2.getAverageScore().compareTo(o1.getAverageScore());
            }
        });
        return sortedChildren;
    }
}

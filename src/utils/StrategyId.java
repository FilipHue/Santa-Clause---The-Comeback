package utils;

import entities.Child;

import java.util.ArrayList;
import java.util.Comparator;

public class StrategyId implements Strategy {

    /**
     *
     * @param children
     * @return
     */
    @Override
    public ArrayList<Child> doStrategy(final ArrayList<Child> children) {
        ArrayList<Child> sortedChildren = new ArrayList<>(children);
        sortedChildren.sort(Comparator.comparing(Child::getId));
        return sortedChildren;
    }
}

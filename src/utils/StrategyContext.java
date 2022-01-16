package utils;

import entities.Child;

import java.util.ArrayList;

public class StrategyContext {

    private Strategy strategy;

    public StrategyContext(final Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     *
     * @param children
     * @return
     */
    public ArrayList<Child> executeStrategy(final ArrayList<Child> children) {
        return strategy.doStrategy(children);
    }
}

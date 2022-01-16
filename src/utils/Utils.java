package utils;

import common.Constants;
import entities.Child;
import entities.Present;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Utils {

    private Utils() {
    }

    /**
     *
     * @param child of which we calculate the type
     */

    public static void getType(final Child child) {
        if (child.getAge() < Constants.FIVE) {
            child.setType("Baby");
        } else if (child.getAge() < Constants.TWELVE) {
            child.setType("Kid");
        } else if (child.getAge() <= Constants.EIGHTEEN) {
            child.setType("Young Teen");
        } else {
            child.setType("Teen");
        }
    }

    /**
     *
     * @param children is the list of children
     * @param newBudget is Santa Claus's new budget
     */

    public static void calculateScore(final ArrayList<Child> children, final Double newBudget) {
        Double sumAverage = 0.0;
        for (var child : children) {
            getType(child);
            Double averageScore = 0.0;
            switch (child.getType()) {
                case "Baby" -> {
                    averageScore = Constants.BABY_GRADE;
                }
                case "Kid" -> {
                    for (Double score : child.getNiceScoreHistory()) {
                        averageScore += score;
                    }
                    averageScore /= child.getNiceScoreHistory().size();
                }
                case "Young Teen" -> {
                    int denominator = 0;
                    for (int i = 0; i < child.getNiceScoreHistory().size(); i++) {
                        averageScore += child.getNiceScoreHistory().get(i) * (i + 1);
                        denominator += (i + 1);
                    }
                    averageScore /= denominator;
                }
                default -> {
                }
            }
            if (child.getNiceScoreBonusHistory() != null) {
                averageScore += (averageScore * child.getNiceScoreBonusHistory())
                        / Constants.ONE_HUNDRED;
                if (averageScore > Constants.TEN) {
                    averageScore = Constants.TEN;
                }
            }
            child.setAverageScore(averageScore);
            sumAverage += averageScore;
        }

        Double newBudgetUnit = newBudget / sumAverage;
        for (var child : children) {
            child.setAssignedBudget(newBudgetUnit * child.getAverageScore());
            if (child.getElfHistory().get(child.getElfHistory().size() - 1).equals("pink")) {
                child.setAssignedBudget(child.getAssignedBudget()
                        + Constants.THIRTY * child.getAssignedBudget() / Constants.ONE_HUNDRED);
            } else if (child.getElfHistory().get(child.getElfHistory().size() - 1)
                    .equals("black")) {
                child.setAssignedBudget(child.getAssignedBudget()
                        - Constants.THIRTY * child.getAssignedBudget() / Constants.ONE_HUNDRED);
            }
        }
    }

    /**
     *
     * @param children is the list of children for which we need to distribute presents
     * @param presents is the list of presents to be distributed
     */
    public static void givePresents(final ArrayList<Child> children,
                                    final ArrayList<Present> presents, final String strategy) {

        ArrayList<Child> sortedChildren = new ArrayList<>(children);

        switch (strategy) {
            case "id" -> {
                StrategyContext context = new StrategyContext(new StrategyId());
                sortedChildren = context.executeStrategy(children);
            }
            case "niceScore" -> {
                StrategyContext context = new StrategyContext(new StrategyNiceScore());
                sortedChildren = context.executeStrategy(children);
            }
            case "niceScoreCity" -> {
                StrategyContext context = new StrategyContext(new StrategyNiceScoreCity());
                sortedChildren = context.executeStrategy(children);
            }
            default -> {
            }
        }

        for (var child: sortedChildren) {
            Map<String, Integer> preferences = new HashMap<>();
            if (!child.getType().equals("Teen")) {
                Double budget = child.getAssignedBudget();
                for (var pref: child.getGiftsPreferences()) {
                    for (var present : presents) {
                        if (present.getQuantity() > 0) {
                            if (pref.equals(present.getCategory())) {
                                preferences.putIfAbsent(present.getCategory(), 0);
                                if (preferences.containsKey(present.getCategory())
                                        && preferences.get(present.getCategory()) == 0) {
                                    if (budget - present.getPrice() > 0.0) {
                                        child.getReceivedGifts().add(present);
                                        preferences.put(present.getCategory(), 1);
                                        budget -= present.getPrice();
                                        present.setQuantity(present.getQuantity() - 1);
                                    }
                                } else if (preferences.containsKey(present.getCategory())
                                        && preferences.get(present.getCategory()) == 1) {
                                    for (var gift : child.getReceivedGifts()) {
                                        if (gift.getCategory().equals(present.getCategory())) {
                                            if (gift.getPrice() > present.getPrice()) {
                                                child.getReceivedGifts().set(
                                                        child.getReceivedGifts().indexOf(gift),
                                                        present
                                                );
                                                budget += (gift.getPrice() - present.getPrice());
                                                present.setQuantity(present.getQuantity() - 1);
                                                gift.setQuantity(gift.getQuantity() + 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (Child child: sortedChildren) {
            if (child.getElfHistory().get(child.getElfHistory().size() - 1).equals("yellow")
                    && child.getReceivedGifts().isEmpty()) {
                System.out.println(child.getId());
                for (String pref: child.getGiftsPreferences()) {
                    Present newGift = new Present();
                    for (Present present: presents) {
                        if (present.getCategory().equals(pref)) {
                            if (newGift.getPrice() == null) {
                                newGift = present;
                            } else if (newGift.getPrice() > present.getPrice()) {
                                newGift = present;
                            }
                        }
                    }
                    if (newGift.getPrice() != null && newGift.getQuantity() > 0) {
                        newGift.setQuantity(newGift.getQuantity() - 1);
                        child.getReceivedGifts().add(newGift);
                        break;
                    }
                }
            }
        }
    }
}

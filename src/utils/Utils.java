package utils;

import common.Constants;
import entities.Child;
import entities.Present;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Utils {

    private Utils() {
    }

    /**
     *
     * @param array of JSONs
     * @return a list of Strings
     */

    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object: array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
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
                sortedChildren.sort(Comparator.comparing(Child::getId));
            }
            case "niceScore" -> {
                sortedChildren.sort((o1, o2) -> {
                    if (o1.getAverageScore().equals(o2.getAverageScore())) {
                        return o1.getId().compareTo(o2.getId());
                    } else {
                        return o2.getAverageScore().compareTo(o1.getAverageScore());
                    }
                });
            }
            case "niceScoreCity" -> {
                sortedChildren = givePresentsAfterCity(children);
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

    /**
     *
     * @param children
     */
    public static ArrayList<Child> givePresentsAfterCity(final ArrayList<Child> children) {
        Map<String, ArrayList<Double>> citiesByAverageScore = new HashMap<>();
        Map<String, Double> finalCitiesByAverageScore = new HashMap<>();

        for (Child child: children) {
            citiesByAverageScore.putIfAbsent(child.getCity(), new ArrayList<>());
            if (citiesByAverageScore.containsKey(child.getCity())) {
                citiesByAverageScore.get(child.getCity()).add(child.getAverageScore());
            }
        }

        for (Map.Entry<String, ArrayList<Double>> entry: citiesByAverageScore.entrySet()) {
            Double scoreCity = 0.0;
            for (var score: entry.getValue()) {
                scoreCity += score;
            }
            scoreCity /= entry.getValue().size();
            finalCitiesByAverageScore.putIfAbsent(entry.getKey(), scoreCity);
        }


        List<Map.Entry<String, Double>> list = new ArrayList<>(
                finalCitiesByAverageScore.entrySet()
        );
        list.sort((o1, o2) -> {
            if (o2.getValue().equals(o1.getValue())) {
                return o1.getKey().compareTo(o2.getKey());
            } else {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        ArrayList<Child> newChildrenList = new ArrayList<>();
        for (Map.Entry<String, Double> city: list) {
            ArrayList<Child> newList = new ArrayList<>();
            for (Child child: children) {
                if (child.getCity().equals(city.getKey())) {
                    newList.add(child);
                }
            }
            newList.sort(Comparator.comparing(Child::getId));
            newChildrenList.addAll(newList);
        }
        return newChildrenList;
    }
}

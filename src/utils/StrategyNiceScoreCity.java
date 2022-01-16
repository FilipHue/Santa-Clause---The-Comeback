package utils;

import entities.Child;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrategyNiceScoreCity implements Strategy {

    /**
     *
     * @param children
     * @return
     */
    @Override
    public ArrayList<Child> doStrategy(final ArrayList<Child> children) {
        ArrayList<Child> sortedChildren = new ArrayList<>(children);
        Map<String, ArrayList<Double>> citiesByAverageScore = new HashMap<>();
        Map<String, Double> finalCitiesByAverageScore = new HashMap<>();

        for (Child child: sortedChildren) {
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

package main;

import common.Constants;
import database.Database;
import auxiliars.NewYear;
import entities.Child;
import entities.PapaNoel;

import java.util.HashMap;
import java.util.Map;

public final class FirstRound {

    private FirstRound() {
    }

    /**
     *
     * @param database from which the input is read
     */
    public static void initSetup(final Database database, final PapaNoel papaNoel) {

        Double sumAverageScore = 0.0;
        Double budgetUnit;

        for (var child: database.getChildren()) {
            if (child.getAge() < Constants.FIVE) {
                child.setType("Baby");
                child.setAverageScore(Constants.BABY_GRADE);
            } else if (child.getAge() < Constants.TWELVE) {
                child.setAverageScore(child.getNiceScore());
                child.setType("Kid");
            } else if (child.getAge() <= Constants.EIGHTEEN) {
                child.setAverageScore(child.getNiceScore());
                child.setType("Young Teen");
            } else {
                child.setType("Teen");
            }

            if (!child.getType().equals("Teen")) {
                sumAverageScore += child.getAverageScore();
            }
        }

        budgetUnit = database.getSantaBudget().get(0) / sumAverageScore;
        for (var child: database.getChildren()) {
            if (child.getAverageScore() != null) {
                child.setAssignedBudget(budgetUnit * child.getAverageScore());
            }
        }

        for (var child: database.getChildren()) {
            Map<String, Integer> preferences = new HashMap<>();
            if (!child.getType().equals("Teen")) {
                Double budget = child.getAssignedBudget();
                for (var pref: child.getGiftsPreferences()) {
                    for (var present : database.getPresents()) {
                        if (pref.equals(present.getCategory())) {
                            preferences.putIfAbsent(present.getCategory(), 0);
                            if (preferences.containsKey(present.getCategory())
                                    && preferences.get(present.getCategory()) == 0) {
                                if (budget - present.getPrice() > 0.0) {
                                    child.getReceivedGifts().add(present);
                                    preferences.put(present.getCategory(), 1);
                                    budget -= present.getPrice();
                                }
                            } else if (preferences.containsKey(present.getCategory())
                                    && preferences.get(present.getCategory()) == 1) {
                                for (var gift : child.getReceivedGifts()) {
                                    if (gift.getCategory().equals(present.getCategory())) {
                                        if (gift.getPrice() > present.getPrice()) {
                                            child.getReceivedGifts().set(
                                                    child.getReceivedGifts().indexOf(gift), present
                                            );
                                            budget += (gift.getPrice() - present.getPrice());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        NewYear newYear = new NewYear();
        for (var child: database.getChildren()) {
            if (!child.getType().equals("Teen")) {
                newYear.getChildren().add(child);
                papaNoel.getChildrenList().add(new Child.Builder(
                        child.getId(),
                        child.getAge(),
                        child.getFirstName(),
                        child.getLastName(),
                        child.getGiftsPreferences(),
                        child.getCity())
                        .niceScore(child.getNiceScore())
                        .type(child.getType())
                        .build());
            }
        }
        database.getAnnualChanges().getAnnualChildren().add(newYear);
    }
}

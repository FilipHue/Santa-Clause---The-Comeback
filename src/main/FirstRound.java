package main;

import auxiliars.NewYear;
import common.Constants;
import database.Database;
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
                if (child.getNiceScoreBonus() != null) {
                    child.setAverageScore(child.getAverageScore()
                            + child.getAverageScore() * child.getNiceScoreBonus()
                            / Constants.ONE_HUNDRED);
                    if (child.getAverageScore() > Constants.TEN) {
                        child.setAverageScore(Constants.TEN);
                    }
                }
                sumAverageScore += child.getAverageScore();
            }
        }

        budgetUnit = database.getSantaBudget().get(0) / sumAverageScore;
        for (var child: database.getChildren()) {
            if (child.getAverageScore() != null) {
                child.setAssignedBudget(budgetUnit * child.getAverageScore());
                if (child.getElf() != null) {
                    switch (child.getElf()) {
                        case "yellow":
                            continue;
                        case "black":
                            child.setAssignedBudget(child.getAssignedBudget()
                                    - child.getAssignedBudget()
                                    * Constants.THIRTY / Constants.ONE_HUNDRED);
                            break;
                        case "pink":
                            child.setAssignedBudget(child.getAssignedBudget()
                                    + child.getAssignedBudget()
                                    * Constants.THIRTY / Constants.ONE_HUNDRED);
                            break;
                        case "white":
                        default:
                    }
                }
            }
        }

        for (var child: database.getChildren()) {
            Map<String, Integer> preferences = new HashMap<>();
            if (!child.getType().equals("Teen")) {
                Double budget = child.getAssignedBudget();
                for (var pref: child.getGiftsPreferences()) {
                    for (var present : database.getPresents()) {
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
                        .niceScoreBonus(child.getNiceScoreBonus())
                        .elfHistory(child.getElf())
                        .build());
            }
        }
        database.getAnnualChanges().getAnnualChildren().add(newYear);
    }
}

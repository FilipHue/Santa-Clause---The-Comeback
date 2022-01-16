package main;

import auxiliars.NewYear;
import common.Constants;
import database.Database;
import entities.Change;
import entities.Child;
import entities.PapaNoel;
import utils.Utils;

import java.util.ArrayList;
import java.util.stream.Collectors;

public final class NextRound {

    private NextRound() {
    }

    /**
     *
     * @param database from which the information is extracted
     * @param papaNoel is the Santa Claus
     * @param year The current year
     */

    public static void playRound(final Database database, final PapaNoel papaNoel,
                                 final Integer year) {

        papaNoel.getChildrenList().removeIf(child -> child.getAge() + 1 > Constants.EIGHTEEN);
        ArrayList<Child> children = new ArrayList<>();

        papaNoel.getChildrenList().forEach(child -> children.add(new Child.Builder(
                child.getId(),
                child.getAge(),
                child.getFirstName(),
                child.getLastName(),
                child.getGiftsPreferences(),
                child.getCity())
                .copyNiceScore(child.getNiceScoreHistory())
                .type(child.getType())
                .niceScoreBonus(child.getNiceScoreBonusHistory())
                .copyElfHistory(child.getElfHistory())
                .build()));

        children.forEach(child -> child.setAge(child.getAge() + 1));

        Change change = database.getChanges().get(year - 1);
        if (change.getNewBudget() != null) {
            database.addToSantaBudget(change.getNewBudget());
        }
        if (change.getNewGiftList() != null) {
            database.getPresents().addAll(change.getNewGiftList());
        }
        if (change.getNewChildren() != null) {
            for (var child: change.getNewChildren()) {
                if (child.getAge() <= Constants.EIGHTEEN) {
                    children.add(new Child.Builder(
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
        }
        if (change.getChildrenUpdates() != null) {
            for (var update: change.getChildrenUpdates()) {
                for (var child: children) {
                    if (update.getId().equals(child.getId())) {
                        int itExists = 0;
                        if (change.getNewChildren() != null) {
                            for (var altChild : change.getNewChildren()) {
                                if (altChild.getId().equals(update.getId())) {
                                    itExists = 1;
                                    break;
                                }
                            }
                        }
                        if (update.getNiceScore() != null) {
                            if (itExists == 0) {
                                child.getNiceScoreHistory().add(update.getNiceScore());
                            }
                        }
                        if (itExists == 0) {
                            ArrayList<String> newPreferences = update.getGiftPreferences();
                            if (!newPreferences.equals(child.getGiftsPreferences())) {
                                newPreferences.addAll(child.getGiftsPreferences());
                                newPreferences = (ArrayList<String>) newPreferences.stream()
                                        .distinct().collect(Collectors.toList());
                                child.setGiftsPreferences(newPreferences);
                            }
                        }
                        if (update.getElf() != null) {
                            child.getElfHistory().add(update.getElf());
                        }
                    }
                }
            }
        }

        Utils.calculateScore(children, database.getSantaBudget().get(year));
        Utils.givePresents(children, database.getPresents(), change.getStrategy());

        ArrayList<Child> sortedChildren = new ArrayList<>(children);

        NewYear newYear = new NewYear();
        newYear.getChildren().addAll(sortedChildren);
        ArrayList<Child> newList = new ArrayList<>();
        for (var child: sortedChildren) {
            newList.add(new Child.Builder(
                    child.getId(),
                    child.getAge(),
                    child.getFirstName(),
                    child.getLastName(),
                    child.getGiftsPreferences(),
                    child.getCity())
                    .copyNiceScore(child.getNiceScoreHistory())
                    .type(child.getType())
                    .niceScoreBonus(child.getNiceScoreBonusHistory())
                    .copyElfHistory(child.getElfHistory())
                    .build());
        }
        database.getAnnualChanges().getAnnualChildren().add(newYear);
        papaNoel.setChildrenList(newList);
    }
}

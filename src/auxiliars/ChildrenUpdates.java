package auxiliars;

import java.util.ArrayList;

public final class ChildrenUpdates {

    private Integer id;
    private Double niceScore;
    private ArrayList<String> giftsPreferences;
    private String elf;

    public ChildrenUpdates() {
    }

    public ChildrenUpdates(final Integer id, final Double niceScore,
                           final ArrayList<String> giftPreferences) {
        this.id = id;
        this.niceScore = niceScore;
        this.giftsPreferences = giftPreferences;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setNiceScore(final Double niceScore) {
        this.niceScore = niceScore;
    }

    public Double getNiceScore() {
        return niceScore;
    }

    public void setGiftsPreferences(final ArrayList<String> giftPreferences) {
        this.giftsPreferences = giftPreferences;
    }

    public ArrayList<String> getGiftPreferences() {
        return giftsPreferences;
    }

    public String getElf() {
        return elf;
    }

    public void setElf(final String elf) {
        this.elf = elf;
    }
}

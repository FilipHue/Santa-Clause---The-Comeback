package entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public final class Child {

    private Integer id;
    private String lastName;
    private String firstName;
    private String city;
    private Integer age;
    private ArrayList<String> giftsPreferences;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Double niceScore;
    private Double averageScore = null;
    private ArrayList<Double> niceScoreHistory = new ArrayList<>();
    private Double assignedBudget;
    @JsonIgnore
    private String type;
    private final ArrayList<Present> receivedGifts = new ArrayList<>();

    public static class Builder {
        private final Integer id;
        private final String lastName;
        private final String firstName;
        private final String city;
        private final Integer age;
        private final ArrayList<String> giftsPreferences;
        private ArrayList<Double> niceScoreHistory = new ArrayList<>();
        private String childType;

        public Builder(final Integer id, final Integer age,
                     final String firstName, final String lastName,
                     final ArrayList<String> giftsPreferences, final String city) {
            this.id = id;
            this.age = age;
            this.firstName = firstName;
            this.lastName = lastName;
            this.giftsPreferences = giftsPreferences;
            this.city = city;
        }

        /**
         *
         * @param score is the current score to be added
         * @return
         */
        public Builder niceScore(final Double score) {
            this.niceScoreHistory.add(score);
            return this;
        }

        /**
         *
         * @param scoreList is the score history to be copied
         * @return
         */
        public Builder copyNiceScore(final ArrayList<Double> scoreList) {
            this.niceScoreHistory = new ArrayList<>(scoreList);
            return this;
        }

        /**
         *
         * @param type is the type of the child
         * @return
         */
        public Builder type(final String type) {
            this.childType = type;
            return this;
        }

        /**
         *
         * @return a new Child object
         */
        public Child build() {
            return new Child(this);
        }
    }

    public Child() {
    }

    private Child(final Builder builder) {
        this.id = builder.id;
        this.age = builder.age;
        this.niceScoreHistory = builder.niceScoreHistory;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.giftsPreferences = builder.giftsPreferences;
        this.city = builder.city;
        this.type = builder.childType;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    /**
     *
     * @param niceScore
     */
    public void setNiceScore(final Double niceScore) {
        this.niceScore = niceScore;
        if (this.niceScoreHistory != null) {
            this.niceScoreHistory.add(niceScore);
        }
    }

    public Double getNiceScore() {
        return this.niceScore;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public ArrayList<Double> getNiceScoreHistory() {
        return niceScoreHistory;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<String> getGiftsPreferences() {
        return giftsPreferences;
    }

    public void setGiftsPreferences(final ArrayList<String> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    public String getCity() {
        return city;
    }

    @JsonIgnore
    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(final Double averageScore) {
        this.averageScore = averageScore;
    }

    public ArrayList<Present> getReceivedGifts() {
        return receivedGifts;
    }

    public Double getAssignedBudget() {
        return assignedBudget;
    }

    public void setAssignedBudget(final Double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }
}

package database;

import entities.AnnualChanges;
import entities.Change;
import entities.Child;
import entities.Present;

import java.util.ArrayList;

public final class Database {
    private Integer numberOfYears;
    private final ArrayList<Double> santaBudget = new ArrayList<>();
    private ArrayList<Child> children;
    private ArrayList<Present> presents;
    private ArrayList<Change> changes;
    private final AnnualChanges annualChanges = new AnnualChanges();
    private static Database instance = null;

    private Database() {
    }

    /**
     *
     * @return instance of database
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * clears the database for the next test
     */
    public void clearAll() {
        instance = null;
    }

    public void setNumberOfYears(final Integer numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public Integer getNumberOfYears() {
        return numberOfYears;
    }

    /**
     *
     * @param santaBudget
     */
    public void setSantaBudget(final Double santaBudget) {
        this.santaBudget.add(santaBudget);
    }

    public ArrayList<Double> getSantaBudget() {
        return santaBudget;
    }

    public void setPresents(final ArrayList<Present> presents) {
        this.presents = presents;
    }

    public ArrayList<Present> getPresents() {
        return presents;
    }

    public void setChildren(final ArrayList<Child> children) {
        this.children = children;
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public void setChanges(final ArrayList<Change> changes) {
        this.changes = changes;
    }

    public ArrayList<Change> getChanges() {
        return changes;
    }

    public AnnualChanges getAnnualChanges() {
        return annualChanges;
    }

    /**
     *
     * @param addToSantaBudget is the budget of that year
     */
    public void addToSantaBudget(final Double addToSantaBudget) {
        this.santaBudget.add(addToSantaBudget);
    }
}

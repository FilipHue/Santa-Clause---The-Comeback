package entities;

import auxiliars.NewYear;

import java.util.ArrayList;

public final class AnnualChanges {

    private final ArrayList<NewYear> annualChildren = new ArrayList<>();


    public AnnualChanges() {
    }
    public ArrayList<NewYear> getAnnualChildren() {
        return annualChildren;
    }
}

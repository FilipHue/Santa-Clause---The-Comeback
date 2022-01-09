package entities;

import java.util.ArrayList;

public final class PapaNoel {

    private ArrayList<Child> childrenList = new ArrayList<>();

    public PapaNoel() {
    }

    public ArrayList<Child> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(final ArrayList<Child> childrenList) {
        this.childrenList = childrenList;
    }
}

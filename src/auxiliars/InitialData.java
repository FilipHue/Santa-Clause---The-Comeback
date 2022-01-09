package auxiliars;

import entities.Child;
import entities.Present;

import java.util.ArrayList;

public class InitialData {
    private ArrayList<Child> children;
    private ArrayList<Present> santaGiftsList;

    public InitialData() {
    }

    /**
     *
     * @return
     */
    public ArrayList<Child> getChildren() {
        return children;
    }

    /**
     *
     * @param children
     */
    public void setChildren(final ArrayList<Child> children) {
        this.children = children;
    }

    /**
     *
     * @return
     */
    public ArrayList<Present> getSantaGiftList() {
        return santaGiftsList;
    }

    /**
     *
     * @param santaGiftList
     */
    public void setSantaGiftsList(final ArrayList<Present> santaGiftList) {
        this.santaGiftsList = santaGiftList;
    }
}

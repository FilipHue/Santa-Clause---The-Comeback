package utils;

import entities.Child;

import java.util.ArrayList;

public interface Strategy {

    /**
     *
     * @param children
     * @return
     */
    ArrayList<Child> doStrategy(ArrayList<Child> children);
}

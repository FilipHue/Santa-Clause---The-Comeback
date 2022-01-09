package main;

import checker.Checker;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Constants;
import database.Database;
import entities.PapaNoel;
import fileio.InputLoader;

import java.io.File;
import java.io.IOException;

/**
 * Class used to run the code
 */
public final class Main {

    private Main() {
        ///constructor for checkstyle
    }
    /**
     * This method is used to call the checker which calculates the score
     * @param args
     *          the arguments used to call the main method
     */
    public static void main(final String[] args) throws IOException {
        for (int i = 1; i <= Constants.TESTS_NUMBER; i++) {
            String input = String.format("./tests/test%d.json", i);
            String output = String.format("./output/out_%d.json", i);
            action(input, output);
        }

        Checker.calculateScore();
    }

    /**
     *
     * @param filePath1 for input files
     * @param filePath2 for output files
     */

    public static void action(final String filePath1, final String filePath2) throws IOException {

        InputLoader inputLoader = new InputLoader(filePath1);

        ObjectMapper objectMapper = new ObjectMapper();

        PapaNoel papaNoel = new PapaNoel();
        inputLoader.readData();
        Database database = Database.getInstance();

        FirstRound.initSetup(database, papaNoel);

        for (int year = 1; year <= database.getNumberOfYears(); year++) {
            NextRound.playRound(database, papaNoel, year);
        }

        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(filePath2), database.getAnnualChanges());
        database.clearAll();
    }
}

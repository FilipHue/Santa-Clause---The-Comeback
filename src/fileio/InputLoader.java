package fileio;

import auxiliars.InputParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.Database;

import java.io.IOException;
import java.nio.file.Paths;


public final class InputLoader {

    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * read the data from the json file
     */
    public void readData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputParser inputParser;
        inputParser = objectMapper.readValue(Paths.get(inputPath).toFile(), InputParser.class);
        Database database = Database.getInstance();
        database.setNumberOfYears(inputParser.getNumberOfYears());
        database.setSantaBudget(inputParser.getSantaBudget());
        database.setChildren(inputParser.getInitialData().getChildren());
        database.setPresents(inputParser.getInitialData().getSantaGiftList());
        database.setChanges(inputParser.getAnnualChanges());
    }
}

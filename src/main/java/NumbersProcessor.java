import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NumbersProcessor {

    public static final String INPUT_FILE_NAME = "input.txt";
    public static final String OUTPUT_FILE_NAME = "output.txt";

    public static final String CANT_WRITE_OUTPUT_TXT = "Can't write to output.txt. Writing to System.out...";
    public static final String CANT_READ_INPUT = "Can't read input";
    public static final String WRONG_NUMBER_FORMAT = "Wrong number format";
    public static final String NOT_ENOUGH_ARGS = "Not enough arguments";
    public static final String CANT_CALCULATE = "Can't calculate";

    private File rootFolder;

    public NumbersProcessor(File rootFolder) {
        this.rootFolder = rootFolder;
    }

    private List<String> readInput() throws IOException {
        InputStream inputStream = new FileInputStream(rootFolder + File.separator + INPUT_FILE_NAME);
        return IOUtils.readLines(inputStream);
    }

    private List<String> processInput(List<String> input) {
        List<String> output = new ArrayList<String>();
        for(String line : input) {
            String[] args = line.split(" ");
            int result = 0;
            try {
                int a = Integer.parseInt(args[0]);
                int b = Integer.parseInt(args[1]);
                result = (a*a)/b+(b*b)/a;
                output.add(String.valueOf(result));
            } catch (NumberFormatException e) {
                output.add(WRONG_NUMBER_FORMAT);
            } catch (IndexOutOfBoundsException e) {
                output.add(NOT_ENOUGH_ARGS);
            } catch (ArithmeticException e) {
                output.add(CANT_CALCULATE);
            }
        }
        return output;
    }

    private void writeOutput(List<String> output) {
        PrintStream printStream;
        try {
            printStream = new PrintStream(rootFolder + File.separator + OUTPUT_FILE_NAME);
        } catch (IOException e) {
            System.err.println(CANT_WRITE_OUTPUT_TXT);
            printStream = System.out;
        }
        printStream.println(String.join("\n", output));
        printStream.close();
    }

    public void run() {
        try {
            writeOutput(processInput(readInput()));
        } catch (IOException e) {
            System.err.println(CANT_READ_INPUT);
        }
    }

}

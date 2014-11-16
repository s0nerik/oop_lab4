import java.io.File;

public class Main {

    public static void main(String[] args) {
        new NumbersProcessor(new File(".")).run();
        new ImageSlicer(new File(".")).run();
        ComplexTester.main(new String[]{});
    }
}
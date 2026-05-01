import ParserMethods.ParserChecker;
import ParserMethods.ParserFunctions;

public class Main {
    public static void main(String[] args) {

        ParserFunctions parserFunctions = new ParserFunctions();
        ParserChecker parserChecker = new ParserChecker();
        App app = new App(parserFunctions,parserChecker);
        app.run();
    }
}
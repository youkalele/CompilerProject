package parser;
import scanner.Scanner;

public class CMinusParser implements Parser {
    private Scanner scan;
    public CMinusParser (String file)
    {
        scan = new Scanner(file);
    }
    public Program parse() {
        return;
    }
}

package pipe.ucl.constructor;

import pipe.ucl.models.InputLine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputParser {

//    private ArrayList<TransAssertion> TransAssertionList = new ArrayList<>();

//    public ArrayList<TransAssertion> getTransAssertionList() {
//        return TransAssertionList;
//    }

    private ArrayList<InputLine> ParsedReadDataLinesList = new ArrayList<> ();

    public ArrayList<InputLine> getParsedReadDataLinesList() {
        return this.ParsedReadDataLinesList;
    }

    public InputParser() {

        List<String> readDataLinesList =  ReadFile();
        for (String readDataLine:readDataLinesList) {
            InputLine parsedReadDataLine = ParseReadDataLine(readDataLine);
            if(parsedReadDataLine == null)
                continue;
            ParsedReadDataLinesList.add(parsedReadDataLine);
        }
    }

    public void EmptyParsedReadDataLinesList() {
        ParsedReadDataLinesList.clear ();
    }

    private List<String> ReadFile() {

//        String inputFileName = "input_washer.txt";
        String inputFileName = "input_washer_newNotation.txt";

        List<String> readDataLinesList = new ArrayList<> ();

        try {

            Stream<String> stream = Files.lines (Paths.get (ClassLoader.getSystemResource (inputFileName)
                    .toURI ()));

            readDataLinesList = stream
                    .collect (Collectors.toList ());

        } catch (IOException e) {
            System.out.println ("ERROR while reading input file: " + e);
        } catch (URISyntaxException e) {
            System.out.println ("ERROR while reading input file: " + e);
        }
        catch (Exception e) {
            System.out.println ("ERROR while reading input file: " + e);
        }

        readDataLinesList.forEach (System.out::println);

        return readDataLinesList;
    }

    // This functions splits a string by commas with the exception of commas inside quotes (https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes)
    public static String[] SplitByCommasExceptQuotesAndParentheses(String stringToSplit) {
        String otherThanQuote = " [^\"] ";
        String quotedString = String.format(" \" %s* \" ", otherThanQuote);
        String regex = String.format("(?x) "+ // enable comments, ignore white spaces
                        ",                         "+ // match a comma
                        "(?=                       "+ // start positive look ahead
                        "  (?:                     "+ //   start non-capturing group 1
                        "    %s*                   "+ //     match 'otherThanQuote' zero or more times
                        "    %s                    "+ //     match 'quotedString'
                        "  )*                      "+ //   end group 1 and repeat it zero or more times
                        "  %s*                     "+ //   match 'otherThanQuote'
                        "  $                       "+ // match the end of the string
                        ")                         "+ // stop positive look ahead
                        "(?![^\\(]*\\))            ", // ignore commas in parentheses as well
                otherThanQuote, quotedString, otherThanQuote);

        String[] tokens = stringToSplit.split(regex, -1);
        return tokens;
    }

    private InputLine ParseReadDataLine(String readDataLine) {

        String type;
        String[] parameters;
        String content;

        int indexBegin = readDataLine.indexOf ("(");
        if (indexBegin == -1)
            return null;

        int indexEnd = readDataLine.lastIndexOf (")");
        if (indexEnd == -1)
            return null;

        type = readDataLine.substring (0, indexBegin).toUpperCase ();
        content = readDataLine.substring (indexBegin + 1, indexEnd);

        content = content.replaceAll ("'", "\"");
        content = content.replaceAll ("’", "\"");
        content = content.replaceAll ("'", "\"");
        content = content.replaceAll ("`", "\"");

        // Replaces all white spaces, except white spaces inside quotes. Takes into consideration escaped quotes. (https://stackoverflow.com/questions/9577930/regular-expression-to-select-all-whitespace-that-isnt-in-quotes)
        content = content.replaceAll ("\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)", "");


      //  parameters = content.split (Pattern.quote (","));
        parameters = SplitByCommasExceptQuotesAndParentheses (content);

        return new InputLine (type, parameters);

    }

}

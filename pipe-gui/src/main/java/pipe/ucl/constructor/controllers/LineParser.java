package pipe.ucl.constructor.controllers;

import pipe.ucl.constructor.models.InputLine;
import pipe.ucl.contract.models.*;
import pipe.ucl.contract.models.TimeOperators.AfterTime;
import pipe.ucl.contract.models.TimeOperators.BeforeTime;
import pipe.ucl.contract.models.TimeOperators.TimeOperator;
import pipe.ucl.contract.models.TimeOperators.ScheduleTime;
import pipe.ucl.contract.models.TimeSpanOperators.RDuring;
import pipe.ucl.contract.models.TimeSpanOperators.RThroughout;

import java.util.Arrays;

import static java.lang.String.format;

public class LineParser {

    // This functions splits a string by commas with the exception of commas inside quotes and in parentheses
    private static String SplitByCommasExceptQuotesAndParentheses_otherThanQuote = " [^\"] ";
    private static String SplitByCommasExceptQuotesAndParentheses_quotedString = format(" \" %s* \" ", SplitByCommasExceptQuotesAndParentheses_otherThanQuote);
    private static String SplitByCommasExceptQuotesAndParentheses_regex = format("(?x) " + // enable comments, ignore white spaces
                    ",                         " + // match a comma
                    "(?=                       " + // start positive look ahead
                    "  (?:                     " + //   start non-capturing group 1
                    "    %s*                   " + //     match 'SplitByCommasExceptQuotesAndParentheses_otherThanQuote' zero or more times
                    "    %s                    " + //     match 'SplitByCommasExceptQuotesAndParentheses_quotedString'
                    "  )*                      " + //   end group 1 and repeat it zero or more times
                    "  %s*                     " + //   match 'SplitByCommasExceptQuotesAndParentheses_otherThanQuote'
                    "  $                       " + // match the end of the string
                    ")                         " + // stop positive look ahead
                    "(?![^\\(]*\\))            " + // ignore commas in parentheses
                    "(?![^\\[]*\\])            " + // ignore commas in brackets
                    "(?![^\\{]*\\})            ", // ignore commas in braces
            SplitByCommasExceptQuotesAndParentheses_otherThanQuote, SplitByCommasExceptQuotesAndParentheses_quotedString, SplitByCommasExceptQuotesAndParentheses_otherThanQuote);

    public static String[] SplitByCommasExceptQuotesAndParentheses(String stringToSplit) {
        String[] tokens = stringToSplit.split(SplitByCommasExceptQuotesAndParentheses_regex, -1);
        return tokens;
    }

    public static String NormaliseQuotes(String inputString) {
        inputString = inputString.replaceAll("'", "\"");
        inputString = inputString.replaceAll("’", "\"");
        inputString = inputString.replaceAll("'", "\"");
        inputString = inputString.replaceAll("`", "\"");
        return inputString;
    }

    public static String RemoveWhiteSpacesAndCommentExceptInsideQuotes(String inputString) {
        // Removes all white spaces, except white spaces inside quotes.
        // Takes into consideration escaped quotes.
        // (https://stackoverflow.com/questions/9577930/regular-expression-to-select-all-whitespace-that-isnt-in-quotes)
        inputString = inputString.replaceAll("\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)", "");

        // Removes all comments
        // (https://blog.ostermiller.org/find-comment)
        inputString = inputString.replaceAll("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)", "");


        return inputString;
    }

    public static InputLine ParseLine(String inputString) {

        String type;
        String[] parameters;
        String content;

       inputString = NormaliseQuotes(inputString);
       inputString = RemoveWhiteSpacesAndCommentExceptInsideQuotes(inputString);


        int indexBegin;
        int indexEnd;
        Boolean quoteFound;

        String beginString = "([{";
        String endString = ")]}";

        for (indexBegin = 0, quoteFound = Boolean.FALSE; indexBegin < inputString.length(); indexBegin++) {

            if (inputString.charAt(indexBegin) == '\"') {
                if (quoteFound == Boolean.TRUE) {
                    quoteFound = Boolean.FALSE;
                } else {
                    quoteFound = Boolean.TRUE;
                }
                continue;
            }

            if (quoteFound == Boolean.TRUE)
                continue;

            if (beginString.indexOf(inputString.charAt(indexBegin)) == -1)
                continue;

            break;
        }

        if (indexBegin < inputString.length()) {

            for (indexEnd = inputString.length() - 1; indexEnd > indexBegin; indexEnd--) {

                if (inputString.charAt(indexEnd) == '\"') {
                    if (quoteFound == Boolean.TRUE) {
                        quoteFound = Boolean.FALSE;
                    } else {
                        quoteFound = Boolean.TRUE;
                    }
                    continue;
                }

                if (quoteFound == Boolean.TRUE)
                    continue;

                if (endString.indexOf(inputString.charAt(indexEnd)) == -1)
                    continue;

                break;
            }

            if (indexEnd > indexBegin) { // We have parameters
                type = inputString.substring(0, indexBegin).toUpperCase();
                content = inputString.substring(indexBegin + 1, indexEnd);
                parameters = SplitByCommasExceptQuotesAndParentheses(content);
                return new InputLine(type, parameters);
            }
        }
        // There are not parameters
        return new InputLine(inputString, null);
    }

    public static Object GetToken(InputLine inputLine, Contract currentContract) {

        if (inputLine == null)
            return null;

        String inputType = inputLine.getType().substring(0);
        String[] inputParameters = inputLine.getParameterList();

        if (inputType == null || inputLine.equals("") || inputLine.equals(" "))
            return null;

        if (inputParameters == null || inputParameters.length < 2)
            return inputType;

        // Type should be upper case for easier checking
        inputType = inputType.toUpperCase();

        if (Arrays.asList(Contract.Labels).contains(inputType))
            return new Contract(inputParameters);
        if (Arrays.asList(ActionElement.Labels).contains(inputType))
            return new ActionElement(inputParameters, currentContract);
        if (Arrays.asList(DiscreteTimeElement.Labels).contains(inputType))
            return new DiscreteTimeElement(inputParameters, currentContract);
        if (Arrays.asList(EventElement.Labels).contains(inputType))
            return new EventElement(inputParameters, currentContract);
        if (Arrays.asList(GateElement.Labels).contains(inputType))
            return new GateElement(inputParameters, currentContract);
        if (Arrays.asList(PartyElement.Labels).contains(inputType))
            return new PartyElement(inputParameters, currentContract);
        if (Arrays.asList(StateElement.Labels).contains(inputType))
            return new StateElement(inputParameters, currentContract);
        if (Arrays.asList(TimeSpanElement.Labels).contains(inputType))
            return new TimeSpanElement(inputParameters, currentContract);
        if (Arrays.asList(RDuring.Labels).contains(inputType))
            return new RDuring(inputParameters, currentContract);
        if (Arrays.asList(RThroughout.Labels).contains(inputType))
            return new RThroughout(inputParameters, currentContract);
        if (Arrays.asList(TransAssertion.Labels).contains(inputType))
            return new TransAssertion(inputParameters, currentContract);

        return inputType;

    }

    public static TimeOperator GetDateOperatorToken(InputLine inputLine, Contract currentContract) {

        if (inputLine == null)
            return null;

        String inputType = inputLine.getType().substring(0);
        String[] inputParameters = inputLine.getParameterList();

        if (inputType == null || inputLine.equals("") || inputLine.equals(" "))
            return null;

        if (inputParameters == null || inputParameters.length < 2)
            return null;

        // Type should be upper case for easier checking
        inputType = inputType.toUpperCase();

        if (Arrays.asList(AfterTime.Labels).contains(inputType))
            return new AfterTime(inputParameters, currentContract);
        if (Arrays.asList(BeforeTime.Labels).contains(inputType))
            return new BeforeTime(inputParameters, currentContract);
        if (Arrays.asList(ScheduleTime.Labels).contains(inputType))
            return new ScheduleTime(inputParameters, currentContract);

        return null;
    }
}

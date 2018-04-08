package pipe.ucl.contract.models;

import pipe.ucl.constructor.controllers.LineParser;
import pipe.ucl.constructor.models.InputLine;
import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.TimeOperators.TimeOperator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DiscreteTimeElement extends ContractElement implements GetDiscreteTime {

    private static long NextId = 1;
    public final static String MainLabel = "DT";
    public final static String[] Labels = {"DT", "DISCRETE-TIME"};
    public final static String MainFullLabel = "DISCRETE-TIME";
    public final static String CONTRACT_START = "CONTRACT_START";
    public final static String CONTRACT_TERMINATION = "CONTRACT_TERMINATION";

    public static final List<String> DATE_FORMAT =
            Arrays.asList(
                    "yyyy",
                    "yyyy-MM",
                    "yyyy-MM-dd",
                    "yyyy-MM-dd 'at' HH",
                    "yyyy-MM-dd 'at' HH:mm",
                    "yyyy-MM-dd 'at' HH:mm:ss",
                    "yyyy-MM-dd 'at' HH:mm:ss.SSS"
            );

    protected GregorianCalendar discreteTime;
    protected TimeOperator timeOperator;

    public DiscreteTimeElement(String name) {
        super(name);
    }

    public DiscreteTimeElement(String id, String name) {
        super(id, name);
    }

    public DiscreteTimeElement() {
        super();
        discreteTime = null;
    }

    public DiscreteTimeElement(Contract contract) {
        super(new String[0], contract);
    }


    public DiscreteTimeElement(GregorianCalendar gregorianCalendar) {
        super();
        discreteTime = gregorianCalendar;
    }

    public DiscreteTimeElement(String[] parameters, Contract parentContract) {
        super(parameters, parentContract);
        if (parameters.length < 3) return;

        // First we check for a discrete date (GregorianCalendar)
        for (String dateFormat : DATE_FORMAT) {

            SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(dateFormat);
            SIMPLE_DATE_FORMAT.setLenient(false);

            try {
                Date date = SIMPLE_DATE_FORMAT.parse(parameters[2]);
                discreteTime = new GregorianCalendar();
                discreteTime.setLenient(false);
                discreteTime.setTime(date);
            } catch (Exception e) {
                elementCorrectness = Boolean.FALSE;
            }
        }

        try {
            discreteTime.getTime();
            elementCorrectness = Boolean.TRUE;
            return;
        } catch (Exception e) {
            elementCorrectness = Boolean.FALSE;
        }

        //Now we check for a time keyword
        try {
            if (parameters[2].toUpperCase().equals(CONTRACT_START)) {
                this.discreteTime = parentContract.getContractStartTime().GetCalendarTime();
                elementCorrectness = Boolean.TRUE;
                return;
            }
            if (parameters[2].toUpperCase().equals(CONTRACT_TERMINATION)) {
                this.discreteTime = parentContract.getContractEndTime().GetCalendarTime();
                elementCorrectness = Boolean.TRUE;
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            elementCorrectness = Boolean.FALSE;
        }

        // Now we check for a date operator
        discreteTime = null;
        try {
            InputLine parsedObject = LineParser.ParseLine(parameters[2]);
//            Object tempDateOperator =
            this.timeOperator = LineParser.GetDateOperatorToken(parsedObject, parentContract);
            elementCorrectness = Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            elementCorrectness = Boolean.FALSE;
        }
    }


    @Override
    public DiscreteTimeElement GetDiscreteTime() {
        return this;
    }

    @Override
    public String GetDiscreteTimeString() {

        if (discreteTime != null) {

            try {

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss.SSS");
                formatter.setLenient(false);

                return formatter.format(discreteTime.getTime());

            } catch (Exception err) {
                System.out.print("Error when getting " + MainLabel + " value: " + err);
            }

        }


        if (timeOperator.GetDiscreteTime() != null) {
            discreteTime = timeOperator.GetDiscreteTime().GetCalendarTime();
        }
        return timeOperator.GetDiscreteTimeString();

    }

    public GregorianCalendar GetCalendarTime() {
        return discreteTime;
    }

    public void setDiscreteDate(GregorianCalendar discreteTime) {
        this.discreteTime = discreteTime;
    }

    public static String ConvertGregorialCalendarToString(GregorianCalendar _gregorianCalendar) {

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss.SSS");
            formatter.setLenient(false);

            return formatter.format(_gregorianCalendar.getTime());
        }
        catch (Exception err) {
            System.out.println(err);
            return "ERROR";
        }
    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + GetDiscreteTimeString();
        return string;
    }

    @Override
    protected String getUniqueId() {
        String id = MainLabel + NextId;
        NextId++;
        return id;
    }

    @Override
    public String getMainFullLabel() {
        return MainFullLabel;
    }
}

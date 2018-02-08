package pipe.ucl.contract.models;

import pipe.ucl.constructor.controllers.LineParser;
import pipe.ucl.constructor.models.InputLine;
import pipe.ucl.contract.interfaces.GetDiscreteTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DiscreteTimeElement extends ContractElement implements GetDiscreteTime {

    private static long NextId = 1;
    public final static String MainLabel = "DT";
    public final static String[] Labels = {"DT", "DISCRETE-TIME"};
//    public final static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm");
public final static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    protected GregorianCalendar discreteTime;
    protected GetDiscreteTime dateOperator;

    public DiscreteTimeElement(String name) {
        super(name);
    }

    public DiscreteTimeElement(String id, String name) {
        super(id, name);
    }

    public DiscreteTimeElement(String[] parameters) {
        super(parameters);
        if (parameters.length < 3) return;

        // First we check for a discrete date (GregorianCalendar)
        try {
            DATE_FORMAT.setLenient(false);
            Date date = DATE_FORMAT.parse(parameters[2]);
            discreteTime = new GregorianCalendar();
            discreteTime.setLenient(false);
            discreteTime.setTime(date);
            discreteTime.getTime();
            elementCorrectness = Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            elementCorrectness = Boolean.FALSE;
        }
        if(elementCorrectness == Boolean.TRUE)
            return;

        discreteTime = null;

        // Now we check for a date operator

        try {
            InputLine parsedObject = LineParser.ParseLine(parameters[2]);
            this.dateOperator = (GetDiscreteTime) LineParser.GetToken(parsedObject);
            elementCorrectness = Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {
        return this;
    }

    public GregorianCalendar GetCalendarTime() {
        return discreteTime;
    }

    public void setDiscreteDate(GregorianCalendar discreteTime) {
        this.discreteTime = discreteTime;
    }

    @Override
    public String toString() {
        String string = id + " : " + name + " : " + discreteTime;
        return string;
    }

    @Override
    protected String getUniqueId() {
        String id = MainLabel + NextId;
        NextId++;
        return id;
    }
}

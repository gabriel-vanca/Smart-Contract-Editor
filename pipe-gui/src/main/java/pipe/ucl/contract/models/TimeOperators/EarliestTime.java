package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.Contract;
import pipe.ucl.contract.models.DiscreteTimeElement;

import java.util.GregorianCalendar;

public class EarliestTime extends TimeOperator {

    public final static String MainLabel = "EST";
    public final static String[] Labels = {"EST", "EARLIEST"};
    private final String[] inputParametersLocal;

    protected GetDiscreteTime referenceTime1 = null;
    protected GetDiscreteTime referenceTime2 = null;

    public EarliestTime(String[] inputParameters, Contract currentContract) {
        super(currentContract);

        inputParametersLocal = inputParameters;

        if (inputParameters == null || inputParameters.length < 2)
            return;

        try {

            referenceTime1 = GetDiscreteTimeFromParameters(inputParameters[0]);
            referenceTime2 = GetDiscreteTimeFromParameters(inputParameters[1]);

        } catch (Exception err) {
            System.out.print(err);
        }

    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {

        try {

            DiscreteTimeElement tempDiscreteTime1 = referenceTime1.GetDiscreteTime();
            DiscreteTimeElement tempDiscreteTime2 = referenceTime2.GetDiscreteTime();

            if (tempDiscreteTime1 == null) {
                if (tempDiscreteTime2 == null) {
                    return null;
                } else {
                    return tempDiscreteTime2;
                }
            }
            if (tempDiscreteTime2 == null) {
                return tempDiscreteTime1;
            }

            GregorianCalendar tempCalendarTime1 = tempDiscreteTime1.GetCalendarTime();
            GregorianCalendar tempCalendarTime2 = tempDiscreteTime1.GetCalendarTime();

            if (tempCalendarTime1 == null) {
                if (tempCalendarTime2 == null) {
                    return null;
                } else {
                    return tempDiscreteTime2;
                }
            }
            if (tempCalendarTime2 == null) {
                return tempDiscreteTime1;
            }

            if(tempCalendarTime1.compareTo(tempCalendarTime2) < 0) {
                return tempDiscreteTime1;
            } else {
                return tempDiscreteTime2;
            }

        } catch (Exception err) {
            System.out.print("Error when getting " + MainLabel + " value: " + err);
        }

        return null;
    }

    @Override
    public String GetDiscreteTimeString() {

        DiscreteTimeElement discreteTime = GetDiscreteTime();

        if (discreteTime != null)
            return discreteTime.toString();
        else
            return toString();
    }

    public String toString() {

        try {
            if (referenceTime1 != null && referenceTime2 != null)
                return MainLabel + "(" + referenceTime1.GetDiscreteTimeString() + ", " + referenceTime2.GetDiscreteTimeString() + ")";
            else
                return "";
        } catch (Exception err) {
            System.out.print("Error when getting " + MainLabel + " value: " + err);
        }

        return "";
    }
}

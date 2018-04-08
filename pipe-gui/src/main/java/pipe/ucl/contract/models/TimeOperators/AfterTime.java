package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.Contract;
import pipe.ucl.contract.models.DiscreteTimeElement;

import java.util.GregorianCalendar;

public class AfterTime extends TimeOperator {

    public final static String MainLabel = "AFT";
    public final static String[] Labels = {"AFT", "AFTER"};
    private final String[] inputParametersLocal;

    protected GetDiscreteTime referenceTime = null;

    protected int Years = 0, Months = 0, Days = 0, Hours = 0, Minutes = 0, Seconds = 0, Milliseconds = 0;


    public AfterTime(String[] inputParameters, Contract currentContract) {
        super(currentContract);

        inputParametersLocal = inputParameters;

        if (inputParameters == null || inputParameters.length < 2)
            return;

        try {

            referenceTime = GetDiscreteTimeFromParameters(inputParameters[0]);

            try {
                this.Years = Integer.parseInt(inputParameters[1]);
                if (inputParameters.length < 3) return;
                this.Months = Integer.parseInt(inputParameters[2]);
                if (inputParameters.length < 4) return;
                this.Days = Integer.parseInt(inputParameters[3]);
                if (inputParameters.length < 5) return;
                this.Hours = Integer.parseInt(inputParameters[4]);
                if (inputParameters.length < 6) return;
                this.Minutes = Integer.parseInt(inputParameters[5]);
                if (inputParameters.length < 7) return;
                this.Seconds = Integer.parseInt(inputParameters[6]);
                if (inputParameters.length < 8) return;
                this.Milliseconds = Integer.parseInt(inputParameters[7]);

            } catch (Exception err) {
                System.out.println("Could not parse differential time");
            }


//            discreteTimeElementWrapper[2] = inputParameters[1];
//            if(discreteTimeElementWrapper[2].contains("and")) {
//                discreteTimeElementWrapper[2] = discreteTimeElementWrapper[2].replace("and", "at");
//            }
//            differentialTime = new DiscreteTimeElement(discreteTimeElementWrapper, currentContract).GetCalendarTime();
        } catch (Exception err) {
            System.out.print(err);
        }

    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {

        GregorianCalendar referenceTimeCal = null;

        try {

            DiscreteTimeElement tempDiscreteTime = referenceTime.GetDiscreteTime();

            if (tempDiscreteTime == null)
                return null;

            referenceTimeCal = tempDiscreteTime.GetCalendarTime();

            if (referenceTimeCal == null)
                return null;


            referenceTimeCal.add(GregorianCalendar.YEAR, Years);
            referenceTimeCal.add(GregorianCalendar.MONTH, Months);
            referenceTimeCal.add(GregorianCalendar.DAY_OF_MONTH, Days);
            referenceTimeCal.add(GregorianCalendar.HOUR_OF_DAY, Hours);
            referenceTimeCal.add(GregorianCalendar.MINUTE, Minutes);
            referenceTimeCal.add(GregorianCalendar.SECOND, Seconds);
            referenceTimeCal.add(GregorianCalendar.MILLISECOND, Milliseconds);

        } catch (Exception err) {
            System.out.print("Error when getting " + MainLabel + " value: " + err);
        }

        if (referenceTimeCal == null)
            return null;
        return new DiscreteTimeElement(referenceTimeCal);
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
            if (referenceTime != null)
                return MainLabel + "(" + referenceTime.GetDiscreteTimeString() + ", " + inputParametersLocal + ")";
            else
                return "";
        } catch (Exception err) {
            System.out.print("Error when getting " + MainLabel + " value: " + err);
        }

        return "";
    }
}

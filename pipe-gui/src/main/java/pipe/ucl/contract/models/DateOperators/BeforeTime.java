package pipe.ucl.contract.models.DateOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.Contract;
import pipe.ucl.contract.models.DiscreteTimeElement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class BeforeTime extends DateOperator {

    public final static String MainLabel = "BFR";
    public final static String[] Labels = {"BFR", "BEFORE"};

    protected GetDiscreteTime referenceTime;
    protected GregorianCalendar differentialTime;
    private Boolean computed;

//    public BeforeTime(GetDiscreteTime referenceTime, GregorianCalendar differentialTime) {
//        this.referenceTime = referenceTime;
//        this.differentialTime = differentialTime;
//    }

    public BeforeTime(String[] inputParameters, Contract currentContract) {
        super(currentContract);

        if(inputParameters == null || inputParameters.length < 2)
            return;

        try {
            String[] discreteTimeElementWrapper = new String[3];
            discreteTimeElementWrapper[0] = discreteTimeElementWrapper[1] = MainLabel;
            discreteTimeElementWrapper[2] = inputParameters[0];
            referenceTime = new DiscreteTimeElement(discreteTimeElementWrapper, currentContract);

            discreteTimeElementWrapper[2] = inputParameters[1];
            if(discreteTimeElementWrapper[2].contains("and")) {
                discreteTimeElementWrapper[2] = discreteTimeElementWrapper[2].replace("and", "at");
            }
            differentialTime = new DiscreteTimeElement(discreteTimeElementWrapper, currentContract).GetCalendarTime();
        }catch(Exception err) {
            System.out.print(err);
        }
    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {
        GregorianCalendar referenceTimeCal = null;

        try {
            referenceTimeCal = referenceTime.GetDiscreteTime().GetCalendarTime();

            if(referenceTimeCal == null)
                return null;

            if(differentialTime == null || computed == Boolean.TRUE)
                return new DiscreteTimeElement(referenceTimeCal);

            computed = Boolean.TRUE;

            referenceTimeCal.add(GregorianCalendar.YEAR * (-1), differentialTime.get(GregorianCalendar.YEAR));
            referenceTimeCal.add(GregorianCalendar.MONTH * (-1), differentialTime.get(GregorianCalendar.MONTH));
            referenceTimeCal.add(GregorianCalendar.DAY_OF_MONTH * (-1), differentialTime.get(GregorianCalendar.DAY_OF_MONTH));
            referenceTimeCal.add(GregorianCalendar.HOUR_OF_DAY * (-1), differentialTime.get(GregorianCalendar.HOUR_OF_DAY));
            referenceTimeCal.add(GregorianCalendar.MINUTE * (-1), differentialTime.get(GregorianCalendar.MINUTE));
            referenceTimeCal.add(GregorianCalendar.SECOND * (-1), differentialTime.get(GregorianCalendar.SECOND));
            referenceTimeCal.add(GregorianCalendar.MILLISECOND * (-1), differentialTime.get(GregorianCalendar.MILLISECOND));
        } catch (Exception err) {
            System.out.print("Error when getting " + MainLabel + " value: " + err);
        }

        if(referenceTimeCal == null)
            return null;
        return new DiscreteTimeElement(referenceTimeCal);
    }

    @Override
    public String GetDiscreteTimeString() {

        DiscreteTimeElement discreteTime = GetDiscreteTime();

        if(discreteTime != null)
            return discreteTime.toString();
        else
            return toString();
    }

    public String toString() {

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'and' HH:mm:ss.SSS");
            formatter.setLenient(false);

            return MainLabel + "(" + referenceTime.GetDiscreteTimeString() + ", " + formatter.format(differentialTime.getTime()) + ")";
        } catch (Exception err) {
            System.out.print("Error when getting " + MainLabel + " value: " + err);
        }

        return "";
    }

}

package pipe.ucl.contract.models.DateOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.DiscreteTimeElement;

import java.util.GregorianCalendar;

public class AfterTime implements GetDiscreteTime {

    public final static String MainLabel = "AFT";
    public final static String[] Labels = {"AFT", "AFTER"};

    protected GetDiscreteTime referenceTime;
    protected GregorianCalendar differencialTime;

    public AfterTime(GetDiscreteTime referenceTime, GregorianCalendar differencialTime) {
        this.referenceTime = referenceTime;
        this.differencialTime = differencialTime;
    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {
        GregorianCalendar referenceTimeCal = null;

        try {
            referenceTimeCal = referenceTime.GetDiscreteTime().GetCalendarTime();
            referenceTimeCal.add(GregorianCalendar.YEAR, differencialTime.get(GregorianCalendar.YEAR));
            referenceTimeCal.add(GregorianCalendar.MONTH, differencialTime.get(GregorianCalendar.MONTH));
            referenceTimeCal.add(GregorianCalendar.DAY_OF_MONTH, differencialTime.get(GregorianCalendar.DAY_OF_MONTH));
            referenceTimeCal.add(GregorianCalendar.HOUR_OF_DAY, differencialTime.get(GregorianCalendar.HOUR_OF_DAY));
            referenceTimeCal.add(GregorianCalendar.MINUTE, differencialTime.get(GregorianCalendar.MINUTE));
            referenceTimeCal.add(GregorianCalendar.SECOND, differencialTime.get(GregorianCalendar.SECOND));
            referenceTimeCal.add(GregorianCalendar.MILLISECOND, differencialTime.get(GregorianCalendar.MILLISECOND));
        } catch (Exception err) {
            System.out.print("Error when getting " + MainLabel + " value: " + err);
        }

        if(referenceTimeCal == null)
            return null;
        return new DiscreteTimeElement(referenceTimeCal);
    }

    public String toString() {
        return MainLabel + "(" + referenceTime  + ", " + differencialTime + ")";
    }
}

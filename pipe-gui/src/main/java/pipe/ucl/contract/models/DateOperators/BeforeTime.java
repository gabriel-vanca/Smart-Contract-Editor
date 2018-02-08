package pipe.ucl.contract.models.DateOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.DiscreteTimeElement;

import java.util.GregorianCalendar;

public class BeforeTime implements GetDiscreteTime {

    public final static String MainLabel = "BFR";
    public final static String[] Labels = {"BFR", "BEFORE"};

    protected GetDiscreteTime referenceTime;
    protected GregorianCalendar differencialTime;

    public BeforeTime(GetDiscreteTime referenceTime, GregorianCalendar differencialTime) {
        this.referenceTime = referenceTime;
        this.differencialTime = differencialTime;
    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {
        GregorianCalendar referenceTimeCal = null;

        try {
            referenceTimeCal = referenceTime.GetDiscreteTime().GetCalendarTime();
            referenceTimeCal.add(GregorianCalendar.YEAR * (-1), differencialTime.get(GregorianCalendar.YEAR));
            referenceTimeCal.add(GregorianCalendar.MONTH * (-1), differencialTime.get(GregorianCalendar.MONTH));
            referenceTimeCal.add(GregorianCalendar.DAY_OF_MONTH * (-1), differencialTime.get(GregorianCalendar.DAY_OF_MONTH));
            referenceTimeCal.add(GregorianCalendar.HOUR_OF_DAY * (-1), differencialTime.get(GregorianCalendar.HOUR_OF_DAY));
            referenceTimeCal.add(GregorianCalendar.MINUTE * (-1), differencialTime.get(GregorianCalendar.MINUTE));
            referenceTimeCal.add(GregorianCalendar.SECOND * (-1), differencialTime.get(GregorianCalendar.SECOND));
            referenceTimeCal.add(GregorianCalendar.MILLISECOND * (-1), differencialTime.get(GregorianCalendar.MILLISECOND));
        } catch (Exception err) {
            System.out.print("Error when getting " + MainLabel + " value: " + err);
        }

        if(referenceTimeCal == null)
            return null;
        return new DiscreteTimeElement(referenceTimeCal);
    }

    public String toString() {
        return MainLabel + "(" + referenceTime  + ", " + differencialTime+ ")";
    }

}

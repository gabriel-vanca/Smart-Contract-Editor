package pipe.ucl.contract.models.DateOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.DiscreteTimeElement;

import java.util.GregorianCalendar;

public class AfterTime implements GetDiscreteTime {

    public final static String MainLabel = "AFT";
    public final static String[] Labels = {"AFT", "AFTER"};

    protected GetDiscreteTime referenceTime;
    protected GregorianCalendar differentialTime;

    public AfterTime(GetDiscreteTime referenceTime, GregorianCalendar differentialTime) {
        this.referenceTime = referenceTime;
        this.differentialTime = differentialTime;
    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {
        GregorianCalendar referenceTimeCal = null;

        try {
            referenceTimeCal = referenceTime.GetDiscreteTime().GetCalendarTime();
            referenceTimeCal.add(GregorianCalendar.YEAR, differentialTime.get(GregorianCalendar.YEAR));
            referenceTimeCal.add(GregorianCalendar.MONTH, differentialTime.get(GregorianCalendar.MONTH));
            referenceTimeCal.add(GregorianCalendar.DAY_OF_MONTH, differentialTime.get(GregorianCalendar.DAY_OF_MONTH));
            referenceTimeCal.add(GregorianCalendar.HOUR_OF_DAY, differentialTime.get(GregorianCalendar.HOUR_OF_DAY));
            referenceTimeCal.add(GregorianCalendar.MINUTE, differentialTime.get(GregorianCalendar.MINUTE));
            referenceTimeCal.add(GregorianCalendar.SECOND, differentialTime.get(GregorianCalendar.SECOND));
            referenceTimeCal.add(GregorianCalendar.MILLISECOND, differentialTime.get(GregorianCalendar.MILLISECOND));
        } catch (Exception err) {
            System.out.print("Error when getting " + MainLabel + " value: " + err);
        }

        if(referenceTimeCal == null)
            return null;
        return new DiscreteTimeElement(referenceTimeCal);
    }

    public String toString() {
        return MainLabel + "(" + referenceTime  + ", " + differentialTime + ")";
    }
}

package pipe.ucl.contract.models.DateOperators;

import pipe.ucl.contract.models.Contract;
import pipe.ucl.contract.models.DiscreteTimeElement;

public class ScheduleTime extends DateOperator {

    public final static String MainLabel = "SCH";
    public final static String[] Labels = {"SCH", "SCHEDULE"};

    public ScheduleTime(String[] inputParameters, Contract currentContract) {
        super(currentContract);
    }

    @Override
    public DiscreteTimeElement GetDiscreteTime() {
        return null;
    }

    @Override
    public String GetDiscreteTimeString() {
        return null;
    }

    //TODO: To be implemented
}

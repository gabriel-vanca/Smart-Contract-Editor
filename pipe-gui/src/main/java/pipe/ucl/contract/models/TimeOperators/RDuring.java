package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;

public class RDuring extends TimeOperator {

    public final static String MainLabel = "RD";
    public final static String[] Labels = {"RD", "DURING"};

    public RDuring(GetDiscreteTime initialDate, GetDiscreteTime finalDate) {
        super(initialDate, finalDate);
    }

    public RDuring(String[] parameters) {
        super(parameters);
    }

    @Override
    public String toString() {
        return MainLabel + "(" + initialDate.GetDiscreteTimeString() + ", " + finalDate.GetDiscreteTimeString() + ")";
    }

}

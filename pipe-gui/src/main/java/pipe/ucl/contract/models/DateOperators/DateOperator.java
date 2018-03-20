package pipe.ucl.contract.models.DateOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.Contract;

public abstract class DateOperator implements GetDiscreteTime {

    protected Contract parentContract = null;

    public DateOperator(Contract currentContract) {
        this.parentContract = currentContract;
    }
}

package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.Contract;
import pipe.ucl.contract.models.ContractElement;
import pipe.ucl.contract.models.DiscreteTimeElement;

import java.util.List;

public abstract class TimeOperator {

    protected GetDiscreteTime initialDate = null;
    protected GetDiscreteTime finalDate = null;

    private Contract parentContract = null;

    public TimeOperator(GetDiscreteTime initialDate, GetDiscreteTime finalDate) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public TimeOperator(String[] parameters, Contract currentContract) {
        if (parameters.length < 2)
            return;

        List<ContractElement> contractElements = currentContract.getContractElementsList();

        try {
            for (ContractElement currentContractElement : contractElements) {
                if (currentContractElement.getId().equals(parameters[0])) {
                    this.initialDate = (GetDiscreteTime) currentContractElement;
                    break;
                }
            }
        } catch (Exception err) {

        }

        if (this.initialDate == null) {
            try {
                String[] discreteTimeElementWrapper = new String[3];
                discreteTimeElementWrapper[0] = discreteTimeElementWrapper[1] = "TIME_OPERATOR";
                discreteTimeElementWrapper[2] = parameters[0];

                this.initialDate = new DiscreteTimeElement(discreteTimeElementWrapper, currentContract);
            } catch (Exception err2) {
                System.out.println("TimeSpan initial DT not found");
            }
        }


        try {
            for (ContractElement currentContractElement : contractElements) {
                if (currentContractElement.getId().equals(parameters[1])) {
                    this.finalDate = (GetDiscreteTime) currentContractElement;
                    break;
                }
            }
        } catch (Exception err) {
        }

        if (this.finalDate == null) {
            try {
                String[] discreteTimeElementWrapper = new String[3];
                discreteTimeElementWrapper[0] = discreteTimeElementWrapper[1] = "TIME_OPERATOR";
                discreteTimeElementWrapper[2] = parameters[1];

                this.finalDate = new DiscreteTimeElement(discreteTimeElementWrapper, currentContract);
            } catch (Exception err2) {
                System.out.println("TimeSpan final DT not found");
            }
        }
    }


    public GetDiscreteTime getInitialDate() {
        return initialDate;
    }

    public GetDiscreteTime getFinalDate() {
        return finalDate;
    }

    public abstract String toString();

}

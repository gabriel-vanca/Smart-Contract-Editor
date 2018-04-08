package pipe.ucl.contract.models.TimeOperators;

import pipe.ucl.contract.interfaces.GetDiscreteTime;
import pipe.ucl.contract.models.Contract;
import pipe.ucl.contract.models.ContractElement;
import pipe.ucl.contract.models.DiscreteTimeElement;

import java.util.List;

public abstract class TimeOperator implements GetDiscreteTime {

    protected Contract parentContract = null;

    public TimeOperator(Contract currentContract) {
        this.parentContract = currentContract;
    }

    public GetDiscreteTime GetDiscreteTimeFromParameters(String inputParameter) {


        GetDiscreteTime discreteTime = null;

        try {

            List<ContractElement> contractElements = parentContract.getContractElementsList();
            for (ContractElement currentContractElement : contractElements) {
                if (currentContractElement.getId().equals(inputParameter)) {
                    discreteTime = (GetDiscreteTime) currentContractElement;
                    break;
                }
            }

            if (discreteTime == null) {
                String[] discreteTimeElementWrapper = new String[3];
                discreteTimeElementWrapper[0] = discreteTimeElementWrapper[1] = "GetDiscreteTimeFromParameters";
                discreteTimeElementWrapper[2] = inputParameter;
                discreteTime = new DiscreteTimeElement(discreteTimeElementWrapper, parentContract);
            }
        } catch (Exception err) {
            System.out.print(err);
        }

        return discreteTime;
    }
}

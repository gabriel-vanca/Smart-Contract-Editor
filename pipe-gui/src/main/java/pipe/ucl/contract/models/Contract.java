package pipe.ucl.contract.models;

import pipe.ucl.contract.enums.StateType;
import pipe.ucl.gui.ConsoleFrameManager;
import pipe.ucl.gui.ContractTreeManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contract {

    public final static String MainLabel = "CONTRACT";
    public final static String[] Labels = {"CONTRACT"};
    public final static String MainFullLabel = "CONTRACT";

    private String name;

    private ArrayList<ContractElement> ContractElementsList;

    private ContractTreeManager contractTreeManager;

    private ConsoleFrameManager consoleFrameManager;

    private StateElement startState;

    private DiscreteTimeElement contractStartTime;
    private DiscreteTimeElement contractEndTime;

    public Contract(String name) {
        this.name = name;
        ContractElementsList = new ArrayList<ContractElement>();
        contractTreeManager = new ContractTreeManager(this);
        consoleFrameManager = new ConsoleFrameManager(this);

        consoleFrameManager.addLineToLabel("Opened " + name);
    }

    public Contract(String[] inputParameters) {
        if (inputParameters.length < 3)
            return;

        name = inputParameters[0];


        String[] discreteTimeElementWrapper = new String[3];
        discreteTimeElementWrapper[0] = discreteTimeElementWrapper[1] = "CONTRACT";

        discreteTimeElementWrapper[2] = inputParameters[1];
        contractStartTime = new DiscreteTimeElement(discreteTimeElementWrapper);

        if (inputParameters[2].toUpperCase().equals("N/A")) {
            contractEndTime = new DiscreteTimeElement();
        } else {
            discreteTimeElementWrapper[2] = inputParameters[2];
            contractEndTime = new DiscreteTimeElement(discreteTimeElementWrapper);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        contractTreeManager.Rename(name);
    }

    public List<ContractElement> getContractElementsList() {
        return Collections.unmodifiableList(ContractElementsList);
    }

    public void addContractElement(ContractElement contractElement) {
        ContractElementsList.add(contractElement);

        contractTreeManager.addNewContractElement(contractElement);

        if (StateElement.class.isInstance(contractElement)) {
            StateElement stateElement = (StateElement) contractElement;
            if(stateElement.getType()==StateType.START) {
                startState = stateElement;
                stateElement.setdiscreteTime(contractStartTime);
            }
        }
    }

    public ContractTreeManager getContractTreeManager() {
        return contractTreeManager;
    }

    public ConsoleFrameManager getConsoleFrameManager() {
        return consoleFrameManager;
    }

    public StateElement getStartState() {
        return startState;
    }

    public void setContractProperties(Contract _contract) {
        this.name = _contract.getName();
        contractTreeManager.Rename(name);
        this.contractStartTime = _contract.contractStartTime;
        this.contractEndTime = _contract.contractEndTime;
    }

    public DiscreteTimeElement getContractStartTime() {
        return contractStartTime;
    }

    public DiscreteTimeElement getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(DiscreteTimeElement contractEndTime) {
        this.contractEndTime = contractEndTime;
    }
}

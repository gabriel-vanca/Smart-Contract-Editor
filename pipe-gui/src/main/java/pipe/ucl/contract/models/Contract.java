package pipe.ucl.contract.models;

import pipe.ucl.gui.ConsoleFrameManager;
import pipe.ucl.gui.ContractTreeManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contract {

    private String name;

    private ArrayList<ContractElement> ContractElementsList;

    private ContractTreeManager contractTreeManager;

    private ConsoleFrameManager consoleFrameManager;

    public Contract(String name) {
        this.name = name;
        ContractElementsList = new ArrayList<ContractElement>();
        contractTreeManager = new ContractTreeManager(this);
        consoleFrameManager = new ConsoleFrameManager();

        consoleFrameManager.addLineToLabel("Opened " + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContractElement> getContractElementsList() {
        return Collections.unmodifiableList(ContractElementsList);
    }

    public void addContractElement(ContractElement contractElement) {
        ContractElementsList.add(contractElement);

        contractTreeManager.addNewContractElement(contractElement);
    }

    public ContractTreeManager getContractTreeManager() {
        return contractTreeManager;
    }

    public ConsoleFrameManager getConsoleFrameManager() {
        return consoleFrameManager;
    }

}

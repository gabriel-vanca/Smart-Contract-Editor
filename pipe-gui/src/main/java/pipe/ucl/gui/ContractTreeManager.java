package pipe.ucl.gui;

import pipe.ucl.contract.models.Contract;
import pipe.ucl.contract.models.ContractElement;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ContractTreeManager {

    private final DefaultMutableTreeNode treeRoot;
    private DefaultTreeModel contractJTreeModel;

    private JTree contractJTree;

    private Contract contract;

    public ContractTreeManager(Contract _contract)
    {
        this.contract = _contract;

        if(contract == null) {
            treeRoot = new DefaultMutableTreeNode("Empty Contract");
            return;
        }

        //create the root node
        treeRoot = new DefaultMutableTreeNode(contract.getName());

        //create the contractJTree by passing in the root node
        contractJTree = new JTree(treeRoot);
        contractJTreeModel = (DefaultTreeModel) contractJTree.getModel();
    }

    public void Rename(String name) {
        treeRoot.setUserObject(name);
        contractJTreeModel.nodeChanged(treeRoot);
    }

    public JTree getModuleTree() {
        return contractJTree;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public JTree getContractJTree() {
        return contractJTree;
    }

    public void addNewContractElement(ContractElement contractElement) {

        int numberOfChildren = treeRoot.getChildCount();

        for(int index=0; index < numberOfChildren; index++) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeRoot.getChildAt(index);

            if(contractElement.getMainFullLabel() == node.toString()) {
                addNewContractElementAsChildToNode(node, contractElement);
                return;
            }
        }

        DefaultMutableTreeNode newCategoryNode = new DefaultMutableTreeNode(contractElement.getMainFullLabel());
        treeRoot.add(newCategoryNode);
        addNewContractElementAsChildToNode(newCategoryNode, contractElement);
        contractJTreeModel.reload(treeRoot);
    }

    private void addNewContractElementAsChildToNode(DefaultMutableTreeNode parentNode, ContractElement contractElement) {
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(contractElement.toString());
        parentNode.add(newNode);
    }


}

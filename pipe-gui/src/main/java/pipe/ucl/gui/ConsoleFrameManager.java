package pipe.ucl.gui;

import pipe.ucl.constructor.controllers.ConsoleManager;
import pipe.ucl.contract.models.Contract;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsoleFrameManager {

    private final JSplitPane querryPane;
    private final JSplitPane submissionPane;

    private JButton btnCompute;

    private JTextArea consoleOutput;

    private JTextArea consoleInput;

    private Contract contract;

    public Contract getContract() {
        return contract;
    }

    public ConsoleFrameManager(Contract _contract)
    {
        this.contract = _contract;

        consoleInput =  new JTextArea("");
        consoleInput.setEditable(true);
        consoleInput.setLineWrap(true);
        consoleInput.setWrapStyleWord(true);
        JScrollPane jScrollPane1ConsoleInput = new JScrollPane(consoleInput);

        consoleOutput =  new JTextArea("");
        consoleOutput.setEditable(false);
        consoleOutput.setLineWrap(true);
        consoleOutput.setWrapStyleWord(true);
        JScrollPane jScrollPane1ConsoleOutput = new JScrollPane(consoleOutput);

        btnCompute = new JButton("Compute");
        btnCompute.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Compute();
            }
        });

        submissionPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jScrollPane1ConsoleInput, btnCompute);
        submissionPane.setContinuousLayout(true);
        submissionPane.setOneTouchExpandable(true);
        submissionPane.setDividerSize(6);

        querryPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,jScrollPane1ConsoleOutput, submissionPane);
        querryPane.setContinuousLayout(true);
        querryPane.setOneTouchExpandable(true);
        querryPane.setDividerSize(6);

        if(contract == null) {
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                querryPane.setDividerLocation(0.70);
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
//                        querryPane.setDividerLocation(0.65);
                        submissionPane.setDividerLocation(submissionPane.getSize().height - 40);
                    }
                });
            }
        });



        // JPanel bounds
//        pnlButton.setBounds(800, 800, 200, 100);
//
//        // Adding to JFrame
//        pnlButton.add(btnCompute);
//        add(pnlButton);
//

//
//
//        setTitle("Simple example");
//        setSize(300, 200);
//
//        setLocationRelativeTo(null);
//
//        //chnge to exit on close to exit program.
//        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void Compute() {

        addLineToLabel("Computing...");
        addLineToLabel("");

        String results = ConsoleManager.Compute(this);

        addLineToLabel(results);
        addLineToLabel("");
    }

    public void addLineToLabel(String textToAdd) {
        String existingText = consoleOutput.getText();
        consoleOutput.setText(existingText+textToAdd+"\n");
    }

    public String getTextFromConsoleInput() {
        return consoleInput.getText();
    }


//    public void addLineToLabel(String textToAdd) {
//        String existingText = consoleOutput.getText();
//        consoleOutput.setText(existingText+textToAdd);
//    }

    public JSplitPane getQuerryPane() {
        return querryPane;
    }

    public JSplitPane getSubmissionPane() {
        return submissionPane;
    }

    public JButton getBtnCompute() {
        return btnCompute;
    }

    public JTextArea getConsoleOutput() {
        return consoleOutput;
    }

    public JTextArea getConsoleInput() {
        return consoleInput;
    }
}
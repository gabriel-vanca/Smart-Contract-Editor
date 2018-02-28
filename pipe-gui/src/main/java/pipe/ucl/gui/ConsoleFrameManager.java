package pipe.ucl.gui;

import javax.swing.*;

public class ConsoleFrameManager {

    private final JSplitPane querryPane;
    private final JSplitPane submissionPane;

    private JButton btnCompute;

    private JLabel querryLabel;

    private JTextArea querryField;


    public ConsoleFrameManager() {

        querryPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JLabel(""),
                new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JTextArea(), new JButton("Compute")));

        querryLabel = (JLabel) querryPane.getTopComponent();
        submissionPane = (JSplitPane) querryPane.getBottomComponent();
        querryField = (JTextArea) submissionPane.getTopComponent();
        btnCompute = (JButton) submissionPane.getBottomComponent();

//        btnCompute.setBounds(60, 400, 220, 30);
//        btnCompute.setSize(new Dimension(40, 40));

        querryPane.setContinuousLayout(true);
        querryPane.setOneTouchExpandable(true);
        querryPane.setDividerSize(6);

        submissionPane.setContinuousLayout(true);
        submissionPane.setOneTouchExpandable(true);
        submissionPane.setDividerSize(6);

        querryLabel.setVerticalAlignment(JLabel.TOP);

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                querryPane.setDividerLocation(0.65);
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
//        btnCompute.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("OUtput to console");
//
//            }
//        });
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

    public void addLineToLabel(String textToAdd) {
        String existingText = querryLabel.getText();
        querryLabel.setText(existingText+textToAdd+"\n");
    }


//    public void addLineToLabel(String textToAdd) {
//        String existingText = querryLabel.getText();
//        querryLabel.setText(existingText+textToAdd);
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

    public JLabel getQuerryLabel() {
        return querryLabel;
    }

    public JTextArea getQuerryField() {
        return querryField;
    }
}
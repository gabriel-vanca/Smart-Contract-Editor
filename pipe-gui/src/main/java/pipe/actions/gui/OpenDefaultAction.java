package pipe.actions.gui;

import pipe.controllers.application.PipeApplicationController;
import pipe.ucl.constructor.controllers.Constructor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Opens a PNML Petri net
 */
@SuppressWarnings("serial")
public class OpenDefaultAction extends GuiAction {


    /**

    /**
     * Constructor
     * @param applicationController Main PIPE application controller
     */
    public OpenDefaultAction(PipeApplicationController applicationController) {
        super("Open Example Contract", "Open Example Contract", KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());

    }

    /**
     * When this action is performed it shows the file dialog and processes the file selected for loading
     * @param e event 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
//UCL
       Constructor.LoadDefaultContractExample();

    }
}

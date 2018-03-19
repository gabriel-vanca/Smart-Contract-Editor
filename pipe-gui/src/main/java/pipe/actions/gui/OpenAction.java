package pipe.actions.gui;

import pipe.controllers.PetriNetController;
import pipe.controllers.application.PipeApplicationController;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.ucl.constructor.controllers.Constructor;
import pipe.utilities.gui.GuiUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Opens a PNML Petri net
 */
@SuppressWarnings("serial")
public class OpenAction extends GuiAction {

    /**
     * Main PIPE application controller
     */
    private final PipeApplicationController applicationController;


    /**
     * File dialog for selecting a Petri net to load
     */
    private final FileDialog fileChooser;

    /**
     * Constructor
     * @param applicationController Main PIPE application controller
     * @param fileChooser file dialog responsible for choosing files to load
     */
    public OpenAction(PipeApplicationController applicationController, FileDialog fileChooser) {
        super("Open", "Open", KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        this.applicationController = applicationController;
        this.fileChooser = fileChooser;
    }

    /**
     * When this action is performed it shows the file dialog and processes the file selected for loading
     * @param e event 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
//UCL
        try {
            fileChooser.setVisible(true);
            fileChooser.setMode(FileDialog.LOAD);
            fileChooser.setMultipleMode(false);
            File[] files = fileChooser.getFiles();
            if(files.length < 1) {
              return;
            }
            File file = files[0];

            if (file.exists() &&
                    file.isFile() &&
                    file.canRead()) {

//                PetriNetReader petriNetIO = new PetriNetIOImpl();
//                PetriNet petriNet = new PetriNet();
////      petriNetIO.read(file.getAbsolutePath());
//
//                PetriNetManager manager = Constructor.getApplicationController().getManager();
//
//                manager.createFromFile(file);

//                manager.namePetriNetFromFile(petriNet, file);
//                manager.changeSupport.firePropertyChange("New Petri net!", (Object)null, petriNet);


                PetriNetController currentPetriNetController = applicationController.getActivePetriNetController();
                PetriNet petriNet = currentPetriNetController.getPetriNet();
                if(!petriNet.isEmpty())
                    petriNet = applicationController.createNewTabFromFile(file);

//                String filePath = file.getAbsolutePath();
//                applicationController.createEmptyPetriNet();
                Constructor.LoadContractFile(file.toURI(), petriNet);
            } else {
                String message = "File \"" + file.getName() + "\" does not exist.";
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception err) {
            System.out.println("File could not be loaded due to error: " + err);
            GuiUtils.displayErrorMessage(null, "File could not be loaded due to error: " + err);
        }

    }
}

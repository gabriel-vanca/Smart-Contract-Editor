package pipe.actions.gui;

import pipe.controllers.application.PipeApplicationController;
import pipe.utilities.gui.GuiUtils;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Unfolds a coloured Petri net to an ordinary Petri net.
 */
@SuppressWarnings("serial")
public class UnfoldAction extends GuiAction {

    /**
     * Constructor
     * @param pipeApplicationController main PIPE application controller
     */
    public UnfoldAction(PipeApplicationController pipeApplicationController) {
        super("unfoldAction", "Unfold Petri Net", KeyEvent.VK_U,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | InputEvent.SHIFT_DOWN_MASK);
    }

    /**
     * Unfolds the Petri net and creates a new Petri net tab with it.
     *
     * This functionality has not yet been implemented.
     *
     * @param e event 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GuiUtils.displayErrorMessage(null,
                "Currently unfolding a Petri net is not supported due to errors found in the PIPE 4 implementation. We hope to fix them soon!");
        //        PetriNetController controller = pipeApplicationController.getActivePetriNetController();

        //TODO: SHOW MESSAGES
        //        if(controller.getNetTokens().size() > 1 && controller.hasFunctionalRatesOrWeights()){
        //        	JOptionPane.showMessageDialog(null, "This is CGSPN. The analysis will only apply to default color (black). \r\n"+"This net contains functional rates or weights. The unfolder will replace these rates or weights with " +
        //        			"their current constant tokens.",
        //					"Information", JOptionPane.INFORMATION_MESSAGE);
        //        }else if(!(pipeApplicationView.getCurrentPetriNetView().getEnabledTokenClassNumber()>1) && pipeApplicationView.getCurrentPetriNetView().hasFunctionalRatesOrWeights()){
        //        	JOptionPane.showMessageDialog(null, "This net contains functional rates or weights. The unfolder will replace these rates or weights with " +
        //        			"their current constant tokens.",
        //					"Information", JOptionPane.INFORMATION_MESSAGE);
        //        }else if((pipeApplicationView.getCurrentPetriNetView().getEnabledTokenClassNumber()>1) && !pipeApplicationView.getCurrentPetriNetView().hasFunctionalRatesOrWeights()){
        //        	JOptionPane.showMessageDialog(null, "This is CGSPN. The analysis will only apply to default color (black). ",
        //					"Information", JOptionPane.INFORMATION_MESSAGE);
        //        }

        //        PetriNet petriNet = controller.getPetriNet();
        //        Expander expander = new Expander(petriNet);
        //        PetriNet unfolded = expander.unfold();
        //TODO: REIMPLEMENT
        //        pipeApplicationController.createNewTab(unfolded, pipeApplicationView);
    }
}

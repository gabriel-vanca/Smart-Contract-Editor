package pipe.actions.gui;

import pipe.constants.FontSizeEnum;
import pipe.constants.GUIConstants;
import pipe.controllers.PetriNetController;
import pipe.controllers.application.PipeApplicationController;
import pipe.gui.PetriNetTab;
import pipe.ucl.constructor.controllers.Constructor;
import pipe.views.TextLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Map;

public class FontSizeAction extends GuiAction {
    private final PipeApplicationController applicationController;

    public FontSizeAction(PipeApplicationController applicationController) {
        super("Cycle font size", "Change the font size (alt-F)", KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK);
        this.applicationController = applicationController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        GUIConstants.LABEL_FONT_SIZE = FontSizeEnum.getNextInCycle(GUIConstants.LABEL_FONT_SIZE);
        TextLabel.font = new Font("Dialog", Font.BOLD, GUIConstants.LABEL_FONT_SIZE.getValue());

        for(TextLabel textLabel : Constructor.TextLabels) {
            if(textLabel == null)
                continue;

            textLabel.UpdateFont();
        }

        for(Map.Entry<PetriNetTab, PetriNetController>  currentPetriNetTabControllerPair : applicationController.netControllers.entrySet()) {

            Constructor.Layout(currentPetriNetTabControllerPair.getValue().getPetriNet());
            currentPetriNetTabControllerPair.getKey().repaint();
        }
    }

}

package pipe.actions.gui;

import pipe.controllers.DragManager;
import pipe.controllers.PetriNetController;
import pipe.controllers.SelectionManager;
import pipe.controllers.application.PipeApplicationController;
import pipe.gui.PetriNetTab;
import pipe.views.PipeApplicationView;
import pipe.gui.imperial.pipe.models.petrinet.Connectable;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class SelectAction extends CreateAction {
    private final PipeApplicationView pipeApplicationView;

    private final PipeApplicationController pipeApplicationController;

    public SelectAction(PipeApplicationModel applicationModel, PipeApplicationView pipeApplicationView,
                        PipeApplicationController pipeApplicationController) {
        super("Select", "Select components (alt-S)", KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK, applicationModel);
        this.pipeApplicationView = pipeApplicationView;
        this.pipeApplicationController = pipeApplicationController;
    }

    @Override
    public void doAction(MouseEvent event, PetriNetController petriNetController) {
        // No action needed

    }

    @Override
    public <T extends Connectable> void doConnectableAction(T connectable, PetriNetController petriNetController) {
        if (!petriNetController.isSelected(connectable)) {
            SelectionManager selectionManager =
                    pipeApplicationController.getActivePetriNetController().getSelectionManager();
            selectionManager.clearSelection();
        }
        petriNetController.select(connectable);
        DragManager dragManager = petriNetController.getDragManager();
        dragManager.setDragStart(connectable.getCentre());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if (pipeApplicationView.areAnyTabsDisplayed()) {
            PetriNetTab petriNetTab = pipeApplicationView.getCurrentTab();
            SelectionManager selectionManager =
                    pipeApplicationController.getActivePetriNetController().getSelectionManager();
            selectionManager.enableSelection();
            petriNetTab.setCursorType("arrow");
        }
    }
}

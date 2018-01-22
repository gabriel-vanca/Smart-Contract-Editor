package pipe.handlers;

import pipe.actions.petrinet.SplitArcAction;
import pipe.controllers.PetriNetController;
import pipe.actions.gui.PipeApplicationModel;
import pipe.gui.widgets.ArcWeightEditorPanel;
import pipe.gui.widgets.EscapableDialog;
import pipe.gui.imperial.pipe.models.petrinet.Arc;
import pipe.gui.imperial.pipe.models.petrinet.ArcType;
import pipe.gui.imperial.pipe.models.petrinet.Connectable;

import javax.swing.*;
import java.awt.Container;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Class used to implement methods corresponding to mouse events on arcs.
 */
public class ArcHandler<S extends Connectable, T extends Connectable>
        extends PetriNetObjectHandler<Arc> {


    /**
     * Constructor
     * @param contentPane content pane 
     * @param component  underlying Petri net component
     * @param controller controller of the Petri net the arc belongs to
     * @param applicationModel main PIPE application model
     */
    public ArcHandler(Container contentPane, Arc component, PetriNetController controller, PipeApplicationModel applicationModel) {
        super(contentPane, component, controller, applicationModel);
        enablePopup = true;
    }

    /**
     * Creates the popup menu that the user will see when they right click on a
     * component
     * @param e mouse event 
     */
    @Override
    public JPopupMenu getPopup(MouseEvent e) {
        int popupIndex = 0;
        JMenuItem menuItem;
        JPopupMenu popup = super.getPopup(e);


        Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), contentPane);
        menuItem = new JMenuItem(new SplitArcAction(petriNetController.getArcController(component),
                point));
        menuItem.setText("Split Arc Segment");
        popup.insert(menuItem, popupIndex++);

        popup.insert(new JPopupMenu.Separator(), popupIndex++);

        if (component.getType().equals(ArcType.NORMAL)) {
            menuItem = new JMenuItem("Edit Weight");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showEditor();
                }
            });
            popup.insert(menuItem, popupIndex++);

            popup.insert(new JPopupMenu.Separator(), popupIndex);
        }
        return popup;
    }


    /**
     * Noop action
     * @param e mouse event 
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        //Do nothing on mouse drag
    }

    /**
     * Noop action
     * @param e mouse event 
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //Do nothing on mouse wheel move
    }

    /**
     * Shows the arc editor menu
     */
    public void showEditor() {
        // Build interface
        Window owner = SwingUtilities.getWindowAncestor(contentPane);

        EscapableDialog guiDialog = new EscapableDialog(owner, "PIPE", true);

        ArcWeightEditorPanel arcWeightEditor = new ArcWeightEditorPanel(guiDialog.getRootPane(), petriNetController,
                petriNetController.getArcController(component));

        guiDialog.add(arcWeightEditor);

        guiDialog.getRootPane().setDefaultButton(null);

        guiDialog.setResizable(false);

        // Make window fit contents' preferred size
        guiDialog.pack();

        // Move window to the middle of the screen
        guiDialog.setLocationRelativeTo(null);

        guiDialog.setVisible(true);
    }
}


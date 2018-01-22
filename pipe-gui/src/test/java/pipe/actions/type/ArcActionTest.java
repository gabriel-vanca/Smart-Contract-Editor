package pipe.actions.type;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pipe.actions.gui.ArcAction;
import pipe.actions.gui.PipeApplicationModel;
import pipe.controllers.PetriNetController;
import pipe.controllers.application.PipeApplicationController;
import pipe.controllers.arcCreator.ArcActionCreator;
import pipe.gui.PetriNetTab;
import pipe.gui.imperial.pipe.models.petrinet.*;
import pipe.gui.imperial.pipe.visitor.connectable.arc.ArcSourceVisitor;
import pipe.historyActions.component.AddPetriNetObject;
import pipe.utilities.transformers.Contains;
import pipe.views.InhibitorArcHead;
import pipe.views.PipeApplicationView;
import pipe.views.TemporaryArcView;

import javax.swing.event.UndoableEditListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ArcActionTest {
    @Mock
    UndoableEditListener listener;

    @Mock
    PipeApplicationModel applicationModel;

    private ArcAction action;

    @Mock
    private PipeApplicationController mockApplicationController;

    @Mock
    private PetriNetController mockController;

    @Mock
    private PetriNet mockNet;

    @Mock
    private PipeApplicationView mockApplicationView;

    @Mock
    private PetriNetTab mockTab;

    private static final String ACTIVE_TOKEN_ID = "Default";

    @Mock
    private ArcSourceVisitor mockSourceVisitor;

    @Mock
    private ArcActionCreator mockCreatorVisitor;

    @Before
    public void setUp() {
        when(mockController.getPetriNet()).thenReturn(mockNet);

        when(mockApplicationView.getSelectedTokenName()).thenReturn(ACTIVE_TOKEN_ID);

        when(mockController.getPetriNetTab()).thenReturn(mockTab);
        when(mockController.getSelectedToken()).thenReturn(ACTIVE_TOKEN_ID);

        when(mockApplicationController.getActivePetriNetController()).thenReturn(mockController);

        action = new ArcAction("Inhibitor Arc", "Add an inhibitor arc", KeyEvent.VK_H, InputEvent.ALT_DOWN_MASK,
                mockSourceVisitor, mockCreatorVisitor, applicationModel, mockApplicationController,
                new InhibitorArcHead());
        action.addUndoableEditListener(listener);
    }

    @Test
    public void createsTemporaryArcViewOnClick() {
        Transition transition = new DiscreteTransition("", "");
        when(mockSourceVisitor.canStart(transition)).thenReturn(true);
        action.doConnectableAction(transition, mockController);

        verify(mockTab).add(any(TemporaryArcView.class));
    }

    @Test
    public void removesTemporaryArcViewOnRealCreation() {
        Transition transition = new DiscreteTransition("", "");
        when(mockSourceVisitor.canStart(transition)).thenReturn(true);
        action.doConnectableAction(transition, mockController);


        DiscretePlace place = new DiscretePlace("", "");
        when(mockCreatorVisitor.canCreate(transition, place)).thenReturn(true);

        action.doConnectableAction(place, mockController);
        verify(mockTab).remove(any(TemporaryArcView.class));
    }

    @Test
    public void callsCreateOnRealCreation() {
        Transition transition = new DiscreteTransition("", "");
        when(mockSourceVisitor.canStart(transition)).thenReturn(true);
        action.doConnectableAction(transition, mockController);


        DiscretePlace place = new DiscretePlace("", "");
        when(mockCreatorVisitor.canCreate(transition, place)).thenReturn(true);

        action.doConnectableAction(place, mockController);
        verify(mockCreatorVisitor).createOutboundArc(eq(place), eq((DiscreteTransition) transition), anyListOf(ArcPoint.class));
    }

    @Test
    public void updatesEndPointOnAction() {
        //TODO: WORK OUT HOW TO TEST SINCE TEMP ARC VIEW ISNT PUBLIC
    }

    @Test
    public void createsUndoAction() {
        Transition transition = new DiscreteTransition("", "");
        when(mockSourceVisitor.canStart(transition)).thenReturn(true);
        action.doConnectableAction(transition, mockController);

        DiscretePlace place = new DiscretePlace("", "");
        when(mockCreatorVisitor.canCreate(transition, place)).thenReturn(true);

        OutboundArc mockArc = mock(OutboundArc.class);
        when(mockCreatorVisitor.createOutboundArc(any(DiscretePlace.class), any(DiscreteTransition.class), anyListOf(ArcPoint.class))).thenReturn(
                mockArc);


        action.doConnectableAction(place, mockController);


        AddPetriNetObject addAction = new AddPetriNetObject(mockArc, mockNet);
        verify(listener).undoableEditHappened(argThat(Contains.thisAction(addAction)));
    }

}

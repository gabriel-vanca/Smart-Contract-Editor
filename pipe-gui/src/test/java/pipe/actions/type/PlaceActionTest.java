package pipe.actions.type;

import matchers.component.HasMultiple;
import matchers.component.HasXY;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pipe.actions.gui.PipeApplicationModel;
import pipe.actions.gui.PlaceAction;
import pipe.controllers.PetriNetController;
import pipe.gui.imperial.pipe.models.petrinet.DiscretePlace;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.historyActions.component.AddPetriNetObject;
import pipe.utilities.transformers.Contains;

import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.event.MouseEvent;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlaceActionTest {

    @Mock
    private PetriNetController mockController;

    @Mock
    private PetriNet mockNet;

    private PlaceAction action;

    @Mock
    UndoableEditListener listener;

    @Mock
    PipeApplicationModel applicationModel;

    @Before
    public void setUp() {
        action = new PlaceAction(applicationModel);
        action.addUndoableEditListener(listener);
        when(mockController.getPetriNet()).thenReturn(mockNet);
        when(mockController.getUniquePlaceName()).thenReturn("P0");
    }

    @Test
    public void createsPlaceOnClick() {

        Point point = new Point(10, 20);
        MouseEvent mockEvent = mock(MouseEvent.class);
        when(mockEvent.getClickCount()).thenReturn(1);
        when(mockEvent.getPoint()).thenReturn(point);

        action.doAction(mockEvent, mockController);

        verify(mockNet).addPlace(argThat(new HasMultiple<DiscretePlace>(new HasXY(point.getX(), point.getY()))));
    }

    @Test
    public void createsUndoOnClickAction() {
        Point point = new Point(10, 20);
        MouseEvent mockEvent = mock(MouseEvent.class);
        when(mockEvent.getClickCount()).thenReturn(1);
        when(mockEvent.getPoint()).thenReturn(point);

        action.doAction(mockEvent, mockController);
        DiscretePlace place = new DiscretePlace("P0", "P0");
        place.setX(10);
        place.setY(20);

        UndoableEdit action = new AddPetriNetObject(place, mockNet);
        verify(listener).undoableEditHappened(argThat(Contains.thisAction(action)));
    }


}

package pipe.handler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pipe.actions.gui.CreateAction;
import pipe.controllers.PetriNetController;
import pipe.gui.PetriNetTab;
import pipe.actions.gui.PipeApplicationModel;
import pipe.handlers.PetriNetMouseHandler;
import pipe.handlers.mouse.MouseUtilities;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

import java.awt.Point;
import java.awt.event.MouseEvent;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MouseHandlerTest {
    PetriNetMouseHandler handler;

    @Mock
    PetriNetController mockController;

    @Mock
    PetriNet mockNet;

    @Mock
    PetriNetTab mockTab;

    @Mock
    MouseUtilities mockUtilities;


    @Mock
    PipeApplicationModel mockModel;

    @Mock
    MouseEvent mockEvent;

    @Mock
    CreateAction mockAction;

    @Before
    public void setup() {
        handler = new PetriNetMouseHandler(mockModel, mockController, mockTab);

        when(mockEvent.getPoint()).thenReturn(new Point(0, 0));
        when(mockUtilities.isLeftMouse(mockEvent)).thenReturn(true);
        when(mockModel.getSelectedAction()).thenReturn(mockAction);
    }

    @Test
    public void doesNoActionIfAnimating() {
        when(mockModel.isInAnimationMode()).thenReturn(true);
        handler.mousePressed(mockEvent);
        verify(mockAction, never()).doAction(any(MouseEvent.class), any(PetriNetController.class));
    }

}

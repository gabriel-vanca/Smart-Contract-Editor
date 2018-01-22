package pipe.actions.type;


import matchers.component.HasAnnotationFields;
import matchers.component.HasMultiple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pipe.actions.gui.AnnotationAction;
import pipe.controllers.PetriNetController;
import pipe.actions.gui.PipeApplicationModel;
import pipe.historyActions.component.AddPetriNetObject;
import pipe.utilities.transformers.Contains;
import pipe.gui.imperial.pipe.models.petrinet.Annotation;
import pipe.gui.imperial.pipe.models.petrinet.AnnotationImpl;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

import javax.swing.event.UndoableEditListener;
import java.awt.Point;
import java.awt.event.MouseEvent;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationActionTest {
    @Mock
    private PetriNetController mockController;

    @Mock
    private PetriNet mockNet;

    @Mock
    UndoableEditListener listener;

    private AnnotationAction action;


    @Mock
    PipeApplicationModel applicationModel;

    @Before
    public void setUp() {
        action = new AnnotationAction(applicationModel);
        action.addUndoableEditListener(listener);
        when(mockController.getPetriNet()).thenReturn(mockNet);
    }

    @Test
    public void createsAnnotationOnClick() {

        Point point = new Point(10, 20);
        MouseEvent mockEvent = mock(MouseEvent.class);
        when(mockEvent.getClickCount()).thenReturn(1);
        when(mockEvent.getPoint()).thenReturn(point);

        action.doAction(mockEvent, mockController);

        verify(mockNet).addAnnotation(
                argThat(new HasMultiple<>(new HasAnnotationFields("Enter text here", 10, 20, 100, 50))));
    }

    @Test
    public void createsUndoOnClickAction() {
        Point point = new Point(10, 20);
        MouseEvent mockEvent = mock(MouseEvent.class);
        when(mockEvent.getClickCount()).thenReturn(1);
        when(mockEvent.getPoint()).thenReturn(point);

        action.doAction(mockEvent, mockController);
        Annotation annotation = new AnnotationImpl(10, 20, "Enter text here", 100, 50, true);
        AddPetriNetObject addItem = new AddPetriNetObject(annotation, mockNet);

        verify(listener).undoableEditHappened(argThat(Contains.thisAction(addItem)));
    }

}

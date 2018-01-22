package pipe.actions.file;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pipe.actions.gui.SaveAsAction;
import pipe.controllers.PetriNetController;
import pipe.controllers.application.PipeApplicationController;
import pipe.views.PipeApplicationView;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.pipe.models.petrinet.name.NormalPetriNetName;
import pipe.gui.imperial.pipe.models.petrinet.name.PetriNetFileName;
import pipe.gui.imperial.pipe.models.petrinet.name.PetriNetName;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.FileDialog;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SaveAsActionTest {
    SaveAsAction saveAsAction;
    @Mock
    PipeApplicationView mockView;

    @Mock
    PipeApplicationController mockController;

    @Mock
    PetriNetController mockPetriNetController;

    @Mock
    FileDialog mockFileChooser;

    @Mock
    PetriNet mockPetriNet;

    @Before
    public void setUp() {
        when(mockController.getActivePetriNetController()).thenReturn(mockPetriNetController);
        when(mockPetriNetController.getPetriNet()).thenReturn(mockPetriNet);
        saveAsAction = new SaveAsAction(mockController, mockFileChooser);
    }

    @Test
    public void setShortDescription()
    {
        Object shortDescription = saveAsAction.getValue(Action.SHORT_DESCRIPTION);
        assertEquals("Save as...", shortDescription);
    }

    @Test
    public void setKeyboardShortcut()
    {
        Object acceleratorKey = saveAsAction.getValue(Action.ACCELERATOR_KEY);
        KeyStroke stroke = KeyStroke.getKeyStroke("meta shift S");
        assertEquals(stroke, acceleratorKey);
    }

    @Test
    public void performsSaveAsWhenPetriNetHasNoFile()
            throws InvocationTargetException, ParserConfigurationException, NoSuchMethodException,
            IllegalAccessException, TransformerException {
        PetriNetName normalName = new NormalPetriNetName("");
        when(mockPetriNet.getName()).thenReturn(normalName);

        File file = new File("test.xml");
        when(mockFileChooser.getFiles()).thenReturn(new File[]{file});
        saveAsAction.actionPerformed(null);
        verify(mockController).saveAsCurrentPetriNet(file);
    }

    @Test
    public void performsSaveAsWhenEvenIfPetriNetHasFile()
            throws InvocationTargetException, ParserConfigurationException, NoSuchMethodException,
            IllegalAccessException, TransformerException {
        File file1 = new File("test.xml");
        PetriNetName fileName = new PetriNetFileName(file1);
        when(mockPetriNet.getName()).thenReturn(fileName);
        File file2 = new File("test2.xml");
        when(mockFileChooser.getFiles()).thenReturn(new File[]{file2});
        saveAsAction.actionPerformed(null);
        verify(mockController).saveAsCurrentPetriNet(file2);
    }

}

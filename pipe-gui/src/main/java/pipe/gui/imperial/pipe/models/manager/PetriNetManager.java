package pipe.gui.imperial.pipe.models.manager;

import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.pipe.parsers.UnparsableException;

import javax.xml.bind.JAXBException;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface PetriNetManager {
   void createNewPetriNet();

   void addPropertyChangeListener(PropertyChangeListener var1);

   void removePropertyChangeListener(PropertyChangeListener var1);

   PetriNet getLastNet();

   PetriNet createFromFile(File var1) throws JAXBException, UnparsableException, FileNotFoundException;

   void savePetriNet(PetriNet var1, File var2) throws JAXBException, IOException;

   void remove(PetriNet var1);
}

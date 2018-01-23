package pipe.gui.imperial.pipe.io;

import java.io.FileNotFoundException;
import javax.xml.bind.JAXBException;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

public interface PetriNetReader {
   PetriNet read(String var1) throws JAXBException, FileNotFoundException;
}

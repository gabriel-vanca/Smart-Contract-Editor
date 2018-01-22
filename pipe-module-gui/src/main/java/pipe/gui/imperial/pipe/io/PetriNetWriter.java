package pipe.gui.imperial.pipe.io;

import java.io.IOException;
import java.io.Writer;
import javax.xml.bind.JAXBException;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

public interface PetriNetWriter {
   void writeTo(String var1, PetriNet var2) throws JAXBException, IOException;

   void writeTo(Writer var1, PetriNet var2) throws JAXBException;
}

package pipe.gui.imperial.pipe.models.manager;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import pipe.gui.imperial.pipe.io.PetriNetIOImpl;
import pipe.gui.imperial.pipe.io.PetriNetReader;
import pipe.gui.imperial.pipe.io.PetriNetWriter;
import pipe.gui.imperial.pipe.models.PetriNetHolder;
import pipe.gui.imperial.pipe.models.petrinet.ColoredToken;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.pipe.models.petrinet.Token;
import pipe.gui.imperial.pipe.models.petrinet.name.NormalPetriNetName;
import pipe.gui.imperial.pipe.models.petrinet.name.PetriNetFileName;
import pipe.gui.imperial.pipe.models.petrinet.name.PetriNetName;
import pipe.gui.imperial.pipe.models.manager.PetriNetManager;
import pipe.gui.imperial.pipe.naming.PetriNetNamer;
import pipe.gui.imperial.pipe.parsers.UnparsableException;

public final class PetriNetManagerImpl implements PetriNetManager {
   public static final String NEW_PETRI_NET_MESSAGE = "New Petri net!";
   public static final String REMOVE_PETRI_NET_MESSAGE = "Removed Petri net";
   private final PetriNetNamer petriNetNamer = new PetriNetNamer();
   private final PetriNetHolder holder = new PetriNetHolder();
   protected final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

   public void addPropertyChangeListener(PropertyChangeListener listener) {
      this.changeSupport.addPropertyChangeListener(listener);
   }

   public void removePropertyChangeListener(PropertyChangeListener listener) {
      this.changeSupport.removePropertyChangeListener(listener);
   }

   public PetriNet getLastNet() {
      if (!this.holder.isEmpty()) {
         return this.holder.getNet(this.holder.size() - 1);
      } else {
         throw new RuntimeException("No Petri nets stored in the manager");
      }
   }

   public void createFromFile(File file) throws JAXBException, UnparsableException, FileNotFoundException {
      PetriNetReader petriNetIO = new PetriNetIOImpl();
      PetriNet petriNet = petriNetIO.read(file.getAbsolutePath());
      this.namePetriNetFromFile(petriNet, file);
      this.changeSupport.firePropertyChange("New Petri net!", (Object)null, petriNet);
   }

   public void savePetriNet(PetriNet petriNet, File outFile) throws JAXBException, IOException {
      PetriNetWriter writer = new PetriNetIOImpl();
      writer.writeTo(outFile.getAbsolutePath(), petriNet);
      this.petriNetNamer.deRegisterPetriNet(petriNet);
      this.namePetriNetFromFile(petriNet, outFile);
   }

   public void remove(PetriNet petriNet) {
      this.holder.remove(petriNet);
      this.changeSupport.firePropertyChange("Removed Petri net", petriNet, (Object)null);
   }

   private void namePetriNetFromFile(PetriNet petriNet, File file) {
      PetriNetName petriNetName = new PetriNetFileName(file);
      petriNet.setName(petriNetName);
      this.petriNetNamer.registerPetriNet(petriNet);
   }

   public void createNewPetriNet() {
      PetriNet petriNet = new PetriNet();
      this.namePetriNet(petriNet);
      petriNet.addToken(this.createDefaultToken());
      this.changeSupport.firePropertyChange("New Petri net!", (Object)null, petriNet);
      this.holder.addNet(petriNet);
   }

   private Token createDefaultToken() {
      return new ColoredToken("Default", Color.BLACK);
   }

   private void namePetriNet(PetriNet petriNet) {
      String name = this.petriNetNamer.getName();
      PetriNetName petriNetName = new NormalPetriNetName(name);
      petriNet.setName(petriNetName);
      this.petriNetNamer.registerPetriNet(petriNet);
   }
}

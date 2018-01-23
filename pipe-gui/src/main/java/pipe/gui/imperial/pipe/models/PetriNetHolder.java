package pipe.gui.imperial.pipe.models;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.PetriNetAdapter;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;

@XmlRootElement(
   name = "pnml"
)
public final class PetriNetHolder {
   @XmlJavaTypeAdapter(PetriNetAdapter.class)
   @XmlElement(
      name = "net"
   )
   private final List nets = new ArrayList();

   public void addNet(PetriNet net) {
      this.nets.add(net);
   }

   public PetriNet getNet(int index) {
      return (PetriNet)this.nets.get(index);
   }

   public int size() {
      return this.nets.size();
   }

   public boolean isEmpty() {
      return this.nets.isEmpty();
   }

   public void remove(PetriNet petriNet) {
      this.nets.remove(petriNet);
   }
}

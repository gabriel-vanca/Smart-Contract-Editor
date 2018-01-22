package pipe.gui.imperial.pipe.io;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import pipe.gui.imperial.pipe.io.PetriNetIO;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.ArcAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.PlaceAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.RateParameterAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.TokenAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.TokenSetIntegerAdapter;
import pipe.gui.imperial.pipe.io.adapters.modelAdapter.TransitionAdapter;
import pipe.gui.imperial.pipe.models.PetriNetHolder;
import pipe.gui.imperial.pipe.models.petrinet.ColoredToken;
import pipe.gui.imperial.pipe.models.petrinet.PetriNet;
import pipe.gui.imperial.pipe.models.petrinet.Token;

public class PetriNetIOImpl implements PetriNetIO {
   private final JAXBContext context;
   protected PetriNetValidationEventHandler petriNetValidationEventHandler;
   private Unmarshaller unmarshaller;

   public PetriNetIOImpl(boolean continueProcessing, boolean suppressUnexpectedElementMessages) throws JAXBException {
      this.context = JAXBContext.newInstance(PetriNetHolder.class);
      this.petriNetValidationEventHandler = new PetriNetValidationEventHandler(continueProcessing, suppressUnexpectedElementMessages);
      Logger.getLogger("com.sun.xml.internal.bind").setLevel(Level.FINEST);
   }

   public PetriNetIOImpl() throws JAXBException {
      this(false, true);
   }

   public void writeTo(String path, PetriNet petriNet) throws JAXBException, IOException {
      this.writeTo((Writer)(new FileWriter(new File(path))), petriNet);
   }

   public void writeTo(Writer stream, PetriNet petriNet) throws JAXBException {
      Marshaller m = this.context.createMarshaller();
      m.setEventHandler(this.getEventHandler());
      m.setProperty("jaxb.formatted.output", Boolean.TRUE);
      PetriNetHolder holder = this.getPetriNetHolder();
      holder.addNet(petriNet);

      try {
         m.marshal(holder, stream);
         this.getEventHandler().printMessages();
      } catch (JAXBException var6) {
         this.getEventHandler().printMessages();
         throw var6;
      }
   }

   protected PetriNetHolder getPetriNetHolder() {
      return new PetriNetHolder();
   }

   public PetriNet read(String path) throws JAXBException, FileNotFoundException {
      this.initialiseUnmarshaller();
      this.getUnmarshaller().setEventHandler(this.getEventHandler());
      PetriNetHolder holder = null;

      try {
         holder = (PetriNetHolder)this.getUnmarshaller().unmarshal(new FileReader(path));
         this.getEventHandler().printMessages();
      } catch (JAXBException var5) {
         this.getEventHandler().printMessages();
         throw var5;
      }

      PetriNet petriNet = holder.getNet(0);
      if (petriNet.getTokens().isEmpty()) {
         Token token = this.createDefaultToken();
         petriNet.addToken(token);
      }

      return petriNet;
   }

   protected void initialiseUnmarshaller() throws JAXBException {
      this.unmarshaller = this.context.createUnmarshaller();
      Map places = new HashMap();
      Map transitions = new HashMap();
      Map tokens = new HashMap();
      Map rateParameters = new HashMap();
      this.unmarshaller.setAdapter(new RateParameterAdapter(rateParameters));
      this.unmarshaller.setAdapter(new ArcAdapter(places, transitions));
      this.unmarshaller.setAdapter(new PlaceAdapter(places));
      this.unmarshaller.setAdapter(new TransitionAdapter(transitions, rateParameters));
      this.unmarshaller.setAdapter(new TokenAdapter(tokens));
      this.unmarshaller.setAdapter(new TokenSetIntegerAdapter(tokens));
   }

   protected PetriNetValidationEventHandler getEventHandler() {
      return this.petriNetValidationEventHandler;
   }

   private Token createDefaultToken() {
      return new ColoredToken("Default", new Color(0, 0, 0));
   }

   protected final Unmarshaller getUnmarshaller() {
      return this.unmarshaller;
   }
}

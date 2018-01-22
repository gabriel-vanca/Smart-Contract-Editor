package pipe.gui.imperial.pipe.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

public class PetriNetValidationEventHandler implements ValidationEventHandler {
   private boolean continueProcessing;
   private ValidationEvent event;
   private List formattedEvents;
   private boolean suppressUnexpectedElementMessages;

   public PetriNetValidationEventHandler(boolean continueProcessing, boolean suppressUnexpectedElementMessages) {
      this.continueProcessing = continueProcessing;
      this.suppressUnexpectedElementMessages = suppressUnexpectedElementMessages;
      this.formattedEvents = new ArrayList();
   }

   public PetriNetValidationEventHandler() {
      this(false, true);
   }

   public boolean handleEvent(ValidationEvent event) {
      this.event = event;
      if (event.getSeverity() == 2) {
         return false;
      } else {
         boolean unexpectedElement = this.saveAndCheckUnexpectedElement(event);
         return unexpectedElement && !this.continueProcessing ? true : this.continueProcessing;
      }
   }

   public void printMessages() {
      Iterator i$ = this.formattedEvents.iterator();

      while(i$.hasNext()) {
         PetriNetValidationEventHandler.FormattedEvent event = (PetriNetValidationEventHandler.FormattedEvent)i$.next();
         if (this.printMessage(event)) {
            System.err.println(event.formattedEvent);
         }
      }

   }

   protected boolean printMessage(PetriNetValidationEventHandler.FormattedEvent event) {
      if (!this.suppressUnexpectedElementMessages) {
         return true;
      } else {
         return !this.continueProcessing && !event.unexpected;
      }
   }

   private boolean saveAndCheckUnexpectedElement(ValidationEvent event) {
      if (event.getLinkedException() == null && event.getMessage().startsWith("unexpected element")) {
         this.formattedEvents.add(new PetriNetValidationEventHandler.FormattedEvent(true, this.printEvent()));
         return true;
      } else {
         this.formattedEvents.add(new PetriNetValidationEventHandler.FormattedEvent(false, this.printEvent()));
         return false;
      }
   }

   public String printEvent() {
      ValidationEventLocator locator = this.event.getLocator();
      StringBuffer sb = new StringBuffer();
      sb.append("PetriNetValidationEventHandler received a ValidationEvent, probably during processing by PetriNetIOImpl.  Details: ");
      sb.append("\nMessage: ");
      sb.append(this.event.getMessage());
      sb.append("\nObject: ");
      sb.append(locator.getObject() != null ? locator.getObject().toString() : "null");
      sb.append("\nURL: ");
      sb.append(locator.getURL() != null ? locator.getURL().toString() : "null");
      sb.append("\nNode: ");
      sb.append(locator.getNode() != null ? locator.getNode().toString() : "null");
      sb.append("\nLine: ");
      sb.append(locator.getLineNumber());
      sb.append("\nColumn: ");
      sb.append(locator.getColumnNumber());
      sb.append("\nLinked exception: ");
      sb.append(this.event.getLinkedException() != null ? this.event.getLinkedException().getMessage() : "null");
      return sb.toString();
   }

   public List getFormattedEvents() {
      return this.formattedEvents;
   }

   protected class FormattedEvent {
      public boolean unexpected;
      public String formattedEvent;

      public FormattedEvent(boolean unexpected, String formattedEvent) {
         this.unexpected = unexpected;
         this.formattedEvent = formattedEvent;
      }
   }
}

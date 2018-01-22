package pipe.gui.imperial.pipe.models.petrinet;

import pipe.gui.imperial.pipe.models.petrinet.PetriNetComponent;

public interface PlaceablePetriNetComponent extends PetriNetComponent {
   String X_CHANGE_MESSAGE = "x";
   String Y_CHANGE_MESSAGE = "y";
   String WIDTH_CHANGE_MESSAGE = "width";
   String HEIGHT_CHANGE_MESSAGE = "height";

   int getX();

   void setX(int var1);

   int getY();

   void setY(int var1);

   int getHeight();

   int getWidth();
}

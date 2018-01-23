package pipe.gui.imperial.pipe.models.petrinet;

import java.awt.Color;

public interface Token extends PetriNetComponent {
   String TOKEN_ENABLED_CHANGE_MESSAGE = "enabled";
   String COLOR_CHANGE_MESSAGE = "color";

   Color getColor();

   void setColor(Color var1);
}

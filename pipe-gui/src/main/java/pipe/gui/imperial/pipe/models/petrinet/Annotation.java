package pipe.gui.imperial.pipe.models.petrinet;

public interface Annotation extends PlaceablePetriNetComponent {
   String getText();

   void setText(String var1);

   boolean hasBorder();
}

/*
 * PIPESwingContextMenuListener.java
 */

package pipe.gui.reachability;

import net.sourceforge.jpowergraph.Edge;
import net.sourceforge.jpowergraph.Graph;
import net.sourceforge.jpowergraph.Node;
import net.sourceforge.jpowergraph.lens.LensSet;
import net.sourceforge.jpowergraph.swing.manipulator.DefaultSwingContextMenuListener;

import javax.swing.*;


//REMARK: this class extends a jpowergraph's class which is LGPL


/**
 * This class prevents from showing context menus in order to make the graph 
 * "uneditable" (there's no "delete node" or "delete edge")
 * @author Pere Bonet
 */
public class PIPESwingContextMenuListener 
        extends DefaultSwingContextMenuListener {
   
   /** Creates a new instance of NewClass
    * @param theGraph graph
    * @param theLensSet lens set
    * @param theZoomLevels zoom levels
    * @param theRotateAngles rotate angels
    * */
   public PIPESwingContextMenuListener(Graph theGraph, LensSet theLensSet, 
           Integer[] theZoomLevels, Integer[] theRotateAngles) {
      super(theGraph, theLensSet, theZoomLevels, theRotateAngles);
   }


    /**
     * Noop
     * @param theNode node
     * @param theMenu menu
     */
   @Override
   public void fillNodeContextMenu(final Node theNode, JPopupMenu theMenu) {
       // Noop
   }


    /**
     * Noop
     * @param theEdge edge
     * @param theMenu menu 
     */
   @Override
   public void fillEdgeContextMenu(final Edge theEdge, JPopupMenu theMenu) {
       // Noop
   }
   
}

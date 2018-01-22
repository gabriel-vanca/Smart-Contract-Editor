package pipe.gui.imperial.pipe.layout;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.view.mxGraph;
import pipe.gui.imperial.pipe.models.petrinet.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Layout {
   private static final int LAYOUT_OFFSET = 20;

   public static void layoutHierarchical(PetriNet petriNet, int interRankCell, int interHierarchy, int parallelEdge, int intraCell, int orientation) {
      mxGraph graph = initialiseGraph(petriNet);
      mxHierarchicalLayout layout = new mxHierarchicalLayout(graph, orientation);
      layout.setInterRankCellSpacing((double)interRankCell);
      layout.setInterHierarchySpacing((double)interHierarchy);
      layout.setParallelEdgeSpacing((double)parallelEdge);
      layout.setIntraCellSpacing((double)intraCell);
      layout.execute(graph.getDefaultParent());
      layoutPetriNet(graph, petriNet);
   }

   private static mxGraph initialiseGraph(PetriNet petriNet) {
      mxGraph graph = new mxGraph();
      Object parent = graph.getDefaultParent();
      graph.getModel().beginUpdate();
      HashMap objectMap = new HashMap();

      try {
         Iterator i$ = petriNet.getPlacesMap ().iterator();

         while(i$.hasNext()) {
            DiscretePlace place = (DiscretePlace)i$.next();
            objectMap.put(place.getId(), graph.insertVertex(parent, place.getId(), place.getId(), 0.0D, 0.0D, 30.0D, 30.0D));
         }

         i$ = petriNet.getTransitionsMap ().iterator();

         while(i$.hasNext()) {
            Transition transition = (Transition)i$.next();
            objectMap.put(transition.getId(), graph.insertVertex(parent, transition.getId(), transition.getId(), 0.0D, 0.0D, 10.0D, 30.0D));
         }

         i$ = petriNet.getArcs().iterator();

         while(i$.hasNext()) {
            Arc arc = (Arc)i$.next();
            Object source = objectMap.get(arc.getSource().getId());
            Object target = objectMap.get(arc.getTarget().getId());
            graph.insertEdge(parent, arc.getId(), arc.getId(), source, target);
         }
      } finally {
         graph.getModel().endUpdate();
      }

      return graph;
   }

   private static void layoutPetriNet(mxGraph graph, PetriNet petriNet) {
      double minX = 0.0D;
      double minY = 0.0D;
      Map points = new HashMap();
      Object[] arr$ = graph.getChildVertices(graph.getDefaultParent());
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Object o = arr$[i$];
         mxCell cell = (mxCell)o;
         mxGeometry geometry = cell.getGeometry();
         points.put(cell.getId(), geometry.getPoint());
         if (geometry.getX() < minX) {
            minX = geometry.getX();
         }

         if (geometry.getY() < minY) {
            minY = geometry.getY();
         }
      }

      Iterator i$ = petriNet.getTransitionsMap ().iterator();

      Point point;
      while(i$.hasNext()) {
         Transition transition = (Transition)i$.next();
         point = (Point)points.get(transition.getId());
         transition.setX((int)(point.getX() + Math.abs(minX) + 400.0D));
         transition.setY((int)(point.getY() + Math.abs(minY) + 40.0D));
      }

      i$ = petriNet.getPlacesMap ().iterator();

      while(i$.hasNext()) {
         DiscretePlace place = (DiscretePlace)i$.next();
         point = (Point)points.get(place.getId());
         place.setX((int)(point.getX() + Math.abs(minX) + 400.0D));
         place.setY((int)(point.getY() + Math.abs(minY) + 40.0D));
      }

   }

   public static void layoutOrganic(PetriNet petriNet, int forceConstant, int minDistanceLimit) {
      mxGraph graph = initialiseGraph(petriNet);
      mxFastOrganicLayout layout = new mxFastOrganicLayout(graph);
      layout.setForceConstant((double)forceConstant);
      layout.setMinDistanceLimit((double)minDistanceLimit);
      layout.execute(graph.getDefaultParent());
      layoutPetriNet(graph, petriNet);
   }
}

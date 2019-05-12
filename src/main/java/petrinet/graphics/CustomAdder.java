package petrinet.graphics;

import petrinet.logic.Edge;
import petrinet.logic.Petrinet;

public class CustomAdder {
    private Petrinet petrinet = null;
    private PetrinetCanvas canvas = null;
    private long id;

    private long edgeSourceid = -1;
    private long edgeDestinationid = -1;

    public CustomAdder(Petrinet petrinet, PetrinetCanvas canvas) {
        this.petrinet = petrinet;
        this.canvas = canvas;
    }

    public void setId(long id) {
        System.out.println("id: " + id);
        this.id = id;
        System.out.println("this.id: " + this.id);
    }

//    public void addPlace(int x, int y) {
//        id++;
//        petrinet.addPlace("", 1, id);
//        canvas.addPlace(x, y, petrinet.getPlaceById(id));
//        resetEdgeIds();
//    }

//    public void addTransition(int x, int y) {
//        id++;
//        petrinet.addTransition("", id);
//        canvas.addTransition(x, y, petrinet.getTransitionById(id));
//        resetEdgeIds();
//    }

//    public void addEdge(long id, boolean reset) {
//        if(edgeSourceid < 0) {
//            edgeSourceid = id;
//        } else if (edgeDestinationid < 0) {
//            edgeDestinationid = id;
//        }
//        if(edgeSourceid >= 0 && edgeDestinationid >= 0) {
//            if (reset) petrinet.addEdgeReset(edgeSourceid, edgeDestinationid);
//            else petrinet.addEdgeNormal(edgeSourceid, edgeDestinationid, 1);
//
//            Edge edge = reset ? petrinet.getEdgeResetBetweenVertex(edgeSourceid, edgeDestinationid) : petrinet.getEdgeNormalBetweenVertex(edgeSourceid, edgeDestinationid);
//            if(edge != null) {
//                canvas.addEdge(edge, edgeSourceid, edgeDestinationid, reset);
//            }
//            resetEdgeIds();
//        }
//    }

//    private void resetEdgeIds() {
//        this.edgeSourceid = -1;
//        this.edgeDestinationid = -1;
//    }

//    public void removePlace(long id) {
//        canvas.removePlace2D(id);
//        petrinet.removePlace(id);
//    }
//
//    public void removeTransition(long id) {
//        canvas.removeTransition2D(id);
//        petrinet.removeTransition(id);
//    }
//
//    public void removeEdge(Edge2D edge) {
//        Edge e = edge.getEdge();
//        canvas.removeEdge2D(edge);
//        petrinet.removeEdge(e);
//    }

}

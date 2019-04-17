package generated;

import graphics.*;
import petrinet.*;

import java.util.List;

public class GraphicsTransformer extends Transformer<PetrinetCanvas> {
    private Petrinet petrinet;

    public GraphicsTransformer(Petrinet petrinet) {
        this.petrinet = petrinet;
    }

    public PetrinetCanvas transform(Document document) {
        PetrinetCanvas canvas = new PetrinetCanvas();

        List<Place> places = document.getPlace();
        List<Transition> transitions = document.getTransition();
        List<Arc> arcs = document.getArc();

        for (Place place : places) {
            canvas.addPlace(place.getX(), place.getY(), petrinet.getPlaceById(place.getId()));
        }

        for (Transition transition : transitions) {
            canvas.addTransition(transition.getX(), transition.getY(), petrinet.getTransitionById(transition.getId()));
        }

        for (Arc arc : arcs) {
            if (arc.getType().value().equals("regular")) {
                EdgeNormal edge = getEdgeNormal(arc.getSourceId(), arc.getDestinationId());
                canvas.addEdge(edge, arc.getSourceId(), arc.getDestinationId(), false);
            } else if (arc.getType().value().equals("reset")) {
                EdgeReset edge = getEdgeReset(arc.getSourceId(), arc.getDestinationId());
                canvas.addEdge(edge, arc.getSourceId(), arc.getDestinationId(), true);
            }
        }

        return canvas;
    }


    private EdgeNormal getEdgeNormal(long sourceId, long destinationId) {
        Vertex source = petrinet.getVertexById(sourceId);
        Vertex destination = petrinet.getVertexById(destinationId);

        return petrinet.getEdgeNormalBetweenVertex(source, destination);
    }

    private EdgeReset getEdgeReset(long sourceId, long destinationId) {
        Vertex source = petrinet.getVertexById(sourceId);
        Vertex destination = petrinet.getVertexById(destinationId);

        return petrinet.getEdgeResetBetweenVertex(source, destination);
    }


}

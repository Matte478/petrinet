package generated;

import graphics.*;
import petrinet.*;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class GraphicsTransformer extends Transformer<PetrinetCanvas> {
    private Vector<Drawable> elements;
    private Petrinet petrinet;

    public GraphicsTransformer(Petrinet petrinet) {
        this.petrinet = petrinet;
        this.elements = new Vector<Drawable>();
    }

    public PetrinetCanvas transform(Document document) {
        PetrinetCanvas canvas = new PetrinetCanvas();

        List<Place> places = document.getPlace();
        List<Transition> transitions = document.getTransition();
        List<Arc> arcs = document.getArc();

        for (Place place : places) {
            canvas.addPlace( new Place2D(place.getX(), place.getY(), petrinet.getPlaceById(place.getId())) );
            elements.add( new Place2D(place.getX(), place.getY(), petrinet.getPlaceById(place.getId())) );
        }

        for (Transition transition : transitions) {
            canvas.addTransition( new Transition2D(transition.getX(), transition.getY(), petrinet.getTransitionById(transition.getId())) );
            elements.add( new Transition2D(transition.getX(), transition.getY(), petrinet.getTransitionById(transition.getId())) );
        }

        for (Arc arc : arcs) {
            Drawable source = getElementById(arc.getSourceId());
            Drawable destination = getElementById(arc.getDestinationId());

            if(source != null && destination != null) {

                if (arc.getType().value().equals("regular")) {
                    EdgeNormal edge = getEdgeNormal(arc.getSourceId(), arc.getDestinationId());

                    if(source instanceof Place2D && destination instanceof Transition2D) {
                        canvas.addEdge(new Edge2D(arc.getId(), edge, (Place2D) source, (Transition2D) destination, false));
                        elements.add(new Edge2D(arc.getId(), edge, (Place2D) source, (Transition2D) destination, false));

                    } else if (source instanceof Transition2D && destination instanceof Place2D) {
                        canvas.addEdge(new Edge2D(arc.getId(), edge, (Transition2D) source, (Place2D) destination, false));
                        elements.add(new Edge2D(arc.getId(), edge, (Transition2D) source, (Place2D) destination, false));
                    }
                } else if (arc.getType().value().equals("reset")) {
                    EdgeReset edge = getEdgeReset(arc.getSourceId(), arc.getDestinationId());

                    canvas.addEdge(new Edge2D(arc.getId(), edge, (Transition2D) source, (Place2D) destination, true));
                    elements.add(new Edge2D(arc.getId(), edge, (Transition2D) source, (Place2D) destination, true));
                }
            }
        }

        return canvas;
    }

    private Drawable getElementById(float id) {
        for (Drawable element : elements) {
            if(element.getId() == id) {
                return element;
            }
        }

        return null;
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

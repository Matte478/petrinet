package petrinet.generated;

import petrinet.graphics.Edge2D;
import petrinet.graphics.PetrinetCanvas;
import petrinet.graphics.Place2D;
import petrinet.graphics.Transition2D;
import petrinet.logic.EdgeNormal;
import petrinet.logic.EdgeReset;
import petrinet.logic.Petrinet;
import petrinet.logic.Vertex;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

    public Document transformToDocument(Vector<Place2D> places2D, Vector<Transition2D> transitions2D, Vector<Edge2D> edges2D) {
        List<Place> generatedPlaces = transformToGeneratedPlaces(places2D);
        List<Transition> generatedTransitions = transformToGeneratedTransitions(transitions2D);
        List<Arc> generatedEdges = transformToGeneratedEdges(edges2D);

        Document document = new Document();
        document.setPlace(generatedPlaces);
        document.setTransition(generatedTransitions);
        document.setArc(generatedEdges);

        return document;
    }

    public List<Place> transformToGeneratedPlaces(Vector<Place2D> places2D) {
        List<Place> generatedPlaces = new ArrayList<>();

        for (Place2D place2D : places2D) {
            Place place = new Place();
            place.setId((int) place2D.getId());
            place.setLabel(place2D.getName());
            place.setX((int) place2D.getX());
            place.setY((int) place2D.getY());
            place.setTokens(place2D.getToken());

            generatedPlaces.add(place);
        }

        return generatedPlaces;
    }

    public List<Transition> transformToGeneratedTransitions(Vector<Transition2D> transitions2D) {
        List<Transition> generatedTransitions = new ArrayList<>();

        for(Transition2D transition2D : transitions2D) {
            Transition transition = new Transition();
            transition.setId((int) transition2D.getId());
            transition.setLabel(transition2D.getName());
            transition.setX((int) transition2D.getX());
            transition.setY((int) transition2D.getY());

            generatedTransitions.add(transition);
        }

        return generatedTransitions;
    }

    public List<Arc> transformToGeneratedEdges(Vector<Edge2D> edges2D) {
        List<Arc> generatedEdges = new ArrayList<>();

        for(Edge2D edge2D : edges2D) {
            Arc edge = new Arc();

            edge.setSourceId((int) edge2D.getSourceId());
            edge.setDestinationId((int) edge2D.getDestinationId());
            if(edge2D.isReset()) {
                edge.setType(ArcType.RESET);
            } else {
                edge.setType(ArcType.REGULAR);
            }
            edge.setMultiplicity(edge2D.getWeight());

            generatedEdges.add(edge);
        }

        return generatedEdges;
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

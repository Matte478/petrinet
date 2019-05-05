package petrinet.generated;

import petrinet.logic.Petrinet;

import java.util.List;

public class PetrinetTransformer extends Transformer<Petrinet> {
    private long maxId = -1;

    public long getMaxId() {
        return maxId;
    }

    public Petrinet transform(Document document) {
        Petrinet petrinet = new Petrinet();

        List<Place> places = document.getPlace();
        List<Transition> transitions = document.getTransition();
        List<Arc> arcs = document.getArc();

        for (Place place : places) {
            petrinet.addPlace(place.getLabel(), place.getTokens(), place.getId());
            maxId = place.getId() > maxId ? place.getId() : maxId;
        }

        for(Transition transition : transitions) {
            petrinet.addTransition(transition.getLabel(), transition.getId());
            maxId = transition.getId() > maxId ? transition.getId() : maxId;
        }

        for (Arc arc : arcs) {
            if(arc.getType().value().equals("regular")) {
                petrinet.addEdgeNormal(arc.getSourceId(), arc.getDestinationId(), arc.getMultiplicity());
            } else if (arc.getType().value().equals("reset")) {
                petrinet.addEdgeReset(arc.getSourceId(), arc.getDestinationId());
            }
        }

        return petrinet;
    }
}

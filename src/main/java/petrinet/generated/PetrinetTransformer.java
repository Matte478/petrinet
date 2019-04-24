package petrinet.generated;

import petrinet.logic.Petrinet;

import java.util.List;

public class PetrinetTransformer extends Transformer<Petrinet> {

    public Petrinet transform(Document document) {
        Petrinet petrinet = new Petrinet();

        List<Place> places = document.getPlace();
        List<Transition> transitions = document.getTransition();
        List<Arc> arcs = document.getArc();

        for (Place place : places) {
            petrinet.addPlace(place.getLabel(), place.getTokens(), place.getId());
        }

        for(Transition transition : transitions) {
            petrinet.addTransition(transition.getLabel(), transition.getId());
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

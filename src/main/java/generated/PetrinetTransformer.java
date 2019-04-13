package generated;

import petrinet.Petrinet;

import java.util.List;

public class PetrinetTransformer extends Transformer<Petrinet> {

    public Petrinet transform(Document document) {
        Petrinet petrinet = new Petrinet();

        List<Place> places = document.getPlace();

        for (Place place : places) {
            System.out.println(place.id);
        }

        return null;
    }
}

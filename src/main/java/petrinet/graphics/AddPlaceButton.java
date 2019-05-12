package petrinet.graphics;

import petrinet.logic.Petrinet;

public class AddPlaceButton extends ModeButton {

    public AddPlaceButton(final PetrinetCanvas canvas, final Petrinet petrinet) {
        super("Add place", canvas, petrinet);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new AddPlaceListener(canvas, petrinet));
    }
}

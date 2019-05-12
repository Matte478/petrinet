package petrinet.graphics;

import petrinet.logic.Petrinet;

import java.util.Vector;

public class AddPlaceButton extends ModeButton {

    public AddPlaceButton(final PetrinetCanvas canvas, final Petrinet petrinet, Vector<ModeButton> otherButtons) {
        super("Add place", canvas, petrinet, otherButtons);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new AddPlaceListener(canvas, petrinet));
    }
}

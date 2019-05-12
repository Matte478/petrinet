package petrinet.graphics;

import petrinet.logic.Petrinet;

import java.util.Vector;

public class AddTransitionButton extends ModeButton {

    public AddTransitionButton(PetrinetCanvas canvas, Petrinet petrinet, Vector<ModeButton> otherButtons) {
        super("Add transition", canvas, petrinet, otherButtons);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new AddTransitionListener(canvas, petrinet));
    }
}

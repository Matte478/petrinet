package petrinet.graphics;

import petrinet.logic.Petrinet;

public class AddTransitionButton extends ModeButton {

    public AddTransitionButton(PetrinetCanvas canvas, Petrinet petrinet) {
        super("Add transition", canvas, petrinet);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new AddTransitionListener(canvas, petrinet));
    }
}

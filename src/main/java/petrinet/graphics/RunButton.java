package petrinet.graphics;

import petrinet.logic.Petrinet;

public class RunButton extends ModeButton {

    public RunButton(PetrinetCanvas canvas, Petrinet petrinet) {
        super("Run", canvas, petrinet);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new RunListener(canvas));
    }
}

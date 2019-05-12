package petrinet.graphics;

import petrinet.logic.Petrinet;

import java.util.Vector;

public class RunButton extends ModeButton {

    public RunButton(PetrinetCanvas canvas, Petrinet petrinet, Vector<ModeButton> otherButtons) {
        super("Run", canvas, petrinet, otherButtons);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new RunListener(canvas));
    }
}

package petrinet.graphics;

import petrinet.logic.Petrinet;

import java.util.Vector;

public class AddEdgeResetButton extends ModeButton {

    public AddEdgeResetButton(PetrinetCanvas canvas, Petrinet petrinet, Vector<ModeButton> otherButtons) {
        super("Add edge reset", canvas, petrinet, otherButtons);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new AddEdgeListener(canvas, petrinet, true));
    }
}

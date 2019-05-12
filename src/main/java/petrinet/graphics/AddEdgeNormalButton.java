package petrinet.graphics;

import petrinet.logic.Petrinet;

import java.util.Vector;

public class AddEdgeNormalButton extends ModeButton {

    public AddEdgeNormalButton(PetrinetCanvas canvas, Petrinet petrinet, Vector<ModeButton> otherButtons) {
        super("Add edge", canvas, petrinet, otherButtons);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new AddEdgeListener(canvas, petrinet,false));
    }
}

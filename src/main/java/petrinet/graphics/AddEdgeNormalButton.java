package petrinet.graphics;

import petrinet.logic.Petrinet;

public class AddEdgeNormalButton extends ModeButton {

    public AddEdgeNormalButton(PetrinetCanvas canvas, Petrinet petrinet) {
        super("Add edge", canvas, petrinet);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new AddEdgeListener(canvas, petrinet,false));
    }
}

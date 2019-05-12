package petrinet.graphics;

import petrinet.logic.Petrinet;

public class AddEdgeResetButton extends ModeButton {

    public AddEdgeResetButton(PetrinetCanvas canvas, Petrinet petrinet) {
        super("Add edge reset", canvas, petrinet);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new AddEdgeListener(canvas, petrinet, true));
    }
}

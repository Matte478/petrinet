package petrinet.graphics;

import petrinet.logic.Petrinet;

import java.util.Vector;

public class DeleteButton extends ModeButton {

    public DeleteButton(PetrinetCanvas canvas, Petrinet petrinet, Vector<ModeButton> otherButtons) {
        super("Delete", canvas, petrinet, otherButtons);
    }

    @Override
    protected void setModeListener() {
        this.canvas.addMouseListener(new DeleteListener(canvas, petrinet));
    }
}

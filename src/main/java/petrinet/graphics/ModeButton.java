package petrinet.graphics;

import petrinet.logic.Petrinet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Vector;

public abstract class ModeButton extends Button {
    protected PetrinetCanvas canvas;
    protected Petrinet petrinet;
    private Vector<ModeButton> otherButtons;

    public ModeButton(String label, PetrinetCanvas canvas, Petrinet petrinet, Vector<ModeButton> otherButtons) {
        this.setLabel(label);
        this.canvas = canvas;
        this.petrinet = petrinet;
        this.otherButtons = otherButtons;
        this.addActionListener(createActionListener());
    }

    protected ActionListener createActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeListeners();
                setModeListener();
            }
        };
    }

    protected void removeListeners() {
        MouseListener[] listeners = this.canvas.getMouseListeners();
        for(MouseListener listener : listeners) {
            this.canvas.removeMouseListener(listener);
        }
        this.setActiveBtn();
    }

    public void setCanvas(PetrinetCanvas canvas) {
        this.canvas = canvas;
    }

    public void setPetrinet(Petrinet petrinet) {
        this.petrinet = petrinet;
    }

    private void setActiveBtn() {
        for(Button btn : otherButtons) {
            btn.setForeground(Color.BLACK);
        }
        this.setForeground(Color.BLUE);
    }

    protected abstract void setModeListener();

}

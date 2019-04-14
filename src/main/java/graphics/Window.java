package graphics;

import generated.Document;
import generated.GraphicsTransformer;
import generated.PetrinetTransformer;
import petrinet.Petrinet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.Vector;

public class Window {
    private Frame frame;
    private PetrinetCanvas canvas;
    private Petrinet petrinet;

    public Window() {
        this.frame = new Frame();
        this.canvas = new PetrinetCanvas();


        frame.addWindowListener(new WindowAdapter() {@Override public void windowClosing(WindowEvent e) { System.exit(0); }});
        frame.setSize(1500, 800);
    }

    private void loadPetrinet() {
        try {
            InputStream resource = ClassLoader.getSystemResourceAsStream("insurance.xml");
            JAXBContext context = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Document document = (Document) unmarshaller.unmarshal(resource);

            PetrinetTransformer transformer = new PetrinetTransformer();
            petrinet = transformer.transform(document);

            GraphicsTransformer transformerGraphics = new GraphicsTransformer(petrinet);
            this.canvas = transformerGraphics.transform(document);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void launch() {
        loadPetrinet();
        frame.add(canvas);
        frame.setVisible(true);
    }
}

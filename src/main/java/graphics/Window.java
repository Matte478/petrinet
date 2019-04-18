package graphics;

import generated.Document;
import generated.GraphicsTransformer;
import generated.PetrinetTransformer;
import petrinet.Petrinet;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Window extends Frame implements ActionListener {
    private PetrinetCanvas canvas;
    private Petrinet petrinet;
    private final String btnLabel;

    public Window() {
        this.canvas = new PetrinetCanvas();
        this.btnLabel = "Load petrinet";

        createLayout();
        setCloseBtn();
    }

    private void createLayout() {
        Button loadBtn = new Button(btnLabel);
        loadBtn.addActionListener(this);
        this.setBackground(new Color(255,255,255));

        this.setLayout(new BorderLayout());
        Panel panel = new Panel();
        panel.add(loadBtn);
        panel.setBackground(new Color(241, 244, 247) );

        this.add(panel, BorderLayout.NORTH);
        this.setSize(500, 200);
        this.setVisible(true);
    }

    private void setCloseBtn() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(btnLabel);
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setFileFilter(createFileFilter());
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (e.getActionCommand().equals(btnLabel))
        {
            int returnValue = fc.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                String filename = file.getAbsolutePath();

                loadPetrinet(filename);
            }
        }
    }

    private FileFilter createFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                return f.getName().endsWith(".xml");
            }

            @Override
            public String getDescription() {
                return "XML FILE";
            }
        };
    }

    private void loadPetrinet(String filename) {
        try {
            File file = new File(filename);
            JAXBContext context = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Document document = (Document) unmarshaller.unmarshal(file);

            PetrinetTransformer petrinetTransformer = new PetrinetTransformer();
            petrinet = petrinetTransformer.transform(document);

            GraphicsTransformer graphicsTransformer = new GraphicsTransformer(petrinet);

            this.remove(canvas); // if I want to open new petrinet
            this.canvas = graphicsTransformer.transform(document);
            this.canvas.setBackground(new Color(255, 255, 255));
            setFrameSize();
            this.add(canvas, BorderLayout.CENTER);
            this.repaint();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void setFrameSize() {
        int padding = 80;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int petrinetWidth = this.canvas.getPetrinetWidth() + padding;
        int petrinetHeight = this.canvas.getPetrinetHeight() + padding;

        if(petrinetWidth > screenWidth) petrinetWidth = screenWidth;
        if(petrinetHeight > screenHeight) petrinetHeight = screenHeight;

        this.setSize(petrinetWidth, petrinetHeight);
    }
}

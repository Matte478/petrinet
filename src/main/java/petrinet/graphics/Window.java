package petrinet.graphics;

import petrinet.generated.Document;
import petrinet.generated.GraphicsTransformer;
import petrinet.generated.PetrinetTransformer;
import petrinet.logic.Petrinet;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import org.apache.commons.io.FilenameUtils;

public class Window extends Frame implements ActionListener {
    private PetrinetCanvas canvas;
    private Petrinet petrinet;
    private final String btnLoadLabel;
    private final String btnExportLabel;

    public Window() {
        this.canvas = new PetrinetCanvas();
        this.btnLoadLabel = "Load petrinet";
        this.btnExportLabel = "Export petrinet";

        createLayout();
        setCloseBtn();
    }

    private void createLayout() {
        Button loadBtn = new Button(btnLoadLabel);
        Button exportBtn = new Button(btnExportLabel);
        loadBtn.addActionListener(this);
        exportBtn.addActionListener(this);
        this.setBackground(Color.WHITE);

        this.setLayout(new BorderLayout());
        Panel panel = new Panel();
        panel.add(loadBtn);
        panel.add(exportBtn);
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
        fc.setDialogTitle(btnLoadLabel);
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setFileFilter(createFileFilter());
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (e.getActionCommand().equals(btnLoadLabel)) {
            clickedLoadBtn(fc);
        }
        else if (e.getActionCommand().equals(btnExportLabel)) {
            clickedExportBtn(fc);
        }
    }

    private void clickedLoadBtn(JFileChooser fc) {
        int returnValue = fc.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String filePame = file.getAbsolutePath();

            loadPetrinet(filePame);
        }
    }

    private void clickedExportBtn(JFileChooser fc) {
        int returnValue = fc.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            if ( ! FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xml")) {
                file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName()) + ".xml"); // remove the extension (if any) and replace it with ".xml"
            }

            String filePath = file.getAbsolutePath();

            exportPetrinet(filePath);
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

    private void loadPetrinet(String filePath) {
        try {
            File file = new File(filePath);
            JAXBContext context = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Document document = (Document) unmarshaller.unmarshal(file);

            PetrinetTransformer petrinetTransformer = new PetrinetTransformer();
            petrinet = petrinetTransformer.transform(document);

            GraphicsTransformer graphicsTransformer = new GraphicsTransformer(petrinet);

            this.remove(canvas); // if I want to open new petrinet
            this.canvas = graphicsTransformer.transform(document);
            this.canvas.setBackground(Color.WHITE);
            setFrameSize();
            this.add(canvas, BorderLayout.CENTER);
            repaint();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void exportPetrinet(String filePath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            GraphicsTransformer graphicsTransformer = new GraphicsTransformer(petrinet);

            Document document = graphicsTransformer.transformToDocument(canvas.getPlaces(), canvas.getTransitions(), canvas.getEdges());

            marshaller.marshal(document, new File(filePath));
        } catch (Exception ignored) {
            ignored.printStackTrace();
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

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
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

import org.apache.commons.io.FilenameUtils;

public class Window extends Frame implements ActionListener {
    private PetrinetCanvas canvas;
    private Petrinet petrinet;
    private Vector<ModeButton> buttons;
    private final String btnLoadLabel;
    private final String btnExportLabel;


    public Window() {
        this.petrinet = new Petrinet();
        this.canvas = new PetrinetCanvas();
        this.buttons = new Vector<>();
        this.btnLoadLabel = "Import petrinet";
        this.btnExportLabel = "Export petrinet";

        createLayout();
        setCloseBtn();
    }

    public PetrinetCanvas getCanvas() {
        return canvas;
    }

    public Petrinet getPetrinet() {
        return petrinet;
    }

    private void createLayout() {
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        this.add(createControls(), BorderLayout.NORTH);
        this.add(this.canvas, BorderLayout.CENTER);
        this.setSize(1000, 500);
        this.setVisible(true);
    }

    private Panel createControls() {
        Panel panel = new Panel();
        panel.setBackground(new Color(241, 244, 247) );

        Button loadBtn = new Button(btnLoadLabel);
        loadBtn.addActionListener(this);
//        buttons.add(loadBtn);
        panel.add(loadBtn);

        Button exportBtn = new Button(btnExportLabel);
        exportBtn.addActionListener(this);
//        buttons.add(exportBtn);
        panel.add(exportBtn);

//        AddPlaceButton addPlaceButton = new AddPlaceButton(this.canvas, this.petrinet);
        AddPlaceButton addPlaceButton = new AddPlaceButton(this.canvas, this.petrinet, buttons);
        buttons.add(addPlaceButton);
        panel.add(addPlaceButton);

        AddTransitionButton addTransitionButton = new AddTransitionButton(this.canvas, this.petrinet, buttons);
        buttons.add(addTransitionButton);
        panel.add(addTransitionButton);

        AddEdgeNormalButton addEdgeNormalButton = new AddEdgeNormalButton(this.canvas, this.petrinet, buttons);
        buttons.add(addEdgeNormalButton);
        panel.add(addEdgeNormalButton);

        AddEdgeResetButton addEdgeResetButton = new AddEdgeResetButton(this.canvas, this.petrinet, buttons);
        buttons.add(addEdgeResetButton);
        panel.add(addEdgeResetButton);

        DeleteButton deleteButton = new DeleteButton(this.canvas, this.petrinet, buttons);
        buttons.add(deleteButton);
        panel.add(deleteButton);

        RunButton runButton = new RunButton(this.canvas, this.petrinet, buttons);
        buttons.add(runButton);
        panel.add(runButton);

        return panel;
    }

    public void setActiveBtn(Button button) {
        for(Button btn : buttons) {
            if(btn == button) {
                btn.setForeground(Color.BLUE);
            } else {
                btn.setForeground(Color.BLACK);
            }
        }
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
            String filePath = file.getAbsolutePath();

            loadPetrinet(filePath);
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
            petrinet.setId(petrinetTransformer.getMaxId());

            GraphicsTransformer graphicsTransformer = new GraphicsTransformer(petrinet);

            this.remove(canvas); // if I want to open new petrinet
            this.setSize(1000, 500);
            this.canvas = graphicsTransformer.transform(document);

            for(ModeButton btn : this.buttons) {
                btn.setCanvas(this.canvas);
                btn.setPetrinet(this.petrinet);
            }

            this.canvas.setBackground(Color.WHITE);
            this.add(canvas, BorderLayout.CENTER);
            setFrameSize();
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

        if(petrinetWidth < 1000) petrinetWidth = 1000;
        if(petrinetHeight < 500) petrinetHeight = 500;

        this.setSize(petrinetWidth, petrinetHeight);
    }
}

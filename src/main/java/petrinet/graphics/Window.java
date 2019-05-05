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
import java.util.Vector;

import org.apache.commons.io.FilenameUtils;

public class Window extends Frame implements ActionListener {
    private PetrinetCanvas canvas;
    private Petrinet petrinet;
    private Vector<Button> buttons;
    private final String btnLoadLabel;
    private final String btnExportLabel;
    private final String btnAddPlaceLabel;
    private final String btnAddTransitionLabel;
    private final String btnAddEdgeNormalLabel;
    private final String btnAddEdgeResetLabel;
    private final String btnDeleteLabel;
    private final String btnFireLabel;

    public Window() {
        this.petrinet = new Petrinet();
        this.canvas = new PetrinetCanvas();
        this.canvas.setCustomAdder(new CustomAdder(petrinet, this.canvas));
        this.buttons = new Vector<>();
        this.btnLoadLabel = "Import petrinet";
        this.btnExportLabel = "Export petrinet";
        this.btnAddPlaceLabel = "Add place";
        this.btnAddTransitionLabel = "Add transition";
        this.btnAddEdgeNormalLabel = "Add edge";
        this.btnAddEdgeResetLabel = "Add reset edge";
        this.btnDeleteLabel = "Delete";
        this.btnFireLabel = "Run";

        createLayout();
        setCloseBtn();
    }

    private void createLayout() {
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        this.add(createControls(), BorderLayout.NORTH);
        this.add(this.canvas, BorderLayout.CENTER);
        this.setSize(850, 500);
        this.setVisible(true);
    }

    private Panel createControls() {
        Panel panel = new Panel();
        panel.setBackground(new Color(241, 244, 247) );
        final Button loadBtn = new Button(btnLoadLabel);
        final Button exportBtn = new Button(btnExportLabel);
        final Button addPlaceBtn = new Button(btnAddPlaceLabel);
        final Button addTransitionBtn = new Button(btnAddTransitionLabel);
        final Button addEdgeNormalBtn = new Button(btnAddEdgeNormalLabel);
        final Button addEdgeResetBtn = new Button(btnAddEdgeResetLabel);
        final Button deleteBtn = new Button(btnDeleteLabel);
        final Button fireBtn = new Button(btnFireLabel);
        buttons.add(loadBtn);
        buttons.add(exportBtn);
        buttons.add(addPlaceBtn);
        buttons.add(addTransitionBtn);
        buttons.add(addEdgeNormalBtn);
        buttons.add(addEdgeResetBtn);
        buttons.add(deleteBtn);
        buttons.add(fireBtn);

        loadBtn.addActionListener(this);
        exportBtn.addActionListener(this);
        addPlaceBtn.addActionListener(
            createActionListener(canvas.ADD_PLACE, addPlaceBtn)
        );
        addTransitionBtn.addActionListener(
            createActionListener(canvas.ADD_TRANSITION, addTransitionBtn)
        );
        addEdgeNormalBtn.addActionListener(
            createActionListener(canvas.ADD_EDGE_NORMAL, addEdgeNormalBtn)
        );
        addEdgeResetBtn.addActionListener(
                createActionListener(canvas.ADD_EDGE_RESET, addEdgeResetBtn)
        );
        deleteBtn.addActionListener(
            createActionListener(canvas.DELETE, deleteBtn)
        );
        fireBtn.addActionListener(
            createActionListener(canvas.RUN, fireBtn)
        );

        panel.add(loadBtn);
        panel.add(exportBtn);
        panel.add(addPlaceBtn);
        panel.add(addTransitionBtn);
        panel.add(addEdgeNormalBtn);
        panel.add(addEdgeResetBtn);
        panel.add(deleteBtn);
        panel.add(fireBtn);

        return panel;
    }

    private ActionListener createActionListener(final int mode, final Button btn) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setMode(mode);
                setActiveBtn(btn);
            }
        };
    }

    private void setActiveBtn(Button button) {
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
            this.setSize(500, 200);
            this.canvas = graphicsTransformer.transform(document);
            CustomAdder ca = new CustomAdder(petrinet, this.canvas);
            System.out.println(petrinetTransformer.getMaxId());
            ca.setId(petrinetTransformer.getMaxId());
            this.canvas.setCustomAdder(ca);
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

        this.setSize(petrinetWidth, petrinetHeight);
    }
}

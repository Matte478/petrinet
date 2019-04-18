import graphics.Window;


public class Main {

    public static void main(String[] args) {
        Window window = new Window();
//        window.launch();

//        try {
//            InputStream resource = ClassLoader.getSystemResourceAsStream("insurance.xml");
//            JAXBContext context = JAXBContext.newInstance(Document.class);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//
//            Document document = (Document) unmarshaller.unmarshal(resource);
//
//            PetrinetTransformer transformer = new PetrinetTransformer();
//            Petrinet petrinet = transformer.transform(document);
//
//            GraphicsTransformer transformerGraphics = new GraphicsTransformer(petrinet);
//            Vector<Drawable> drawable = transformerGraphics.transform(document);
//
////
//            Frame frame = new Frame();
//            frame.setSize(1500, 800);
////
//            PetrinetCanvas canvas = new PetrinetCanvas();
//            canvas.setDrawables(drawable);
//
//
//
//            frame.add(canvas);
//
//            frame.setVisible(true);
//
//
////            System.out.println("Number of places:" + document.getPlace().size());
////            System.out.println("Number of tranistions:" + document.getTransition().size());
////            System.out.println("Number of arcs:" + document.getArc().size());
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
    }
}
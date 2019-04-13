import generated.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        try {
            InputStream resource = ClassLoader.getSystemResourceAsStream("insurance.xml");
            JAXBContext context = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Document document = (Document) unmarshaller.unmarshal(resource);

            System.out.println("Number of places:" + document.getPlace().size());
            System.out.println("Number of tranistions:" + document.getTransition().size());
            System.out.println("Number of arcs:" + document.getArc().size());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
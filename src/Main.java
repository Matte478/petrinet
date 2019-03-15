import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        Place place1 = new Place(1, "place1", 5);
        Transition transition1 = new Transition(1, "transition1");

        Edge edge1 = new EdgeNormal(place1, transition1, 5);

        transition1.start();

        System.out.println(place1.getToken());



    }
}

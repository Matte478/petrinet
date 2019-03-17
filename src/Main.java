import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        Place place1 = new Place(1, "place1", 15);
        Transition transition1 = new Transition(1, "transition1");

        Edge edge1 = new EdgeNormal(place1, transition1, "transition", 5);
//        Edge edge2 = new EdgeNormal(place1, transition1, "place", 10);
//        Edge edge3 = new EdgeNormal(place1, transition1, "transition", 1);
        Edge edge3 = new EdgeReset(place1, transition1, "place");

        transition1.start();

        System.out.println(place1.getToken());



    }
}

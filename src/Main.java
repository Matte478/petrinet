import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        Petrinet petrinet = new Petrinet();

        petrinet.addPlace("p1", 0); // 0
        petrinet.addPlace("p2", 0); // 1

        petrinet.addTransition("t1"); // 2
        petrinet.addTransition("t2"); // 3
        petrinet.addTransition("t3"); // 4

        petrinet.addEdgeNormal(3, 0, 5);


//        Place place = petrinet.getPlaceById(0);
////        String name = place.getName();
////        System.out.println(name);
////
////        Transition transition = petrinet.getTransitionById(0);
////        String name1 = transition.getName();
////        System.out.println(name1);

        petrinet.print();

        petrinet.launchTransition(3);

        petrinet.print();

    }
}

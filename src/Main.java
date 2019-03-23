import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        Petrinet petrinet = new Petrinet();

//        petrinet.addPlace("p1", 0); // 0
//        petrinet.addPlace("p2", 0); // 1
//        petrinet.addPlace("p3", 1); // 2
//        petrinet.addPlace("p4", 1); // 3
//        petrinet.addPlace("p5", 5); // 4
//        petrinet.addPlace("p6", 0); // 5
//        petrinet.addPlace("p7", 0); // 6
//
//        petrinet.addTransition("t1");   // 7
//        petrinet.addTransition("t2");   // 8
//        petrinet.addTransition("t3");   // 9
//        petrinet.addTransition("t4");   // 10
//        petrinet.addTransition("t5");   // 11
//
//        petrinet.addEdgeNormal(8, 0, 5);
//        petrinet.addEdgeNormal(1, 9, 1);
//        petrinet.addEdgeNormal(2, 10, 1);
//        petrinet.addEdgeNormal(10, 2, 2);
//        petrinet.addEdgeNormal(3, 11, 1);
//        petrinet.addEdgeReset(4, 11);
//        petrinet.addEdgeNormal(11, 5, 1);
//        petrinet.addEdgeNormal(11, 6, 1);

        petrinet.addPlace("p1", 5);
        petrinet.addTransition("t1");
        petrinet.addEdgeReset(1,0);
//        petrinet.addEdgeNormal(0,1, 5);


        petrinet.print();

//        petrinet.launchTransition(1);

//        petrinet.print();

    }
}

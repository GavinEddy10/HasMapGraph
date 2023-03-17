import java.util.ArrayList;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        //in degree and out degree are the same ask about it
        //ask about outgoing and incoming edges and see if theyre the same
        MyGraph g = new MyGraph();
        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");
        g.insertVertex("E");
        g.insertVertex("F");
        g.insertVertex("G");
        g.insertVertex("H");
        g.insertVertex("I");
        g.insertVertex("J");
        g.insertVertex("K");
        g.insertVertex("L");
        g.insertVertex("M");
        g.insertVertex("N");
        g.insertVertex("O");
        g.insertVertex("P");
        g.insertEdge("A", "B", "1");
        g.insertEdge("C", "D", "2");
        g.insertEdge("A", "F", "3");
        g.insertEdge("B", "G", "4");
        g.insertEdge("G", "D", "5");
        g.insertEdge("D", "H", "6");
        g.insertEdge("E", "F", "7");
        g.insertEdge("G", "H", "8");
        g.insertEdge("E", "I", "9");
        g.insertEdge("E", "J", "10");
        g.insertEdge("J", "G", "11");
        g.insertEdge("G", "K", "12");
        g.insertEdge("H", "L", "13");
        g.insertEdge("I", "J", "14");
        g.insertEdge("K", "L", "15");
        g.insertEdge("I", "M", "16");
        g.insertEdge("J", "N", "17");
        g.insertEdge("K", "P", "18");
        g.insertEdge("L", "P", "19");
        g.insertEdge("M", "N", "20");
        g.insertEdge("O", "P", "21");
        g.print();
        System.out.println();
        System.out.println();
        System.out.println();

        //test area
        ArrayList<VertexPair> moves = g.BreadthFirstSort("A");
        System.out.println();
        System.out.println("Breadth Search Output: ");
        for (VertexPair move: moves) {
            move.printVertexPair();
        }
        ArrayList<VertexPair> moves2 = g.DepthSearchSort("A");
        System.out.println("Depth Search OutPut: ");
        for (VertexPair move : moves2) {
            move.printVertexPair();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        ArrayList<VertexPair> path = g.findPath_BreadthSearch("A", "C");
        System.out.println("Breadth Search Find: ");
        for (VertexPair move: path) {
            move.printVertexPair();
        }


    }
}
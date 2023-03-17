import java.awt.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.*;

public class MyGraph {
    private int numVertices;
    private int numEdges;
    private HashMap<Vertex, ArrayList<GraphPairing>> graph;

    public MyGraph() {
        numEdges = 0;
        numVertices = 0;
        graph = new HashMap<>(); //don't know order, vertex, graphing pair
    }

    public int NumVertices() {
        return numVertices;
    }

    public int numEdges() {
        return numEdges;
    }

    public ArrayList<VertexPair> findPath_BreadthSearch(String vert_start, String vert_dest) {
        Vertex start = getVertexFromString(vert_start);
        Vertex finish = getVertexFromString(vert_dest);
        ArrayList<VertexPair> tot_path = BreadthFirstSort(vert_start);
        ArrayList<VertexPair> way_back = new ArrayList<>();

        ArrayList<VertexPair> copy = new ArrayList<>();
        if (start.equals(finish))
            copy.add(new VertexPair(start, finish));
        for (VertexPair vp: tot_path) {
            copy.add(vp);
            if (vp.getV2().equals(finish))
                break;
        }
        //start from end of copy, bring in all elements into way_back.
        //
        int index = 0;
        System.out.println("Copy" + copy);
        for (int i = copy.size()-1; i>= 0; i--) {
            VertexPair vp = copy.get(i);
            System.out.println("RAN");
            if (index == 0) {
                way_back.add(copy.get(i));//gets last to front index of copy of path
                Vertex v1 = way_back.get(index).getV1();//swap the v1 and v2 of the first vert pair for the path back
                Vertex v2 = way_back.get(index).getV2();
                way_back.get(index).setV1(v2);
                way_back.get(index).setV2(v1);
                index++;
            }
            else if (vp.getV2().equals(way_back.get(index-1).getV2())) {// {A -> B} {a -> F} {B -> G} if the second vertex current pair is equal to the first vertex
                //of the next pair. Then we add it to the list. Have excess junk in list.
                way_back.add(copy.get(i));//gets last to front index of copy of path
                Vertex v1 = way_back.get(index).getV1();//swap the v1 and v2 of the first vert pair for the path back
                Vertex v2 = way_back.get(index).getV2();
                way_back.get(index).setV1(v2);
                way_back.get(index).setV2(v1);
                index++;
            }
        }
        return way_back;
    }

    public ArrayList<VertexPair> findPath_DepthSearch(String vert_start, String vert_dest) {
        ArrayList<VertexPair> path = DepthSearchSort(vert_start);
        ArrayList<VertexPair> way_back = new ArrayList<>();
        Vertex start = getVertexFromString(vert_start);
        Vertex finish = getVertexFromString(vert_dest);

        ArrayList<VertexPair> copy = new ArrayList<>();
        if (start.equals(finish))
            copy.add(new VertexPair(start, finish));
        for (VertexPair vp : path) {
            copy.add(vp);
            if (vp.getV2().equals(finish))
                break;
        }
        //have list from start all the way to vp2 desired endpoing
        int index = 0;
        for (int i = copy.size()-1; i >= 0; i--) {//making way_back from end of copy to front. Swapping all vertexes as well
            way_back.add(copy.get(i));//gets last to front index of copy of path
            Vertex v1 = way_back.get(index).getV1();//swap the v1 and v2 of the first vert pair for the path back
            Vertex v2 = way_back.get(index).getV2();
            way_back.get(index).setV1(v2);
            way_back.get(index).setV2(v1);
            index++;
        }
        return way_back;
    }

    public ArrayList<VertexPair> DepthSearchSort(String V_start) {
        Vertex start_loc = getVertexFromString(V_start);
        start_loc.setVisited(true);
        ArrayList<Vertex> moves = new ArrayList<>();//all moves even backtracks
        Stack<Vertex> stack = new Stack<Vertex>(); //stack.push, pushes onto top, stack.pop, removes top, stack.peek gets top of but doens't remove it
        stack.push(start_loc);

        while (!all_vertexes_visited()) {//not all vertexes visited run
            ArrayList<Vertex> neighbors = get_neighbors(stack.peek());
            moves.add(stack.peek());
            if (neighbors.size() == 0)
                stack.pop();
            else {
                stack.push(neighbors.get(0));
                stack.peek().setVisited(true);
            }
        }
        moves.add(stack.peek());//adds last term
        all_vert_tounseen(moves);
        return listvert_toVP_wo_corners(moves);
    }

    public void all_vert_tounseen(ArrayList<Vertex> list) {
        for (Vertex current: list) {
            current.setVisited(false);
        }
    }

    public void all_vertPair_tounseen(ArrayList<VertexPair> list) {
        for (VertexPair current: list) {
            current.getV1().setVisited(false);
            current.getV2().setVisited(false);
        }
    }

    public ArrayList<VertexPair> listvert_toVP_wo_corners(ArrayList<Vertex> list) {
        ArrayList<VertexPair> vertpairs = new ArrayList<>();
        for (int i = 0; i < list.size()-1; i++) {
            vertpairs.add(new VertexPair(list.get(i), list.get(i + 1)));// current vertex, vertex +1.
        }
        return vertpairs;
    }

    //returns true if vertex only has one connection, the other vert passed in
    private boolean check_only_one_vert(Vertex origin, Vertex alone_vert) {
        ArrayList<Vertex> neighbors = all_neighbors(alone_vert);
        //System.out.println("NEIGHBORS FOR " + alone_vert + ": " + neighbors);
        return neighbors.size() == 1 && neighbors.get(0).equals(origin);
    }

    //returns all neighbors without worrying if they visited or not
    public ArrayList<Vertex> all_neighbors(Vertex origin) {
        Set<Vertex> vertices = vertices();
        ArrayList<Vertex> neighbors = new ArrayList<>();
        for (Vertex vertex: vertices) {
            if (origin.getName().equals(vertex.getName())) {
                for (int i = 0; i < graph.get(vertex).size(); i++) {
                    GraphPairing gp = graph.get(vertex).get(i);
                    neighbors.add(gp.getV());
                }
            }
        }
        return neighbors;
    }

    public ArrayList<VertexPair> list_vertex_to_vertexPairs(ArrayList<Vertex> list) {
        ArrayList<VertexPair> vertpairs = new ArrayList<>();
        for (int i = 0; i < list.size()-1; i++) {
            vertpairs.add(new VertexPair(list.get(i), list.get(i+1)));// current vertex, vertex +1.
        }
        return vertpairs;
    }

    public boolean all_vertexes_visited() {
        Set<Vertex> vertices = vertices();

        for (Vertex v: vertices) {
            ArrayList<GraphPairing> gp_list = graph.get(v);
            for (GraphPairing gp : gp_list) {
                if (!gp.getV().isVisited())//one vertex not visited
                    return false;
            }
        }
        return true;
    }

    public ArrayList<VertexPair> BreadthFirstSort(String V_start) {
        Vertex start = getVertexFromString(V_start);
        Queue<Vertex> vert_queue = new LinkedList<>();
        vert_queue.add(start);//vert_queue.add(); adds to end  //vert_queue.poll(); takes first one out //vert_queue.peek(); look at first guy in line but don't take it off
        ArrayList<VertexPair> moves = new ArrayList<>();//(v1 -> v2), (v1 -> v2);
        Set<Vertex> vertices = vertices();
        Set<Vertex> vertices_copy = new HashSet<>();
        vertices_copy.addAll(vertices);


        while(vertices_copy.size() != 0) {
            ArrayList<Vertex> neighbors = get_neighbors(vert_queue.peek());//have neighbors
            if (neighbors.size() == 0) {
                vertices_copy.remove(vert_queue.peek());
                vert_queue.peek().setVisited(true);
            }
            for (Vertex neighbor : neighbors) {
                vert_queue.add(neighbor);
                moves.add(new VertexPair(vert_queue.peek(), neighbor));
                vert_queue.peek().setVisited(true);
            }
            vertices_copy.remove(vert_queue.poll());
        }
        all_vertPair_tounseen(moves);
        return moves;
    }


    private ArrayList<Vertex> get_neighbors(Vertex origin) {
        //System.out.println("get_neighbors passed in: " + origin);
        Set<Vertex> vertices = vertices();
        ArrayList<Vertex> neighbors = new ArrayList<>();

        for (Vertex vertex: vertices) {
            //System.out.println("Vertex: " + vertex + "|| Origin: " + origin);
            if (origin.getName().equals(vertex.getName())) {
                //System.out.println("RANNN");

                for (int i = 0; i < graph.get(vertex).size(); i++) {
                    GraphPairing gp = graph.get(vertex).get(i);

                    if (!gp.getV().isVisited())//haven't already seen the vertex
                        neighbors.add(gp.getV());
                }
            }
        }

        return neighbors;
    }

    //v1 for all vertex pairs
    private ArrayList<Vertex> vertex_pair_vertexList(ArrayList<VertexPair> moves) {
        ArrayList<Vertex> list = new ArrayList<>();
        for (VertexPair move: moves) {
            list.add(move.getV1());
        }
        return list;
    }

    private boolean all_tiles_searched(ArrayList<VertexPair> moves) {
        ArrayList<Vertex> seen_v1s = vertex_pair_vertexList(moves);
        seen_v1s = remove_repeats(seen_v1s);
        //System.out.println(seen_v1s);
        // System.out.println("seen v1s size: " + seen_v1s.size());
        //System.out.println(vertices());
        //System.out.println("vertices.size: " + vertices().size());
        return seen_v1s.size() == vertices().size();
    }

    private ArrayList<Vertex> remove_repeats(ArrayList<Vertex> list) {
        ArrayList<Vertex> edited = new ArrayList<>();
        for (Vertex v: list) {
            if (!edited.contains(v))
                edited.add(v);
        }
        return edited;
    }

    public void insertVertex(String name) {
        Vertex temp = new Vertex(name);
        //assume for now, the given name does not exist in map
        graph.put(temp, null); //assume new vertex with no edges
        numVertices++;
    }

    public void print() {
        Set<Vertex> vertexArrayList = graph.keySet();

        for (Vertex v: vertexArrayList) {
            System.out.print(v + ": ");
            System.out.println(graph.get(v));
        }
    }

    //THis is O(2n) can we make this 0 (1n)
    //assumes v1 and v2 exist
    public void insertEdge(String vert1, String vert2, String edgeName) {
        Set<Vertex> vertices = graph.keySet();
        Vertex vertex1 = null;
        Vertex vertex2 = null;
        Edge edge = new Edge(edgeName);

        //add in // v != null && // to each if to prevent null pointer exception
        for (Vertex v: vertices) {
            if (v.compareTo(new Vertex(vert1)) == 0)
                vertex1 = v;
            if (v.compareTo(new Vertex(vert2)) == 0)
                vertex2 = v;
        }

        //System.out.println("Contains v1: " + graph.containsKey(vertex1));
        //System.out.println("Contains v2: " + graph.containsKey(vertex1));
        ArrayList<GraphPairing> v1EdgeList = graph.get(vertex1);
        if (v1EdgeList == null)
            graph.put(vertex1, new ArrayList<>());
        graph.get(vertex1).add(new GraphPairing(vertex2, edge));

        ArrayList<GraphPairing> v2EdgeList = graph.get(vertex2);
        if (v2EdgeList == null)
            graph.put(vertex2, new ArrayList<>());
        graph.get(vertex2).add(new GraphPairing(vertex1, edge));

        numEdges++;
    }

    public Set<Edge> edges() {
        Set<Vertex> vertices = vertices();
        Set<Edge> edges = new HashSet<>();//set can only add once
        for (Vertex v: vertices) {
            for (int i = 0; i < graph.get(v).size(); i++) {//goes through the graph pairings
                Edge tempE = graph.get(v).get(i).getE();//gets graph object for vertice,then gets edge
                edges.add(tempE);
            }
        }
        return edges;
    }

    public Set<Vertex> vertices() {
        return graph.keySet();
    }

    public ArrayList<String> connectedEdges(String Vertex) {
        Vertex v = getVertexFromString(Vertex);
        ArrayList<GraphPairing> list = graph.get(v);
        ArrayList<String> edges = new ArrayList<>();
        for (GraphPairing gp: list) {
            edges.add(gp.getE().toString());
        }
        return edges;
    }


    //add in // v != null && // to each if to prevent null pointer exception
    public String getEdge(String u, String v) {
        Vertex v1 = getVertexFromString(u);
        Vertex v2 = getVertexFromString(v);
        Set<Vertex> vertices = vertices();
        for (Vertex vertex: vertices) {
            if (vertex.equals(v1)) {//found vertex in set of keys
                for (int i = 0; i < graph.get(vertex).size(); i++) {//loop through key graph objects
                    Vertex gVert = graph.get(vertex).get(i).getV();
                    if (gVert.getName().equals(v2.getName()))
                        return graph.get(vertex).get(i).getE().toString();
                }
            }
        }
        return null;
    }


    public String[] endVertices(String Edge) {
        Edge e = getEdgeFromString(Edge);
        String[] endVertices  = new String[2];
        Set<Vertex> vertices = vertices();

        for (Vertex v: vertices) {
            for (int i = 0; i < graph.get(v).size(); i++) {
                if (graph.get(v).get(i).getE().equals(e)) {//found edge pairing in graph object
                    endVertices[0] = v.toString();
                    endVertices[1] = graph.get(v).get(i).getV().toString();
                    return endVertices;//don't want to run again
                }
            }
        }
        return endVertices;//will return empty array
    }

    public String opposite(String V, String E) {
        Vertex v = getVertexFromString(V);
        Edge e = getEdgeFromString(E);
        Set<Vertex> vertices = vertices();
        for (Vertex vertex: vertices) {
            if (vertex.equals(v)) {
                ArrayList<GraphPairing> pairs = graph.get(vertex);
                for (GraphPairing pair: pairs) {
                    if (pair.getE().equals(e))
                        return pair.getV().toString();
                }
            }
        }
        return null;
    }

    public Edge getEdgeFromString(String str) {
        Set<Vertex> vertices = vertices();
        for (Vertex v: vertices) {
            if (graph.get(v) != null) {
                for (int i = 0; i < graph.get(v).size(); i++) {//arraylist of graph pairings
                    Edge e = graph.get(v).get(i).getE();
                    if (e.getName().equals(str))
                        return e;
                }
            }
        }
        return null;
    }

    //same as in degree and out degree
    public int Degree(String vertex) {
        Vertex v = getVertexFromString(vertex);
        ArrayList<GraphPairing> list = graph.get(v);
        return list.size();
    }

    public void removeEdge(String Edge) {//edge we want to remove
        Edge e = getEdgeFromString(Edge);
        Set<Vertex> vertices = vertices();

        for (Vertex v: vertices) {
            ArrayList<GraphPairing> list = graph.get(v);

            for (int i = 0; i < list.size(); i++) {
                GraphPairing gp = list.get(i);
                if (gp.getE().equals(e)) {//edges are the same
                    list.remove(gp);//if edges same, then remove the graph pair from list and subtract one on list
                    i--;
                }
            }
        }
    }

    public void removeVertex(String vertex) {
        Vertex vert_remove = getVertexFromString(vertex);
        //remove all edges connected to vertex
        Set<Vertex> vertices = vertices();
        graph.remove(vert_remove);

        for (Vertex vert: vertices) {
            ArrayList<GraphPairing> list = graph.get(vert);
            for (int i = 0; i < list.size(); i++) {
                GraphPairing gp = list.get(i);
                if (gp.getV().equals(vert_remove)) {//same vertex in graph pairing list
                    list.remove(gp);
                    i--;
                }
            }
        }
    }

    public Vertex getVertexFromString(String str) {
        Set<Vertex> vertices = vertices();
        for (Vertex v: vertices) {
            if (v.getName().equals(str))
                return v;
        }
        return null;
    }


}
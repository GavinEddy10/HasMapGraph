public class GraphPairing {
    private Vertex v;
    private Edge e;

    public GraphPairing(Vertex V, Edge e) {
        this.v = V;
        this.e = e;
    }

    public Vertex getV() {
        return v;
    }

    public Edge getE() {
        return e;
    }

    @Override
    public String toString() {
        return "MyGraph {" +
                "vert = " + v +
                ", edge = " + e +
                '}';
    }
}

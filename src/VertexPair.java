public class VertexPair {
    private Vertex v1;
    private Vertex v2;

    public VertexPair(Vertex v1, Vertex v2) {//v1 where start, v2 where going to
        this.v1 = v1;
        this.v2 = v2;
    }

    public void printVertexPair() {
        System.out.print("{V1: " + v1.getName() + " --> V2: " + v2.getName() + "} ");
    }
    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public void setV1(Vertex v1) {
        this.v1 = v1;
    }

    public void setV2(Vertex v2) {
        this.v2 = v2;
    }
}

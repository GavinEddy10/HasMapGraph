public class Vertex extends GraphObject{
    private boolean visited;
    public Vertex(String name) {
        super(name);
        this.visited = false;
    }

    public Vertex (String name, int weight) {
        super (name, weight);
        this.visited = false;
    }

    public boolean equals(Vertex other) {
        return this.getName().equals(other.getName());
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}

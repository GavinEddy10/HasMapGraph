public class GraphObject implements Comparable<GraphObject>{
    private String name;
    private int weight;

    public GraphObject(String name) {
        this.name = name;
    }

    public GraphObject(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "{" +
                "'" + name + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(GraphObject o) {
        return name.compareTo(o.name);
    }
}
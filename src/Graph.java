import java.util.*;
import java.util.LinkedList;

class Graph<T> {

    private Map<Vertex<T>, List<Vertex<T>>> adjVertices = new HashMap<>();

    void addVertex(Vertex<T> vertex) {
        adjVertices.putIfAbsent(vertex, new ArrayList<>());
    }

    void removeVertex(Vertex<T> vertex) {
        adjVertices.values().forEach(e -> e.remove(vertex));
        adjVertices.remove(vertex);
    }

    void addEdge(Vertex<T> src, Vertex<T> dest) {
        adjVertices.get(src).add(dest);
        adjVertices.get(dest).add(src);
    }

    void removeEdge(Vertex<T> src, Vertex<T> dest) {
        adjVertices.get(src).removeIf(v -> v != null && v.equals(dest));
        adjVertices.get(dest).removeIf(v -> v != null && v.equals(src));
    }

    void breadthFirstSearchTraversal(Vertex<T> beginning){
        System.out.println("Breadth First Search Traversal:");
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        Queue<Vertex<T>> way = new LinkedList<>();
        way.add(beginning);
        visited.put(beginning, true);
        while (!way.isEmpty()){
            Vertex<T> vertex = way.remove();
            System.out.print(vertex.getValue() + " ");
            List<Vertex<T>> adjVertices = this.adjVertices.getOrDefault(vertex, new ArrayList<>());
            adjVertices.forEach(v-> {
                if(!visited.containsKey(v)){
                    way.add(v);
                    visited.put(v, true);
                }
            });
        }
    }
    //TBC
    void depthFirstSearchTraversal(){

    }

    static class Vertex<T> {

        private T value;

        Vertex(T value) {
            this.value = value;
        }

        T getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex<?> vertex = (Vertex<?>) o;
            return value.equals(vertex.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();
        Vertex<String> minsk = new Vertex<>("Minsk");
        Vertex<String> mahilyow = new Vertex<>("Mahilyow");
        Vertex<String> viciebsk = new Vertex<>("Viciebsk");
        Vertex<String> homel = new Vertex<>("Homel");
        Vertex<String> hrodna = new Vertex<>("Hrodna");
        Vertex<String> brest = new Vertex<>("Brest");
        Vertex<String> kyiv = new Vertex<>("Kyiv");
        Arrays.asList(minsk, mahilyow, viciebsk, homel, hrodna, brest, kyiv).forEach(graph::addVertex);
        Arrays.asList(mahilyow, viciebsk, homel, hrodna, brest).forEach(dest -> graph.addEdge(minsk, dest));
        Arrays.asList(minsk, viciebsk, homel).forEach(dest -> graph.addEdge(mahilyow, dest));
        Arrays.asList(minsk, mahilyow, hrodna).forEach(dest -> graph.addEdge(viciebsk, dest));
        Arrays.asList(minsk, mahilyow, brest).forEach(dest -> graph.addEdge(homel, dest));
        Arrays.asList(minsk, mahilyow, brest).forEach(dest -> graph.addEdge(hrodna, dest));
        Arrays.asList(minsk, viciebsk, brest).forEach(dest -> graph.addEdge(brest, dest));
        graph.addEdge(homel, kyiv);
        graph.breadthFirstSearchTraversal(minsk);

        graph.removeEdge(kyiv, homel);
        graph.removeVertex(kyiv);
        graph.depthFirstSearchTraversal();
    }
}

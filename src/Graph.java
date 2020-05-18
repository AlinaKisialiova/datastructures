import java.util.*;
import java.util.LinkedList;

/*
 * A Graph is a non-linear data structure consisting of nodes(vertex) and edges(relationship).
 *
 * Breadth First Search (BFS) for a graph is visiting of nodes "layer-by-layer".
 * Depth-First Search (DFS) searches as far as possible along a branch and then backtracks to search as far as possible in the next branch.
 *
 * Difference between Tree and Graph traversal:
 * graphs may contain cycles. To avoid processing a node more than once, we use a visited map.
 *
 * Example of Graph and traversal:
 *
 *               A
 *          /    |    \
 *        B  ——  C      D
 *       /
 *     E
 *
 * DFS(from 0): A D C B E
 * BFS(from 0): A B C D E
 */
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

    void breadthFirstSearchTraversal(Vertex<T> beginning) {
        System.out.println("\n===============================");
        System.out.println("Breadth First Search Traversal:");
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        Queue<Vertex<T>> way = new LinkedList<>();
        way.add(beginning);
        visited.put(beginning, true);
        while (!way.isEmpty()) {
            Vertex<T> vertex = way.remove();
            System.out.print(vertex.getValue() + " ");
            List<Vertex<T>> adjVertices = this.adjVertices.getOrDefault(vertex, new ArrayList<>());
            adjVertices.forEach(v -> {
                if (!visited.containsKey(v)) {
                    way.add(v);
                    visited.put(v, true);
                }
            });
        }
    }

    void depthFirstSearchTraversal(Vertex<T> beginning) {
        System.out.println("\n===============================");
        System.out.println("Depth-First-Search traversal:");
        Stack<Vertex<T>> way = new Stack<>();
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        way.push(beginning);
        visited.put(beginning, true);
        while (!way.isEmpty()) {
            Vertex<T> vertex = way.pop();
            System.out.print(vertex.getValue() + " ");
            List<Vertex<T>> adjVertices = this.adjVertices.getOrDefault(vertex, new ArrayList<>());
            adjVertices.forEach(v -> {
                if (!visited.containsKey(v)) {
                    way.push(v);
                    visited.put(v, true);
                }
            });
        }
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
        Graph<String> travelGraph = new Graph<>();
        Vertex<String> countryA = new Vertex<>("Australia");
        Vertex<String> countryB = new Vertex<>("Belarus");
        Vertex<String> countryC = new Vertex<>("Canada");
        Vertex<String> countryD = new Vertex<>("Denmark");
        Vertex<String> countryE = new Vertex<>("Ethiopia");

        Arrays.asList(countryA, countryB, countryC, countryD, countryE).forEach(travelGraph::addVertex);
        Arrays.asList(countryB, countryC, countryD).forEach(dest -> travelGraph.addEdge(countryA, dest));
        Arrays.asList(countryC, countryE).forEach(dest -> travelGraph.addEdge(countryB, dest));
        Arrays.asList(countryA, countryB).forEach(dest -> travelGraph.addEdge(countryC, dest));
        travelGraph.addEdge(countryD, countryA);
        travelGraph.addEdge(countryE, countryB);
        travelGraph.breadthFirstSearchTraversal(countryA);
        travelGraph.depthFirstSearchTraversal(countryA);

        travelGraph.removeEdge(countryA, countryD);
        travelGraph.removeVertex(countryD);
        travelGraph.depthFirstSearchTraversal(countryA);
    }
}

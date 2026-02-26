package graph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeightedGraphTest {

    WeightedGraph<String,Integer> g;

    @BeforeEach
    void init() {
        g = new WeightedGraph<>();
    }

    @Test
    void testConnectOnePair() {
        g.addVertex("a");
        assertEquals(1, g.numVertices());
        g.addVertex("b");
        assertEquals(2, g.numVertices());
        assertFalse(g.adjacent("a", "b"));
        g.addEdge("a","b", 10);
        assertTrue(g.adjacent("a", "b"));
    }

    @Test
    void testAddOneEdge() {
        g.addVertex("a");
        g.addVertex("b");
        assertFalse(g.adjacent("a", "b"));
        g.addEdge(new Edge<String>("a","b"), 10);
        assertTrue(g.adjacent("a", "b"));
    }

    void makeSimpleGraph(){
        g.addVertex("a");
        g.addVertex("b");
        g.addEdge("a","b", 10);
    }

    @Test
    void testRemoveEdge() {
        makeSimpleGraph();
        assertTrue(g.adjacent("a", "b"));
        g.removeEdge("a", "b");
        assertFalse(g.adjacent("a", "b"));

    }
}
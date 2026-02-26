package student;

import java.util.*;

import graph.*;

public class ProblemSolver implements IProblem {

	@Override
	public <V, E extends Comparable<E>> List<Edge<V>> mst(WeightedGraph<V, E> g) { //O(m*log(m))
		V firstNode = g.getFirstNode(); //O(1)
		PriorityQueue<Edge<V>> priorityWeight = new PriorityQueue<Edge<V>>(g);

		for(Edge<V> node : g.adjacentEdges(firstNode)) { //O(degree)
			priorityWeight.add(node); //O(log (degree))
		}
		Set<V> found = new HashSet<>();
		LinkedList<Edge<V>> mst = new LinkedList<>();
		found.add(firstNode); //O(1)

		while(found.size() < g.size()) { //kjører n ganger, så O(n*degree*log(m)) = O(mlog(m))
			Edge<V> smallest = priorityWeight.remove(); //O(1) siden det er bakerste element
			if(found.contains(smallest.a) && found.contains(smallest.b)) { //O(1) siden det er hashSet
				continue;
				} else {
				mst.add(smallest); //O(1)
				found.add(smallest.b); //O(1)
				for (Edge<V> edge : g.adjacentEdges(smallest.b)) { //O(degree)*O(log(m))
					if (!found.contains(edge.b)) {
						priorityWeight.add(edge); //O(log(n))
					}
				}
			}
		}
		return mst;
	}

	@Override
	public <V> V lca(Graph<V> g, V root, V u, V v) {
		if(g.adjacent(root,u) && g.adjacent(root,v)) {
			return root;
		}

		Set<V> found = new HashSet<>();
		List<V> toSearch = new LinkedList<>();
		List<V> path = new LinkedList<>();
		List<V> pathU = new LinkedList<>();
		Set<V> pathV = new HashSet<>();
		found.add(root);
		path.add(root);

		for(V node : g.neighbours(root)) {
			toSearch.add(node);
		}

		while(true){ //kjører helt til man finner u og v.
			if(found.contains(u) && pathU.size()==0) {
				pathU.addAll(path);
			}
			if(found.contains(v) && pathV.size()==0) {
				pathV.addAll(path);
			}
			if(found.contains(u) && found.contains(v)){
				break;
			}

			V node = toSearch.remove(toSearch.size()-1); //Last in, first out.

			while(!g.adjacent(path.get(path.size()-1), node)) { //passer på at path er riktig i forhold til u og v. potensielt O(n)
				path.remove(path.size()-1);
			}

			if(!found.contains(node)) {
				found.add(node);
				path.add(node);

				for (V neighbours : g.neighbours(node)) {
					if(!found.contains(neighbours)) {
						toSearch.add(neighbours);
					}
				}
			}
		}
		Collections.reverse(pathU); //pathU er i rekkefølgen root, nabo til root, ..., nabo til u, u. Og jeg vil finne lca

		for(V elem : pathU) {
			if (pathV.contains(elem)) {
				return elem;
			}
		}
		throw new NoSuchElementException("");
	}

	@Override
	public <V> Edge<V> addRedundant(Graph<V> g, V root) {

		HashMap<V, Integer> found = new HashMap<>();
		HashSet<V> visited = new HashSet<>();
		mostChildren(g, root, found,visited); //O(n)

		List<V> children = new ArrayList<>();
		List<Integer> childrenScore = new ArrayList<>();

		for(V neighbor : g.neighbours(root)){ //O(degree)
			children.add(neighbor); //O(1) (forventet)
			childrenScore.add(found.get(neighbor)); //O(1)
		}

		if(children.size()==1) { //hvis roten bare har ett barn, må en av nodene til kanten være roten
			V r = correctNode(g, root, root, found); //O(n^2)
			return new Edge<>(root, r);
		}

		int max = Collections.max(childrenScore); //O(n)
		int index = childrenScore.indexOf(max); //O(n)
		V node = children.get(index); //O(1)

		childrenScore.remove(index); //O(n)
		children.remove(index); //O(n)

		int max2 = Collections.max(childrenScore); //O(n)
		int index2 = childrenScore.indexOf(max2); //O(n)
		V node2 = children.get(index2);//O(1)

		V u = correctNode(g, node, root, found); //O(n^2)
		V v = correctNode(g, node2, root, found); //O(n^2)
		return new Edge<>(u,v);
	}

	private <V> V correctNode(Graph<V> g, V var,V root, HashMap<V, Integer> found){

		Set<V> visited = new HashSet<>();
		visited.add(root);
		List<V> toSearch = new ArrayList<>();
		List<Integer> toSearchScore = new ArrayList<>();

		for(V neighbours : g.neighbours(var)) { //degree
			if(!(visited.contains(neighbours))) { //for at man ikke skal tro root er en nabo
				toSearch.add(neighbours); //O(1)
				toSearchScore.add(found.get(neighbours)); //O(1)
			}
		}
		if(toSearch.size()==0) { //O(1)
			return root;
		}

		int max = Collections.max(toSearchScore); //O(n)
		int index = toSearchScore.indexOf(max); //O(n)
		V node = toSearch.get(index); //O(1)

		return correctNode(g, node ,var, found);
	}


	private <V> void mostChildren(Graph<V> g, V root, HashMap<V, Integer> found, HashSet<V> visited) {
		int children = 0;
		visited.add(root);

		for(V neighbor : g.neighbours(root)) { //O
			if(!visited.contains(neighbor)){ //O(1)
				mostChildren(g, neighbor, found, visited);
				children += found.get(neighbor)+1; //O(1)
			}
		}
		found.put(root, children); //O(1)
	}
}

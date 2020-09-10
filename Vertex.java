
//Date: 			May 15, 2020
//Description:	
//				Vertex class which implements VertexInterface.
//


import java.util.Iterator;
import java.util.NoSuchElementException;


public class Vertex<T> implements VertexInterface<T> {
	
	public T label;
	public ListWithIteratorInterface<Edge> edgeList; 
	private boolean visited;
	private VertexInterface<T> previousVertex;
	private double cost;

	public Vertex() {
		
		label = null;
		edgeList = new LinkedListWithIterator<>(); 
		visited = false;
		previousVertex = null;
		cost = 0;
	}
	
	
	public Vertex(T vertexLabel) {

		label = vertexLabel;
		edgeList = new LinkedListWithIterator<>(); 
		visited = false;
		previousVertex = null;
		cost = 0;
	}
	

	public T getLabel() {
		
		return label;
	}
	

	public void visit() {
		
		visited = true;
	}

	public void unvisit() {
		
		visited = false;
	}

	public boolean isVisited() {
		
		return visited == true;
	}


	public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {

		boolean result = false;

		if(!this.equals(endVertex)) {

			Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
			boolean duplicateEdge = false;

			while(!duplicateEdge && neighbors.hasNext()) {

				VertexInterface<T> nextNeighbor = neighbors.next();
				if(endVertex.equals(nextNeighbor))
					duplicateEdge = true;
			}

			if(!duplicateEdge) {
				
				Edge newEdge = new Edge(endVertex, edgeWeight);
				
				edgeList.add(newEdge); //adds the new edge to edgeList
				result = true;
			}
		}
		return result;
	}
	
	
	public boolean connect(VertexInterface<T> endVertex) {

		return connect(endVertex, 0);
	}
	

	public boolean disconnect(VertexInterface<T> endVertex) { 
		
		boolean result = false;
		int index = 0; 

		if(!this.equals(endVertex)) {

			Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
			boolean foundEdge = false;

			while(!foundEdge && neighbors.hasNext()) {

				VertexInterface<T> nextNeighbor = neighbors.next();
				
				if(endVertex.equals(nextNeighbor)) 
					foundEdge = true;
				
				index++; //getting the index of the vertex connection...
			}

			if(foundEdge) {

				edgeList.remove(index); //removes the edge from edgeList
				result = true;
			}
		}
		return result;
		
	}
	
	
	public Iterator<VertexInterface<T>> getNeighborIterator() {

		return new NeighborIterator();
	}


	public Iterator<Double> getWeightIterator() {
		
		return new WeightIterator();
	}


	public boolean hasNeighbor() {

		return !edgeList.isEmpty();
	}


	public VertexInterface<T> getUnvisitedNeighbor() {

		VertexInterface<T> result = null;

		Iterator<VertexInterface<T>> neighbors = getNeighborIterator();

		while( neighbors.hasNext() && result == null) {
			
			VertexInterface<T> nextNeighbor = neighbors.next();
			if(!nextNeighbor.isVisited())
				result = nextNeighbor;
		}

		return result;
	}


	public void setPredecessor(VertexInterface<T> predecessor) {
		
		previousVertex = predecessor;
	}


	public VertexInterface<T> getPredecessor() {
		
		return previousVertex;
	}


	public boolean hasPredecessor() {
		
		return previousVertex != null;
	}

	
	public void setCost(double newCost) {
		
		cost = newCost;
	}

	
	public double getCost() {
						
		return cost;
	}
	

	private class NeighborIterator implements Iterator<VertexInterface<T>> {

		private Iterator<Edge> edges;

		
		private NeighborIterator() {

			edges = edgeList.getIterator();
		}

		
		public boolean hasNext() {

			return edges.hasNext();
		}

		
		public VertexInterface<T> next() {

			VertexInterface<T> nextNeighbor = null;

			if(edges.hasNext()){
				
				Edge edgeToNextNeighbor = edges.next();
				nextNeighbor = edgeToNextNeighbor.getEndVertex();

			} else 
				throw new NoSuchElementException();
			
			return nextNeighbor;
		}

		
		public void remove() {

			throw new java.lang.UnsupportedOperationException();
		}
	}
	

	private class WeightIterator implements Iterator<Double> {
		
		private Iterator<Edge> edges;
		
		private WeightIterator() {

			edges = edgeList.getIterator();
		}

		
		public boolean hasNext() {

			return edges.hasNext();
		}

		
		public Double next() {

			Double nextNeighborCost = 0.;

			if(edges.hasNext()){
				
				Edge edgeToNextNeighbor = edges.next(); //gets the next edge
				nextNeighborCost = edgeToNextNeighbor.getWeight(); //gets the weight of the edge.

			} else 
				throw new NoSuchElementException();
			
			return nextNeighborCost;
		}

		
		public void remove() {

			throw new java.lang.UnsupportedOperationException();
		}
	}


	protected class Edge {

		private VertexInterface<T> vertex;
		private double weight;

		protected Edge(VertexInterface<T> endVertex, double edgeWeight) {
			
			vertex = endVertex;
			weight = edgeWeight;
		}

		protected Edge(VertexInterface<T> endVertex) {
			
			vertex = endVertex;
			weight = 0;
		}

		protected VertexInterface<T> getEndVertex() {
			
			return vertex;
		}

		protected double getWeight() {
			
			return weight;
		}
	}

}
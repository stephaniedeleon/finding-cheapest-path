

//Date: 			May 15, 2020
//Description:	
//				DirectedGraph class which implements GraphInterface.
//

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.PriorityQueue; 


public class DirectedGraph<T> implements GraphInterface<T> {
	
	private HashMap<T, VertexInterface<T>> vertices;
	private int edgeCount;
	
	public DirectedGraph() {
		
		vertices = new LinkedHashMap<>();
		edgeCount = 0;
	} 

	public boolean addVertex(T vertexLabel) {

		VertexInterface<T> addOutcome = vertices.put(vertexLabel, new Vertex<>(vertexLabel)); //adding the vertex to the HashMap
		return addOutcome != null;		
	}


	public boolean addEdge(T begin, T end, double edgeWeight) { 

		boolean result = false;
		
		VertexInterface<T> beginVertex = vertices.get(begin); 
		VertexInterface<T> endVertex = vertices.get(end); 
		
		if( beginVertex != null && endVertex != null) 
			result = beginVertex.connect(endVertex, edgeWeight); //connecting!!!
		if(result) 
			edgeCount++;
		
		return result;	
	}


	public boolean addEdge(T begin, T end) {

		return addEdge(begin, end, 0);
	}


	public boolean removeEdge(T begin, T end) {

		boolean result = false;
		
		VertexInterface<T> beginVertex = vertices.get(begin);
		VertexInterface<T> endVertex = vertices.get(end);
		
		if( beginVertex != null && endVertex != null) 
			result = beginVertex.disconnect(endVertex); //disconnecting!!!
		if(result) 
			edgeCount--;
		
		return result;
	}


	public boolean hasEdge(T begin, T end) {

		boolean found = false;

		VertexInterface<T> beginVertex = vertices.get(begin);
		VertexInterface<T> endVertex = vertices.get(end);


		if( beginVertex != null && endVertex != null) {

			Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();

			while(!found && neighbors.hasNext()) {

				VertexInterface<T> nextNeighbor = neighbors.next();
				if(endVertex.equals(nextNeighbor))
					found = true;
			}
		}

		return found;
	}


	public boolean isEmpty() {

		return vertices.isEmpty();
	}


	public int getNumberOfVertices() {

		return vertices.size();
	}


	public int getNumberOfEdges() {

		return edgeCount;
	}


	public void clear() {

		vertices.clear();
		edgeCount = 0;
	}


	public void resetVertices() {

		Iterator<Entry<T, VertexInterface<T>>> vertexIterator = vertices.entrySet().iterator();

		while(vertexIterator.hasNext()) {

			//Iterates through the vertices and resets them
			VertexInterface<T> nextVertex = vertexIterator.next().getValue();
			nextVertex.unvisit();
			nextVertex.setCost(0);
			nextVertex.setPredecessor(null);
		}
	}


	public QueueInterface<T> getBreadthFirstTraversal(T origin) {
		throw new java.lang.UnsupportedOperationException();
	}


	public QueueInterface<T> getDepthFirstTraversal(T origin) {
		throw new java.lang.UnsupportedOperationException();
	}


	public StackInterface<T> getTopologicalOrder() {
		throw new java.lang.UnsupportedOperationException();
	}


	public int getShortestPath(T begin, T end, StackInterface<T> path) {
		throw new java.lang.UnsupportedOperationException();
	}


	
	public double getCheapestPath(T begin, T end, StackInterface<T> path) {

		resetVertices();
		
		boolean done = false;
		PriorityQueue<EntryPQ> priorityQueue = new PriorityQueue<>();
		VertexInterface<T> originVertex = vertices.get(begin);
		VertexInterface<T> endVertex = vertices.get(end);

		priorityQueue.add(new EntryPQ(originVertex, 0, null)); // origin/default vertex
		
		while(!done && !priorityQueue.isEmpty()) {
					
			EntryPQ frontEntry = priorityQueue.remove();
			VertexInterface<T> frontVertex = frontEntry.getVertex();
			
			Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
			Iterator<Double> weights = frontVertex.getWeightIterator();
						
			if(!frontVertex.isVisited()) {
				
				frontVertex.visit();
				frontVertex.setCost(frontEntry.getCost());
				frontVertex.setPredecessor(frontEntry.getPredecessor());
				
				if(frontVertex.equals(endVertex)) {
					
					done = true;
					
				} else {
					
					while(neighbors.hasNext()) {
						
						VertexInterface<T> nextNeighbor = neighbors.next();	//gets the nextNeighbor						
						double edgeWeightToNeighbor = weights.next(); //gets the edgeWeight to nextNeighbor
						
						if(!nextNeighbor.isVisited()) {
							
							double nextCost =  edgeWeightToNeighbor + frontVertex.getCost(); 
							priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
						}
					}
				}
			}
		}
		
		double pathCost = endVertex.getCost(); //gets the total cost
		path.push(endVertex.getLabel());
		
		VertexInterface<T> vertex = endVertex;
		
		while(vertex.hasPredecessor()) {
			
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());	
		}
			
		return pathCost; 
		
	}
	
	
	//Objects in the PriorityQueue are instances of this private class EntryPQ
	private class EntryPQ implements Comparable<EntryPQ>{
		
		private VertexInterface<T> vertex;
		private double cost;
		private VertexInterface<T> predecessor;
		
		
		public EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> predecessor) {
			
			this.vertex = vertex;
			this.cost = cost;
			this.predecessor = predecessor;
		}
		
		
		public VertexInterface<T> getVertex() {
			
			return vertex;
		}
		
		public double getCost() {
			
			return cost;
		}
		
		public VertexInterface<T> getPredecessor() {
			
			return predecessor;
		}
		
		@Override
		public int compareTo(EntryPQ entry) {
			
			if(this.cost < entry.cost)
				return -1;
			if(this.cost > entry.cost)
				return 1;
			return 0;
		}
		
	}

}
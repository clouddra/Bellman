import java.util.*;

// A0072292H
// Chong Yun Long

class Caesarean {
	private int V; // number of vertices in the graph (steps of a Caesarean section surgery)
	private int E; // number of edges in the graph (dependency information between various steps of a Caesarean section surgery)
	private Vector < IntegerPair > EL; // the unweighted graph, an edge (u, v) in EL implies that step u must be performed before step v
	private Vector < Integer > estT; // the estimated time to complete each step

	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	// --------------------------------------------
	private Vector < Vector < Integer > > AdjList; 
	private Stack < Integer > topoOrder ;
	private Vector < Boolean > visited ;
	private Vector < Integer > dist ;
	// --------------------------------------------

	public Caesarean() {
		// Write necessary codes during construction;
		//
		// write your answer here
		topoOrder = new Stack < Integer > ();
	}

	int Query() {

		// You have to report the quickest time to complete the whole Caesarean section surgery
		// (from vertex 0 to vertex V-1)
		//
		// write your answer here
		AdjList = new Vector < Vector < Integer > >();
		
		visited = new Vector<Boolean>() ;
		visited.setSize(V) ;
		Collections.fill(visited, false) ;

		dist = new Vector<Integer>() ;
		dist.setSize(V) ;
		Collections.fill(dist, -1) ;
		
		
		for (int i = 0; i < V; i++) 
			AdjList.add(new Vector<Integer>());		
		
		for (int i = 0 ; i < E ; i++) { // build the graph
			IntegerPair temp = EL.get(i) ;
			AdjList.get(temp.first()).add(new Integer(temp.second())) ;		
		}

		toposort(0) ;		
		bellFord() ;
		
		return dist.get(V-1) ;
	}

	// You can add extra function if needed
	// --------------------------------------------
	void toposort(int current)
	{
		visited.set(current, true) ;		
		for (Integer next : AdjList.get(current)) 
			if (!visited.get(next))
				toposort(next) ;
							
		topoOrder.push(current) ; //post-order
	}


	void bellFord()	// Do Bellman Ford in topological order
	{
		int u ;
		dist.set(0, estT.get(0)) ;
		
		while (!topoOrder.empty()){
			u  = topoOrder.pop() ; // reverse toposort
			for (Integer v : AdjList.get(u)) 
				if (dist.get(v) < dist.get(u) +  estT.get(v))
					dist.set(v, dist.get(u) +  estT.get(v)) ;
		}
	}
	
	// --------------------------------------------

	void run() {
		// do not alter this method
		Scanner sc = new Scanner(System.in);

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt(); E = sc.nextInt(); // read V and then E

			estT = new Vector < Integer > ();
			for (int i = 0; i < V; i++)
				estT.add(sc.nextInt());

			// clear the graph and read in a new graph as an unweighted Edge List (only using IntegerPair, not IntegerTriple)
			EL = new Vector < IntegerPair > ();
			for (int i = 0; i < E; i++)
				EL.add(new IntegerPair(sc.nextInt(), sc.nextInt())); // just directed edge (u -> v)

			System.out.println(Query());
		}
	}

	public static void main(String[] args) {
		// do not alter this method
		Caesarean ps6 = new Caesarean();
		ps6.run();
	}
}

package it.polito.tdp.borders.model;

import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	// System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"),
	// rs.getString("StateNme"));

	private BordersDAO dao;
	private Map<Integer, Country> idMap;
	private List<Border> borders;
	private Graph<Country, DefaultEdge> grafo;

	public Model() {
		this.dao = new BordersDAO();
		this.idMap = new HashMap<>();
		this.borders = new LinkedList<>();
	}

	public void createGraph(int anno) {
		this.grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);

		dao.loadAllCountries(idMap);

		Graphs.addAllVertices(grafo, idMap.values());

		this.borders = dao.getCountryPairs(idMap, anno);

		for (Border b : borders) {
			this.grafo.addEdge(idMap.get(b.getC1().getCode()), idMap.get(b.getC2().getCode()));
		}

		System.out.println("Grafo creato");
		System.out.println("# Vertici: " + this.grafo.vertexSet().size());
		System.out.println("# Archi: " + this.grafo.edgeSet().size());

	}
	
	public String getStampa() {
	  String s = "";
	  for(Country c : idMap.values()) {
		  s+=c.getName()+" "+grafo.degreeOf(c) + "\n";
	  }
	  
	  return s;
	}

	public List<Country> getCountries() {

		List<Country> result = new LinkedList<>();

		for (Country c : idMap.values()) {
			result.add(c);
		}
		return result;
	}

	public int getNumberOfConnectedComponents() {
		
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		
		return ci.connectedSets().size();
	}
	
	public List<Country> nazioniConnesse(Country inizio){
		
		List<Country> res = new LinkedList<>();
		
		BreadthFirstIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<Country, DefaultEdge>(grafo);
		res.add(inizio);
		
		while(bfv.hasNext()) {
			res.add(bfv.next());
		}
		
		return res;
	}

}

package it.polito.tdp.borders.model;

import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

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
		  s+=c.getName()+"\n";
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

	public List<Country> getNumberOfConnectedComponents() {
		
		List<Country> res = new LinkedList<>();

		for (Border b : this.borders) {
			if (!res.contains(b.getC1())) {
				res.add(b.getC1());
			}
			if (!res.contains(b.getC1())) {
				res.add(b.getC2());
			}

		}
		return res;
	}

}

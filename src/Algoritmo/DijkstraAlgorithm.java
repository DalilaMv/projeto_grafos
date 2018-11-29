package Algoritmo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Grafo.Aresta;
import Grafo.Vertice;
import Grafo.Voos;

public class DijkstraAlgorithm {
	private final List<Vertice> nodes;
	private final List<Aresta> Arestas;
	private Set<Vertice> settledNodes;
	private Set<Vertice> unSettledNodes;
	private Map<Vertice, Vertice> predecessors;
	private Map<Vertice, Integer> distance;

	public DijkstraAlgorithm(Voos graph) {
		// create a copy of the array so that we can operate on this array
		this.nodes = new ArrayList<Vertice>(graph.vertices());
		this.Arestas = new ArrayList<Aresta>(graph.arestas());
	}

	public void execute(String origem,int op) {
		Vertice source = null;
		for(Vertice v:nodes) {
			if(v.getRotulo().equals(origem)) {
				source = v;
				break;
			}
		}
		settledNodes = new HashSet<Vertice>();
		unSettledNodes = new HashSet<Vertice>();
		distance = new HashMap<Vertice, Integer>();
		predecessors = new HashMap<Vertice, Vertice>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Vertice node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			switch(op){
			case 1:
				break;
			case 2:
				findMinimalDistances(node);
				break;
			case 3:
				findMinimalTime(node);
				break;
			case 4:
				 break;
			}
		}
	}

	private void findMinimalDistances(Vertice node) {
		List<Vertice> adjacentNodes = getNeighbors(node);
		for (Vertice target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}
	
	private void findMinimalTime(Vertice node) {
		List<Vertice> adjacentNodes = getNeighbors(node);
		for (Vertice target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getTempo(node, target)) {
				distance.put(target, getShortestDistance(node) + getTempo(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	private int getDistance(Vertice node, Vertice target) {
		for (Aresta Aresta : Arestas) {
			if (Aresta.getV1().equals(node) && Aresta.getV2().equals(target)) {
				return Aresta.getDistancia();
			}
		}
		throw new RuntimeException("Should not happen");
	}
	
	private int getTempo(Vertice node, Vertice target) {
		for (Aresta Aresta : Arestas) {
			if (Aresta.getV1().equals(node) && Aresta.getV2().equals(target)) {
				String hr[] = Aresta.getDuracao().split(":");
				int hora = (Integer.parseInt(hr[0])*60)+Integer.parseInt(hr[1]);
				return hora;
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private List<Vertice> getNeighbors(Vertice node) {
		List<Vertice> neighbors = new ArrayList<Vertice>();
		for (Aresta Aresta : Arestas) {
			if (Aresta.getV1().equals(node) && !isSettled(Aresta.getV2())) {
				neighbors.add(Aresta.getV2());
			}
		}
		return neighbors;
	}

	private Vertice getMinimum(Set<Vertice> Verticees) {
		Vertice minimum = null;
		for (Vertice Vertice : Verticees) {
			if (minimum == null) {
				minimum = Vertice;
			} else {
				if (getShortestDistance(Vertice) < getShortestDistance(minimum)) {
					minimum = Vertice;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Vertice Vertice) {
		return settledNodes.contains(Vertice);
	}

	private int getShortestDistance(Vertice destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}
	
	public int distancia(String aero) {
		Vertice ar = null;
		for(Vertice v:nodes) {
			if(v.getRotulo().equals(aero)) {
				ar = v;
				break;
			}
		}
		return distance.get(ar);
	}

	/*
	 * This method returns the path from the source to the selected target and NULL
	 * if no path exists
	 */
	public LinkedList<Vertice> getPath(String target) {
		LinkedList<Vertice> path = new LinkedList<Vertice>();
		Vertice step = null;
		for (Vertice v:nodes) {
			if(v.getRotulo().equals(target)) {
				step = v;
				break;
			}
		}
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}
}

package Grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * classe que representa um grafo generico
 */
public class Voos {

	private ArrayList<Vertice> vertices;
	private ArrayList<Aresta> arestas;
	private ArrayList<Vertice> fechoTransitivo, jaVisitados, visitadosArvore;
	private HashMap<Vertice, Vertice> resp; 
	private int cont;
	private Random rand = new Random();

	public Voos() {
		vertices = new ArrayList<Vertice>();
		arestas = new ArrayList<Aresta>();
		cont = 0;
	}

	public Voos(ArrayList<Vertice> v) {
		vertices = v;
		arestas = new ArrayList<Aresta>();
		cont = vertices.size()-1;
	}
	
	public Voos(ArrayList<Vertice> v, ArrayList<Aresta> a) {
		vertices = v;
		arestas = a;
		cont = vertices.size()-1;
	}

	// Operações básicas em grafos
	public void adicionaVertice(Vertice vertice) {
		if ((!vertices.contains(vertice)) && (this.getVertice(vertice.getRotulo()) == null)) {
			vertices.add(vertice);
		}
	}

	public void removeVertice(Vertice vertice) {
		if (vertices.contains(vertice)) {
			vertices.remove(vertice);
			for (Vertice vAdj : vertice.getAdjacentes().keySet()) {
				vertice.removeAresta(vAdj);
			}
		} else {
			System.out.println("Valor de vertice inválido! -> " + vertice.toString());
		}
	}

	public void conecta(Vertice v1, Vertice v2, int peso) {
		if (!v1.getAdjacentes().containsKey(v2)) {
			Aresta aresta = new Aresta(v1, v2, peso);
			v1.adicionaAresta(aresta, v2);
			v1.updateGrau();
			v2.setGrau(v2.getGrau() + 1);
			arestas.add(aresta);
		} else {
			System.out.println("Aresta " + v1.toString() + "-" + v2.toString() + "já existe.");
		}

	}
	
	public void conecta(Vertice v1, Vertice v2, int distancia, String duracao, ArrayList<String> horarios) {
		if (!v1.getAdjacentes().containsKey(v2)) {
			Aresta aresta = new Aresta(v1, v2, distancia, duracao, horarios);
			v1.adicionaAresta(aresta, v2);
			v1.updateGrau();
			v2.updateGrau();
			arestas.add(aresta);
		} else {
			System.out.println("Aresta " + v1.toString() + "-" + v2.toString() + "já existe.");
		}

	}

	public void desconecta(Vertice v1, Vertice v2) {
		if (v1.getAdjacentes().containsKey(v2)) {
			Aresta aresta = getArestaEntreVertices(v1, v2);
			v1.removeAresta(v2);
			v1.updateGrau();
			v2.setGrau(v2.getGrau() + 1);
			arestas.remove(aresta);
		} else {
			System.out.println("Não é possível remover aresta inexistente.");
		}
	}

	public int ordem() {
		return vertices.size();
	}

	public ArrayList<Vertice> vertices() {
		return vertices;
	}

	public Vertice umVertice() {
		Random random = new Random();
		return vertices.get(random.nextInt(vertices.size()));
	}

	public Set<Vertice> adjacentes(Vertice v) {
		return v.getAdjacentes().keySet();
	}

	// MÉTODOS

	public void completo() {
		resp = new HashMap<Vertice, Vertice>();
		boolean atingiu = false;
		boolean completo = true;
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = i + 1; j < vertices.size() - 1; j++) {
				visitadosArvore = new ArrayList<Vertice>();
				atingiu = navegar(vertices.get(i), vertices.get(j));
				if (atingiu) {
					resp.put(vertices.get(i), vertices.get(j));
				} else {
					completo = false;
					resp.put(vertices.get(i), vertices.get(j));
				}
			}
		}
		if (completo) {
			System.out.println("\nÉ possivel");
			ArrayList<Vertice> result = new ArrayList<Vertice>();
			ArrayList<Vertice> ver;
			ArrayList<Aresta> are;
			for (Vertice v : vertices) {
				ver = new ArrayList<Vertice>();
				are = new ArrayList<Aresta>();
				for (Vertice vr : vertices) {
					ver.add(vr);
				}
				for (Aresta ar : arestas) {
					are.add(ar);
				}
				Voos g = new Voos(ver,are);
				g.removeVertice(v);
				if (!g.completoCompl()) {
					result.add(v);
				}
			}
			System.out.println("\nAeroportos que removidos atrapalham a condição: ");
			System.out.println(result);
		} else {
			System.out.println("\nNão é possivel");
			System.out.println("\nConjunto de aeroporto: ");
			System.out.println(resp);
		}
	}

	public boolean completoCompl() {
		resp = new HashMap<Vertice, Vertice>();
		boolean atingiu = false;
		boolean completo = true;
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = i + 1; j < vertices.size() - 1; j++) {
				visitadosArvore = new ArrayList<Vertice>();
				atingiu = navegar(vertices.get(i), vertices.get(j));
				if (atingiu) {
					resp.put(vertices.get(i), vertices.get(j));
				} else {
					completo = false;
					resp.put(vertices.get(i), vertices.get(j));
				}
			}
		}
		return completo;
	}

	private boolean navegar(Vertice v, Vertice vAnterior) {
		if (visitadosArvore.isEmpty()) {
			visitadosArvore.add(v);
		} else if (visitadosArvore.get(0) == v) {
			return true;
		}
		for (Vertice vAdj : adjacentes(v)) {
			if (vAdj != vAnterior) {
				if (navegar(vAdj, v))
					return true;
			} else if (visitadosArvore.size() == 1) {
				return true;
			}
		}

		return false;
	}

	// Métodos auxiliares

	// AUXILIAR
	private ArrayList<Vertice> fechoTransitivo(Vertice v) {
		fechoTransitivo = new ArrayList<Vertice>();
		jaVisitados = new ArrayList<Vertice>();
		return procuraFechoTransitivo(v);
	}

	private ArrayList<Vertice> procuraFechoTransitivo(Vertice v) {
		jaVisitados.add(v);
		for (Vertice vAdj : adjacentes(v)) {
			if (!jaVisitados.contains(vAdj))
				procuraFechoTransitivo(vAdj);
			if (!fechoTransitivo.contains(vAdj))
				fechoTransitivo.add(vAdj);
		}
		return fechoTransitivo;
	}

	public Aresta getArestaEntreVertices(Vertice _v1, Vertice _v2) {
		Aresta aresta;
		for (int i = 0; i < arestas.size(); i++) {
			aresta = this.getAresta(i);
			if (aresta.contemArestaDir(_v1, _v2))
				return aresta;
		}
		return null;
	}

	public Vertice getVertice(int i) {
		return vertices.get(i);
	}
	
	public Vertice getVertice(String nome) {
		Vertice ver = null;
		for (Vertice v : vertices) {
			if (v.getRotulo().equals(nome)) {
				ver = v;
			}
		}
		return ver;
	}

	public synchronized Aresta getAresta(int i) {
		return arestas.get(i);
	}

	public ArrayList<Vertice> getAdjacencias(Vertice v) {
		Aresta aresta;
		ArrayList<Vertice> adjacencias = new ArrayList<Vertice>();
		for (int i = 0; i < arestas.size(); i++) {
			aresta = this.getAresta(i);
			if (aresta.getV1() == v) {
				adjacencias.add(aresta.getV2());
			}
		}
		return adjacencias;
	}

	public synchronized int getQtdVertices() {
		return vertices.size();
	}

	public String toString() {
		return vertices.toString();
	}

	public String toString_info() {
		String ret = "";
		ret += "Grafo Direcionado\n";
		ret += "Vértices: \n";
		for (Vertice v : vertices)
			ret += "\t" + v + "\n";
		ret += "Arestas: \n";
		for (Vertice v : vertices)
			ret += "\t" + v + " -> " + adjacentes(v).toString() + "\n";
		ret += "Ordem: \n" + "\t" + ordem() + "\n";
		ret += "Grau:  \n";
		for (Vertice v : vertices)
			ret += "\t" + v + " -> " + v.getGrau() + "\n";

		return ret;
	}

	public boolean exists(Vertice v) {
		if (vertices.contains(v))
			return true;
		return false;
	}

}

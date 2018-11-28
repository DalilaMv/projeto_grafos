package Grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import Algoritmo.DisjointSet;

/**
 * classe que representa um grafo generico
 */
public class Rota {

	private ArrayList<Vertice> vertices;
	private ArrayList<Aresta> arestas;
	private ArrayList<Vertice> fechoTransitivo, jaVisitados, visitadosArvore;
	private HashMap<Vertice, Vertice> resp;
	private int cont;
	private Random rand = new Random();

	public Rota() {
		vertices = new ArrayList<Vertice>();
		arestas = new ArrayList<Aresta>();
		cont = 0;
	}

	public Rota(ArrayList<Vertice> v) {
		vertices = v;
		arestas = new ArrayList<Aresta>();
		cont = vertices.size() - 1;
	}

	public Rota(ArrayList<Vertice> v, ArrayList<Aresta> a) {
		vertices = new ArrayList<Vertice>();
		for (Vertice ver : v) {
			vertices.add(ver);
		}
		arestas = new ArrayList<Aresta>();
		for (Aresta are : a) {
			arestas.add(are);
		}
		cont = vertices.size() - 1;
	}

	public void empresaAerea() {
		this.kruskalMST(arestas, vertices);
	}

	private void kruskalMST(ArrayList<Aresta> graphArestas, ArrayList<Vertice> ver) {
		String outputMessage = "";
		ArrayList<Aresta> ares = new ArrayList<Aresta>();
		for (Aresta a : graphArestas) {
			ares.add(a);
		}

		Collections.sort(ares);
		ArrayList<Aresta> mstArestas = new ArrayList<Aresta>();

		DisjointSet nodeSet = new DisjointSet(ver.size() + 1);

		for (int i = 0; i < ares.size() && mstArestas.size() < (ver.size() - 1); i++) {
			Aresta currentAresta = ares.get(i);
			int root1 = nodeSet.find(currentAresta.getV1().getNome());
			int root2 = nodeSet.find(currentAresta.getV2().getNome());
			if (root1 != root2) {
				mstArestas.add(currentAresta);
				nodeSet.union(root1, root2);
			}
		}

		outputMessage += "\nOrdem de Inserção - Kruskal (" + mstArestas.size() + " Arestas)\n";
		int mstTotalArestaWeight = 0;
		for (Aresta Aresta : mstArestas) {
			outputMessage += Aresta + "\n";
			mstTotalArestaWeight += Aresta.getDistancia();
		}
		outputMessage += "\nPeso Total = " + mstTotalArestaWeight;

		System.out.println(outputMessage);

	}

	public void completo() {
		resp = new HashMap<Vertice, Vertice>();
		boolean atingiu = false;
		boolean completo = true;
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size() - 1; j++) {
				if (i != j) {
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
		}
		if (completo) {
			System.out.println("\nÉ possivel");
			ArrayList<Vertice> result = new ArrayList<Vertice>();
			Rota g;
			for (int i = 0; i < vertices.size(); i++) {
				g = new Rota();
				for (Vertice v : vertices) {
					v.setAdjacentes(new HashMap<Vertice, Aresta>());
					g.adicionaVertice(v);
				}
				for (Aresta a : arestas) {
					g.conecta(a.getV1(), a.getV2(), a.getDistancia(), a.getDuracao(), a.getHorarios());
				}
				if (!g.completoCompl(i)) {
					result.add(vertices.get(i));
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

	public boolean completoCompl(int v) {
		this.removeVertice(vertices.get(v));
		resp = new HashMap<Vertice, Vertice>();
		boolean atingiu = false;
		boolean completo = true;
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size() - 1; j++) {
				if (i != j) {
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

	public String reuniao(String horacompromisso, Vertice origem, Vertice destino) {
		visitadosArvore = new ArrayList<Vertice>();
		String ultimo = null;
		String hora[] = horacompromisso.split(":");

		return ultimo;
	}

	// Operações básicas em grafos
	public void adicionaVertice(Vertice vertice) {
		if ((!vertices.contains(vertice)) && (this.getVertice(vertice.getRotulo()) == null)) {
			vertice.setNome(cont++);
			vertices.add(vertice);
		}
	}

	public void removeVertice(Vertice vertice) {
		if (vertices.contains(vertice)) {
			vertices.remove(vertice);
			for (Vertice v : vertices) {
				this.desconecta(v, vertice);
				this.desconecta(vertice, v);
			}
		} else {
			System.out.println("Valor de vertice inválido! -> " + vertice.toString());
		}
	}

	public void conecta(Vertice v1, Vertice v2, int peso) {
		if (!v1.getAdjacentes().containsKey(v2)) {
			Aresta aresta = new Aresta(v1, v2, peso);
			v1.adicionaAresta(aresta, v2);
			v2.adicionaAresta(aresta, v1);
			v1.updateGrau();
			v2.updateGrau();
			arestas.add(aresta);
		}

	}

	public void conecta(Vertice v1, Vertice v2, int distancia, String duracao, ArrayList<String> horarios) {
		if (!v1.getAdjacentes().containsKey(v2)) {
			Aresta aresta = new Aresta(v1, v2, distancia, duracao, horarios);
			v1.adicionaAresta(aresta, v2);
			v2.adicionaAresta(aresta, v1);
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
			v2.removeAresta(v1);
			v1.updateGrau();
			v2.updateGrau();
			arestas.remove(aresta);
		}
	}

	public int ordem() {
		return vertices.size();
	}

	public ArrayList<Vertice> vertices() {
		return vertices;
	}

	public ArrayList<Aresta> arestas() {
		return arestas;
	}

	public Vertice umVertice() {
		Random random = new Random();
		return vertices.get(random.nextInt(vertices.size()));
	}

	public Set<Vertice> adjacentes(Vertice v) {
		return v.getAdjacentes().keySet();
	}

	// MÉTODOS

	public boolean isIsolado(Vertice _v1) { // verifica se não contém nenhuma aresta incidente no vertice
		boolean verificar = false;
		if (_v1.getGrau() == 0) {
			verificar = true;
		}
		return verificar;
	}

	public boolean isPendente(Vertice _v1) { // verifica se o vertice possui apenas uma aresta incidente
		boolean verificar = false;
		if (_v1.getGrau() == 1) {
			verificar = true;
		}
		return verificar;
	}

	private boolean isRegular() { // verifica se todos os vertices de um grafo tem o mesmo grau

		int grau = vertices.get(0).getGrau();
		int grauTestado;
		for (Vertice v : vertices) {
			grauTestado = v.getGrau();
			if (grauTestado != grau)
				return false;
		}
		return true;
	}

	public boolean isNulo() { // verifica se o grafo contém arestas
		boolean verificar = false;
		if (arestas.size() == 0) {
			verificar = true;
		}
		return verificar;
	}

	private boolean isCompleto() { // verifica se todo vértice é adjacente a todos os outros vértices do grafo

		int n = ordem() - 1;
		for (Vertice v : vertices) {
			if (v.getGrau() != n)
				return false;
		}
		return true;
	}

	private boolean isConexo() { // verifica se todos os vertices do grafo são conexos

		boolean valorVerdade = false;
		for (Vertice v : vertices) {
			if (fechoTransitivo(v).size() == ordem())
				valorVerdade = true;
			else {
				valorVerdade = false;
				break;
			}
		}
		return valorVerdade;
	}

	public boolean isEuleriano() { // verifica se o grau de cada vertice é par para afirmar se ele é euleriano ou
									// nao.
		boolean verificar = true;
		if (isConexo()) {
			for (Vertice v : vertices) {
				if (v.getGrau() % 2 != 0) {
					verificar = false;
				}
			}
		} else {
			verificar = false;
		}
		return verificar;
	}

	public boolean isUnicursal() { // verifica se o grafo nao é euleriano, se nao for ele verifica se o grafo
									// contém somente dois vertices de grau ímpar
		boolean verificar = true;
		int cont = 0;
		if (!isEuleriano() && isConexo()) {
			for (Vertice v : vertices) {
				if (v.getGrau() % 2 != 0) {
					cont++;
				}
			}
			if (cont != 2) {
				verificar = false;
			}
		} else {
			verificar = false;
		}
		return verificar;
	}

	public boolean isAdjacente(Vertice v1, Vertice v2) { //
		boolean verificar = false;
		if (getArestaEntreVertices(v1, v2) != null) {
			verificar = true;
		}
		return verificar;
	}

	public int getGrau(Vertice v1) {
		int grau = v1.getGrau();
		return grau;
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
			if (aresta.contemAresta(_v1, _v2))
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
			if (aresta.getV2() == v) {
				adjacencias.add(aresta.getV1());
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
		ret += "\nGrafo\n";
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

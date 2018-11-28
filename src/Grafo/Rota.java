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
	private ArrayList<Aresta> rota;
	private ArrayList<ArrayList<Aresta>> rotas;
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
		visitadosArvore = new ArrayList<Vertice>();
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
			visitadosArvore.add(vAnterior);
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

	public String reuniao(String horacompromisso, String origem, String destino) {
		Vertice a = null;
		Vertice b = null;
		rotas = new ArrayList<ArrayList<Aresta>>();
		rota = new ArrayList<Aresta>();
		visitadosArvore = new ArrayList<Vertice>();
		String ultimo = null;
		int hora[] = new int[2];
		int horaAtual[];
		String h[] = horacompromisso.split(":");
		for (Vertice v : vertices) {
			if (v.getRotulo().equals(origem)) {
				a = v;
			} else if (v.getRotulo().equals(destino)) {
				b = v;
			}
		}
		hora[0] = Integer.valueOf(h[0]);
		hora[1] = Integer.valueOf(h[1]);
		navegarReuniao(a, b);
		//System.out.println(rotas);
		for (ArrayList<Aresta> rt : rotas) {
			horaAtual = new int[2];
			String temp = null;
			Aresta are = rt.get(0);
			String dr[] = are.getDuracao().split(":");
			for (int i = are.getHorarios().size() - 1; i >= 0; i--) {
				String hr[] = are.getHorarios().get(i).split(":");
				int hro = Integer.parseInt(hr[0]) + Integer.parseInt(dr[0]);
				int min = Integer.parseInt(hr[1]) + Integer.parseInt(dr[1]);
				if (min >= 60) {
					min = min - 60;
					hro++;
				}
				if (hro < hora[0]) {
					horaAtual[0] = hro;
					horaAtual[1] = min;
					temp = are.getHorarios().get(i);
					break;
				} else if (hro == hora[0]) {
					if (min <= hora[1]) {
						horaAtual[0] = hro;
						horaAtual[1] = min;
						temp = are.getHorarios().get(i);
						break;
					}
				}
			}
			for (int g = 1; g < rt.size(); g++) {
				System.out.println(horaAtual[0]+":"+horaAtual[1]);
				are = rt.get(g);
				dr = are.getDuracao().split(":");
				String hr[] = are.getHorarios().get(0).split(":");
				int hro;
				int min;
				if (Integer.parseInt(hr[0]) > horaAtual[0]) {
					hro = Integer.parseInt(hr[0]) + Integer.parseInt(dr[0]);
					min = Integer.parseInt(hr[1]) + Integer.parseInt(dr[1]);
					if (min >= 60) {
						min = min - 60;
						hro++;
					}
					horaAtual[0] = hro;
					horaAtual[1] = min;
				} else if (Integer.parseInt(hr[0]) == horaAtual[0]) {
					if (Integer.parseInt(hr[1]) > horaAtual[1]) {
						hro = Integer.parseInt(hr[0]) + Integer.parseInt(dr[0]);
						min = Integer.parseInt(hr[1]) + Integer.parseInt(dr[1]);
						if (min >= 60) {
							min = min - 60;
							hro++;
						}
						horaAtual[0] = hro;
						horaAtual[1] = min;
					}
				}
				if (horaAtual[0] > hora[0]) {
					break;
				} else if (horaAtual[0] == hora[0]) {
					if (horaAtual[1] > hora[1]) {
						break;
					}
				}
			}
			if (horaAtual[0] < hora[0]) {
				ultimo = rt.get(0) + " -> " + temp;
				break;
			} else if (horaAtual[0] == hora[0]) {
				if (horaAtual[1] <= hora[1]) {
					ultimo = rt.get(0) + " -> " + temp;
					break;
				}
			}
		}
		return ultimo;
	}

	private void navegarReuniao(Vertice v, Vertice vAnterior) {
		if (visitadosArvore.isEmpty()) {
			visitadosArvore.add(vAnterior);
		} else if (visitadosArvore.get(0) == v) {
			rota.add(this.getArestaEntreVertices(v, vAnterior));
			rotas.add(new ArrayList<Aresta>(rota));
			rota.remove(this.getArestaEntreVertices(v, vAnterior));
			return;
		} else {
			visitadosArvore.add(v);
		}
		if (adjacentes(v).contains(visitadosArvore.get(0))) {
			rota.add(this.getArestaEntreVertices(v, vAnterior));
			navegarReuniao(visitadosArvore.get(0), v);
			return;
		}
		for (Vertice vAdj : adjacentes(v)) {
				if (visitadosArvore.size() == 1) {
					rota.add(this.getArestaEntreVertices(v, vAdj));
				} else {
					rota.add(this.getArestaEntreVertices(v, vAnterior));
				}
				if (vAdj != vAnterior && !visitadosArvore.contains(vAdj)) {
					navegarReuniao(vAdj, v);
				} else if (visitadosArvore.size() == 1) {
					rotas.add(rota);
					return;
				}
		}
		rota.remove(this.getArestaEntreVertices(v, vAnterior));
		return;
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

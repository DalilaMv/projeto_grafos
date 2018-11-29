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
	private HashMap<Vertice, ArrayList<Vertice>> vistados;
	private ArrayList<Vertice> resp;
	private ArrayList<Aresta> rota;
	private ArrayList<ArrayList<Aresta>> rotas;
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
		cont = vertices.size() - 1;
	}

	public Voos(ArrayList<Vertice> v, ArrayList<Aresta> a) {
		vertices = v;
		arestas = a;
		cont = vertices.size() - 1;
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
			for (Vertice v : vertices) {
				this.desconecta(v, vertice);
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

	public void completo() { // verifica se a partir de um aeroporto e possivel chegar em todos os outros
		resp = new ArrayList<Vertice>();
		boolean atingiu = false;
		boolean completo = true;
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size() - 1; j++) {
				if (i != j) {
					visitadosArvore = new ArrayList<Vertice>();
					atingiu = navegar(vertices.get(i), vertices.get(j));
					if (atingiu) {
						if (!resp.contains(vertices.get(i))) {
							resp.add(vertices.get(i));
						}
					} else {
						completo = false;
					}
				}
			}
		}
		if (completo) { // se possivel, mostramos os aeroportos que quando retirados atrapalham a
						// condição
			System.out.println("\nÉ possivel");
			ArrayList<Vertice> result = new ArrayList<Vertice>();
			Voos g;
			for (int i = 0; i < vertices.size(); i++) {
				g = new Voos();
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
		} else { // se nao, mostramos os conjuntos de aeroportos que cumprem essa condição
			System.out.println("\nNão é possivel");
			System.out.println("\nConjunto de aeroporto: ");
			System.out.println(resp);
		}
	}

	public boolean completoCompl(int v) { // verifica se o grafo e completo
		this.removeVertice(vertices.get(v));
		visitadosArvore = new ArrayList<Vertice>();
		boolean atingiu = false;
		boolean completo = true;
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size() - 1; j++) {
				if (i != j) {
					visitadosArvore = new ArrayList<Vertice>();
					atingiu = navegar(vertices.get(i), vertices.get(j));
					if (!atingiu) {
						completo = false;
						break;
					}
				}
			}
		}
		return completo;
	}

	private boolean navegar(Vertice v, Vertice vAnterior) { // navega pelo grafo de um ponto A ate um ponto B, se
															// possivel
		if (visitadosArvore.isEmpty()) {
			visitadosArvore.add(vAnterior);
		} else if (visitadosArvore.get(0) == v) {
			return true;
		} else {
			visitadosArvore.add(v);
		}
		for (Vertice vAdj : adjacentes(v)) {
			if (vAdj != vAnterior && !visitadosArvore.contains(vAdj)) {
				if (navegar(vAdj, v))
					return true;
			} else if (visitadosArvore.get(0) == vAdj) {
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
		vistados = new HashMap<Vertice, ArrayList<Vertice>>();
		String ultimo = null;
		int hora[] = new int[2];
		int horaAtual[];
		String h[] = horacompromisso.split(":");
		for (Vertice v : vertices) {
			vistados.put(v, new ArrayList<Vertice>());
			if (v.getRotulo().equals(origem)) {
				a = v;
			} else if (v.getRotulo().equals(destino)) {
				b = v;
			}
		}
		hora[0] = Integer.valueOf(h[0]);
		hora[1] = Integer.valueOf(h[1]);
		navegarReuniao(a, b);
		for (ArrayList<Aresta> rt : rotas) {
			horaAtual = new int[2];
			String temp = null;
			Aresta are = rt.get(0);
			String dr[] = are.getDuracao().split(":");
			for (int i = are.getHorarios().size() - 1; i >= 0; i--) {
				horaAtual = new int[2];
				are = rt.get(0);
				String hr[] = are.getHorarios().get(i).split(":");
				int hro = Integer.parseInt(hr[0]) + Integer.parseInt(dr[0]);
				int min = Integer.parseInt(hr[1]) + Integer.parseInt(dr[1]);
				if (min >= 60) {
					min = min - 60;
					hro++;
				}
				horaAtual[0] = hro;
				horaAtual[1] = min;
				temp = are.getHorarios().get(i);
				for (int g = 1; g < rt.size(); g++) {
					are = rt.get(g);
					if (are != null) {
						dr = are.getDuracao().split(":");
						for (int j = 0; j < are.getHorarios().size(); j++) {
							hr = are.getHorarios().get(j).split(":");
							if (Integer.parseInt(hr[0]) > horaAtual[0]) {
								hro = Integer.parseInt(hr[0]) + Integer.parseInt(dr[0]);
								min = Integer.parseInt(hr[1]) + Integer.parseInt(dr[1]);
								if (min >= 60) {
									min = min - 60;
									hro++;
								}
								horaAtual[0] = hro;
								horaAtual[1] = min;
								break;
							} else if (Integer.parseInt(hr[0]) == horaAtual[0]) {
								if (Integer.parseInt(hr[1]) >= horaAtual[1]) {
									hro = Integer.parseInt(hr[0]) + Integer.parseInt(dr[0]);
									min = Integer.parseInt(hr[1]) + Integer.parseInt(dr[1]);
									if (min >= 60) {
										min = min - 60;
										hro++;
									}
									horaAtual[0] = hro;
									horaAtual[1] = min;
									break;
								}
							}
							if (horaAtual[0] > hora[0]) {
								ultimo = "a";
								break;
							} else if (horaAtual[0] == hora[0]) {
								if (horaAtual[1] > hora[1]) {
									ultimo = "a";
									break;
								} else {
									ultimo = null;
								}
							} else {
								ultimo = null;
							}
						}
						if (ultimo != null) {
							break;
						}
					}
					if (ultimo == null) {
						break;
					}
				}
			}

			if (horaAtual[0] < hora[0]) {
				ultimo = "Rota: " + rt + "\nÚltimo Voo: " + rt.get(0) + " -> " + temp;
				break;
			} else if (horaAtual[0] == hora[0]) {
				if (horaAtual[1] <= hora[1]) {
					ultimo = rt + "\n" + rt.get(0) + " -> " + temp;
					break;
				}
			} else {
				ultimo = null;
			}
		}
		if (ultimo == null) {
			ultimo = "Não há um voo que chegue antes ou às " + horacompromisso + " em " + b + " partindo de " + a;
		}
		return ultimo;
	}

	private void navegarReuniao(Vertice v, Vertice vAnterior) {
		if (visitadosArvore.isEmpty()) {
			visitadosArvore.add(vAnterior);
			visitadosArvore.add(v);
		} else if (visitadosArvore.get(0) == v) {
			rota.add(this.getArestaEntreVertices(vAnterior, v));
			rotas.add(new ArrayList<Aresta>(rota));
			rota.remove(this.getArestaEntreVertices(vAnterior, v));
			return;
		} else {
			visitadosArvore.add(v);
		}
		if (adjacentes(v).contains(visitadosArvore.get(0))) {
			rota.add(this.getArestaEntreVertices(vAnterior, v));
			navegarReuniao(visitadosArvore.get(0), v);
			rota.remove(this.getArestaEntreVertices(vAnterior, v));
			return;
		}
		// System.out.println(rota);
		for (Vertice vAdj : adjacentes(v)) {
			if (visitadosArvore.get(1) == v) {
				for (int i = 0; i < vertices.size(); i++) {
					vistados.replace(vertices.get(i), new ArrayList<Vertice>());
				}
				rota = new ArrayList<Aresta>();
				rota.add(this.getArestaEntreVertices(v, vAdj));
			} else {
				if (!rota.contains(this.getArestaEntreVertices(vAnterior, v))) {
					rota.add(this.getArestaEntreVertices(vAnterior, v));
				}
			}
			if (vAdj != vAnterior && !vistados.get(v).contains(vAdj) && vAdj != visitadosArvore.get(1)) {
				vistados.get(v).add(vAdj);
				navegarReuniao(vAdj, v);
				rota.remove(this.getArestaEntreVertices(vAnterior, v));
			} else if (visitadosArvore.size() == 2) {
				rotas.add(rota);
				return;
			}
		}
		rota.remove(this.getArestaEntreVertices(vAnterior, v));
		return;
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

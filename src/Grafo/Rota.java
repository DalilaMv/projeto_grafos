package Grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * classe que representa um grafo generico
 */
public class Rota {

	private ArrayList<Vertice> vertices;
	private ArrayList<Aresta> arestas;
	private ArrayList<Vertice> fechoTransitivo, jaVisitados, visitadosArvore;
	private HashMap<Vertice, Vertice> resp;
	private int[][] matrizAdjacencia;
	private int[][] matrizCusto;
	private Random rand = new Random();

	public Rota() {
		vertices = new ArrayList<Vertice>();
		arestas = new ArrayList<Aresta>();
		this.matrizAdjacencia = new int[vertices.size()][vertices.size()];
		this.matrizCusto = new int[vertices.size()][vertices.size()];
	}

	public Rota(ArrayList<Vertice> v) {
		vertices = v;
		arestas = new ArrayList<Aresta>();
		this.matrizAdjacencia = new int[vertices.size()][vertices.size()];
		this.matrizCusto = new int[vertices.size()][vertices.size()];
	}

	public Rota(int[][] matrizAdjacencias, int[][] matrizCusto) {
		vertices = new ArrayList<Vertice>();
		arestas = new ArrayList<Aresta>();
		for (int i = 0; i < matrizAdjacencias.length; i++) {
			this.adicionaVertice(new Vertice(String.valueOf(i + 1)));
		}
		System.out.println("\nOrdem de Inserção - Prim: ");
		for (int i = 0; i < matrizAdjacencias.length; i++) {
			for (int j = 0; j < matrizAdjacencias.length; j++) {
				if (matrizAdjacencias[i][j] == 1) {
					Vertice v1 = this.getVertice(i);
					Vertice v2 = this.getVertice(j);
					if (!v1.getAdjacentes().containsKey(v2)) {
						Aresta a = new Aresta(v1, v2, matrizCusto[i][j]);
						System.out.println(a);
						this.conecta(v1, v2, matrizCusto[i][j]);
					}
				}
			}
		}
	}

	public void empresaAerea() {

		char colors[];
		int custo = 0;
		int mincusto = 0;
		int aeronaves = 0;
		char label_color;
		ArrayList<Integer> rotas = null;
		HashMap<ArrayList<Integer>, Integer> possible = new HashMap<ArrayList<Integer>, Integer>();

		for (int g = 0; g < arestas.size(); g++) {

			rotas = new ArrayList<Integer>();
			label_color = 'A';
			colors = new char[arestas.size()];
			aeronaves = 0;
			custo = 0;

			for (int i = g; i < colors.length; i++) {
				if (colors[i] == 0) {
					colors[i] = label_color;
					rotas.add(i);
					aeronaves = 1;
					custo = 0;
					label_color = (char) ((int) label_color + 1);
				}
				for (int j = i + 1; j < colors.length; j++) {
					if (colors[j] == 0) {
						boolean adj = false;
						for (int k = 0; k < colors.length; k++) {
							if (k != j && colors[k] == colors[i]) {
								if (isAdjacent(k, j)) {
									adj = true;
									break;
								}
							}
						}
						if (!adj) {
							colors[j] = colors[i];
							rotas.add(j);
							aeronaves++;
						}
					}
				}
				if ((!rotas.isEmpty()) && (aeronaves == (vertices.size() / 2))) {
					for (int r : rotas) {
						custo += arestas.get(r).getDistancia();
					}
					possible.put(rotas, custo);
				}
				rotas = new ArrayList<Integer>();
			}
		}
		for (ArrayList<Integer> p : possible.keySet()) {
			custo = possible.get(p);
			if (mincusto == 0) {
				mincusto = custo;
				rotas = p;
			} else if (mincusto > custo) {
				mincusto = custo;
				rotas = p;
			}
		}
		System.out.println("\nNúmero de Aeronaves: " + (vertices.size() / 2));
		System.out.println("\nRotas Utilizadas: ");
		for (int r : rotas) {
			System.out.println(arestas.get(r));
			custo += arestas.get(r).getDistancia();
		}
		System.out.println("\nConsumo Total: " + mincusto);
	}

	private boolean isAdjacent(int i, int j) {
		// check if the edge:i is adjacent to edge: j
		return (arestas.get(j).getV1().getNome() == arestas.get(i).getV1().getNome() || // whenever the two edges share
																						// a common node they are said
				arestas.get(j).getV1().getNome() == arestas.get(i).getV2().getNome() || // to be adjacent.
				arestas.get(j).getV2().getNome() == arestas.get(i).getV1().getNome()
				|| arestas.get(j).getV2().getNome() == arestas.get(i).getV2().getNome());

	}

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
		} else {
			System.out.println("\nNão é possivel");
			System.out.println("\nConjunto de aeroporto: ");
			System.out.println(resp);
		}
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
		if (!vertices.contains(vertice)) {
			vertices.add(vertice);
			this.updateMatrizAdjacencias();
			this.updateMatrizCusto();
		} else {
			System.out.println("Valor de vertice ja existente no grafo! -> " + vertice.toString());
		}
	}

	public void removeVertice(Vertice vertice) {
		if (vertices.contains(vertice)) {
			vertices.remove(vertice);
			for (Vertice vAdj : vertice.getAdjacentes().keySet()) {
				vertice.removeAresta(vAdj);
			}
			this.updateMatrizAdjacencias();
			this.updateMatrizCusto();
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
			this.updateMatrizAdjacencias();
			this.updateMatrizCusto();
		} else {
			System.out.println("Aresta " + v1.toString() + "-" + v2.toString() + "já existe.");
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
			this.updateMatrizAdjacencias();
			this.updateMatrizCusto();
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
			this.updateMatrizAdjacencias();
			this.updateMatrizCusto();
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

	private void updateMatrizAdjacencias() {
		Vertice vertice;
		Vertice verticeAux;
		ArrayList<Vertice> v_adjacencias;
		matrizAdjacencia = new int[vertices.size()][vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			vertice = this.getVertice(i);
			v_adjacencias = this.getAdjacencias(vertice);
			for (int j = 0; j < v_adjacencias.size(); j++) {
				verticeAux = v_adjacencias.get(j);
				this.matrizAdjacencia[i][verticeAux.getNome() - 1] = 1;
			}
		}
	}

	private void updateMatrizCusto() {
		Vertice vertice;
		Vertice verticeAux;
		ArrayList<Vertice> v_adjacencias;
		matrizCusto = new int[vertices.size()][vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			vertice = this.getVertice(i);
			v_adjacencias = this.getAdjacencias(vertice);
			for (int j = 0; j < v_adjacencias.size(); j++) {
				verticeAux = v_adjacencias.get(j);
				this.matrizCusto[i][verticeAux.getNome() - 1] = getArestaEntreVertices(vertice, verticeAux)
						.getDistancia();
			}
		}
	}

	public synchronized int getQtdVertices() {
		return vertices.size();
	}

	public String imprimeMatrizAdj() {
		String ret = "";
		for (int i = 0; i < matrizAdjacencia.length; i++) {
			for (int j = 0; j < matrizAdjacencia.length; j++) {
				ret += matrizAdjacencia[i][j] + "\t";
			}
			ret += "\n";
		}
		return ret;
	}

	public String imprimeMatrizCusto() {
		String ret = "";
		for (int i = 0; i < matrizCusto.length; i++) {
			for (int j = 0; j < matrizCusto.length; j++) {
				ret += matrizCusto[i][j] + "\t";
			}
			ret += "\n";
		}
		return ret;
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
		ret += "Matriz de Adjacência: \n" + imprimeMatrizAdj() + "\n";
		ret += "Matriz de Custo: \n" + imprimeMatrizCusto() + "\n";

		return ret;
	}

	public int[][] getMatrizCusto() {
		return matrizCusto.clone();
	}

	public int[][] getMatrizAdj() {
		return matrizAdjacencia.clone();
	}

	public void setCusto(int vOrigem, int vDestino, int custo) {
		this.matrizCusto[vOrigem][vDestino] = custo;
	}

	public int getCusto(int vOrigem, int vDestino) {
		return matrizCusto[vOrigem][vDestino];
	}

	public boolean exists(Vertice v) {
		if (vertices.contains(v))
			return true;
		return false;
	}

}

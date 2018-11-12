package Grafo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * classe que representa um grafo generico
 */
public class GrafoDir {

	private ArrayList<Vertice> vertices;
	private ArrayList<Aresta> arestas;
	private ArrayList<Vertice> fechoTransitivo, jaVisitados, visitadosArvore;
	private int[][] matrizAdjacencia;
	private int[][] matrizCusto;
	private Random rand = new Random();

	public GrafoDir() {
		vertices = new ArrayList<Vertice>();
		arestas = new ArrayList<Aresta>();
		this.matrizAdjacencia = new int[vertices.size()][vertices.size()];
		this.matrizCusto = new int[vertices.size()][vertices.size()];
	}

	public GrafoDir(ArrayList<Vertice> v) {
		vertices = v;
		arestas = new ArrayList<Aresta>();
		this.matrizAdjacencia = new int[vertices.size()][vertices.size()];
		this.matrizCusto = new int[vertices.size()][vertices.size()];
	}

	public GrafoDir(int[][] matrizAdjacencias, int[][] matrizCusto) {
		vertices = new ArrayList<Vertice>();
		arestas = new ArrayList<Aresta>();
		for (int i = 0; i < matrizAdjacencias.length; i++) {
			this.adicionaVertice(new Vertice(String.valueOf(i + 1)));
		}
		for (int i = 0; i < matrizAdjacencias.length; i++) {
			for (int j = 0; j < matrizAdjacencias.length; j++) {
				if (matrizAdjacencias[i][j] == 1) {
					Vertice v1 = this.getVertice(i);
					Vertice v2 = this.getVertice(j);
					this.conecta(v1, v2, matrizCusto[i][j]);
				}
			}
		}
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
			v1.updateGrau();
			v2.setGrau(v2.getGrau() + 1);
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
			v1.updateGrau();
			v2.setGrau(v2.getGrau() + 1);
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

	public Vertice umVertice() {
		Random random = new Random();
		return vertices.get(random.nextInt(vertices.size()));
	}

	public Set<Vertice> adjacentes(Vertice v) {
		return v.getAdjacentes().keySet();
	}

	// MÉTODOS

	public boolean hasCiclo() {
		boolean resp = false;
		for (Vertice v : vertices) {
			visitadosArvore = new ArrayList<Vertice>();
			resp = hasCiclo(v, v);
			if (resp) {
				break;
			}
		}

		return resp;
	}

	private boolean hasCiclo(Vertice v, Vertice vAnterior) {
		if (visitadosArvore.isEmpty()) {
			visitadosArvore.add(v);
		} else if (visitadosArvore.get(0) == v) {
			return true;
		}
		for (Vertice vAdj : adjacentes(v)) {
			if (vAdj != vAnterior)
				if (hasCiclo(vAdj, v))
					return true;
		}

		return false;
	}

	public int getGrauEntrada(Vertice v1) {
		int entrada = 0;
		int v = vertices.indexOf(v1);
		for (int j = 0; j < matrizAdjacencia.length; j++) {
			if (matrizAdjacencia[j][v] == 1) {
				entrada++;
			}
		}
		return entrada;
	}

	public int getGrauSaida(Vertice v1) {
		int saida = 0;
		int v = vertices.indexOf(v1);
		for (int j = 0; j < matrizAdjacencia.length; j++) {
			if (matrizAdjacencia[v][j] == 1) {
				saida++;
			}
		}
		return saida;
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
				this.matrizCusto[i][verticeAux.getNome() - 1] = getArestaEntreVertices(vertice, verticeAux).getPeso();
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
		ret += "Ciclo: \n" + "\t" + hasCiclo() + "\n";

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

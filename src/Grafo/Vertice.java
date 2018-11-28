package Grafo;

import java.util.HashMap;


public class Vertice {
	
		private String rotulo;
		private HashMap<Vertice, Aresta> adjacentes;
		private int grau;
		private int nome;
				
		
		public Vertice() {
			super();
			setAdjacentes(new HashMap<Vertice, Aresta>());
			setGrau(adjacentes.size());
		}
		
		public Vertice(String nome) {
			super();
			setRotulo(nome);
			setAdjacentes(new HashMap<Vertice, Aresta>());
			setGrau(adjacentes.size());
		}
		
		public void adicionaAresta(Aresta aresta, Vertice destino) {
			if (!adjacentes.containsKey(destino))
				adjacentes.put(destino, aresta);
			else
				System.out.println("Aresta já existe.");
		}

		public void removeAresta(Vertice vertice) {
			if (!adjacentes.containsKey(vertice)) {
				return;
			}
			adjacentes.remove(vertice);
		}

		public String getRotulo() {
			return rotulo;
		}
		
		public void setRotulo(String rotulo) {
			this.rotulo = rotulo;
		}
		
		public void setGrau(int grau) {
			this.grau = grau;
		}
		
		public int getGrau() { //grau do vértice (numero de arestas incidentes a ele)
			return grau;
		}
		
		public void updateGrau() {
			setGrau(adjacentes.size());
		}

		public HashMap<Vertice, Aresta> getAdjacentes() {
			return adjacentes;
		}

		public void setAdjacentes(HashMap<Vertice, Aresta> adjacentes) {
			this.adjacentes = adjacentes;
		}

		public int getNome() {
			return nome;
		}

		public void setNome(int nome) {
			this.nome = nome;
		}

		public String toString() {
			return "Vertice " + rotulo;
		}
}

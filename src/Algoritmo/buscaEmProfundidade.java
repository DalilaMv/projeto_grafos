package Algoritmo;

import Grafo.Rota;
import Grafo.Vertice;

public class buscaEmProfundidade {
	
	private boolean[] marcados;
	private int count;
	private Rota g;
	
	public buscaEmProfundidade (Rota g, Vertice v) {
		this.g = g;
		marcados = new boolean[g.ordem()];
		dfs(g, v);
	}
	
	private void dfs(Rota g, Vertice v){
		count++;
		marcados[g.vertices().indexOf(v)] = true;
		for (Vertice w : g.adjacentes(v)){
			if (!marcados[g.vertices().indexOf(w)])
				dfs(g, w);
		}
	}
	
	public boolean marcados(Vertice v){
		return marcados[g.vertices().indexOf(v)];
	}
	
	public int count() {
		return count;
	}
}

package Algoritmo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Grafo.Aresta;
import Grafo.Grafo;
import Grafo.Vertice;

public class Kruskal {
	public static Grafo kruskalMST(ArrayList<Aresta> graphArestas, ArrayList<Vertice> ver){
		String outputMessage="";
		Grafo g = new Grafo();
		ArrayList<Aresta> arestas = new ArrayList<Aresta>();
		for(Aresta a: graphArestas){
			a.getV1().setGrau(0);
			a.getV1().setAdjacentes(new HashMap<Vertice,Aresta>());
			a.getV2().setGrau(0);
			a.getV2().setAdjacentes(new HashMap<Vertice,Aresta>());
			arestas.add(a);
		}
		for(Vertice v: ver){
			v.setGrau(0);
			g.adicionaVertice(v);
		}

		Collections.sort(arestas);		
		ArrayList<Aresta> mstArestas = new ArrayList<Aresta>();	

		DisjointSet nodeSet = new DisjointSet(ver.size()+1);		

		for(int i=0; i<arestas.size() && mstArestas.size()<(ver.size()-1); i++){		
			Aresta currentAresta = arestas.get(i);
			int root1 = nodeSet.find(currentAresta.getV1().getNome());	
			int root2 = nodeSet.find(currentAresta.getV2().getNome());
			if(root1 != root2){			
				mstArestas.add(currentAresta);		
				nodeSet.union(root1, root2);	
				g.conecta(currentAresta.getV1(), currentAresta.getV2(), currentAresta.getPeso());
			}
		}

		outputMessage+="\nOrdem de Inserção - Kruskal ("+mstArestas.size()+" Arestas)\n";
		int mstTotalArestaWeight=0;
		for(Aresta Aresta: mstArestas){
			outputMessage+=Aresta +"\n";
			mstTotalArestaWeight += Aresta.getPeso();
		}
		outputMessage+="\nPeso Total = "+mstTotalArestaWeight;

		System.out.println(outputMessage);
		
		return g;
	}
}


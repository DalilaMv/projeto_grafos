package Main;

import java.util.ArrayList;

import Grafo.Arquivo;
import Grafo.Rota;
import Grafo.Vertice;
import Grafo.Voos;

public class Main {

	public static void main(String[] args) {

		Arquivo arq = new Arquivo("teste.txt");
		Rota grafo = new Rota();
		Voos grafo2 = new Voos();

		String s = arq.ler();
		String[] arestas = s.split(",");

		// Exemplos de grafos
		// Grafo Completo
		
		for (int i = 0; i < arestas.length - 1; i++) {
			String[] ar = arestas[i].split(";");
			Vertice v1 = new Vertice(ar[0]);
			Vertice v2 = new Vertice(ar[1]);
			grafo.adicionaVertice(v1);
			grafo.adicionaVertice(v2);
			ArrayList<String> horarios = new ArrayList<String>();
			v1 = grafo.getVertice(ar[0]);
			v2 = grafo.getVertice(ar[1]);
			for(int j = 5; j < ar.length; j++) {
				horarios.add(ar[j]);
			}
			grafo.conecta(v1, v2, Integer.valueOf(ar[3]),ar[4],horarios);
		}
		
		for (int i = 0; i < arestas.length - 1; i++) {
			String[] ar = arestas[i].split(";");
			Vertice v1 = new Vertice(ar[0]);
			Vertice v2 = new Vertice(ar[1]);
			grafo2.adicionaVertice(v1);
			grafo2.adicionaVertice(v2);
			ArrayList<String> horarios = new ArrayList<String>();
			v1 = grafo2.getVertice(ar[0]);
			v2 = grafo2.getVertice(ar[1]);
			for(int j = 5; j < ar.length; j++) {
				horarios.add(ar[j]);
			}
			if(Integer.valueOf(ar[2])==1) {
				grafo2.conecta(v1, v2, Integer.valueOf(ar[3]),ar[4],horarios);
			}else {
				grafo2.conecta(v2, v1, Integer.valueOf(ar[3]),ar[4],horarios);
			}
		}
		
		//System.out.println(grafo.toString_info());
		
		grafo.empresaAerea();
		//grafo.completo();


	}
}

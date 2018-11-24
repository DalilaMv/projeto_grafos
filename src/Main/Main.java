package Main;

import java.util.ArrayList;

import Grafo.Arquivo;
import Grafo.Rota;
import Grafo.Vertice;

public class Main {

	public static void main(String[] args) {

		Arquivo arq = new Arquivo("teste.txt");
		Rota grafo = new Rota();

		String s = arq.ler();
		String[] arestas = s.split(",");
		int ordem = arq.lerTotal();

		// Exemplos de grafos
		// Grafo Completo

		for (int i = 1; i <= ordem; i++) {
			Vertice v = new Vertice(String.valueOf(i));
			grafo.adicionaVertice(v);
		}
		
		for (int i = 0; i < arestas.length - 1; i++) {
			String[] ar = arestas[i].split(";");
			ArrayList<String> horarios = new ArrayList<String>();
			Vertice v1 = grafo.getVertice(Integer.valueOf(ar[0]) - 1);
			Vertice v2 = grafo.getVertice(Integer.valueOf(ar[1]) - 1);
			for(int j = 4; j < ar.length; j++) {
				horarios.add(ar[j]);
			}
			grafo.conecta(v1, v2, Integer.valueOf(ar[2]),ar[3],horarios);
		}
		
		//System.out.println(grafo.toString_info());
		
		grafo.empresaAerea();
		grafo.completo();


	}
}

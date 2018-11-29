package Main;

import java.util.ArrayList;
import java.util.LinkedList;

import Algoritmo.DijkstraAlgorithm;
import Grafo.Arquivo;
import Grafo.Rota;
import Grafo.Vertice;
import Grafo.Voos;

public class Main {

	public static void main(String[] args) {

		Arquivo arq = new Arquivo("dadosFortementeConexo.txt");
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
		
		System.out.println("\n------------Rotas------------");
		System.out.println("\n->Empresa Aerea");
		grafo.empresaAerea();
		System.out.println("\n------------Voos------------");
		DijkstraAlgorithm dr = new DijkstraAlgorithm(grafo2);
		
		System.out.println("\n->Menor Distância de GUARULHOS à GALEAO: ");
		dr.execute("GUARULHOS",2);
        LinkedList<Vertice> path = dr.getPath("GALEAO");
        if(path.size()>0) {
        System.out.println("\nRota: ");
        for (Vertice vertex : path) {
            System.out.println(vertex);
        }
        System.out.println("\nCusto: "+dr.distancia("GALEAO")+" Km");
        }else {
        	System.out.println("Não há rota entre estes dois aeroportos");
        }
        
		System.out.println("\n->Menor Tempo de Voo de GUARULHOS à GALEAO: ");
        dr.execute("GUARULHOS",3);
        path = dr.getPath("GALEAO");
        if(path.size()>0) {
        System.out.println("\nRota: ");
        for (Vertice vertex : path) {
            System.out.println(vertex);
        }
        System.out.println("\nCusto: "+dr.distancia("GALEAO")+" Mins");
        }else {
        	System.out.println("Não há rota entre estes dois aeroportos");
        }
		System.out.println("\n->Compromisso\n");
		System.out.println(grafo2.reuniao("17:00", "CONFINS", "GALEAO"));
		System.out.println("\n->De um aeroporto ao outro");
		grafo2.completo();


	}
}

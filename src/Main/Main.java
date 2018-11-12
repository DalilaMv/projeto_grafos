package Main;

import Algoritmo.buscaEmProfundidade;
import Grafo.Arquivo;
import Grafo.Grafo;
import Grafo.GrafoDir;
import Grafo.Vertice;

public class Main {

	public static void main(String[] args) {

		Arquivo arq = new Arquivo("aciclico.txt");
		Arquivo arq2 = new Arquivo("ciclico.txt");
		Arquivo arq3 = new Arquivo("completo.txt");
		Arquivo arq4 = new Arquivo("nulo.txt");
		Arquivo arq5 = new Arquivo("pendente.txt");
		Arquivo arq6 = new Arquivo("regular.txt");
		Arquivo arq7 = new Arquivo("unicursal.txt");
		Grafo grafo = new Grafo();
		GrafoDir grafo2 = new GrafoDir();
		Vertice teste = null;

		String s = arq.ler();
		String[] arestas = s.split(",");
		int ordem = arq.lerTotal();

		// Exemplos de grafos
		// Grafo Completo

		System.out.println("\nAciclico\n");
		for (int i = 1; i <= ordem; i++) {
			Vertice v = new Vertice(String.valueOf(i));
			grafo2.adicionaVertice(v);
		}

		for (int i = 0; i < arestas.length - 1; i++) {
			String[] ar = arestas[i].split(";");
			Vertice v1 = grafo2.getVertice(Integer.valueOf(ar[0]) - 1);
			Vertice v2 = grafo2.getVertice(Integer.valueOf(ar[1]) - 1);
			if (Integer.valueOf(ar[3]) == 1) {
				grafo2.conecta(v1, v2, Integer.valueOf(ar[2]));
			} else {
				grafo2.conecta(v2, v1, Integer.valueOf(ar[2]));
			}
		}

		System.out.println(grafo2.toString_info());

		for (int i = 0; i < grafo2.ordem(); i++) {
			teste = grafo2.getVertice(i);
			System.out.println("\n" + teste + ": ");
			System.out.println("\nGrau de Entrada");
			System.out.println(grafo2.getGrauEntrada(teste));
			System.out.println("\nGrau de Saida");
			System.out.println(grafo2.getGrauSaida(teste));
		}

		grafo = new Grafo();
		grafo2 = new GrafoDir();

		s = arq2.ler();
		arestas = s.split(",");
		ordem = arq2.lerTotal();

		System.out.println("\nCiclico\n");
		for (int i = 1; i <= ordem; i++) {
			Vertice v = new Vertice(String.valueOf(i));
			grafo2.adicionaVertice(v);
		}

		for (int i = 0; i < arestas.length - 1; i++) {
			String[] ar = arestas[i].split(";");
			Vertice v1 = grafo2.getVertice(Integer.valueOf(ar[0]) - 1);
			Vertice v2 = grafo2.getVertice(Integer.valueOf(ar[1]) - 1);
			if (Integer.valueOf(ar[3]) == 1) {
				grafo2.conecta(v1, v2, Integer.valueOf(ar[2]));
			} else {
				grafo2.conecta(v2, v1, Integer.valueOf(ar[2]));
			}
		}

		System.out.println(grafo2.toString_info());

		for (int i = 0; i < grafo2.ordem(); i++) {
			teste = grafo2.getVertice(i);
			System.out.println("\n" + teste + ": ");
			System.out.println("\nGrau de Entrada");
			System.out.println(grafo2.getGrauEntrada(teste));
			System.out.println("\nGrau de Saida");
			System.out.println(grafo2.getGrauSaida(teste));
		}

		grafo = new Grafo();
		grafo2 = new GrafoDir();

		s = arq3.ler();
		arestas = s.split(",");
		ordem = arq3.lerTotal();

		System.out.println("\nCompleto\n");
		for (int i = 1; i <= ordem; i++) {
			Vertice v = new Vertice(String.valueOf(i));
			grafo.adicionaVertice(v);
		}

		for (int i = 0; i < arestas.length - 1; i++) {
			String[] ar = arestas[i].split(";");
			Vertice v1 = grafo.getVertice(Integer.valueOf(ar[0]) - 1);
			Vertice v2 = grafo.getVertice(Integer.valueOf(ar[1]) - 1);
			grafo.conecta(v1, v2, Integer.valueOf(ar[2]));
		}

		System.out.println(grafo.toString_info());

		for (int i = 0; i < grafo.ordem(); i++) {
			teste = grafo.getVertice(i);
			System.out.println("\n" + teste + ": ");
			System.out.println("\nIsolado");
			System.out.println(grafo.isIsolado(teste));
			System.out.println("\nPendente");
			System.out.println(grafo.isPendente(teste));
		}

		teste = grafo.getVertice(0);
		buscaEmProfundidade search = new buscaEmProfundidade(grafo, teste);

		System.out.println(" ");
		if (search.count() != grafo.ordem())
			System.out.println("Grafo não conexo");
		else
			System.out.println("Grafo conexo");

		grafo.getAGMKruskal();

		grafo.getAGMPrim(teste);

		grafo = new Grafo();
		grafo2 = new GrafoDir();

		s = arq4.ler();
		arestas = s.split(",");
		ordem = arq4.lerTotal();

		System.out.println("\nNulo\n");
		for (int i = 1; i <= ordem; i++) {
			Vertice v = new Vertice(String.valueOf(i));
			grafo.adicionaVertice(v);
		}

		for (int i = 0; i < arestas.length - 1; i++) {
			String[] ar = arestas[i].split(";");
			Vertice v1 = grafo.getVertice(Integer.valueOf(ar[0]) - 1);
			Vertice v2 = grafo.getVertice(Integer.valueOf(ar[1]) - 1);
			grafo.conecta(v1, v2, Integer.valueOf(ar[2]));
		}

		System.out.println(grafo.toString_info());

		for (int i = 0; i < grafo.ordem(); i++) {
			teste = grafo.getVertice(i);
			System.out.println("\n" + teste + ": ");
			System.out.println("\nIsolado");
			System.out.println(grafo.isIsolado(teste));
			System.out.println("\nPendente");
			System.out.println(grafo.isPendente(teste));
		}
		
		teste = grafo.getVertice(0);

		search = new buscaEmProfundidade(grafo, teste);

		System.out.println(" ");
		if (search.count() != grafo.ordem())
			System.out.println("Grafo não conexo");
		else
			System.out.println("Grafo conexo");

		grafo.getAGMKruskal();

		grafo.getAGMPrim(teste);

		grafo = new Grafo();
		grafo2 = new GrafoDir();

		s = arq5.ler();
		arestas = s.split(",");
		ordem = arq5.lerTotal();

		System.out.println("\nPendente\n");
		for (int i = 1; i <= ordem; i++) {
			Vertice v = new Vertice(String.valueOf(i));
			grafo.adicionaVertice(v);
		}

		for (int i = 0; i < arestas.length - 1; i++) {
			String[] ar = arestas[i].split(";");
			Vertice v1 = grafo.getVertice(Integer.valueOf(ar[0]) - 1);
			Vertice v2 = grafo.getVertice(Integer.valueOf(ar[1]) - 1);
			grafo.conecta(v1, v2, Integer.valueOf(ar[2]));
		}

		System.out.println(grafo.toString_info());

		for (int i = 0; i < grafo.ordem(); i++) {
			teste = grafo.getVertice(i);
			System.out.println("\n" + teste + ": ");
			System.out.println("\nIsolado");
			System.out.println(grafo.isIsolado(teste));
			System.out.println("\nPendente");
			System.out.println(grafo.isPendente(teste));
		}
		
		teste = grafo.getVertice(0);

		search = new buscaEmProfundidade(grafo, teste);

		System.out.println(" ");
		if (search.count() != grafo.ordem())
			System.out.println("Grafo não conexo");
		else
			System.out.println("Grafo conexo");

		grafo.getAGMKruskal();

		grafo.getAGMPrim(teste);

		grafo = new Grafo();
		grafo2 = new GrafoDir();

		s = arq6.ler();
		arestas = s.split(",");
		ordem = arq6.lerTotal();

		System.out.println("\nRegular\n");
		for (int i = 1; i <= ordem; i++) {
			Vertice v = new Vertice(String.valueOf(i));
			grafo.adicionaVertice(v);
		}

		for (int i = 0; i < arestas.length - 1; i++) {
			String[] ar = arestas[i].split(";");
			Vertice v1 = grafo.getVertice(Integer.valueOf(ar[0]) - 1);
			Vertice v2 = grafo.getVertice(Integer.valueOf(ar[1]) - 1);
			grafo.conecta(v1, v2, Integer.valueOf(ar[2]));
		}

		System.out.println(grafo.toString_info());

		for (int i = 0; i < grafo.ordem(); i++) {
			teste = grafo.getVertice(i);
			System.out.println("\n" + teste + ": ");
			System.out.println("\nIsolado");
			System.out.println(grafo.isIsolado(teste));
			System.out.println("\nPendente");
			System.out.println(grafo.isPendente(teste));
		}

		teste = grafo.getVertice(0);
		
		search = new buscaEmProfundidade(grafo, teste);

		System.out.println(" ");
		if (search.count() != grafo.ordem())
			System.out.println("Grafo não conexo");
		else
			System.out.println("Grafo conexo");

		grafo.getAGMKruskal();

		grafo.getAGMPrim(teste);

		grafo = new Grafo();
		grafo2 = new GrafoDir();

		s = arq7.ler();
		arestas = s.split(",");
		ordem = arq7.lerTotal();

		System.out.println("\nUnicursal\n");
		for (int i = 1; i <= ordem; i++) {
			Vertice v = new Vertice(String.valueOf(i));
			grafo.adicionaVertice(v);
		}

		for (int i = 0; i < arestas.length - 1; i++) {
			String[] ar = arestas[i].split(";");
			Vertice v1 = grafo.getVertice(Integer.valueOf(ar[0]) - 1);
			Vertice v2 = grafo.getVertice(Integer.valueOf(ar[1]) - 1);
			grafo.conecta(v1, v2, Integer.valueOf(ar[2]));
		}

		System.out.println(grafo.toString_info());

		for (int i = 0; i < grafo.ordem(); i++) {
			teste = grafo.getVertice(i);
			System.out.println("\n" + teste + ": ");
			System.out.println("\nIsolado");
			System.out.println(grafo.isIsolado(teste));
			System.out.println("\nPendente");
			System.out.println(grafo.isPendente(teste));
		}

		teste = grafo.getVertice(0);
		
		search = new buscaEmProfundidade(grafo, teste);

		System.out.println(" ");
		if (search.count() != grafo.ordem())
			System.out.println("Grafo não conexo");
		else
			System.out.println("Grafo conexo");

		grafo.getAGMKruskal();

		grafo.getAGMPrim(teste);
	}
}


package Algoritmo;

import java.util.ArrayList;

import Grafo.Grafo;
import Grafo.Vertice;

public class Prim {

	public static Grafo prim(int[][] matrizSistema, int[][] matrizC, Vertice v) {
		/**
		 * ArrayList para guardar os v�rtices j� verificados pelo Algoritmo de Prim
		 */
		ArrayList<Boolean> verticesVerificados = new ArrayList<Boolean>();

		/**
		 * ArrayList para guardar as dist�ncias relativas para cada v�rtice em cada
		 * itera��o do Algoritmo de Prim
		 */
		ArrayList<Integer> distanciaRelativa = new ArrayList<Integer>();

		/**
		 * ArrayList unidimensional que guarda os n�s vizinhos de cada n� do grafo da
		 * �rvore final produzida pelo Algoritmo de Prim
		 */
		ArrayList<Integer> nosVizinhos = new ArrayList<Integer>();

		/**
		 * Inicializa��o de vari�veis
		 */
		for (Integer contador = 0; contador < matrizSistema[0].length; contador++) {
			verticesVerificados.add(false);
			nosVizinhos.add(0);
			distanciaRelativa.add(Integer.MAX_VALUE);
		}

		distanciaRelativa.set(0, new Integer(0));

		/**
		 * Defini��o do ponto que ser� a raiz da �rvore resultante
		 */
		Integer pontoAvaliado = v.getNome();

		/**
		 * Estrutura para execu��o das itera��es do Algoritmo de Prim
		 */
		for (Integer contadorPontosAvaliados = 0; contadorPontosAvaliados < matrizSistema[0].length; contadorPontosAvaliados++) {
			for (Integer contadorVizinhos = 0; contadorVizinhos < matrizSistema[0].length; contadorVizinhos++) {

				/**
				 * Verifica se o n� a ser avaliado nesta itera��o j� foi avaliado anteriormente;
				 * se sim, passa para a pr�xima itera��o
				 */
				if ((verticesVerificados.get(contadorVizinhos)) || (contadorVizinhos == pontoAvaliado)) {
					continue;
				}

				/**
				 * Duas compara��es aqui:
				 * 
				 * 1� - Verifica se na matrizSistema h� algum valor na coluna que seja > 0. Caso
				 * afirmativo, significa que h� uma aresta entre estes dois pontos do grafo.
				 * 
				 * 2� - Verifica se o peso da aresta entre os dois n�s � menor que a atual
				 * distanciaRelativa do n� vizinho.
				 * 
				 * Caso correto, a distanciaRelativa do n� vizinho ao que est� sendo avaliado no
				 * momento ser� atualizada pelo valor dessa nova distancia avaliada at� o
				 * pontoAvaliado.
				 */

				if ((matrizSistema[pontoAvaliado][contadorVizinhos] > 0)
						&& ((matrizSistema[pontoAvaliado][contadorVizinhos] < distanciaRelativa
								.get(contadorVizinhos)))) {

					distanciaRelativa.set(contadorVizinhos, matrizSistema[pontoAvaliado][contadorVizinhos]);

					nosVizinhos.set(contadorVizinhos, pontoAvaliado);

				}
			}

			/**
			 * Marca o v�rtice de pontoAvaliado como um v�rtice j� verificado
			 */
			verticesVerificados.set(pontoAvaliado, true);

			/**
			 * Prepara��o para sele��o do pr�ximo v�rtice a ser avaliado
			 */
			pontoAvaliado = new Integer(0);
			Integer distanciaComparada = new Integer(Integer.MAX_VALUE);

			/**
			 * Sele��o do pr�ximo v�rtice a ser avaliado
			 */
			for (Integer contador = 1; contador < verticesVerificados.size(); contador++) {

				/**
				 * Se o vertice a ser verificado j� foi verificado anteriormente (true) passa �
				 * pr�xima itera��o.
				 */
				if (verticesVerificados.get(contador)) {
					continue;
				}

				/**
				 * Se a dist�ncia relativa desse ponto for menor que a do ponto avaliado
				 * assume-se esse novo ponto como o ponto avaliado.
				 * 
				 * Ao final da itera��o, ser� selecionado o ponto com menor dist�ncia relativa.
				 */
				if (distanciaRelativa.get(contador) < distanciaComparada) {
					distanciaComparada = distanciaRelativa.get(contador);
					pontoAvaliado = contador;
				}

			}

		}

		int[][] matrizResposta = new int[matrizSistema[0].length][matrizSistema[0].length];
		int[][]	matrizCusto = new int[matrizC[0].length][matrizC[0].length];

		/**
		 * Cria��o da matrizResposta com a �rvore resultante do Algoritmo de Prim
		 */
		for (int contador = 1; contador < nosVizinhos.size(); contador++) {
			matrizResposta[contador][nosVizinhos.get(contador)] = matrizSistema[contador][nosVizinhos.get(contador)];
			matrizResposta[nosVizinhos.get(contador)][contador] = matrizResposta[contador][nosVizinhos.get(contador)];
			matrizCusto[contador][nosVizinhos.get(contador)] = matrizC[contador][nosVizinhos.get(contador)];
			matrizCusto[nosVizinhos.get(contador)][contador] = matrizCusto[contador][nosVizinhos.get(contador)];
		}
		Grafo g = new Grafo(matrizResposta,matrizCusto);
		return g;
	}
}

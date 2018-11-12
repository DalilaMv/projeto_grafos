package Grafo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Arquivo {
	private static int cont = 0;
	public static int contl = 0;
	private String nome;

	public Arquivo() {
		this.nome = "Arquivo.txt";
	}

	public Arquivo(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public String ler() {
		String s = "";
		try {
			File arquivo = new File(nome);
			FileReader ler = new FileReader(arquivo);
			BufferedReader lerb = new BufferedReader(ler);
			String linha = lerb.readLine();
			while (linha != null) {
				linha = lerb.readLine();
				s += linha+",";
			}
			lerb.close();
			ler.close();
		} catch (IOException e) {
			System.out.println("Arquivo não existe!");
		}
		return s;
	}
	public int lerTotal() {
		String linha;
		int total = 0;
		try {
			File arquivo = new File(nome);
			FileReader ler = new FileReader(arquivo);
			BufferedReader lerb = new BufferedReader(ler);
			linha = lerb.readLine();
			if (linha == null) {
				total = 0;
			} else {
				total = Integer.parseInt(linha);
			}
			lerb.close();
			ler.close();
		} catch (IOException e) {
			System.out.println("Arquivo não existe!");
		}
		return total;
	}



}
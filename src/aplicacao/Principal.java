package aplicacao;

import java.awt.Dimension;
import java.awt.Label;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.google.gson.Gson;

import model.Palo;

public class Principal  {

	public static void main(String[] args) {
		// p131 - segunda linha
		
		JFrame frame = new JFrame("Ordenação dos Palos");
		JPanel panel = new JPanel();
		frame.getContentPane();

		panel.setLayout(null);
		List<Palo> lista = new ArrayList<Palo>(lerJson());

		lista.sort(new Comparator<Palo>() {
			@Override
			public int compare(Palo o1, Palo o2) {
				return o1.getCentroidY().compareTo(o2.getCentroidY());
			}
		});
		
		List<Palo> listaPaloProcessado = retornaPalosProcessados(lista);
		
		for (Palo palo : listaPaloProcessado) {
			System.out.println(palo);
			panel.add(getLabel(palo));
		}
		
		
		// impressao do primeiro palo
//		Palo primeiroPalo = primeiroPalo(lista);
//		
//		List<Palo> primeiraLinha = retornaPrimeiraLinha(primeiroPalo, lista);
//		
//		for (Palo palo : primeiraLinha) {
//			panel.add(getLabel(palo));
//			lista.remove(palo);
//		}
		
		// removendo a primiera linha da lista original
		
//		lista.removeAll(primeiraLinha);
		
		// impressao do primeiro palo
//		primeiroPalo = primeiroPalo(lista);
//		
//		List<Palo> segundaLinha = retornaPrimeiraLinha(primeiroPalo, lista);
//		
//		for (Palo palo : segundaLinha) {
//			panel.add(getLabel(palo));
//		}
		
		
		

//		// impressao do primeiro palo
//		Palo p136 = lista.get(135);
//		System.out.println(p136);
//		panel.add(getLabel(p136));
//
//		Palo proximo = proximo(p136, lista);
//		while ( proximo != null) {
//			System.out.println(proximo);
//			panel.add(getLabel(proximo));
//			proximo = proximo(proximo, lista);
//		}

		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(1500, 1200);
		frame.setVisible(true);

	}
	
	public static List<Palo> retornaPalosProcessados(List<Palo> lista) {
		// lista de dos palos finais
		List<Palo> resultado = new ArrayList<Palo>();
		
		// definindo variaveis de controle
		int numero = 1;
		int linha = 0;
		
		// fazendo um clone dos valores da lista para manter a original sem modificacao
		List<Palo> listaClone = new ArrayList<Palo>(lista);
		// limpando os dados de processo de cada palo (numero e linha)
		for (int i = 0; i < listaClone.size(); i++) {
			listaClone.get(i).setNumero(null);
			listaClone.get(i).setLinha(null);
		}
		// obtendo o primeiro palo da primeira linha (eh o primiro de todas as linhas)
		Palo primeiroPalo = primeiroPalo(listaClone);
		
		// esse laco serah executado para encontar o primeiro palo das proximas linhas, ateh encontar o valor null
		while (primeiroPalo!= null) {
			//obtendo a primeira linha
			List<Palo> listaLinha = retornaPrimeiraLinha(primeiroPalo, listaClone);
			linha++;
			
			// 3 acoes: 
			//		1 - definindo valores dos palos
			//		2 - adicinando a lista de resultados
			// 		3 - removendo o palo da lista clone
			for (Palo palo : listaLinha) {
				// acao 01
				palo.setNumero(numero++);
				palo.setLinha(linha);
				// acao 02
				resultado.add(palo);
				// acao 03
				listaClone.remove(palo);
			}
			
			// apos a remocao dos palos referente a primeira linha
			// buscar novamente o primeiro palo
			primeiroPalo = primeiroPalo(listaClone);
		}
		
		return resultado;
	}
	
	
	// primeiro palo da primeira linha
	public static Palo primeiroPalo(List<Palo> listaPalo) {
		
		// ordena pelo valor eixo y para obter o palo mais alto
		listaPalo.sort((p1,p2) -> p1.getCentroidY().compareTo(p2.getCentroidY()));
		
		Palo anterior = (listaPalo.isEmpty()) ? null : anterior(listaPalo.get(0), listaPalo);
		Palo resultado = null;
		while ( anterior != null) {
			resultado = anterior;
		//	System.out.println(anterior);
			anterior = anterior(anterior, listaPalo);
		}
		
		return resultado;
		
	}
	
	// montar a primeira linha em uma lista
	// remover os palos adicionados na lista
	// buscar o primeiro palo do restante da lista :)
	
	public static List<Palo> retornaPrimeiraLinha(Palo paloInicial, List<Palo> lista) {
		
		List<Palo> resultado = new ArrayList<Palo>();
		resultado.add(paloInicial);
		
		Palo proximo = proximo(paloInicial, lista);
		while (proximo != null) {
			resultado.add(proximo);
			proximo = proximo(proximo, lista);
		}
		
		return resultado;
	}
	
	
	// retorna o palo anterior mais proximo (a esquerda)
	public static Palo anterior(Palo pAtual, List<Palo> listaPalo) {
		Palo resultado = null;
		int eixoX = 0;
		
		// busca do palo anterior mais proximo pelo eixo X (andando pela esquerda)
		for (Palo palo : listaPalo) {
			if (pAtual.isMenor(palo)) {
				if (palo.getCentroidX() > eixoX) {
					resultado = palo;
					eixoX = resultado.getCentroidX();
				}
			}
		}
		
		return resultado;
	}
	
	// retorna o proximo palo a direita
	public static Palo proximo(Palo pAtual, List<Palo> listaPalo) {
		Palo resultado = null;
		int eixoX = 999999999;
		
		// busca do proximo palo e selecao do mais proximo ao atual do eixo X
		for (Palo palo : listaPalo) {
			if (pAtual.isMaior(palo)) {
				if (palo.getCentroidX() < eixoX) {
					resultado = palo;
					eixoX = resultado.getCentroidX();
				}
			}
		}
		
		return resultado;
	}
	
	static int numero = 1;
	public static JLabel getLabel(Palo palo) {
		
		  JLabel label = new JLabel("p"+numero++);
		  label.setBounds(palo.getCentroidX(), palo.getCentroidY(), label.getPreferredSize().width, label.getPreferredSize().height);
	      
	      return label;
		
	}

	public static void main2(String[] args) {
		List<Palo> lista = lerJson();
		
		lista.sort( new Comparator<Palo>() {
			@Override
			public int compare(Palo o1, Palo o2) {
				return o1.getCentroidY().compareTo(o2.getCentroidY());
			}
		});
		
		for (Palo palo : lista) {
			System.out.println(palo);
		}

	}

	public static List<Palo> lerJson() {

		try {
			String json = String.join(" ",
					Files.readAllLines(Paths.get("./resources/palos.json"), StandardCharsets.UTF_8));

			Palo palos[] = new Gson().fromJson(json, Palo[].class);

			return Arrays.asList(palos);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}

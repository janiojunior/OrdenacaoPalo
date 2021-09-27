package aplicacao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
		List<Palo> lista = new ArrayList<Palo>(lerJson("palos_02.json"));

		// preenche os atributos linha e numero de cada palo
		List<Palo> listaPaloProcessado = retornaPalosProcessados(lista);
		
		// imprime no console e adiciona no panel
		for (Palo palo : listaPaloProcessado) {
			System.out.println(palo);
			panel.add(getLabel(palo));
		}
		
		// finaliza as configuracoes para apresentar no interface desktop swing
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(1500, 1200);
		frame.setVisible(true);

	}
	
	
	// Faz o processamento/numeracao (numero e linha) dos palos
	private static List<Palo> retornaPalosProcessados(List<Palo> lista) {
	
		// lista de palo para retorno
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
			//obtendo a primeira linha dos palos
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
	
	
	// primeiro palo da lista 
	private static Palo primeiroPalo(List<Palo> listaPalo) {
		
		// ordena pelo valor eixo y para obter o palo mais alto
		listaPalo.sort((p1,p2) -> p1.getCentroidY().compareTo(p2.getCentroidY()));
		
		Palo anterior = null;
		if (!listaPalo.isEmpty()) {
			anterior =  buscarPaloAnterior(listaPalo.get(0), listaPalo);
			// se exite palo na lista e o resultado for nulo, significa que o primeiro palo eh o item 0 
			if (anterior == null)
				anterior = listaPalo.get(0);
		}
		
		Palo resultado = null;
		while ( anterior != null) {
			resultado = anterior;
			anterior = buscarPaloAnterior(anterior, listaPalo);
		}
		
		return resultado;
		
	}
	
	// montar a primeira linha em uma lista
	// remover os palos adicionados na lista
	// buscar o primeiro palo do restante da lista :)
	private static List<Palo> retornaPrimeiraLinha(Palo paloInicial, List<Palo> lista) {
		
		List<Palo> resultado = new ArrayList<Palo>();
		resultado.add(paloInicial);
		
		Palo proximo = buscarProximoPalo(paloInicial, lista);
		while (proximo != null) {
			resultado.add(proximo);
			proximo = buscarProximoPalo(proximo, lista);
		}
		
		return resultado;
	}
	
	
	// retorna o palo anterior mais proximo (a esquerda)
	private static Palo buscarPaloAnterior(Palo pAtual, List<Palo> listaPalo) {
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
	
	// retorna o proximo palo (mais proximo) a direita
	private static Palo buscarProximoPalo(Palo pAtual, List<Palo> listaPalo) {
		Palo resultado = null;
		int eixoX = Integer.MAX_VALUE;
		
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
	// retorna um label (interface) com a identificacao do palo
	private static JLabel getLabel(Palo palo) {
		
		  JLabel label = new JLabel("p"+numero++);
		  label.setBounds(palo.getCentroidX(), palo.getCentroidY(), label.getPreferredSize().width, label.getPreferredSize().height);
	      
	      return label;
	}


	// faz a leitura de um Json e retorna uma lista de palos	
	private static List<Palo> lerJson(String nomeArquivo) {

		try {
			String json = String.join(" ",
					Files.readAllLines(Paths.get("./resources/"+nomeArquivo), StandardCharsets.UTF_8));

			Palo palos[] = new Gson().fromJson(json, Palo[].class);

			return Arrays.asList(palos);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}

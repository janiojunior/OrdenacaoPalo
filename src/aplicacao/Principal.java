package aplicacao;

import java.awt.Dimension;
import java.awt.Label;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
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
		List<Palo> lista = lerJson();

		lista.sort(new Comparator<Palo>() {
			@Override
			public int compare(Palo o1, Palo o2) {
				return o1.getCentroidY().compareTo(o2.getCentroidY());
			}
		});
		
		// impressao do primeiro palo
		Palo p136 = lista.get(135);
		System.out.println(p136);
		panel.add(getLabel(p136));

		Palo proximo = proximo(p136, lista);
		while ( proximo != null) {
			System.out.println(proximo);
			panel.add(getLabel(proximo));
			proximo = proximo(proximo, lista);
		}

		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(1500, 1200);
		frame.setVisible(true);

	}
	
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

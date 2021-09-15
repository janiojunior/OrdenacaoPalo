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

			for (Palo palo : lista) {
				panel.add(getLabel(palo));
			}
	      
	      panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	      frame.add(panel);
	      frame.setSize(1500, 1200);
	      frame.setVisible(true);

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

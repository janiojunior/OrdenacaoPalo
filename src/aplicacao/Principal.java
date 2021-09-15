package aplicacao;

import java.awt.Dimension;
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

	public static void main2(String[] args) {
	      JFrame frame = new JFrame("Ordenação dos Palos");
	      JPanel panel = new JPanel();
	      frame.getContentPane();
	      
	      JLabel p1 = new JLabel("p1");
	      Dimension size = p1.getPreferredSize();
	      p1.setBounds(1403, 42, p1.getPreferredSize().width, p1.getPreferredSize().height);
	      
	      JLabel p2 = new JLabel("p2");
	      p2.setBounds(1424, 43, p2.getPreferredSize().width, p2.getPreferredSize().height);
	      
	      JLabel p3 = new JLabel("p3");
	      p3.setBounds(1382, 43, p3.getPreferredSize().width, p3.getPreferredSize().height);
	      
	      JLabel p4 = new JLabel("p4");
	      p4.setBounds(626, 43, p4.getPreferredSize().width, p4.getPreferredSize().height);
	      
	      JLabel p5 = new JLabel("p5");
	      p5.setBounds(528, 43, p5.getPreferredSize().width, p5.getPreferredSize().height);
	      
	      JLabel p6 = new JLabel("p6");
	      p6.setBounds(734, 43, p6.getPreferredSize().width, p6.getPreferredSize().height);
	
	      
	      
//	      Palo [x=1400, y=19, width=7, height=46, centroidX=1403, centroidY=42]
//		  Palo [x=1422, y=25, width=5, height=37, centroidX=1424, centroidY=43]
//		  Palo [x=1379, y=20, width=7, height=46, centroidX=1382, centroidY=43]
//		  Palo [x=624, y=16, width=5, height=55, centroidX=626, centroidY=43]
//		  Palo [x=526, y=16, width=5, height=54, centroidX=528, centroidY=43]
//		  Palo [x=729, y=9, width=10, height=68, centroidX=734, centroidY=43]	      
	      
	      
	      panel.setLayout(null);
	      panel.add(p1);
	      panel.add(p2);
	      panel.add(p3);
	      panel.add(p4);
	      panel.add(p5);
	      panel.add(p6);
	      panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	      frame.add(panel);
	      frame.setSize(1500, 1200);
	      frame.setVisible(true);

	}

	public static void main(String[] args) {
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

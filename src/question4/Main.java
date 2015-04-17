package question4;

import java.awt.BorderLayout;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import question3.compImplem.RootCompQ3Impl;
import estore.RootComp;

@SuppressWarnings("serial")
public class Main extends JPanel {
	
	private JTextArea textArea = new JTextArea(15, 30);
	PrintStream out = new PrintStream(new TextAreaOutputStream(textArea));

	   public Main() {
	      setLayout(new BorderLayout());
	      add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	      System.setOut(out);

	   }
	
	private static void createAndShowGui() {
	      JFrame frame = new JFrame("Question 4");
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.getContentPane().add(new Main());
	      frame.pack();
	      frame.setLocationRelativeTo(null);
	      frame.setVisible(true);
	   }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createAndShowGui();
		
		RootComp.Component comp = (new RootCompQ3Impl()).newComponent();
		comp.go().run();
	}

}

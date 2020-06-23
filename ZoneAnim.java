import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ZoneAnim{

    public static void main(String[] args){
		
		StandardWindow window = new StandardWindow("title");
		GamePanel manager = GamePanel.getGamePanel();
		//JPanel container = new JPanel();
		//container.setLayout(new BorderLayout());
		//JTextField text = new JTextField();
		//container.add(manager, BorderLayout.PAGE_START);
		//container.add(text, BorderLayout.PAGE_END);
		window.setContentPane(manager);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		
	}

}
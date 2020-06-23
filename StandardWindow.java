import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StandardWindow extends JFrame{
	
	public StandardWindow(){
		this("default title", 600, 400);
	}
	
	public StandardWindow(String title){
		this(title, 600, 400);
	}
	
	public StandardWindow(int x, int y){
		this("default title", x, y);
	}
	
	public StandardWindow(String title, int x, int y){
		super(title);
		//setSize(x, y);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void ready(){
		setResizable(false);
		//setVisible(true);
		pack();
		setVisible(true);
		
	}
	/*
	public void add(Component c){
		super.add(c);
	}
	
	public void setDisplayPanel(JPanel p){
		setContentPane(p);
	}
	*/
	
	
}
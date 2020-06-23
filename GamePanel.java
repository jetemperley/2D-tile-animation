import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GamePanel extends JPanel implements ActionListener {

	// private static final long serialVersionUID = 1L;

	// the timer triggers the actionListener every tick (game loop)
	// Mouse mouse;
	JPanel ref;
	Timer timer;
	// key press/release flags whos indexes are 0-127
	// keyed according to its VK_CONSTANT integer value
	boolean[] keyPressFlags, keyTypedFlags;
	final int N_KEYS = 128;
	Matrix matrix;
	TileGraphics tg;
	Player player;
	// world stuff
	int mouseX = 0, mouseY = 0;
	int typeDelay = 0;
	Controllable inputFocus = null;
	private static GamePanel panel = null;

	private GamePanel() {

		Input input = new Input();
		addMouseListener(input);
		addKeyListener(input);
		addMouseWheelListener(input);
		tg = new TileGraphics(450, 450, 50, 50);
		setPreferredSize(new Dimension(tg.screenWidth, tg.screenHeight));
		setLayout(new BorderLayout());
		setFocusable(true);
		requestFocus();
		ref = this;
		timer = new Timer(25, this);

		keyPressFlags = new boolean[N_KEYS];
		keyTypedFlags = new boolean[N_KEYS];
		for (int i = 0; i < keyPressFlags.length; i++) {
			keyPressFlags[i] = false;
			keyTypedFlags[i] = false;
		}

		// LOAD IN CSV OF WORLD DATA

		BufferedReader dataFile = null;
		// calculate the number of lines (rows in the level datafile)
		int lineN = 0;
		try {
			dataFile = new BufferedReader(new FileReader("level1.csv"));
			lineN = (int) Files.lines(Paths.get("level1.csv")).count();
			// System.out.println("" + lineN);
		} catch (IOException ioe) {
			System.out.println("failed load level");
		}

		int[][] dataArray;
		dataArray = new int[lineN][0];

		// try and iterate through the level file data
		try {
			String line;
			// iterate through the lines (rows) of datafile
			// (line = dataFile.readLine()) != null
			for (int j = 0; j < lineN; j++) {
				line = dataFile.readLine();
				if (line == null) {
					System.out.println("level iterator broke early");
					break;
				}
				// int[] arr = new int[line.length];

				// calculate the number of commas in the line
				// which is the number of commas [values = commas + 1]
				int commaCount = 0;
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) == ',') {
						commaCount++;
					}
				}
				dataArray[j] = new int[commaCount + 1];

				// System.out.println("commaCount = " + commaCount);
				int commai = 0;
				int commaFollow = 0;
				String cellData;
				int k = 0;
				while (commai != -1) {

					commai = line.indexOf(",", commaFollow);
					if (commai == -1) {
						cellData = line.substring(commaFollow, line.length());
					} else {
						cellData = line.substring(commaFollow, commai);
						commaFollow = commai + 1;
					}
					dataArray[j][k] = Integer.valueOf(cellData);
					k++;
					// System.out.print(cellData);
				}
				// System.out.println();
				// System.out.println(line);
			}
		} catch (IOException ioe) {
			System.out.println("failed data to array");
		}

		for (int y = 0; y < dataArray.length; y++) {
			for (int x = 0; x < dataArray[y].length; x++) {
				// System.out.print(dataArray[y][x]);
			}
			// System.out.println();
		}

		// initialise cell array
		matrix = new Matrix(dataArray);

		player = new Player(2, 2);
		inputFocus = player;
		matrix.add(player);
		matrix.setFocus(player);
		matrix.add(new Slime(1, 1));
		matrix.add(new Apple(10, 2));
		matrix.add(new Inventory(5, 1));
		matrix.setDimensions(tg);

		timer.start();
		panel = this;

	}

	public static GamePanel getGamePanel(){
		if (panel == null){
			panel = new GamePanel();
		}
		return panel;
	}

	public void actionPerformed(ActionEvent e) {
		checkInput();
		updateState();
		repaint(); // invokes paintComponent()
	}

	public void checkInput() {
		for (int i = 0; i < keyPressFlags.length; i++) {
			// if (keyPressFlags[i]) {
			// 	player.applyKeyPressed(i);
			// }

			if (keyTypedFlags[i]) {
				inputFocus.applyKeyTyped(i);
				//System.out.println("typed");
				keyTypedFlags[i] = false;
			} else if (keyPressFlags[i]) {

				if (typeDelay >= 50) {
					inputFocus.applyKeyPressed(i);
					//System.out.println("pressed");
				} else {
					typeDelay += timer.getDelay();
				}

			}

		}
	}

	public void updateState() {

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		tg.setGraphics(g);
		setBackground(Color.GREEN);
		matrix.updateAndDraw();

		// player.draw(tg);
		// Cell cell = matrix.getCellAt(mouseX, mouseY);
		// if (cell != null) {
		// 	g.drawString("length: " + cell.objects.length + ", " + "volume: " + cell.currentVolume, mouseX, mouseY);
		// }

	}

	private class Input implements MouseListener, KeyListener, MouseWheelListener {
		public void mouseClicked(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {
			mouseY = ref.getMousePosition(false).y;
			mouseX = ref.getMousePosition(false).x;
		}

		public void mouseReleased(MouseEvent e) {

		}

		public void keyPressed(KeyEvent e) {
			//keyPressFlags[e.getKeyCode()] = true;
			
			if (!keyPressFlags[e.getKeyCode()] && !keyTypedFlags[e.getKeyCode()]) { 
				keyPressFlags[e.getKeyCode()] = true;
				keyTypedFlags[e.getKeyCode()] = true; 
				typeDelay = 0;
			}

			
			
			// System.out.println("After = press: " + keyPressFlags[e.getKeyCode()] + ",
			// type: " + keyTypedFlags[e.getKeyCode()]);
		}

		public void keyReleased(KeyEvent e) {
			keyPressFlags[e.getKeyCode()] = false;
			// typeDelay = 0;

		}

		public void keyTyped(KeyEvent e) {
			/*
			 * keyTypedFlags[e.getKeyCode()] = true; System.out.println("key typed");
			 */
		}

		public void mouseWheelMoved(MouseWheelEvent e) {
			tg.zoom(e.getWheelRotation());
		}

	}

}
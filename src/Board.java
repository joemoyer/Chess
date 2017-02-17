
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	private Timer timer;
	private final int DELAY = 10;
	private Image background, Wpawn, Wrook, Wknight, Wbishop, Wqueen, Wking, Bpawn, Brook, Bknight, Bbishop, Bqueen,
			Bking, Sbox, Mbox, Abox, Checkbox, CheckmateBox, MainMenu, PlayMenu, JoinMenu;
	public boolean selection = false, CanCastle = true, CastleLeft = false, CastleRight = false, errorMessage = false,
			isHosting = false, disconnect = false;

	static GameState state = GameState.MainMenu;

	static ServerSocket serverSocket;
	static Socket socket;
	static DataOutputStream out;
	static DataInputStream in;

	public char Turn = 'W', color = 'B';

	int[][] PTCoords = { { 12, 13, 14, 15, 16, 14, 13, 12 }, { 11, 11, 11, 11, 11, 11, 11, 11 },
			{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
			{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 21, 21, 21, 21, 21, 21, 21, 21 },
			{ 22, 23, 24, 25, 26, 24, 23, 22 }, },
			PCoords = { { 12, 13, 14, 15, 16, 14, 13, 12 }, { 11, 11, 11, 11, 11, 11, 11, 11 },
					{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 21, 21, 21, 21, 21, 21, 21, 21 }, { 22, 23, 24, 25, 26, 24, 23, 22 }, },
			Moves = { { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
					{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
					{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
					{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, },
			Attacks = { { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
					{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
					{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
					{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, };
	int[] Ebox = { 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00 },
			Fbox = { 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00 };

	int q = 0, x, Sx, y, Sy, Selectx, Selecty, check = 0, checkmate = 0, playerid = 0, c = 0, p = 0, messageWidth = 0,
			d = 0;

	char[] ChatBar = { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
			' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			ipBar = { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };

	String Chatbar = " ", IpBar = " ", HostIp = "Thing not added :(";
	String[] ChatBox = { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", };

	/*
	 * PTCoords = { { 12, 13, 14, 15, 16, 14, 13, 12 }, { 11, 11, 11, 11, 11,
	 * 11, 11, 11 }, { 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00,
	 * 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00,
	 * 00, 00, 00 }, { 21, 21, 21, 21, 21, 21, 21, 21 }, { 22, 23, 24, 26, 25,
	 * 24, 23, 22 }, }, PCoords = { { 12, 13, 14, 15, 16, 14, 13, 12 }, { 11,
	 * 11, 11, 11, 11, 11, 11, 11 }, { 00, 00, 00, 00, 00, 00, 00, 00 }, { 00,
	 * 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 }, { 00,
	 * 00, 00, 00, 00, 00, 00, 00 }, { 21, 21, 21, 21, 21, 21, 21, 21 }, { 22,
	 * 23, 24, 26, 25, 24, 23, 22 }, },
	 */
	public Board() {

		initBoard();
		LoadImage();

	}

	public void connect(String ip) {
		try {
			System.out.println("Connecting...");
			socket = new Socket(ip, 7777);
			System.out.println("Connection successful.");
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			Input input = new Input(in, this);
			Thread thread = new Thread(input);
			thread.start();

		} catch (Exception e) {
			System.out.println("Unable to start client");
		}
	}

	public void Host() throws Exception {
		String ip = Inet4Address.getLocalHost().getHostAddress();
		System.out.println(ip);
		color = 'B';
		HostIp = ip;
		isHosting = true;
		
		repaint();

		System.out.println("Starting Server...");
		serverSocket = new ServerSocket(7777);
		System.out.println("Server Started...");
		while (true) {
			System.out.println("loop started");
			socket = serverSocket.accept();
			System.out.println("Thing Did");
			System.out.println("Connection from:" + socket.getInetAddress());
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			Input input = new Input(in, this);
			Thread thread = new Thread(input);
			thread.start();
			break;
		}		
	}

	public void Join(String ip) throws Exception {
		color = 'W';
		flip();
		connect(ip);
	}

	public void LoadImage() {
		background = new ImageIcon("UImetalic.png").getImage();
		Wpawn = new ImageIcon("Wpawn.png").getImage();
		Wrook = new ImageIcon("Wrook.png").getImage();
		Wknight = new ImageIcon("Wknight.png").getImage();
		Wbishop = new ImageIcon("Wbishop.png").getImage();
		Wqueen = new ImageIcon("Wqueen.png").getImage();
		Wking = new ImageIcon("Wking.png").getImage();
		Bpawn = new ImageIcon("Bpawn.png").getImage();
		Brook = new ImageIcon("Brook.png").getImage();
		Bknight = new ImageIcon("Bknight.png").getImage();
		Bbishop = new ImageIcon("Bbishop.png").getImage();
		Bqueen = new ImageIcon("Bqueen.png").getImage();
		Bking = new ImageIcon("Bking.png").getImage();
		Sbox = new ImageIcon("Select.png").getImage();
		Mbox = new ImageIcon("Move.png").getImage();
		Abox = new ImageIcon("Attack.png").getImage();
		Checkbox = new ImageIcon("Check.png").getImage();
		CheckmateBox = new ImageIcon("Checkmate.png").getImage();
		MainMenu = new ImageIcon("MainMenu.png").getImage();
		PlayMenu = new ImageIcon("PlayMenu.png").getImage();
		JoinMenu = new ImageIcon("JoinMenu.png").getImage();
	}

	public Image getImage(int i) {
		Image I = Wpawn;
		switch (i) {
		case 0:
			I = null;
			break;
		case 11:
			I = Wpawn;
			break;
		case 12:
			I = Wrook;
			break;
		case 13:
			I = Wknight;
			break;
		case 14:
			I = Wbishop;
			break;
		case 15:
			I = Wqueen;
			break;
		case 16:
			I = Wking;
			break;
		case 21:
			I = Bpawn;
			break;
		case 22:
			I = Brook;
			break;
		case 23:
			I = Bknight;
			break;
		case 24:
			I = Bbishop;
			break;
		case 25:
			I = Bqueen;
			break;
		case 26:
			I = Bking;
			break;
		}
		return I;
	}
	
	public void Reset() {
		selection = false; 
		CanCastle = true;
		CastleLeft = false;
		CastleRight = false;
		errorMessage = false;
		isHosting = false;
		disconnect = false;

		state = GameState.MainMenu;

		serverSocket = null;
		socket = null;
		out = null;
		in = null;

		Turn = 'W';
		color = 'B';

		int[][] NewPTCoords = { { 12, 13, 14, 15, 16, 14, 13, 12 }, { 11, 11, 11, 11, 11, 11, 11, 11 },
				{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
				{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 21, 21, 21, 21, 21, 21, 21, 21 },
				{ 22, 23, 24, 25, 26, 24, 23, 22 }, },	
		NewPCoords = { { 12, 13, 14, 15, 16, 14, 13, 12 }, { 11, 11, 11, 11, 11, 11, 11, 11 },
				{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
				{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
				{ 21, 21, 21, 21, 21, 21, 21, 21 }, { 22, 23, 24, 25, 26, 24, 23, 22 }, },
		NewMoves = { { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
				{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
				{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
				{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, },
		NewAttacks = { { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
				{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
				{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 },
				{ -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, { -1, -1 }, };
		for (int i = 0; i < NewPTCoords.length; i++) {
			for (int j = 0; j < NewPTCoords[i].length; j++) {
				PTCoords[i][j] = NewPTCoords[i][j];
				PCoords[i][j] = NewPCoords[i][j];
			}
		}
		for (int i = 0; i < NewMoves.length; i++) {
			for (int j = 0; j < NewMoves[i].length; j++) {
				Moves[i][j] = NewMoves[i][j];
				Attacks[i][j] = NewAttacks[i][j];
			}
		}
		for (int i = 0; i < Ebox.length; i++) {
			Ebox[i] = 0;
			Fbox[i] = 0;
		}

		q = 0;
		x = -1;
		Sx = -1;
		y = -1;
		Sy = -1;
		Selectx = -1;
		Selecty = -1;
		check = 0;
		checkmate = 0;
		playerid = 0;
		c = 0;
		p = 0;
		messageWidth = 0;
		d = 0;

		for (int i = 0; i < ChatBar.length; i++) {
			ChatBar[i] = ' ';
			ipBar[i] = ' ';
		}

		Chatbar = " ";
		IpBar = " ";
		HostIp = "Thing not added :(";
		
		for(int i = 0; i < ChatBox.length; i++) {
			ChatBox[i] = " ";
		}
	}

	private void initBoard() {

		addKeyListener(new TAdapter());
		addMouseListener(new MAdapter());
		setFocusable(true);
		setBackground(Color.GRAY);

		timer = new Timer(DELAY, this);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);

		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		switch (state) {

		case MainMenu:
			g2d.drawImage(MainMenu, 0, 0, this);
			
			if (disconnect == true) {
				drawString(g2d, "Opponent Has Disconnected, Game Terminated", "Times New Roman", Color.RED, 18, 50, 350);
			}
			break;

		case PlayMenu:
			g2d.drawImage(PlayMenu, 0, 0, this);
			break;

		case JoinMenu:
			g2d.drawImage(JoinMenu, 0, 0, this);
			drawString(g2d, IpBar, "Calibri", Color.BLACK, 18, 900, 240);
			if (errorMessage) {
				drawString(g2d, "Could not connect, try again", "Calibri", Color.RED, 12, 850, 200);
			}
			break;

		case Instructions:
			break;

		case Game:

			g2d.drawImage(background, 0, 0, this);

			// Pieces
			for (int j = 0; j < 8; j++) {
				for (int i = 0; i < 8; i++) {
					int t = PCoords[i][j];
					switch (t) {
					case 11:
						g2d.drawImage(Wpawn, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					case 12:
						g2d.drawImage(Wrook, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					case 13:
						g2d.drawImage(Wknight, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					case 14:
						g2d.drawImage(Wbishop, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					case 15:
						g2d.drawImage(Wqueen, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					case 16:
						g2d.drawImage(Wking, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					case 21:
						g2d.drawImage(Bpawn, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					case 22:
						g2d.drawImage(Brook, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					case 23:
						g2d.drawImage(Bknight, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					case 24:
						g2d.drawImage(Bbishop, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					case 25:
						g2d.drawImage(Bqueen, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					case 26:
						g2d.drawImage(Bking, (311 + (64 * j)), (60 + (66 * i)), this);
						break;
					}

				}
			}
			if (selection) {
				g2d.drawImage(Sbox, (310 + (64 * Selectx)), (60 + (66 * Selecty)), this);
			}
			for (int i = 0; i < 29; i++) {
				if (Moves[i][0] != -1) {
					g2d.drawImage(Mbox, (310 + (64 * Moves[i][0])), (60 + (66 * Moves[i][1])), this);
				}
				if (Attacks[i][0] != -1) {
					g2d.drawImage(Abox, (310 + (64 * Attacks[i][0])), (60 + (66 * Attacks[i][1])), this);
				}
			}

			for (int i = 0; i < 5; i++) {
				g2d.drawImage(getImage(Ebox[i]), (18 + (54 * i)), (72), this);
				g2d.drawImage(getImage(Fbox[i]), (18 + (54 * i)), (340), this);
			}
			for (int i = 5; i < 10; i++) {
				g2d.drawImage(getImage(Ebox[i]), (18 + (54 * (i - 5))), (138), this);
				g2d.drawImage(getImage(Fbox[i]), (18 + (54 * (i - 5))), (406), this);
			}
			for (int i = 10; i < 15; i++) {
				g2d.drawImage(getImage(Ebox[i]), (18 + (54 * (i - 10))), (204), this);
				g2d.drawImage(getImage(Fbox[i]), (18 + (54 * (i - 10))), (472), this);
			}
			// ^^^^^^^^^^^^^^^^^^^^^^^^^^
			if (Turn == 'W') {
				drawString(g2d, "White", "Times New Roman", Color.WHITE, 50, 970, 140);
			} else if (Turn == 'B') {
				drawString(g2d, "Black", "Times New Roman", Color.BLACK, 50, 970, 140);
			} else if (Turn == 'L') {
				drawString(g2d, "Checkmate", "Times New Roman", Color.RED, 35, 970, 140);
			}
			if (check > 0) {
				g2d.drawImage(Checkbox, 430, 275, this);
				check -= 1;
			}
			if (checkmate > 0) {
				g2d.drawImage(CheckmateBox, 375, 275, this);
				checkmate -= 1;
			}
			for (int i = 0; i < ChatBox.length; i++) {
				drawString(g2d, ChatBox[i], "Times New Roman", Color.WHITE, 18, 900, 525 - (35 * i));
			}
			if (isHosting == true) {
			drawString(g2d, "Your Ip: " + HostIp, "Times New Roman", Color.WHITE, 18, 25, 25);
			}
			drawString(g2d, Chatbar, "Times New Roman", Color.WHITE, 18, 900, 575);
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// character.move();
		repaint();
	}

	private class TAdapter extends KeyAdapter {

		public boolean Shift = false;

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_SHIFT) {
				Shift = false;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_SHIFT) {
				Shift = true;
			}
			/*
			 * THIS SHIT AIN'T GETTIN' EASY LIKE DAYUM
			 */
			if (state == GameState.Game || state == GameState.JoinMenu) {
				if (key == KeyEvent.VK_1) {
					if (Shift) {
						addKey('!');
					} else {
						addKey('1');
					}
				}
				if (key == KeyEvent.VK_2) {
					if (Shift) {
						addKey('@');
					} else {
						addKey('2');
					}
				}
				if (key == KeyEvent.VK_3) {
					if (Shift) {
						addKey('#');
					} else {
						addKey('3');
					}
				}
				if (key == KeyEvent.VK_4) {
					if (Shift) {
						addKey('$');
					} else {
						addKey('4');
					}
				}
				if (key == KeyEvent.VK_5) {
					if (Shift) {
						addKey('%');
					} else {
						addKey('5');
					}
				}
				if (key == KeyEvent.VK_6) {
					if (Shift) {
						addKey('^');
					} else {
						addKey('6');
					}
				}
				if (key == KeyEvent.VK_7) {
					if (Shift) {
						addKey('&');
					} else {
						addKey('7');
					}
				}
				if (key == KeyEvent.VK_8) {
					if (Shift) {
						addKey('*');
					} else {
						addKey('8');
					}
				}
				if (key == KeyEvent.VK_9) {
					if (Shift) {
						addKey('(');
					} else {
						addKey('9');
					}
				}
				if (key == KeyEvent.VK_0) {
					if (Shift) {
						addKey(')');
					} else {
						addKey('0');
					}
				}
				if (key == KeyEvent.VK_Q) {
					if (Shift) {
						addKey('Q');
					} else {
						addKey('q');
					}
				}
				if (key == KeyEvent.VK_W) {
					if (Shift) {
						addKey('W');
					} else {
						addKey('w');
					}
				}
				if (key == KeyEvent.VK_E) {
					if (Shift) {
						addKey('E');
					} else {
						addKey('e');
					}
				}
				if (key == KeyEvent.VK_R) {
					if (Shift) {
						addKey('R');
					} else {
						addKey('r');
					}
				}
				if (key == KeyEvent.VK_T) {
					if (Shift) {
						addKey('T');
					} else {
						addKey('t');
					}
				}
				if (key == KeyEvent.VK_Y) {
					if (Shift) {
						addKey('Y');
					} else {
						addKey('y');
					}
				}
				if (key == KeyEvent.VK_U) {
					if (Shift) {
						addKey('U');
					} else {
						addKey('u');
					}
				}
				if (key == KeyEvent.VK_I) {
					if (Shift) {
						addKey('I');
					} else {
						addKey('i');
					}
				}
				if (key == KeyEvent.VK_O) {
					if (Shift) {
						addKey('O');
					} else {
						addKey('o');
					}
				}
				if (key == KeyEvent.VK_P) {
					if (Shift) {
						addKey('P');
					} else {
						addKey('p');
					}
				}
				if (key == KeyEvent.VK_A) {
					if (Shift) {
						addKey('A');
					} else {
						addKey('a');
					}
				}
				if (key == KeyEvent.VK_S) {
					if (Shift) {
						addKey('S');
					} else {
						addKey('s');
					}
				}
				if (key == KeyEvent.VK_D) {
					if (Shift) {
						addKey('D');
					} else {
						addKey('d');
					}
				}
				if (key == KeyEvent.VK_F) {
					if (Shift) {
						addKey('F');
					} else {
						addKey('f');
					}
				}
				if (key == KeyEvent.VK_G) {
					if (Shift) {
						addKey('G');
					} else {
						addKey('g');
					}
				}
				if (key == KeyEvent.VK_H) {
					if (Shift) {
						addKey('H');
					} else {
						addKey('h');
					}
				}
				if (key == KeyEvent.VK_J) {
					if (Shift) {
						addKey('J');
					} else {
						addKey('j');
					}
				}
				if (key == KeyEvent.VK_K) {
					if (Shift) {
						addKey('K');
					} else {
						addKey('k');
					}
				}
				if (key == KeyEvent.VK_L) {
					if (Shift) {
						addKey('L');
					} else {
						addKey('l');
					}
				}
				if (key == KeyEvent.VK_Z) {
					if (Shift) {
						addKey('Z');
					} else {
						addKey('z');
					}
				}
				if (key == KeyEvent.VK_X) {
					if (Shift) {
						addKey('X');
					} else {
						addKey('x');
					}
				}
				if (key == KeyEvent.VK_C) {
					if (Shift) {
						addKey('C');
					} else {
						addKey('c');
					}
				}
				if (key == KeyEvent.VK_V) {
					if (Shift) {
						addKey('V');
					} else {
						addKey('v');
					}
				}
				if (key == KeyEvent.VK_B) {
					if (Shift) {
						addKey('B');
					} else {
						addKey('b');
					}
				}
				if (key == KeyEvent.VK_N) {
					if (Shift) {
						addKey('N');
					} else {
						addKey('n');
					}
				}
				if (key == KeyEvent.VK_M) {
					if (Shift) {
						addKey('M');
					} else {
						addKey('m');
					}
				}
				if (key == KeyEvent.VK_PERIOD) {
					if (Shift) {
						addKey('>');
					} else {
						addKey('.');
					}
				}
				if (key == KeyEvent.VK_SPACE) {
					addKey(' ');
				}
				if (key == KeyEvent.VK_BACK_SPACE) {
					if (state == GameState.Game) {
						if (c > 0) {
							ChatBar[c - 1] = ' ';
							c--;
						}
					} else if (state == GameState.JoinMenu) {
						if (p > 0) {
							ipBar[p - 1] = ' ';
							p--;
						}
					}
					
				}
				Chatbar = new String(ChatBar);
				if (key == KeyEvent.VK_ENTER) {
					if (state == GameState.Game) {
						SendMessage(Chatbar);
					}
					for (int i = 0; i < ChatBar.length; i++) {
						ChatBar[i] = ' ';
					}
					c = 0;
					Chatbar = " ";
				}
			}
		}

		public void addKey(char input) {
			if (state == GameState.Game) {
				if (c < ChatBar.length) {
					ChatBar[c] = input;
					c++;
				}
			} else if (state == GameState.JoinMenu) {
				if (p < ipBar.length) {
					ipBar[p] = input;
					p++;
				}
				IpBar = new String(ipBar);
			}
		}

		public void SendMessage(String message) {
			try {
				out.writeInt(2);
				out.writeUTF(message);
				out.writeChar(color);
				for (int i = ChatBox.length - 1; i > 0; i--) {
					ChatBox[i] = ChatBox[i - 1];
				}
				if (color == 'W') {
					ChatBox[0] = "White: " + message;
				} else {
					ChatBox[0] = "Black: " + message;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void flip() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				PCoords[j][i] = PTCoords[7 - j][7 - i];
			}
		}
		for (int l = 0; l < 8; l++) {
			for (int m = 0; m < 8; m++) {
				PTCoords[m][l] = PCoords[m][l];
			}
		}

	}

	public void drawString(Graphics g, String msg, String fontType, Color color, int font, int x, int y) {

		g.setFont(new Font(fontType, Font.PLAIN, font));

		g.setColor(color);

		g.drawString(msg, x, y);

	}

	public void genMoves(int x, int y, int piece) {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 2; j++) {
				Moves[i][j] = -1;
				Attacks[i][j] = -1;
			}
		}
		int i = 0, a = 0;
		switch (piece) {
		case 11:
			/*
			 * if (inbounds(x, y + 1)) { if (PTCoords[y + 1][x] == 0) {
			 * Moves[i][0] = x; Moves[i][1] = y + 1; i++; if (y == 1 &&
			 * PTCoords[y + 2][x] == 0) { Moves[i][0] = x; Moves[i][1] = y + 2;
			 * i++; } } } if (inbounds(x - 1, y + 1)) { if (PTCoords[y + 1][x -
			 * 1] > 20) { Attacks[a][0] = x - 1; Attacks[a][1] = y + 1; a++; } }
			 * if (inbounds(x + 1, y + 1)) { if (PTCoords[y + 1][x + 1] > 20) {
			 * Attacks[a][0] = x + 1; Attacks[a][1] = y + 1; a++; } } break;
			 */
		case 21:
			if (inbounds(x, y - 1)) {
				if (PTCoords[y - 1][x] == 0) {
					Moves[i][0] = x;
					Moves[i][1] = y - 1;
					i++;
					if (y == 6 && PTCoords[y - 2][x] == 0) {
						Moves[i][0] = x;
						Moves[i][1] = y - 2;
						i++;
					}
				}
			}
			if (inbounds(x - 1, y - 1)) {
				if ((PTCoords[y - 1][x - 1] < 20 && PTCoords[y - 1][x - 1] > 10 && piece == 21)
						|| (PTCoords[y - 1][x - 1] > 20 && piece == 11)) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y - 1;
					a++;
				}
			}
			if (inbounds(x + 1, y - 1)) {
				if ((PTCoords[y - 1][x + 1] < 20 && PTCoords[y - 1][x + 1] > 10 && piece == 21)
						|| (PTCoords[y - 1][x + 1] > 20 && piece == 11)) {
					Attacks[a][0] = x + 1;
					Attacks[a][1] = y - 1;
					a++;
				}
			}
			break;
		case 12:
		case 22:
			for (int j = 1; j < 8; j++) {
				if (inbounds(x - j, y)) {
					if (PTCoords[y][x - j] == 0 && inbounds(x - j, y)) {
						Moves[i][0] = x - j;
						Moves[i][1] = y;
						i++;
					} else {
						if (PTCoords[y][x - j] > 20 && piece == 12) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y;
							a++;
						}
						if (PTCoords[y][x - j] < 20 && PTCoords[y][x - j] > 10 && piece == 22) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x + j, y)) {
					if (PTCoords[y][x + j] == 0 && inbounds(x + j, y)) {
						Moves[i][0] = x + j;
						Moves[i][1] = y;
						i++;
					} else {
						if (PTCoords[y][x + j] > 20 && piece == 12) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y;
							a++;
						}
						if (PTCoords[y][x + j] < 20 && PTCoords[y][x + j] > 10 && piece == 22) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x, y - j)) {
					if (PTCoords[y - j][x] == 0 && inbounds(x, y - j)) {
						Moves[i][0] = x;
						Moves[i][1] = y - j;
						i++;
					} else {
						if (PTCoords[y - j][x] > 20 && piece == 12) {
							Attacks[a][0] = x;
							Attacks[a][1] = y - j;
							a++;
						}
						if (PTCoords[y - j][x] < 20 && PTCoords[y - j][x] > 10 && piece == 22) {
							Attacks[a][0] = x;
							Attacks[a][1] = y - j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x, y + j)) {
					if (PTCoords[y + j][x] == 0 && inbounds(x, y + j)) {
						Moves[i][0] = x;
						Moves[i][1] = y + j;
						i++;
					} else {
						if (PTCoords[y + j][x] > 20 && piece == 12) {
							Attacks[a][0] = x;
							Attacks[a][1] = y + j;
							a++;
						}
						if (PTCoords[y + j][x] < 20 && PTCoords[y + j][x] > 10 && piece == 22) {
							Attacks[a][0] = x;
							Attacks[a][1] = y + j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			break;
		case 13:
		case 23:
			if (inbounds(x - 1, y - 2)) {
				if (PTCoords[y - 2][x - 1] == 0) {
					Moves[i][0] = x - 1;
					Moves[i][1] = y - 2;
					i++;
				}
				if (PTCoords[y - 2][x - 1] > 20 && piece == 13) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y - 2;
					a++;
				}
				if (PTCoords[y - 2][x - 1] < 20 && PTCoords[y - 2][x - 1] > 10 && piece == 23) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y - 2;
					a++;
				}
			}
			if (inbounds(x + 1, y - 2)) {
				if (PTCoords[y - 2][x + 1] == 0) {
					Moves[i][0] = x + 1;
					Moves[i][1] = y - 2;
					i++;
				}
				if (PTCoords[y - 2][x + 1] > 20 && piece == 13) {
					Attacks[a][0] = x + 1;
					Attacks[a][1] = y - 2;
					a++;
				}
				if (PTCoords[y - 2][x + 1] < 20 && PTCoords[y - 2][x + 1] > 10 && piece == 23) {
					Attacks[a][0] = x + 1;
					Attacks[a][1] = y - 2;
					a++;
				}
			}
			if (inbounds(x - 1, y + 2)) {
				if (PTCoords[y + 2][x - 1] == 0) {
					Moves[i][0] = x - 1;
					Moves[i][1] = y + 2;
					i++;
				}
				if (PTCoords[y + 2][x - 1] > 20 && piece == 13) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y + 2;
					a++;
				}
				if (PTCoords[y + 2][x - 1] < 20 && PTCoords[y + 2][x - 1] > 10 && piece == 23) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y + 2;
					a++;
				}
			}
			if (inbounds(x + 1, y + 2)) {
				if (PTCoords[y + 2][x + 1] == 0) {
					Moves[i][0] = x + 1;
					Moves[i][1] = y + 2;
					i++;
				}
				if (PTCoords[y + 2][x + 1] > 20 && piece == 13) {
					Attacks[a][0] = x + 1;
					Attacks[a][1] = y + 2;
					a++;
				}
				if (PTCoords[y + 2][x + 1] < 20 && PTCoords[y + 2][x + 1] > 10 && piece == 23) {
					Attacks[a][0] = x + 1;
					Attacks[a][1] = y + 2;
					a++;
				}
			}
			if (inbounds(x - 2, y - 1)) {
				if (PTCoords[y - 1][x - 2] == 0) {
					Moves[i][0] = x - 2;
					Moves[i][1] = y - 1;
					i++;
				}
				if (PTCoords[y - 1][x - 2] > 20 && piece == 13) {
					Attacks[a][0] = x - 2;
					Attacks[a][1] = y - 1;
					a++;
				}
				if (PTCoords[y - 1][x - 2] < 20 && PTCoords[y - 1][x - 2] > 10 && piece == 23) {
					Attacks[a][0] = x - 2;
					Attacks[a][1] = y - 1;
					a++;
				}
			}
			if (inbounds(x - 2, y + 1)) {
				if (PTCoords[y + 1][x - 2] == 0) {
					Moves[i][0] = x - 2;
					Moves[i][1] = y + 1;
					i++;
				}
				if (PTCoords[y + 1][x - 2] > 20 && piece == 13) {
					Attacks[a][0] = x - 2;
					Attacks[a][1] = y + 1;
					a++;
				}
				if (PTCoords[y + 1][x - 2] < 20 && PTCoords[y + 1][x - 2] > 10 && piece == 23) {
					Attacks[a][0] = x - 2;
					Attacks[a][1] = y + 1;
					a++;
				}
			}
			if (inbounds(x + 2, y - 1)) {
				if (PTCoords[y - 1][x + 2] == 0) {
					Moves[i][0] = x + 2;
					Moves[i][1] = y - 1;
					i++;
				}
				if (PTCoords[y - 1][x + 2] > 20 && piece == 13) {
					Attacks[a][0] = x + 2;
					Attacks[a][1] = y - 1;
					a++;
				}
				if (PTCoords[y - 1][x + 2] < 20 && PTCoords[y - 1][x + 2] > 10 && piece == 23) {
					Attacks[a][0] = x + 2;
					Attacks[a][1] = y - 1;
					a++;
				}
			}
			if (inbounds(x + 2, y + 1)) {
				if (PTCoords[y + 1][x + 2] == 0) {
					Moves[i][0] = x + 2;
					Moves[i][1] = y + 1;
					i++;
				}
				if (PTCoords[y + 1][x + 2] > 20 && piece == 13) {
					Attacks[a][0] = x + 2;
					Attacks[a][1] = y + 1;
					a++;
				}
				if (PTCoords[y + 1][x + 2] < 20 && PTCoords[y + 1][x + 2] > 10 && piece == 23) {
					Attacks[a][0] = x + 2;
					Attacks[a][1] = y + 1;
					a++;
				}
			}
			break;
		case 14:
		case 24:
			for (int j = 1; j < 8; j++) {
				if (inbounds(x - j, y + j)) {
					if (PTCoords[y + j][x - j] == 0) {
						Moves[i][0] = x - j;
						Moves[i][1] = y + j;
						i++;
					} else {
						if (PTCoords[y + j][x - j] > 20 && piece == 14) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y + j;
							a++;
						}
						if (PTCoords[y + j][x - j] < 20 && PTCoords[y + j][x - j] > 10 && piece == 24) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y + j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x + j, y - j)) {
					if (PTCoords[y - j][x + j] == 0) {
						Moves[i][0] = x + j;
						Moves[i][1] = y - j;
						i++;
					} else {
						if (PTCoords[y - j][x + j] > 20 && piece == 14) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y - j;
							a++;
						}
						if (PTCoords[y - j][x + j] < 20 && PTCoords[y - j][x + j] > 10 && piece == 24) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y - j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x - j, y - j)) {
					if (PTCoords[y - j][x - j] == 0) {
						Moves[i][0] = x - j;
						Moves[i][1] = y - j;
						i++;
					} else {
						if (PTCoords[y - j][x - j] > 20 && piece == 14) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y - j;
							a++;
						}
						if (PTCoords[y - j][x - j] < 20 && PTCoords[y - j][x - j] > 10 && piece == 24) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y - j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x + j, y + j)) {
					if (PTCoords[y + j][x + j] == 0) {
						Moves[i][0] = x + j;
						Moves[i][1] = y + j;
						i++;
					} else {
						if (PTCoords[y + j][x + j] > 20 && piece == 14) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y + j;
							a++;
						}
						if (PTCoords[y + j][x + j] < 20 && PTCoords[y + j][x + j] > 10 && piece == 24) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y + j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			break;
		case 15:
		case 25:
			for (int j = 1; j < 8; j++) {
				if (inbounds(x - j, y + j)) {
					if (PTCoords[y + j][x - j] == 0) {
						Moves[i][0] = x - j;
						Moves[i][1] = y + j;
						i++;
					} else {
						if (PTCoords[y + j][x - j] > 20 && piece == 15) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y + j;
							a++;
						}
						if (PTCoords[y + j][x - j] < 20 && PTCoords[y + j][x - j] > 10 && piece == 25) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y + j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x + j, y - j)) {
					if (PTCoords[y - j][x + j] == 0) {
						Moves[i][0] = x + j;
						Moves[i][1] = y - j;
						i++;
					} else {
						if (PTCoords[y - j][x + j] > 20 && piece == 15) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y - j;
							a++;
						}
						if (PTCoords[y - j][x + j] < 20 && PTCoords[y - j][x + j] > 10 && piece == 25) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y - j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x - j, y - j)) {
					if (PTCoords[y - j][x - j] == 0) {
						Moves[i][0] = x - j;
						Moves[i][1] = y - j;
						i++;
					} else {
						if (PTCoords[y - j][x - j] > 20 && piece == 15) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y - j;
							a++;
						}
						if (PTCoords[y - j][x - j] < 20 && PTCoords[y - j][x - j] > 10 && piece == 25) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y - j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x + j, y + j)) {
					if (PTCoords[y + j][x + j] == 0) {
						Moves[i][0] = x + j;
						Moves[i][1] = y + j;
						i++;
					} else {
						if (PTCoords[y + j][x + j] > 20 && piece == 15) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y + j;
							a++;
						}
						if (PTCoords[y + j][x + j] < 20 && PTCoords[y + j][x + j] > 10 && piece == 25) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y + j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x - j, y)) {
					if (PTCoords[y][x - j] == 0 && inbounds(x - j, y)) {
						Moves[i][0] = x - j;
						Moves[i][1] = y;
						i++;
					} else {
						if (PTCoords[y][x - j] > 20 && piece == 15) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y;
							a++;
						}
						if (PTCoords[y][x - j] < 20 && PTCoords[y][x - j] > 10 && piece == 25) {
							Attacks[a][0] = x - j;
							Attacks[a][1] = y;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x + j, y)) {
					if (PTCoords[y][x + j] == 0 && inbounds(x + j, y)) {
						Moves[i][0] = x + j;
						Moves[i][1] = y;
						i++;
					} else {
						if (PTCoords[y][x + j] > 20 && piece == 15) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y;
							a++;
						}
						if (PTCoords[y][x + j] < 20 && PTCoords[y][x + j] > 10 && piece == 25) {
							Attacks[a][0] = x + j;
							Attacks[a][1] = y;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x, y - j)) {
					if (PTCoords[y - j][x] == 0 && inbounds(x, y - j)) {
						Moves[i][0] = x;
						Moves[i][1] = y - j;
						i++;
					} else {
						if (PTCoords[y - j][x] > 20 && piece == 15) {
							Attacks[a][0] = x;
							Attacks[a][1] = y - j;
							a++;
						}
						if (PTCoords[y - j][x] < 20 && PTCoords[y - j][x] > 10 && piece == 25) {
							Attacks[a][0] = x;
							Attacks[a][1] = y - j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			for (int j = 1; j < 8; j++) {
				if (inbounds(x, y + j)) {
					if (PTCoords[y + j][x] == 0 && inbounds(x, y + j)) {
						Moves[i][0] = x;
						Moves[i][1] = y + j;
						i++;
					} else {
						if (PTCoords[y + j][x] > 20 && piece == 15) {
							Attacks[a][0] = x;
							Attacks[a][1] = y + j;
							a++;
						}
						if (PTCoords[y + j][x] < 20 && PTCoords[y + j][x] > 10 && piece == 25) {
							Attacks[a][0] = x;
							Attacks[a][1] = y + j;
							a++;
						}
						break;
					}
				} else {
					break;
				}
			}
			break;
		case 16:
		case 26:
			
			if (CanCastle == true && Turn == piece) {
				if ((PTCoords[7][0] == 12 || PTCoords[7][0] == 22) && PTCoords[7][1] == 0 && PTCoords [7][2] == 0) {
					CastleLeft = true; 
				}
				if (PTCoords[7][7] == 12 || PTCoords[7][7] == 22) {
					CastleRight = true;
				}
			}

			if (inbounds(x + 1, y + 1)) {
				if (PTCoords[y + 1][x + 1] == 0) {
					Moves[i][0] = x + 1;
					Moves[i][1] = y + 1;
					i++;
				}
				if (PTCoords[y + 1][x + 1] > 20 && piece == 16) {
					Attacks[a][0] = x + 1;
					Attacks[a][1] = y + 1;
					a++;
				}
				if (PTCoords[y + 1][x + 1] < 20 && PTCoords[y + 1][x + 1] > 10 && piece == 26) {
					Attacks[a][0] = x + 1;
					Attacks[a][1] = y + 1;
					a++;
				}
			}
			if (inbounds(x - 1, y + 1)) {
				if (PTCoords[y + 1][x - 1] == 0) {
					Moves[i][0] = x - 1;
					Moves[i][1] = y + 1;
					i++;
				}
				if (PTCoords[y + 1][x - 1] > 20 && piece == 16) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y + 1;
					a++;
				}
				if (PTCoords[y + 1][x - 1] < 20 && PTCoords[y + 1][x - 1] > 10 && piece == 26) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y + 1;
					a++;
				}
			}
			if (inbounds(x + 1, y - 1)) {
				if (PTCoords[y - 1][x + 1] == 0) {
					Moves[i][0] = x + 1;
					Moves[i][1] = y - 1;
					i++;
				}
				if (PTCoords[y - 1][x + 1] > 20 && piece == 16) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y - 1;
					a++;
				}
				if (PTCoords[y - 1][x + 1] < 20 && PTCoords[y - 1][x + 1] > 10 && piece == 26) {
					Attacks[a][0] = x + 1;
					Attacks[a][1] = y - 1;
					a++;
				}
			}
			if (inbounds(x - 1, y - 1)) {
				if (PTCoords[y - 1][x - 1] == 0) {
					Moves[i][0] = x - 1;
					Moves[i][1] = y - 1;
					i++;
				}
				if (PTCoords[y - 1][x - 1] > 20 && piece == 16) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y - 1;
					a++;
				}
				if (PTCoords[y - 1][x - 1] < 20 && PTCoords[y - 1][x - 1] > 10 && piece == 26) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y - 1;
					a++;
				}
			}
			if (inbounds(x + 1, y)) {
				if (PTCoords[y][x + 1] == 0) {
					Moves[i][0] = x + 1;
					Moves[i][1] = y;
					i++;
				}
				if (PTCoords[y][x + 1] > 20 && piece == 16) {
					Attacks[a][0] = x + 1;
					Attacks[a][1] = y;
					a++;
				}
				if (PTCoords[y][x + 1] < 20 && PTCoords[y][x + 1] > 10 && piece == 26) {
					Attacks[a][0] = x + 1;
					Attacks[a][1] = y;
					a++;
				}
			}
			if (inbounds(x - 1, y)) {
				if (PTCoords[y][x - 1] == 0) {
					Moves[i][0] = x - 1;
					Moves[i][1] = y;
					i++;
				}
				if (PTCoords[y][x - 1] > 20 && piece == 16) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y;
					a++;
				}
				if (PTCoords[y][x - 1] < 20 && PTCoords[y][x - 1] > 10 && piece == 26) {
					Attacks[a][0] = x - 1;
					Attacks[a][1] = y;
					a++;
				}
			}
			if (inbounds(x, y - 1)) {
				if (PTCoords[y - 1][x] == 0) {
					Moves[i][0] = x;
					Moves[i][1] = y - 1;
					i++;
				}
				if (PTCoords[y - 1][x] > 20 && piece == 16) {
					Attacks[a][0] = x;
					Attacks[a][1] = y - 1;
					a++;
				}
				if (PTCoords[y - 1][x] < 20 && PTCoords[y - 1][x] > 10 && piece == 26) {
					Attacks[a][0] = x;
					Attacks[a][1] = y - 1;
					a++;
				}
			}
			if (inbounds(x, y + 1)) {
				if (PTCoords[y + 1][x] == 0) {
					Moves[i][0] = x;
					Moves[i][1] = y + 1;
					i++;
				}
				if (PTCoords[y + 1][x] > 20 && piece == 16) {
					Attacks[a][0] = x;
					Attacks[a][1] = y + 1;
					a++;
				}
				if (PTCoords[y + 1][x] < 20 && PTCoords[y + 1][x] > 10 && piece == 26) {
					Attacks[a][0] = x;
					Attacks[a][1] = y + 1;
					a++;
				}
			}
			break;
		}
	}

	public int Checkcheck(char turn) {
		int check = -1;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				genMoves(i, j, PTCoords[j][i]);
				for (int k = 0; k < 30; k++) {
					if (Attacks[k][1] != -1 || Attacks[k][0] != -1) {
						if ((turn == 'W' && PTCoords[Attacks[k][1]][Attacks[k][0]] == 26)
								|| (turn == 'B' && PTCoords[Attacks[k][1]][Attacks[k][0]] == 16)) {
							check = 1;

						}
						if ((turn == 'W' && PTCoords[Attacks[k][1]][Attacks[k][0]] == 16)
								|| (turn == 'B' && PTCoords[Attacks[k][1]][Attacks[k][0]] == 26)) {
							check = 0;
							break;

						}
					}
				}
				for (int l = 0; l < 30; l++) {
					for (int h = 0; h < 2; h++) {
						Moves[l][h] = -1;
						Attacks[l][h] = -1;
					}
				}
			}
		}
		if (check == -1)
			check = 2;
		return check;
	}

	public boolean CheckmateCheck(char turn) {
		boolean checkmate = true;

		for (int l = 0; l < 8; l++) {
			for (int m = 0; m < 8; m++) {
				PTCoords[m][l] = PCoords[m][l];
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (((turn == 'W' && PTCoords[j][i] > 20) || (turn == 'B' && PTCoords[j][i] < 20))
						&& PTCoords[j][i] != 0) {
					for (int k = 0; k < 30; k++) {
						genMoves(i, j, PTCoords[j][i]);
						if (Moves[k][0] != -1 && Moves[k][1] != -1) {
							PTCoords[Moves[k][1]][Moves[k][0]] = PTCoords[j][i];
							PTCoords[j][i] = 0;
							if (Checkcheck(turn) != 1) {
								checkmate = false;
							}
							for (int l = 0; l < 8; l++) {
								for (int m = 0; m < 8; m++) {
									PTCoords[m][l] = PCoords[m][l];
								}
							}
						}
					}
					for (int a = 0; a < 30; a++) {
						genMoves(i, j, PTCoords[j][i]);
						if (Attacks[a][0] != -1 && Attacks[a][1] != -1) {
							PTCoords[Attacks[a][1]][Attacks[a][0]] = PTCoords[j][i];
							PTCoords[j][i] = 0;
							if (Checkcheck(turn) != 1) {
								checkmate = false;
							}
							for (int l = 0; l < 8; l++) {
								for (int m = 0; m < 8; m++) {
									PTCoords[m][l] = PCoords[m][l];
								}
							}
						}
					}
					for (int l = 0; l < 30; l++) {
						for (int m = 0; m < 2; m++) {
							Moves[l][m] = -1;
							Attacks[l][m] = -1;
						}
					}
				}
			}
		}
		for (int l = 0; l < 8; l++) {
			for (int m = 0; m < 8; m++) {
				PTCoords[m][l] = PCoords[m][l];
			}
		}
		for (int l = 0; l < 30; l++) {
			for (int m = 0; m < 2; m++) {
				Moves[l][m] = -1;
				Attacks[l][m] = -1;
			}
		}
		return checkmate;
	}

	public boolean inbounds(int x, int y) {
		boolean i = false;
		if (x < 8 && x >= 0 && y < 8 && y >= 0)
			i = true;
		return i;
	}

	private class MAdapter implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			double x = e.getX();
			double y = e.getY();

			switch (state) {

			case MainMenu:
				if (button(775, 100, 415, 130, (int) x, (int) y)) {
					state = GameState.PlayMenu;
				}
				if (button(770, 290, 420, 80, (int) x, (int) y)) {
					state = GameState.Instructions;
				}
				break;

			case PlayMenu:
				if (button(775, 100, 415, 130, (int) x, (int) y)) {
					state = GameState.Game;
					try {
						Host();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (button(770, 250, 415, 125, (int) x, (int) y)) {
					state = GameState.JoinMenu;
				}
				if (button(770, 400, 415, 70, (int) x, (int) y)) {
					state = GameState.MainMenu;
				}
				break;

			case JoinMenu:
				if (button(770, 290, 420, 70, (int) x, (int) y)) {
					state = GameState.Game;
					try {
						String IpBar = new String(ipBar);
						Join(IpBar);
						state = GameState.Game;
						for (int i = 0; i < ChatBar.length; i++) {
							ipBar[i] = ' ';
						}
						p = 0;
						IpBar = " ";
					} catch (Exception e1) {
						errorMessage = true;
					}
				}
				if (button(770, 400, 415, 70, (int) x, (int) y)) {
					state = GameState.PlayMenu;
				}
				break;

			case Instructions:
				break;

			case Game:
				int fromX = 0;
				int fromY = 0;
				int toX = 0;
				int toY = 0;
				int ifCheck = 0;
				int takenPiece = 0;

				if ((x > 310 && x < 822) && (y > 60 && y < 588) && (color == Turn)) {

					Sx = (int) ((x - 311) / 64);
					Sy = (int) ((y - 60) / 66);

					//Scans to check if you have selected a valid move
					if ((Sx <= 7 && Sx >= 0) && (Sy <= 7 && Sy >= 0)) {
						boolean cont = true;
						for (int i = 0; i < 30; i++) {
							if (Moves[i][0] == Sx && Moves[i][1] == Sy) {
								PTCoords[Sy][Sx] = PTCoords[Selecty][Selectx];
								PTCoords[Selecty][Selectx] = 0;
								fromX = Selectx;
								fromY = Selecty;
								toX = Sx;
								toY = Sy;
								takenPiece = 0;
								cont = false;
								selection = false;
								break;
							} else if (Attacks[i][0] == Sx && Attacks[i][1] == Sy) {
								takenPiece = PTCoords[Attacks[i][1]][Attacks[i][0]];
								PTCoords[Sy][Sx] = PTCoords[Selecty][Selectx];
								PTCoords[Selecty][Selectx] = 0;
								fromX = Selectx;
								fromY = Selecty;
								toX = Sx;
								toY = Sy;
								cont = false;
								selection = false;
								break;
							}

						//Generates Moves if piece is selected
						}
						if (cont) {
							if ((PTCoords[Sy][Sx] != 00) && ((PTCoords[Sy][Sx] > 20 && Turn == 'B')
									|| (PTCoords[Sy][Sx] > 10 && PTCoords[Sy][Sx] < 20 && Turn == 'W'))) {
								try {
									genMoves(Sx, Sy, PTCoords[Sy][Sx]);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								selection = true;
								Selectx = Sx;
								Selecty = Sy;
							}
						} else {		//Checks for check and checkmate
							int n = Checkcheck(Turn);
							System.out.println(n);
							if (n == 2) {		//No check or checkmate
								for (int i = 0; i < 8; i++) {
									for (int j = 0; j < 8; j++) {
										PCoords[j][i] = PTCoords[j][i];
									}
								}
								if (Turn == 'W') {
									Turn = 'B';
								} else {
									Turn = 'W';
								}
								Send(fromX, fromY, toX, toY, ifCheck, takenPiece);
								for (int k = 0; k < 30; k++) {
									for (int j = 0; j < 2; j++) {
										Moves[k][j] = -1;
										Attacks[k][j] = -1;
									}
								}
							}
							if (n == 1) {		//Check is detected, Checkmate checked within if statement
								for (int i = 0; i < 8; i++) {
									for (int j = 0; j < 8; j++) {
										PCoords[j][i] = PTCoords[j][i];
									}
								}
								if (CheckmateCheck(Turn)) {
									ifCheck = 2;
									checkmate = 100;
									Turn = 'L';
								} else {
									if (Turn == 'W') {
										Turn = 'B';
									} else {
										Turn = 'W';
									}
									ifCheck = 100;
									Send(fromX, fromY, toX, toY, ifCheck, takenPiece);
									check = 100;
									for (int k = 0; k < 30; k++) {
										for (int j = 0; j < 2; j++) {
											Moves[k][j] = -1;
											Attacks[k][j] = -1;
										}
									}
								}
							}
							if (n == 0) {			//move jeopardizes piece's own king, move is cancelled
								for (int i = 0; i < 8; i++) {
									for (int j = 0; j < 8; j++) {
										PTCoords[j][i] = PCoords[j][i];
									}
								}
							}
						}
					}
				}
				break;
			}

		}

		private boolean button(int buttonx, int buttony, int buttonWidth, int buttonHeight, int x, int y) {
			boolean ispressed = false;
			if (x > buttonx && x < buttonx + buttonWidth && y > buttony && y < buttony + buttonHeight) {
				ispressed = true;
			}
			return ispressed;

		}

	}

	public void Send(int InitialPositionX, int InitialPositionY, int FinalPositionX, int FinalPositionY, int check,
			int takenPiece) {

		try {
			out.writeInt(1);
			out.writeChar(Turn);
			out.writeInt(InitialPositionX);
			out.writeInt(InitialPositionY);
			out.writeInt(FinalPositionX);
			out.writeInt(FinalPositionY);
			out.writeInt(check);
			out.writeInt(takenPiece);
			System.out.println(takenPiece);

			if (((takenPiece > 20) && (color == 'W')) || ((takenPiece < 20) && (color == 'B'))) {
				for (int i = 0; i < Fbox.length; i++) {
					if (Fbox[i] == 0) {
						Fbox[i] = takenPiece;
						break;
					}
				}
			} else {
				for (int i = 0; i < Ebox.length; i++) {
					if (Ebox[i] == 0) {
						Ebox[i] = takenPiece;
						break;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/*
 * try { System.out.println("Sending to server..."); out.writeInt(playerid);
 * System.out.println("Still sending to server..."); out.writeObject(PCoords);
 * System.out.println("Sent to server"); } catch (IOException e1) { // TODO
 * Auto-generated catch block e1.printStackTrace(); }
 */

class Input implements Runnable {

	DataInputStream in;
	Board board;
	Graphics g;

	int fromx;
	int fromy;
	int tox;
	int toy;
	int check;
	int takenPiece;
	char turn = 'W';

	public Input(DataInputStream in, Board board) {
		this.in = in;
		this.board = board;
	}

	@Override
	public void run() {
		while (true) {
			try {
				int InputType = in.readInt();
				if (InputType == 1) {
					System.out.println("doing");
					turn = in.readChar();
					fromx = in.readInt();
					fromy = in.readInt();
					tox = in.readInt();
					toy = in.readInt();
					check = in.readInt();
					takenPiece = in.readInt();
					System.out.println("recieved");
					board.Turn = turn;
					board.PCoords[7 - toy][7 - tox] = board.PCoords[7 - fromy][7 - fromx];
					board.PCoords[7 - fromy][7 - fromx] = 0;
					board.PTCoords[7 - toy][7 - tox] = board.PCoords[7 - toy][7 - tox];
					board.PTCoords[7 - fromy][7 - fromx] = board.PCoords[7 - fromy][7 - fromx];
					board.check = check;

					System.out.println(takenPiece);

					if (((takenPiece > 20) && (board.color == 'W')) || ((takenPiece < 20) && (board.color == 'B'))) {
						for (int i = 0; i < board.Fbox.length; i++) {
							if (board.Fbox[i] == 0) {
								board.Fbox[i] = takenPiece;
								break;
							}
						}
					} else {
						for (int i = 0; i < board.Ebox.length; i++) {
							if (board.Ebox[i] == 0) {
								board.Ebox[i] = takenPiece;
								break;
							}
						}
					}
				} else if (InputType == 2) {
					String message = in.readUTF();
					char who = in.readChar();
					for (int i = board.ChatBox.length - 1; i > 0; i--) {
						board.ChatBox[i] = board.ChatBox[i - 1];
					}
					if (who == 'W') {
						board.ChatBox[0] = "White: " + message;
					} else {
						board.ChatBox[0] = "Black: " + message;
					}
				}
				System.out.println("did");
			} catch (IOException e) {
				System.out.println("Other player has disconnected");
				e.printStackTrace();
				board.Reset();
				break;
			}
		}
	}

}
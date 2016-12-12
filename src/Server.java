import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server {

	static ServerSocket serverSocket;
	static Socket socket;
	static ObjectOutputStream out;
	static Users[] user = new Users[2];
	static ObjectInputStream in;

	public static void main(String[] args) throws Exception {
		Say("Starting Server...");
		serverSocket = new ServerSocket(7777);
		Say("Server Started...");
		while (true) {
			socket = serverSocket.accept();
			for (int i = 0; i < 2; i++) {
				if (user[i] == null) {
					System.out.println("Connection from:" + socket.getInetAddress());
					out = new ObjectOutputStream(socket.getOutputStream());
					char c = 'W';
					if (i == 0) {
						c = 'B';
					}
					System.out.println("tried");
					out.writeObject(c);
					System.out.println("Did");
					in = new ObjectInputStream(socket.getInputStream());
					user[i] = new Users(out, in, user, i, c);
					Thread thread = new Thread(user[i]);
					thread.start();
					System.out.println(user);
					break;
				}
			}
		}
	}

	public static void Say(String s) {
		System.out.println(s);
	}

}

class Users implements Runnable {

	ObjectOutputStream out;
	ObjectInputStream in;
	Users[] user = new Users[10];
	String name;
	int command;
	int playerid;
	char piece;
	
	char turn = 'W';
	
	int[][] PCoords = {{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
	},PTCoords = {{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
			{00, 00, 00, 00, 00, 00, 00, 00},
	};

	public Users(ObjectOutputStream out2, ObjectInputStream in2, Users[] user, int pid, char piece) {
		this.out = out2;
		this.in = in2;
		this.user = user;
		this.playerid = pid;
		this.piece = piece;
	}

	public void run() {
		try {
			out.writeChar(piece);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			try {
				PCoords = (int[][])in.readObject();
					flip();
					System.out.println("sending to black...");
					user[0].out.writeObject(PCoords);	
					System.out.println("sent to black");
			} catch (IOException e) {
				// TODO Auto-generated catch block
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void flip() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				PTCoords[j][i] = PCoords[7 - j][7 - i];
			}
		}
	}
}

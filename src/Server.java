import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server {

	static ServerSocket serverSocket;
	static Socket socket;
	static DataOutputStream out;
	static Users[] user = new Users[2];
	static DataInputStream in;

	public static void main(String[] args) throws Exception {
		Say("Starting Server...");
		serverSocket = new ServerSocket(7777);
		Say("Server Started...");
		while (true) {
			socket = serverSocket.accept();
			for (int i = 0; i < 2; i++) {
				if (user[i] == null) {
					System.out.println("Connection from:" + socket.getInetAddress());
					out = new DataOutputStream(socket.getOutputStream());
					char c = 'W';
					if (i == 0) {
						c = 'B';
					}
					System.out.println("tried");
					out.writeChar(c);
					System.out.println("Did");
					in = new DataInputStream(socket.getInputStream());
					user[i] = new Users(out, in, user);
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

	DataOutputStream out;
	DataInputStream in;
	Users[] user = new Users[10];
	String name;
	int fromX = 0;
	int fromY = 0;
	int toX = 0;
	int toY = 0;


	static char turn = 'W';

	int[][] PCoords = { { 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
			{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
			{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
			{ 00, 00, 00, 00, 00, 00, 00, 00 }, },
			PTCoords = { { 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, 00, 00 }, { 00, 00, 00, 00, 00, 00, 00, 00 }, };

	public Users(DataOutputStream out2, DataInputStream in2, Users[] user) {
		this.out = out2;
		this.in = in2;
		this.user = user;
	}

	public void run() {
		while (true) {
			try {
				fromX = in.readInt();
				fromY = in.readInt();
				toX = in.readInt();
				toY = in.readInt();
				
				changeTurn();
				if(turn == 'B'){
				user[0].out.writeChar(turn);
				user[0].out.writeInt(fromX);
				user[0].out.writeInt(fromY);
				user[0].out.writeInt(toX);
				user[0].out.writeInt(toY);
				} else {
				flip();
				user[1].out.writeChar(turn);
				user[1].out.writeInt(fromX);
				user[1].out.writeInt(fromY);
				user[1].out.writeInt(toX);
				user[1].out.writeInt(toY);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				break;
			}
		}
	}

	public void changeTurn() {
		if (turn == 'W') {
			turn = 'B';
			} else {
			turn = 'W';
		}
	}

	public void flip() {
		fromX = 7 - fromX;
		fromY = 7 - fromY;
		toX = 7 - toX;
		toY = 7 - toY;
	}
}

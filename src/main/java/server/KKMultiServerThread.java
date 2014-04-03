package server;

import java.net.*;
import java.util.HashSet;
import java.util.Set;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;

import user.User;

public class KKMultiServerThread extends Thread {
	private Socket socket = null;
	// private User[] users;
	private Set<User> users = new HashSet<User>();

	public KKMultiServerThread(Set<User> users, Socket socket) {

		super("KKMultiServerThread");
		this.socket = socket;
		this.users = users;
	}

	public void run() {

		try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));) {
			String inputLine, outputLine;
			// KnockKnockProtocol kkp = new KnockKnockProtocol();
			// outputLine = kkp.processInput(null);
			// out.println(outputLine);

			InputParser inputParser = new InputParser();

			System.out.println("Connected from " + socket.getInetAddress()
					+ " on port " + socket.getPort() + "\nLocal address: "
					+ socket.getLocalAddress() + " on port "
					+ socket.getLocalPort());
			String port = "" + socket.getPort();
			String ip = "" + socket.getInetAddress();

			// inputParser.PrintMenu(out);
			// out.println("Add yourself to be tracked: 'add:ausername'\n");
			// out.print("See all users in dir: 'seeusers:all'\n");
			// out.print("Remove yourself: 'remove:ausername'");

			out.println("'add:ausername', 'seeusers:all', 'remove:ausername'");
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Received from client: '" + inputLine + "'");
				// String tmp1 = inputLine;

				// String orders = inputParser.ProcessInput(inputLine);
				if (inputLine.length() > 0 && inputLine.contains(":")) {
					String[] tmp = inputLine.split(":");
					if (tmp[0].equals("add")) {
						this.users.add(new User(tmp[1], ip, port));
						System.out.println("Added user: "
								+ (new User(tmp[1], ip, port).getEverything()));
						out.println("You are now being tracked");

					} else if (tmp[0].equals("remove")) {

						for (User u : users) {
							if (u.equals(new User(tmp[1], ip, port))) {
								System.out.println("Removing: " + u.getName());
								users.remove(u);
								out.println("You have been removed");
							}
						}

					} else if (tmp[0].equals("seeusers")) {
						System.out.println("Users in directory: "
								+ users.size());
						out.println("Users in directory: " + users.size());
						for (User u : users) {
							System.out.println(u.getEverything());
						}

					} else if (tmp[0].equals("quit")) {

						for (User u : users) {
							try {
//								Socket sock = new Socket();
//								SocketAddress endpoint;
//								PrintWriter out2;
//								
//								System.out.println("Sending message to: "
//										+ u.getName());
//
//								String ipuser = "127.0.0.1";//u.getIp();
//								System.out.println("IP: "+ipuser);
//								endpoint = new InetSocketAddress(ipuser
//										, Integer.parseInt(u
//										.getPort()));
//								System.out.println(endpoint.toString())	;
//								sock.connect(endpoint);
//								out2 = new PrintWriter(sock.getOutputStream());
//								out2.println("HEY MAN GUY");
//								sock.close();
//								out2.close();
//								
								Socket client = new Socket("127.0.0.1", Integer.parseInt(u.getPort()));
					            PrintWriter out2 = new PrintWriter(client.getOutputStream(), true);
					            out2.println("HEY PENI");
					            out2.close();
					            client.close();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							}

						}
					}
				} else {
					System.out.println("Received garbage: '" + inputLine + "'");
				}
				// if (tmp.length == 2) {
				// if (tmp[0].contains("username")) {
				// this.users.add(new User(tmp[1], ip, port));
				// System.out.println("Added user: "
				// + (new User(tmp[1], ip, port).getEverything()));
				// }
				// }
				// if (inputLine.contains("users")) {
				// System.out.println("Users in directory: " + users.size());
				// for (User u : users) {
				// System.out.println(u.getEverything());
				// }
				// }
				//
				// outputLine = inputParser.ProcessInput(inputLine);
				// out.println(outputLine);
				// if (outputLine.equals("quit"))
				// break;
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
package clientserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashSet;
import java.util.Vector;

public class Client extends Thread {

	private InetAddress GROUP;
	private int PORT;
	private String username;
	private String to;
	private MessageVerifier verifyMessage;
	private HashSet<String> usersInNetwork = new HashSet<String>();

	public Client(InetAddress group, int port, String name, String to) {
		this.GROUP = group;
		this.PORT = port;
		this.username = name;
		this.to = to;
		verifyMessage = new MessageVerifier(this.username);

	}

	public void run() {
		MulticastSocket multicastSocket = null;
		try {
			multicastSocket = new MulticastSocket(PORT);
			multicastSocket.joinGroup(GROUP);
			String line = String.format("Listening for messages on: %s:%s \n", this.GROUP.toString(), this.PORT);
            System.out.println(line);
			int users = 0;
			while (true) {
				try {
					byte[] receiveData = new byte[256];
					DatagramPacket receivePacket = new DatagramPacket(
							receiveData, receiveData.length);
					multicastSocket.receive(receivePacket);
					String message = new String(receivePacket.getData());
					String from = message.split(":")[0];

					
					//Add the new user o the network of users
					verifyMessage.addUsersInNetwork(from);
					
					if (users != verifyMessage.size()) {
						users++;
						System.out.println("New user found in network: " + from);
						System.out.println("Network: " +verifyMessage.getUsersInNetwork());
						System.out.println();
					}

					if (verifyMessage.checkRecipient(message)) {
						System.out.println("Decrypting message payload...");
						System.out.println("Sender: " + message.split(":")[0]
								+ "@" + receivePacket.getAddress() + ":"
								+ receivePacket.getPort());
						System.out.println("\tCoordinates: "
								+ message.split(":")[2]);
						System.out.println();
					}
					Thread.sleep(3000);
				} catch (Exception e) {
					System.out.println("Exc0: " + e);
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println("Exc: " + e);
		} finally {
			try {
				multicastSocket.leaveGroup(GROUP);
			} catch (IOException e) {
				e.printStackTrace();
			}
			multicastSocket.close();
		}

	}

}

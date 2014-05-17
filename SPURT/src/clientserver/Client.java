package clientserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.sound.midi.Receiver;


public class Client extends Thread {

	private InetAddress GROUP;
	private int PORT;
	private String username;
	private MessageHandler verifyMessage;
	private int broadcastInterval;

	public Client(InetAddress group, int port, String name, String to, int broadcastInterval) {
		this.GROUP = group;
		this.PORT = port;
		this.username = name;
		this.broadcastInterval = broadcastInterval;
		verifyMessage = new MessageHandler(this.username);

	}

	public void run() {
		MulticastSocket multicastSocket = null;
		String MCAST_ADDR = "230.0.0.2";
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
					String senderIPPort = receivePacket.getAddress() + ":" + receivePacket.getPort();
					
					//Add the new user o the network of users					verifyMessage.addUsersInNetwork(from);
					
					if (users != verifyMessage.size()) {
						users++;
						System.out.println("New user found in network: " + from);
						System.out.println("Network: " +verifyMessage.getUsersInNetwork());
						System.out.println();
					}

					if (verifyMessage.checkRecipient(message)) {
						decryptMessage("", message, senderIPPort);
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
	

	public void decryptMessage(String key,  String message, String sender){
		System.out.println("Decrypting message payload...");
		System.out.println("Sender: " + message.split(":")[0] + "@" + sender);
		System.out.println("\tCoordinates: "+ message.split(":")[2]);
		System.out.println();
	}
}

package clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


/*
 * TODO Find new friends			√
 * TODO Notify user of new friends	√
 * TODO Action new found friend		√
 * TODO ask friend to follow them	√
 * TODO friend asks you to allow them to follow you
 * TODO remove friend from following/followed list
 * 
 **/


public class ClientThread extends Client{

	private InetAddress GROUP;
	private int PORT;
	private String username;
	private TrackerManager verifyMessage;
	private int broadcastInterval;
	private Messenger messenger;
	
	public ClientThread(InetAddress group, int port, String name, String to,
			int broadcastInterval, Messenger messenger) {
		super(group, port, name, to, broadcastInterval);
		this.GROUP = group;
		this.PORT = port;
		this.username = name;
		this.broadcastInterval = broadcastInterval;
		verifyMessage = new TrackerManager(this.username);
		this.messenger = messenger;
	}
	
	public void run(){
		MulticastSocket multicastSocket = null;
		String MCAST_ADDR = "230.0.0.2";
		try {
			multicastSocket = new MulticastSocket(PORT);
			multicastSocket.joinGroup(GROUP);

			String line = String.format("Listening for messages on: %s:%s \n", this.GROUP.toString(), this.PORT);
            System.out.println(line);
			int users = 0;
			
			BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
			String input= "";
			
			while (true) {
				try {
					byte[] receiveData = new byte[256];
					DatagramPacket receivePacket = new DatagramPacket(
							receiveData, receiveData.length);
					multicastSocket.receive(receivePacket);
					
					String message = new String(receivePacket.getData());
					
					String from = message.split(":")[0];
					String senderIPPort = receivePacket.getAddress() + ":" + receivePacket.getPort();
					
					//Add the new user o the network of users
					verifyMessage.addUsersInNetwork(from);
					
					if (users != verifyMessage.size()) {
						users++;
						System.out.println("New user found in network: " + from);
						actionNewFoundFriend(from);
						System.out.println("Network: " +verifyMessage.getUsersInNetwork());
//						verifyMessage.addNewFollower("", 10);
						messenger.put("; "+username+" is following you");
					}

					if (verifyMessage.checkRecipient(message)) {
						decryptMessage("", message, senderIPPort);
					}
					
					
					Thread.sleep(broadcastInterval);
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
	

	private void actionNewFoundFriend(String from){

		System.out.print("Add the user to list of following? Y/N: ");
		
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		String input= "";
		try {
			input = br.readLine();
			if (input.toLowerCase().equals("y")){
				verifyMessage.addUsersInNetwork(from);
			} else {
				verifyMessage.removeUserInNetwork(from);
			}
		
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
	}	
}

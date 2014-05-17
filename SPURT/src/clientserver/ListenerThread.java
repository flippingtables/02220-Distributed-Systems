package clientserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.sound.midi.Receiver;


public class ListenerThread extends Thread {

	private InetAddress currentLMGAddr;
	private int currentLMGAddrPort;
	private String senderID;
	private MessageHandler messageHandler;
	private int frequency;
	private String gKey;
	private ArrayList<CommunicationRow> users; 
	private int timeoutCounter;
	
	
	public ListenerThread(String senderID, String currentLMGAddr, int currentLMGAddrPort, String gKey,
			int frequency, ArrayList<CommunicationRow> users){
		try {
			this.currentLMGAddr = InetAddress.getByName(currentLMGAddr);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("ListenerThread constructor failed");
		}
		
		this.currentLMGAddrPort = currentLMGAddrPort;
		this.senderID = senderID;
		this.frequency = frequency * 1000;
		this.gKey = gKey;
		this.users = users;
		this.timeoutCounter= 0;
		messageHandler = new MessageHandler(this.senderID);
	}

	public void run() {
		MulticastSocket multicastSocket = null;
		try {
			multicastSocket = new MulticastSocket(currentLMGAddrPort);
			multicastSocket.joinGroup(currentLMGAddr);

			String line = String.format("Listening for messages by %s on: %s:%s \n", this.senderID, this.currentLMGAddr.toString(), this.currentLMGAddrPort);
            System.out.println(line);
			int index = findUser(senderID);
			
			users.get(index).setFreshnessCounter(0); //Initialize the freshness data
			while (true) {
				try {
					

					//
					if (!users.get(index).currentLMGAddrEqualsLastLMGAddr()){
						multicastSocket.leaveGroup(currentLMGAddr);
						users.get(index).setLastKnownLMGAddr(users.get(index).getCurrentLMGAddr());
						users.get(index).setLastKnownLMGAddrPort(users.get(index).getCurrentLMGAddrPort());
						multicastSocket = new MulticastSocket(currentLMGAddrPort); 
						multicastSocket.joinGroup(currentLMGAddr);
					}

					

					byte[] receiveData = new byte[256];
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					multicastSocket.receive(receivePacket);
					
					
					String message = new String(receivePacket.getData());
					String messageSender = message.split(":")[0];
					
					if (messageSender != senderID){
							
						String senderIPPort = receivePacket.getAddress() + ":" + receivePacket.getPort();
						
//						if (messageHandler.checkRecipient(message)) {
							System.out.println("Received message: "+ message + " from message "+ messageSender);
							String decryptedMessage = messageHandler.decryptMessage(gKey, message, senderIPPort);
							updateTables(index, decryptedMessage);
							users.get(index).setFreshnessCounter(0); //FRESH DATA
//						} else {
//							continue;
//						}
					} else {
						timeoutCounter = users.get(index).getFreshnessCounter(); 
						users.get(index).setFreshnessCounter(users.get(index).getFreshnessCounter() + 1); //DATA is getting older
					}
					
					if (users.get(index).getFreshnessCounter() % 5 == 4){
						users.get(index).setSendGKey(true);
					}
					
					
					Thread.sleep(frequency);
				} catch (Exception e) {
					System.out.println("Exc0: " + e);
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println("Exc2: " + e);
			e.printStackTrace();
		} finally {
			try {
				multicastSocket.leaveGroup(currentLMGAddr);
			} catch (IOException e) {
				System.out.println("LT");
				e.printStackTrace();
			}
			multicastSocket.close();
		}
	}
	
	private int findUser(String senderID) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getReceiverID().equals(senderID)) {
				return i;
			}
		}
		return -1;
	}
	
	private void updateTables(int userIndex, String decryptedMessage){
		String coordinates = decryptedMessage;
		users.get(userIndex).setLocation(coordinates);
	}
	
}

package clientserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.sound.midi.Receiver;

public class CommunicationListenerThread extends Thread {

	private InetAddress communicationBroadCastAddr;
	private int communicationBroadCastAddrPort;
	private String myID;
	private MessageHandler messageHandler;
	private int broadcastInterval = 5000;
	private static volatile ArrayList<CommunicationRow> users;

	public CommunicationListenerThread(String myID, String communicationBroadCastAddrHost, int communicationBroadCastAddrPort, ArrayList<CommunicationRow> users) {
		// 
		this.myID = myID;
		this.communicationBroadCastAddrPort = communicationBroadCastAddrPort;
		this.users = users;
		try {
			this.communicationBroadCastAddr = InetAddress.getByName(communicationBroadCastAddrHost);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("CommunicationListenerThread constructor failed");
		}
	}

	public void run() {
		MulticastSocket multicastSocket = null;
		try {
			multicastSocket = new MulticastSocket(communicationBroadCastAddrPort);
//			multicastSocket.setSoTimeout(5000);
			multicastSocket.joinGroup(communicationBroadCastAddr);

			String line = String.format("Listening for messages by CMG @%s:%s \n", this.communicationBroadCastAddr.toString(), this.communicationBroadCastAddrPort);
            System.out.println(line);
//			int index = findUser(senderID);
//			users.get(index).setFreshnessCounter(0); //Initialize the freshness data
			
			
			while (true) {
				try {
					byte[] receiveData = new byte[256];
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					multicastSocket.receive(receivePacket);
					String message = new String(receivePacket.getData());
					String messageSender = message.split(":")[0];
					String messageContent  =message.split(":")[1];
					if (!messageSender.equalsIgnoreCase(myID) && messageContent.equals("sendNewGKey")){
							System.out.println("Receive generate new gKey request from "+ messageSender+ "\n\tGenerating new Group key and sending it back to sender...");
							String senderIPPort = receivePacket.getAddress() + ":" + receivePacket.getPort();
							int senderIndex = findUser(messageSender);
							String decryptedMessage = messageHandler.decryptMessage("", message, senderIPPort);
							
					} 
					Thread.sleep(broadcastInterval);
				}catch (Exception e) {
					System.out.println("Exc2: " + e);
					e.printStackTrace();
				}
				}
			
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally {
		try {
			multicastSocket.leaveGroup(communicationBroadCastAddr);
		} catch (IOException e) {
			System.out.println("LT");
			e.printStackTrace();
		}
		multicastSocket.close();
	}
	}
	
	private String newGKey(String oldKey){
		return oldKey+"1";
	}
	
	private int findUser(String senderID) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getReceiverID().equals(senderID)) {
				return i;
			}
		}
		return -1;
	}

}
	

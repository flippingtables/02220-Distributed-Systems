package clientserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SenderThread extends Thread {

	private InetAddress currentLMGAddr;
	private int currentLMGAddrPort;
	private String senderID;
//	private String to;
	private Integer frequency;
//	private Messenger messenger;
	private String privateKey;
	private String gKey;
	private ArrayList<CommunicationRow> users;
	
	private static final String	communicationBroadCastAddrHost = "230.0.0.1";
	private static InetAddress	communicationBroadCastAddr;
	private static int			communicationBroadCastAddrPort = 9000;
	
	public SenderThread(String senderID, String currentLMGAddr, int currentLMGAddrPort, int frequency, String privateKey, String gKey, ArrayList<CommunicationRow> users) {
		
		this.currentLMGAddrPort = currentLMGAddrPort;
		this.senderID = senderID;
		this.frequency = frequency * 200;
		this.gKey = gKey;
		this.privateKey = privateKey;
		this.users = users;
		try {
			this.currentLMGAddr = InetAddress.getByName(currentLMGAddr);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("SenderThread constructor failed");
		}
		
	}
	
	public void run() {
		DatagramSocket serverSocket = null;
		Coordinates coords = new Coordinates();
		try {
			serverSocket = new DatagramSocket();
			String line = String.format("Broadcasting messages on: %s:%s", currentLMGAddr.toString(), currentLMGAddrPort);
			System.out.println(line);
			try {
				while (true) {
					byte[] sendData = new byte[256];

					// construct message to send
					String messageToSend = coords.getCoordinates();
					sendData = message(senderID, enryptMessagePayload(gKey, messageToSend));
					DatagramPacket sendPacket = new DatagramPacket(sendData,
							sendData.length, currentLMGAddr, currentLMGAddrPort);
					serverSocket.send(sendPacket);
					
					
					//Rekeying messages
					for (CommunicationRow user: users){
						if (user.getSenderID().equals(senderID) && user.isSendGKey()){
							
							System.out.println("Sending new group key request to "+ user.getReceiverID()+"\n");
							communicationBroadCastAddr = InetAddress.getByName(communicationBroadCastAddrHost);
							byte[] sendRekeyData = new byte[256];

//							String sendRekeyMessage = newGroupKey(gKey);
							sendRekeyData = message(senderID, "sendNewGKey");
							DatagramPacket sendRekeyDataPacket = new DatagramPacket(sendRekeyData,
									sendRekeyData.length, currentLMGAddr, currentLMGAddrPort);
							serverSocket.send(sendRekeyDataPacket);
							user.setSendGKey(false);
						}
					}
					Thread.sleep(frequency);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			serverSocket.close();
		}
	}

	/*
	 * @param from The Sender
	 * 
	 * @message The message payload, this will be encrypted before transmission
	 */
	private byte[] message(String from, String message) {
		return (from + ":" + message).getBytes();
	}
	
	private byte[] message(String from, String to, String message) {
		return (from + ":" + to +":"+ message).getBytes();
	}

	private String enryptMessagePayload(String groupKey, String message) {
		return message;
	}
	
	
}

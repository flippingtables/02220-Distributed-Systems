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
					Thread.sleep(frequency);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			serverSocket.close();
		}
	}

	/*
	 * @param from The Sender
	 * 
	 * @param to The receiver
	 * 
	 * @message The message payload, this will be encrypted before transmission
	 */
	private byte[] message(String from, String message) {
		return (from + ":" + message).getBytes();
	}

	private String enryptMessagePayload(String groupKey, String message) {
		return message;
	}
}

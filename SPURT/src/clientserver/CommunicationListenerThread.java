package clientserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.sound.midi.Receiver;

public class CommunicationListenerThread extends Thread {

	private InetAddress communicationBroadCastAddr;
	private int communicationBroadCastAddrPort;
	private String myID;
	private MessageHandler messageHandler;
	private int broadcastInterval = 5000;

	public CommunicationListenerThread(String myID,
			InetAddress communicationBroadCastAddr,
			int communicationBroadCastAddrPort) {
		// this.GROUP = group;
		// this.PORT = port;
		this.myID = myID;
		this.communicationBroadCastAddr = communicationBroadCastAddr;

		messageHandler = new MessageHandler(this.myID);

	}

	public void run() {
		MulticastSocket multicastSocket = null;
		try {
			multicastSocket = new MulticastSocket(
					communicationBroadCastAddrPort);
			multicastSocket.joinGroup(communicationBroadCastAddr);

			String line = String
					.format("Listening for messages in CommunicationListenerThread @%s:%s",
							communicationBroadCastAddr.toString(),
							communicationBroadCastAddrPort);
			System.out.println(line);
			while (true) {
				try {
					byte[] receiveData = new byte[256];
					DatagramPacket receivePacket = new DatagramPacket(
							receiveData, receiveData.length);
					multicastSocket.receive(receivePacket);

					String message = new String(receivePacket.getData());
					String messageSender = message.split(":")[0];

					if (messageSender.equals(myID)) {
						String senderIPPort = receivePacket.getAddress() + ":"
								+ receivePacket.getPort();

						if (messageHandler.checkRecipient(message)) {
							String decryptedMessage = messageHandler
									.decryptMessage("", message, senderIPPort);
						} else {
							continue;
						}
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
				multicastSocket.leaveGroup(communicationBroadCastAddr);
			} catch (IOException e) {
				System.out.println("CCL");
				e.printStackTrace();
			}
			multicastSocket.close();
		}
	}

}

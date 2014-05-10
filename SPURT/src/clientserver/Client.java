package clientserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Vector;

public class Client extends Thread {

	private InetAddress GROUP;
	private int PORT;
	private String username;
	private String to;
	private MessageVerifier verifyMessage;

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
                while (true) {
                    try {
                        byte[] receiveData = new byte[256];
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        multicastSocket.receive(receivePacket);
                        String message =  new String(receivePacket.getData());
//                        System.out.println(message);
                        if (verifyMessage.checkRecipient(message)){
                        	System.out.println("Decrypting message payload...");
                        	System.out.println("Sender: "+receivePacket.getAddress()+":"+receivePacket.getPort());
                        	System.out.println("\tPayload: "+message.split(":")[2]);
                        } else{
//                        	System.out.println("Not for me.");
                        }
                        
                    } catch (Exception e) {
                    	System.out.println("Exc0: " + e);
                    	e.printStackTrace();
                    }
                }
            } catch (Exception e) {
            	System.out.println("Exc: "+e);
            } finally {
            	try {
					multicastSocket.leaveGroup(GROUP);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                multicastSocket.close();
            }
        
	}

	/*
	 * @param from The Sender
	 * 
	 * @param to The receiver
	 * 
	 * @message The message payload, this will be encrypted before transmission
	 */
	private byte[] message(String from, String to, String message) {
		return (from + ":" + to + ":" + enryptMessagePayload(message))
				.getBytes();
	}

	private String enryptMessagePayload(String message) {
		return message;
	}

}

package clientserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server extends Thread {
	
	private InetAddress GROUP;
	private int PORT;
	private String username;
	private String to;
	
	public Server(InetAddress group, int port, String name, String to) {
		this.GROUP = group;
		this.PORT = port;
		this.username = name;
		this.to= to;
	}
	
	
	public void run() {
            DatagramSocket serverSocket = null;
            Coordinates coords = new Coordinates();
            try {
                serverSocket = new DatagramSocket();
                String line = String.format("Broadcasting messages on: %s:%s \n", this.GROUP.toString(), this.PORT);
                System.out.println(line);
                try {
                    while (true) {
                        byte[] sendData = new byte[256];

                        // construct message to send
                        String messageToSend = coords.sendCoordinates();
                        sendData = message(username, to, messageToSend);
                        
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, GROUP, PORT);
//                        System.out.println("Sending encrypted message to: "+to);
                        serverSocket.send(sendPacket);
                        Thread.sleep(2000);
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
	 * @param to The receiver
	 * @message The message payload, this will be encrypted before transmission
	 */
	private byte[] message(String from, String to, String message){
		return (from+":"+to+":"+enryptMessagePayload(message)).getBytes();
	}
	
	private String enryptMessagePayload(String message){
		return message;
	} 
}

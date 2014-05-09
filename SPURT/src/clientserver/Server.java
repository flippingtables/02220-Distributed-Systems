package clientserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

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
            try {
                serverSocket = new DatagramSocket();
                try {
                    while (true) {
                        byte[] sendData = new byte[256];

                        // construct message to send
                        
//                        sendData = message(username, to, "hey man");
//                        long threadID = Thread.currentThread().getId();
                        sendData = message(username, to, "hey man");
                        
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, GROUP, PORT);
                        serverSocket.send(sendPacket);
                        Thread.sleep(2000);
                    }
                } catch (Exception e) {
                	System.out.println(e);
//                    LOGGER.error(null, e);
                }
            } catch (Exception e) {
//                LOGGER.error(null, e);
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
    
//	private synchronized void putMessage() throws InterruptedException {
//		while (messages.size() == MAXQUEUE)
//			wait();
//		messages.addElement(new java.util.Date().toString());
//		notify();
//	}
//
//	// Called by Consumer
//	public synchronized String getMessage() throws InterruptedException {
//		notify();
//		while (messages.size() == 0)
//			wait();
//		String message = (String) messages.firstElement();
//		messages.removeElement(message);
//		return message;
//
//	}
}

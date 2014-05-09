package clientserver;

import java.io.BufferedReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;
import java.util.Random;

public class UdpBroadcaster {
	
private static final int PORT = 9876;
private static final String MCAST_ADDR = "230.0.0.1";//"FF7E:230::1234";

private static InetAddress GROUP;
private static BufferedReader in = null;
private static int counter = 0;

private static MessageVerifier verifyMessage;

public static void main(String[] args) {
	
	/*
	 * The app should look something like this:
	 * 	starting app:
			broadcast on	: IP:PORT
			listen on	: IP1:PORT1
			username	: USERNAME
			friends		: X,Y,Z
		message: “lat:long”
		sending...
		received from “friend”
			Decrypting…
			“lat:long”
	 */
	
	if (args.length != 2) {
        System.err.println(
            "Usage: java -jar broadcast.jar YOURUSERNAME USERNAME_TO_SEND_TO");
        System.exit(1);
    }
	verifyMessage = new MessageVerifier(args[0]);
	
	
	
    try {
    	System.out.println("Starting up server/Client");
        GROUP = InetAddress.getByName(MCAST_ADDR);
        
//        Thread server = server(new Random().nextInt(10));
        Thread server = new Server(GROUP, PORT, verifyMessage.getUsername(), args[1]);
        server.start();
//        server.start();
        Thread.sleep(3000);
        Thread client = client();
        client.start();
        client.join();
    } catch (Exception e) {
        System.out.println("Usage : [group-ip] [port]");
    	//LOGGER.error("Usage : [group-ip] [port]");
    }
}

private static Thread client() {
    return new Thread(new Runnable() {
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
                        if (verifyMessage.checkRecipient(message)){
                        	System.out.println("Decrypting message payload...");
                        	System.out.println("Sender: "+receivePacket.getAddress());
                        	System.out.println("\tPayload: "+message.split(":")[2]);
                        } else{
//                        	System.out.println("Not for me.");
                        }
                        
                    } catch (Exception e) {
                    	System.out.println(e);
                    }
                }
            } catch (Exception e) {
            	System.out.println(e);
            } finally {
                multicastSocket.close();
            }
        }
    });
}

private static Thread server(int id) {
	final int thisID = id;
	System.out.println("Starting thread: " + thisID);
    return new Thread(new Runnable() {
        public void run() {
            DatagramSocket serverSocket = null;
            try {
                serverSocket = new DatagramSocket();
                try {
                    while (true) {
                        byte[] sendData = new byte[256];

                        // construct quote
                        String dString = null;
//                        long threadId = Thread.currentThread().getId();
                        if (in == null)
                            dString = " thread: "+ thisID; //new Date().toString() + 
                        else
                            dString = "Hey" + counter++;

                        sendData = ("To: superman, From: "+verifyMessage.getUsername()).getBytes();
                        

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
    });
}

}
package clientserver;

import java.io.BufferedReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;
import java.util.Random;

public class SPURT {

	private static final int PORT = 9876;
	private static final String MCAST_ADDR = "230.0.0.1";// "FF7E:230::1234";

	private static InetAddress GROUP;
	private static BufferedReader in = null;
	private static int counter = 0;

	// private static MessageVerifier verifyMessage;

	public static void main(String[] args) {

		/*
		 * The app should look something like this: starting app: broadcast on :
		 * IP:PORT listen on : IP1:PORT1 username : USERNAME friends : X,Y,Z
		 * message: “lat:long” sending... received from “friend” Decrypting…
		 * “lat:long”
		 */

		if (args.length != 2) {
			System.err
					.println("Usage: java -jar spurt.jar YOURUSERNAME USERNAME_TO_SEND_TO");
			System.exit(1);
		}
		String name = args[0];

		try {
			System.out.println("Starting up server/Client");
			GROUP = InetAddress.getByName(MCAST_ADDR);

			Thread server = new Server(GROUP, PORT, name, args[1]);
			server.start();
			Thread.sleep(3000);
			Thread client = new Client(GROUP, PORT, name, args[1]);
			client.start();
			client.join();
		} catch (Exception e) {
			System.out.println("Usage : [group-ip] [port]");
		}
		
	}

}
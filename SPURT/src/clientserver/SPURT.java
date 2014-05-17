package clientserver;

import java.io.BufferedReader;
import java.net.InetAddress;

/*
 * TODO broadcast my username every now and then	√
 * TODO listen for users on the network				√
 * TODO add ability to select friends
 * TODO only send to friends
 * TODO only listen to messages from friends
 * TODO create main listening thread
 * TODO client thread fpr followed user
 * 			Listen for users
 * 				Found new user spawn thread
 * 			New message
 * 			Verify message
 * 			Send message to thread associated with user
 * 			
 * 
 * */

public class SPURT {

	private static final int PORT = 9876;
	
	//It works over IPv6 as well, just substitute the IPv4 address with an IPv6 one "FF7E:230::1234"; 
	private static final String MCAST_ADDR = "230.0.0.1";// "FF7E:230::1234";
	private static InetAddress GROUP;
	private static BufferedReader in = null;
	private static int counter = 0;
	

	public static void main(String[] args) {
		Messenger messenger;
		/*
		 * The app should look something like this: starting app: 
		 * broadcast on : IP:PORT
		 * listen on : IP1:PORT1 
		 * username : USERNAME
		 * friends : X,Y,Z
		 * message: “lat:long” sending... received from “friend” Decrypting…
		 * “lat:long”
		 */

		if (args.length != 2) {
			System.err
					.println("Usage: java -jar spurt.jar YOURUSERNAME USERNAME_TO_SEND_TO");
			System.exit(1);
		}
		String name = args[0];
		String to	= args[1];
		messenger = new Messenger();
		
		try {
			System.out.println("Starting up server/Client");
			GROUP = InetAddress.getByName(MCAST_ADDR);

			
			Utils.clearConsole();
			
			/*
			 * Start the main Broadcast sender/receiver threads
			 * Client thread will listen for messages
			 * 		and spawn new listener/receiver threads
			 * */
			Thread server = new Server(GROUP, PORT, name, to, 5000, messenger);
			server.start();			
			Thread.sleep(3000);
			Thread client = new ClientThread(GROUP, PORT, name, to, 3000, messenger);
			client.start();
			client.join();
		} catch (Exception e) {
			System.out.println("Usage : [group-ip] [port]");
		}
		
	}

}
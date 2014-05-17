package clientserver;

import java.io.BufferedReader;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
	private static final String	communicationBroadCastAddrHost = "230.0.0.1";// "FF7E:230::1234";
	private static InetAddress	communicationBroadCastAddr;
	private static int			communicationBroadCastAddrPort = 9000;

	
	private static int rekeyCount = 0;
	private static int updateFrequency = 0;
	private static String myID ="";
	

	private volatile static String	LMGAddress;
	private volatile static int 	LMGAddressPort;
	private volatile static String	GKey;
	
//	private static CommunicationRow myUser;
	
	private static ArrayList<CommunicationRow> users = new ArrayList<>();
	

//	private static CommunicationTable communicationTable = new CommunicationTable();
	
	public static void main(String[] args) {
		
		CommunicationRow ab = new CommunicationRow("Alice","Bob","231.1.1.1",9999, 15,  "ABKey", "AliceGKey1",  0,  false,  0, "345,436","231.1.1.1",9999);
		CommunicationRow ac = new CommunicationRow("Alice","Charlie","231.1.1.1",9999, 15,  "ACKey", "AliceGKey1",  0,  false,  0, "345,437","231.1.1.1",9999);
		CommunicationRow ad = new CommunicationRow("Alice","Dora","231.1.1.1",9999, 15, "ADKey", "AliceGKey1",  0,  false,  0, "345,438","231.1.1.1",9999);
		CommunicationRow ba = new CommunicationRow("Bob","Alice","232.2.2.2",8888, 10,    "ABKey", "BobGKey1",    0,  false,  0, "345,438","232.2.2.2",8888);
		CommunicationRow da = new CommunicationRow("Dora","Alice","233.3.3.3",7777, 20,   "ADKey", "DoraGKey1",   0,  false,  0, "345,439","233.3.3.3",7777);
		CommunicationRow db = new CommunicationRow("Dora","Bob","233.3.3.3",7777, 20, "BDKey", "DoraGKey1",   0,  false,  0, "345,410","233.3.3.3",7777);
		CommunicationRow dc = new CommunicationRow("Dora","Charlie","233.3.3.3",7777, 20, "CDKey", "DoraGKey1",   0,  false,  0, "345,411","233.3.3.3",7777);
		
		Messenger messenger = new Messenger();
		
		CommunicationRow myUser = null;
		
		try {
			communicationBroadCastAddr = InetAddress.getByName(communicationBroadCastAddrHost);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		users.add(ab);
		users.add(ac);
		users.add(ad);
		users.add(ba);
		users.add(da);
		users.add(db);
		users.add(dc);
		
		if (args.length != 1) {
			System.err
					.println("Usage: java -jar spurt.jar YOURUSERNAME [Alice, Bob, Charlie or Dora]");
			System.exit(1);
		}
		myID = args[0];
		System.out.println("My id: "+ myID);
		updateFrequency = 20;
		
		//Find the first user in the table matching "my username"
		for (CommunicationRow user : users){
			if (user.getSenderID().equals(myID)){
				myUser = user;
				break;
			}
		}
	
//		Utils.clearConsole();
		
		LMGAddress		= myUser.getLastKnownLMGAddr();
		LMGAddressPort	= myUser.getLastKnownLMGAddrPort();
		GKey 			= myUser.getGKey();
		
		try {
			System.out.println("Starting up server/Client");
			
			//Start the sender
			Thread sender = new SenderThread(myUser.getSenderID(), myUser.getCurrentLMGAddr(), myUser.getCurrentLMGAddrPort(), myUser.getFrequency(), myUser.getPrivateKey(), myUser.getGKey());  
			sender.setName("Sender" + myUser.getSenderID());  
			sender.start();
			
			Thread.sleep(1000);

			
			//start and count the number of listener threads
			int ListenerNumber = 0;			
			for (CommunicationRow user : users){
				if (user.getSenderID().equals(myID)){
					new ListenerThread(user.getReceiverID(), user.getCurrentLMGAddr(), user.getCurrentLMGAddrPort(), user.getGKey(), user.getFrequency(), users).start();
//					listener.setName("LocListenerTo"+user.getReceiverID());
//					listener.start();
					ListenerNumber++;
					Thread.sleep(1000);
				}
			}
			
			
			//start the communication thread
			Thread CommListener = new CommunicationListenerThread(myID, communicationBroadCastAddr, communicationBroadCastAddrPort);
			CommListener.setName("CommListener");
			CommListener.start();

			Thread.sleep(1000);
			
			Thread mainThread = new MainThread(users, updateFrequency);
			mainThread.start();
		
			
			
		} catch (Exception e) {
			System.out.println("Usage : [group-ip] [port]");
		}
		
	}

}
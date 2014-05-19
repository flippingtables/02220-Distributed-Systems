package clientserver;

import java.util.ArrayList;

public class MainThread extends Thread {

	private ArrayList<CommunicationRow> users;
	private int updateFrequency;
	private String myID;
	
	public MainThread(String myID, ArrayList<CommunicationRow> users, int updateFrequency) {
		this.users = users;
		this.updateFrequency = (updateFrequency * 100);
		this.myID = myID;
	}

	public void run() {
		while (true) {
			try {
				for (CommunicationRow user : users) {

					if (myID.equals(user.getSenderID())) {
						String nameFreshness = "Followed users' location:\n\t"+ 
								user.getReceiverID() + "\n\tFreshness: "
								+ user.getFreshnessCounter();
						//
						if (user.getFreshnessCounter() == 0
								|| user.getFreshnessCounter() == 1) {
							System.out.println(nameFreshness
									+ "\n\tCurrent location: "
									+ user.getLocation()+"\n");
						}
						if (user.getFreshnessCounter() == 2
								|| user.getFreshnessCounter() == 3) {
							System.out
									.println(nameFreshness
											+ "\n\tStale location: "
											+ user.getLocation()+"\n");
						}
						if (user.getFreshnessCounter() > 3) {
							System.out.println(nameFreshness
									+ "\n\tLast known location: "
									+ user.getLocation()+"\n");
						}
						Thread.sleep(updateFrequency);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

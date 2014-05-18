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
								user.getReceiverID() + " "
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
	
	
	/*Loop
	

	foreach row where ReceiverID = MyID
		Output position (lat,long)
		If row.freshness = 0 or 1
			Output “Current location”
		Else if row.freshness = 2 or 3
			Output  “Stale Location”
		Else Output “Last known location”
	End foreach
End loop

Sleep (UpdateFrequency)

// Create Rekey Event
ReKeyCount++;
If reKeyCount = 10
RekeyCount = 0
LMGAddress = LMGAddress + constant //change address and/or port
foreach row in table where SenderID = myID
		row.sendGKey = True
	end foreach
Endif	
			 * 
			 * */

}

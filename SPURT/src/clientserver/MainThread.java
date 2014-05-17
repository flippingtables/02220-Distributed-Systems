package clientserver;

import java.util.ArrayList;

public class MainThread extends Thread {

	private ArrayList<CommunicationRow> users;
	private int updateFrequency;
	public MainThread(ArrayList<CommunicationRow> users, int updateFrequency) {
		this.users = users;
		this.updateFrequency = updateFrequency * 1000;
	}

	public void run() {
		while (true) {
			try {
				for (CommunicationRow user : users) {
					System.out.println(user.getReceiverID() + " "
							+ user.getFreshnessCounter());
					if (user.getFreshnessCounter() <= 1)
						System.out.println("Current location: "
								+ user.getLocation());
					if (user.getFreshnessCounter() == 2
							|| user.getFreshnessCounter() == 3)
						System.out.println("Stale location: "
								+ user.getLocation());
					if (user.getFreshnessCounter() > 3)
						System.out.println("Last known location: "
								+ user.getLocation());
					Thread.sleep(updateFrequency);
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

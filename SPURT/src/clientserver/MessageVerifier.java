package clientserver;

import java.util.HashSet;

public class MessageVerifier {

	private HashSet<String> usersInNetwork = new HashSet<String>();
	private String username = "";

	public MessageVerifier(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public HashSet<String> getUsersInNetwork() {
		return usersInNetwork;
	}

	public void addUsersInNetwork(String user) {
		this.usersInNetwork.add(user);
	}
	
	
	public int size(){
		return usersInNetwork.size();
	}

	public boolean checkRecipient(String message) {
		String[] messages = message.split(":");

		if (messages.length <= 0) return false;

		if (!messages[0].contains(username) && messages[1].contains(username)) {
			return true;
		} else {
			return false;
		}
	}
}

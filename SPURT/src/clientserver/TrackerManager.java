package clientserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class TrackerManager {

	private HashSet<String> usersFollowingMe = new HashSet<String>();
	private HashMap<String, Date> usersFollowingMe1 = new HashMap();
	private HashSet<String> usersIFollow = new HashSet<String>();
	private String username = "";

	public TrackerManager(String username) {
		this.username = username;
	}

	public void addNewFollower(String follower, Date allowFollowingUntilDate) {
		if (!follower.equals(username)) {
			this.usersFollowingMe1.put(follower, allowFollowingUntilDate);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public HashSet<String> getUsersInNetwork() {
		return usersFollowingMe;
	}

	// Only attempt to add users if its not yourself
	public void addUsersInNetwork(String user) {
		if (!user.equals(username)) {
			this.usersFollowingMe.add(user);
		}
	}

	public void removeUserInNetwork(String user) {
		this.usersFollowingMe.remove(user);
	}

	public int size() {
		return usersFollowingMe.size();
	}

	public boolean checkRecipient(String message) {
		String[] messages = message.split(":");

		if (messages.length <= 0)
			return false;

		if (!messages[0].contains(username) && messages[1].contains(username)) {
			return true;
		} else {
			return false;
		}
	}

}

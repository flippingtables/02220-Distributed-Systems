package clientserver;

public class MessageVerifier {
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private String username = "";
	
	public MessageVerifier(String username){
		this.username = username;
	}
	public boolean checkRecipient(String message){
		
		String[] messages = message.split(":");
		
		if (messages.length <= 0) return false;
		
		if (!messages[0].contains(username) && messages[1].contains(username)){
			return true;
		} else {
			return false;
		}
	}

}

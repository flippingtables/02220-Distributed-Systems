package clientserver;

public class CommunicationRow {
	
	private volatile String SenderID;
	
	private volatile String ReceiverID;
	private volatile String CurrentLMGAddr;
	private volatile int CurrentLMGAddrPort;
	private volatile int LastKnownLMGAddrPort;
	private volatile String LastKnownLMGAddr;
	private volatile int FreshnessCounter;
	private volatile String PrivateKey;
	private volatile String GKey;
	private volatile int Frequency;
	private volatile String Location;
	private volatile int Timestamp;
	private volatile boolean SendGKey;
	
	public CommunicationRow(String senderID, String receiverID,
			String currentLMGAddr, int currentLMGAddrPort, int frequency,
			String privateKey, String gKey, int timestamp,
			boolean sendGKey,
			int freshnessCounter,  String location,
			String lastKnownLMGAddr, int lastKnownLMGAddrPort) {
		super();
		SenderID = senderID;
		ReceiverID = receiverID;
		CurrentLMGAddr = currentLMGAddr;
		LastKnownLMGAddr = lastKnownLMGAddr;
		FreshnessCounter = freshnessCounter;
		PrivateKey = privateKey;
		GKey = gKey;
		Frequency = frequency;
		Location = location;
		Timestamp = timestamp;
		SendGKey = sendGKey;
		CurrentLMGAddrPort = currentLMGAddrPort;
		LastKnownLMGAddrPort = lastKnownLMGAddrPort;
	}
	
	public int getCurrentLMGAddrPort() {
		return CurrentLMGAddrPort;
	}

	public void setCurrentLMGAddrPort(int currentLMGAddrPort) {
		CurrentLMGAddrPort = currentLMGAddrPort;
	}

	public int getLastKnownLMGAddrPort() {
		return LastKnownLMGAddrPort;
	}

	public void setLastKnownLMGAddrPort(int lastKnownLMGAddrPort) {
		LastKnownLMGAddrPort = lastKnownLMGAddrPort;
	}
	
	public String getSenderID() {
		return SenderID;
	}
	public void setSenderID(String senderID) {
		SenderID = senderID;
	}
	public String getReceiverID() {
		return ReceiverID;
	}
	public void setReceiverID(String receiverID) {
		ReceiverID = receiverID;
	}
	public String getCurrentLMGAddr() {
		return CurrentLMGAddr;
	}
	public void setCurrentLMGAddr(String currentLMGAddr) {
		CurrentLMGAddr = currentLMGAddr;
	}
	public String getLastKnownLMGAddr() {
		return LastKnownLMGAddr;
	}
	public void setLastKnownLMGAddr(String lastKnownLMGAddr) {
		LastKnownLMGAddr = lastKnownLMGAddr;
	}
	public int getFreshnessCounter() {
		return FreshnessCounter;
	}
	public void setFreshnessCounter(int freshnessCounter) {
		FreshnessCounter = freshnessCounter;
	}
	public String getPrivateKey() {
		return PrivateKey;
	}
	public void setPrivateKey(String privateKey) {
		PrivateKey = privateKey;
	}
	public String getGKey() {
		return GKey;
	}
	public void setGKey(String gKey) {
		GKey = gKey;
	}
	public int getFrequency() {
		return Frequency;
	}
	public void setFrequency(int frequency) {
		Frequency = frequency;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public int getTimestamp() {
		return Timestamp;
	}
	public void setTimestamp(int timestamp) {
		Timestamp = timestamp;
	}
	public boolean isSendGKey() {
		return SendGKey;
	}
	public void setSendGKey(boolean sendGKey) {
		SendGKey = sendGKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((CurrentLMGAddr == null) ? 0 : CurrentLMGAddr.hashCode());
		result = prime * result + CurrentLMGAddrPort;
		result = prime
				* result
				+ ((LastKnownLMGAddr == null) ? 0 : LastKnownLMGAddr.hashCode());
		result = prime * result + LastKnownLMGAddrPort;
		return result;
	}

	
	/*
	 * @return false if they are not equal, true if current == last LMGAddr
	 * */
	public boolean currentLMGAddrEqualsLastLMGAddr() {
		if (!CurrentLMGAddr.equals(LastKnownLMGAddr))
			return false;
		if (CurrentLMGAddrPort != LastKnownLMGAddrPort)
			return false;
		else return true;
	}
	
	
	
	
}

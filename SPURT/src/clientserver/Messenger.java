package clientserver;

public class Messenger {

	private String contents;
	private boolean available = false;

	public synchronized String get() {
//		while (available == false) {
//			try {
//				wait();
//			} catch (InterruptedException e) {
//			}
//		}
//		available = false;
//		notifyAll();
		return contents;
	}

	public synchronized void put(String value) {
//		while (available == true) {
//			try {
//				wait();
//			} catch (InterruptedException e) {
//			}
//		}
		contents = value;
//		available = true;
//		notifyAll();
	}
}

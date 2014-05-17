package clientserver;

import java.util.ArrayList;
import java.util.Iterator;

public class CommunicationTable {

	private volatile ArrayList<CommunicationRow> table = new ArrayList<>();

	public CommunicationTable() {
		super();
		CommunicationRow ab = new CommunicationRow("Alice","Bob","231.1.1.1",9999, 15,  "ABKey", "AliceGKey1",  0,  false,  0, "345,435","231.1.1.1",9999);
		CommunicationRow ac = new CommunicationRow("Alice","Charlie","231.1.1.1",9999, 15,  "ACKey", "AliceGKey1",  0,  false,  0, "345,435","231.1.1.1",9999);
		CommunicationRow ad = new CommunicationRow("Alice","Dora","231.1.1.1",9999, 15, "ADKey", "AliceGKey1",  0,  false,  0, "345,435","231.1.1.1",9999);
		CommunicationRow ba = new CommunicationRow("Bob","Alice","232.2.2.2.2",8888, 10,    "ABKey", "BobGKey1",    0,  false,  0, "345,435","232.2.2.2.2",8888);
		CommunicationRow da = new CommunicationRow("Dora","Alice","233.3.3.3.3",7777, 20,   "ADKey", "DoraGKey1",   0,  false,  0, "345,435","233.3.3.3.3",7777);
		CommunicationRow db = new CommunicationRow("Dora","Bob","233.3.3.3.3",7777, 20, "BDKey", "DoraGKey1",   0,  false,  0, "345,435","233.3.3.3.3",7777);
		CommunicationRow dc = new CommunicationRow("Dora","Charlie","233.3.3.3.3",7777, 20, "CDKey", "DoraGKey1",   0,  false,  0, "345,435","233.3.3.3.3",7777);		
		table.add(ab);
		table.add(ac);
		table.add(ad);
		table.add(ba);
		table.add(da);
		table.add(db);
		table.add(dc);
			
		this.table = table;

	}

	
}

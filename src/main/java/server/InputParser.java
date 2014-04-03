package server;

import java.io.PrintWriter;

public class InputParser {

	private static final int WAITING = 0;
	private static final int USERADDED = 1;
	private static final int SEEUSERS = 2;
	private static final int REMOVEUSER = 3;

	private int state = WAITING;
	
	public void PrintMenu(PrintWriter out){
		out.println("Add yourself to be tracked: 'add:ausername'");
		out.println("See all users in dir: 'seeusers'");
		out.println("Remove yourslef: 'remove:ausername'");
	}

	public String ProcessInput(String input) {
	
		String output = null;

		if (input.length()<0){
			return "0"; //no input";
		}
		if (!input.contains(":")) return "0";
		
		String[] tmp = input.split(":");
		
		if (tmp[0].equalsIgnoreCase("add")) output="add";
		if (tmp[0].equalsIgnoreCase("remove")) output="remove";
		if (input.equalsIgnoreCase("quit")) output="quit";
		
		return output;

	}

}

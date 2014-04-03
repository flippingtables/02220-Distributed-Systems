package jndi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;
import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import user.User;

public class RmiServer extends UnicastRemoteObject implements RmiServerIntf {
	public static final String MESSAGE = "Hello World";

	public RmiServer() throws RemoteException {
		super(0); // required to avoid the 'rmic' step, see below
	}

	public String getMessage() {
		return MESSAGE;
	}

	public static void main(String args[]) throws Exception {
		System.out.println("RMI server started");

		try { // special exception handler for registry creation
			LocateRegistry.createRegistry(1099);
			System.out.println("java RMI registry created.");
			System.out.println(Naming.lookup("//localhost/RmiServer"));
		} catch (RemoteException e) {
			// do nothing, error means registry already exists
			System.out.println("java RMI registry already exists.");
		}

		// Instantiate RmiServer
		RmiServer obj = new RmiServer();

		// Bind this object instance to the name "RmiServer"
		Naming.rebind("//localhost/RmiServer", obj);
		System.out.println("PeerServer bound in registry");
	}
	
	
	
	// public String getMessage() {
	// return "Yo niggah";
	// }
	//
	// public RmiServer() throws RemoteException {
	// super(0); // required to avoid the 'rmic' step, see below
	// }
	//
	// public static void main(String[] rgstring) {
	// try {
	// // Create the initial context. The environment
	// // information specifies the JNDI provider to use
	// // and the initial URL to use (in our case, a
	// // directory in URL form -- file:///...).
	// Hashtable hashtableEnvironment = new Hashtable();
	// hashtableEnvironment.put(Context.INITIAL_CONTEXT_FACTORY,
	// "com.sun.jndi.fscontext.RefFSContextFactory");
	// hashtableEnvironment.put(Context.PROVIDER_URL, rgstring[0]);
	// Context context = new InitialContext(hashtableEnvironment);
	// // If you provide no other command line arguments,
	// // list all of the names in the specified context and
	// // the objects they are bound to.
	// if (rgstring.length == 1) {
	// NamingEnumeration namingenumeration = context.listBindings("");
	// while (namingenumeration.hasMore()) {
	// Binding binding = (Binding) namingenumeration.next();
	// System.out.println(binding.getName() + " "
	// + binding.getObject());
	// }
	// }
	// // Otherwise, list the names and bindings for the
	// // specified arguments.
	// else {
	// for (int i = 1; i < rgstring.length; i++) {
	// Object object = context.lookup(rgstring[i]);
	// System.out.println(rgstring[i] + " " + object);
	// }
	// }
	// context.close();
	// } catch (NamingException namingexception) {
	// namingexception.printStackTrace();
	// }
	// }
}
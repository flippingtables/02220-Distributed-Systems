package server;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class Server {

	public static void main (String args[]){
		// Set up the environment for creating the initial context
//		Hashtable env = new Hashtable();
//		env.put(Context.INITIAL_CONTEXT_FACTORY, 
//		    "com.sun.jndi.ldap.LdapCtxFactory");
//		env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=JNDITutorial");
//		try {
//			DirContext ctx = new InitialDirContext(env);
//			Attributes answer = ctx.getAttributes("cn=Ted Geisel, ou=People");
//
//			for (NamingEnumeration ae = answer.getAll(); ae.hasMore();) {
//			    Attribute attr = (Attribute)ae.next();
//			    System.out.println("attribute: " + attr.getID());
//			    /* Print each value */
//			    for (NamingEnumeration e = attr.getAll(); e.hasMore();
//				 System.out.println("value: " + e.next()))
//				;
//			}
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	}
}

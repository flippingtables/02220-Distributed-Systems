package jndi;

import java.rmi.Naming;
import java.rmi.Remote;

public class RmiClient { 
    public static void main(String args[]) throws Exception {
        RmiServerIntf obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");
		
        System.out.println(obj.getMessage()); 
    }
}
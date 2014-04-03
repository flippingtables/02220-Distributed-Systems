package server;

import java.io.*;
import java.net.*;

public class SocketServer {

	static ServerSocket server;
	static Socket client;
	static BufferedReader in;
	static PrintWriter out;
	static String line;	
	static int port = 4321;

	public static void listenSocket() {
		try {
			System.out.format("Starting a server on port %d", port);
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.format("Could not listen on port %d", port);
			System.exit(-1);
		}

		try {
			client = server.accept();
		} catch (IOException e) {
			System.out.println("Accept failed: 4321");
			System.exit(-1);
		}

		try {
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("Read failed");
			System.exit(-1);
		}

		while (true) {
			try {
				line = in.readLine();
				// Send data back to client
				out.println(line);
			} catch (IOException e) {
				System.out.println("Read failed");
				System.exit(-1);
			}
		}
	}

//	public static void main(String args[]) {
//
//		try {
//			
//			listenSocket();
//			
//
//		}
//
//		catch (Exception e) {
//			
//			System.out.println(e.toString());
//		} finally {
//			try {
//				server.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				client.close();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			try {
//				in.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			out.close();
//		}
//
//	}
	public static void main(String[] args) throws IOException {
        
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
         
        int portNumber = Integer.parseInt(args[0]);
         
        try (
            ServerSocket serverSocket =
                new ServerSocket(Integer.parseInt(args[0]));
            Socket clientSocket = serverSocket.accept();     
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

}
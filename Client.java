/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_app;
// A Java program for a Client 
import java.net.*; 
import java.io.*; 

public class Client 
{ 
	
	private Socket socket		 = null; 
	private BufferedReader input = null; 
	private DataOutputStream out	 = null;
        private DataInputStream response = null;

	// constructor to put ip address and port 
	public Client(String address, int port) 
	{ 
		// establish a connection 
		try
		{ 
			socket = new Socket(address, port); 
			System.out.println("Connected"); 

			// takes input from terminal 
			input = new BufferedReader(new InputStreamReader(System.in)); 
                        
                        //takes response from the server
                        response = new DataInputStream(socket.getInputStream());
                            
			// sends output to the socket 
			out = new DataOutputStream(socket.getOutputStream()); 
		} 
		catch(UnknownHostException u) 
		{ 
			System.out.println(u); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		}

		// string to read message from input 
		String line = "";
                String received = null;

		// keep reading until "Over" is input 
		while (!line.equals("Over")) 
		{ 
			try
			{ 
				System.out.print("Say something: ");
                                line = input.readLine();
				out.writeUTF(line); 
                                received = response.readUTF();
                                
                               System.out.println(received);
			} 
			catch(IOException i) 
			{ 
				System.out.println("Server closed.");
                                break;
			} 
		} 

		// close the connection 
		try
		{ 
			input.close(); 
			out.close(); 
			socket.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	} 

	public static void main(String args[]) 
	{ 
		Client client = new Client("127.0.0.1", 5000); 
	} 
} 
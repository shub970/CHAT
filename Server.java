/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_app;
// A Java program for a Server 
import java.net.*; 
import java.io.*; 
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server 
{ 
	//initialize socket and input stream 
	private Socket socket = null; 
	private ServerSocket server = null; 
	private DataInputStream in = null; 
        private DataOutputStream out = null;

	// constructor with port 
	public Server(int port) 
	{ 
		// starts server and waits for a connection
               try{
               server = new ServerSocket(port); 
	       System.out.println("Server started at port "+server.getLocalPort()); 
               System.out.println("Waiting for a client ..."); 
               }
               catch(IOException i) 
		{ 
			System.out.println(i); 
		}
            
            int client_id = 1;
            while (true)
            {
			try{
                        socket = server.accept(); 
			System.out.println("A Client accepted at port "+socket.getPort()+". Client ID: "+client_id); 

			// takes input from the client socket 
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
                        
                        out = new DataOutputStream(socket.getOutputStream());
               
                        System.out.println("Assigning new thread for this client"); 
  
                        // create a new thread object 
                         Thread t = new ClientHandler(socket, in, out, client_id); 
  
                        // Invoking the start() method 
                        t.start();  
                        }
                        catch(IOException i) 
                        { 
			System.out.println(i); 
                        } 
		
                   client_id++;
		
            } 
        }

	public static void main(String args[]) 
	{ 
		Server server = new Server(5000); 
	} 
} 

class ClientHandler extends Thread  
{ 
    final DataInputStream dis; 
    final DataOutputStream dout;
    final Socket s; 
    final int id;
  
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dout, int id)  
    { 
        this.s = s; 
        this.dis = dis;  
        this.id = id;
        this.dout = dout;
    } 
  
    @Override
    public void run()  
    { 
        String received; 
        //String toreturn; 
        while (true)  
        { 
            
            try {   
                

                // receive the answer from client 
                received = dis.readUTF();     
                
                
                if(received.equals("Over")) 
                {  
                    System.out.println("Client " + this.s + " sends exit..."); 
                    System.out.println("Closing this connection."); 
                    this.s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
                
               
                System.out.println("Client "+this.id+": "+received);
                
                
            
            } catch (IOException e) { 
                System.out.println("client "+this.id+" closed.");
                break;
            } 
            
        } 
          
        try
        { 
            // closing resources 
            this.dis.close();
              
        } catch(IOException e){ 
            System.out.println(e.getMessage());
        } 
    } 
} 

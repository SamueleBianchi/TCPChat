/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Samuele
 */
public class RiceviDalClientThread implements Runnable
{
	Socket clientSocket=null;
	BufferedReader inDalClient;
	
	public RiceviDalClientThread(Socket clientSocket)
	{
            this.inDalClient = null;
            this.clientSocket = clientSocket;
	}
        
        @Override
	public void run() {
		try{
                    
		inDalClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));		
		
		String messageString;
		while(true){
		while((messageString = inDalClient.readLine())!= null){
			if(messageString.equals("end"))
			{
				break;//break to close socket if EXIT
			}
			System.out.println("Client: " + messageString);
		}
		this.clientSocket.close();
		System.exit(0);
	}
		
	}
	catch(Exception ex){
            System.out.println(ex.getMessage());
        }
	}
}


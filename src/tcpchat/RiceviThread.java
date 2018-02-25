/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Samuele
 */
public class RiceviThread implements Runnable
{
	Socket sock=null;
	BufferedReader inDalServer=null;
	
	public RiceviThread(Socket sock) {
		this.sock = sock;
	}
        
        @Override
	public void run() {
		try{
                    
		inDalServer = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
		String msgRecieved = null;
                
		while((msgRecieved = inDalServer.readLine())!= null)
		{
			System.out.println("Server: " + msgRecieved);
		}
		}catch(IOException e){
                    System.out.println(e.getMessage());
                }
	}
}

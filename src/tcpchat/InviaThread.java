/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Samuele
 */
public class InviaThread implements Runnable
{
	Socket sock=null;
	PrintWriter outAlServer=null;
	BufferedReader tastiera=null;
	
	public InviaThread(Socket sock)
	{
		this.sock = sock;
	}
        
        @Override
	public void run(){
		try{
		if(sock.isConnected())
		{
			System.out.println("Client connesso a "+sock.getInetAddress() + " nella porta "+sock.getPort());
			this.outAlServer = new PrintWriter(sock.getOutputStream(), true);	
		while(true){
                    
			tastiera = new BufferedReader(new InputStreamReader(System.in));
			String msgtoServerString=null;
			msgtoServerString = tastiera.readLine();
			this.outAlServer.println(msgtoServerString);
			this.outAlServer.flush();
		
			if(msgtoServerString.equals("end"))
			break;
                        
			}
		sock.close();}
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
	}
}

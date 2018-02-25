/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Samuele
 */
public class InviaAlClientThread implements Runnable
{
	PrintWriter outAlClient;
	Socket clientSock = null;
	
	public InviaAlClientThread(Socket clientSock)
	{
		this.clientSock = clientSock;
	}
        @Override
	public void run() {
		try{
                    
		outAlClient =new PrintWriter(new OutputStreamWriter(this.clientSock.getOutputStream()));
		
		while(true)
		{
			String msgAlClient = null;
			BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
			
			msgAlClient = tastiera.readLine();
			
			outAlClient.println(msgAlClient);
			outAlClient.flush();
		}
		}
		catch(Exception ex){
                    System.out.println(ex.getMessage());
                }	
	}
}

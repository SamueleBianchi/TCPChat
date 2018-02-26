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
        String ultimo;
        String username;
        InviaAlClientThread invia;
	
	public RiceviDalClientThread(Socket clientSocket,String username)
	{
            this.inDalClient = null;
            this.clientSocket = clientSocket;
            this.username=username;
	}
        
        @Override
	public void run() {
		try{
                    
		inDalClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));		
		
		String messageString;
		while(true){
		while((messageString = inDalClient.readLine())!= null){
                    setUltimoMess(messageString);
			if(messageString.equals("end"))
			{
				break;//break to close socket if EXIT
			}
			System.out.print("\r" + messageString);
                        System.out.print("\n");
                        setUsername(invia.getUsername());
                        System.out.print(username+": ");
		}
		this.clientSocket.close();
		System.exit(0);
	}
		
	}
	catch(Exception ex){
            System.out.println(ex.getMessage());
        }
	}
        
        public void setUltimoMess(String mess){
            ultimo=mess;
        }
        
        public String getUltimoMess(){
            return ultimo;
        }
        
        public void setUsername(String user){
            username=user;
        }
        
        public void setInvioThread(InviaAlClientThread inviat){
            invia=inviat;
        }
}


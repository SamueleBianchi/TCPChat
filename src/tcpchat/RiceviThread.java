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
        String ultimo;
        String username;
        InviaThread invia;
	
	public RiceviThread(Socket sock,String username) {
		this.sock = sock;
                this.username=username;
	}
        
        @Override
	public void run() {
		try{
                String msgRecieved;
		inDalServer = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
		
                
		while((msgRecieved = inDalServer.readLine())!= null)
		{
                        setUltimoMess(msgRecieved);
			System.out.print("\r" + msgRecieved);
                        System.out.print("\n");
                        setUsername(invia.getUsername());
                        System.out.print(username+": ");
		}
		}catch(IOException e){
                    System.out.println(e.getMessage());
                }
	}
        
        public void setUltimoMess(String mess){
            this.ultimo=mess;
        }
        
        public String getUltimoMess(){
            return ultimo;
        }
        
        public void setUsername(String user){
            username=user;
        }
        
        public void setInvioThread(InviaThread inviat){
            invia=inviat;
        }}

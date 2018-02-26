/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuele
 */
public class InviaAlClientThread implements Runnable
{
	PrintWriter outAlClient;
	Socket clientSock = null;
        Gestore g;
        String username;
        boolean userchange = false;
        String ultimomess;
        RiceviDalClientThread ricevi;
	
	public InviaAlClientThread(Socket clientSock,String username,RiceviDalClientThread ricevi)
	{
		this.clientSock = clientSock;
                this.username=username;
                this.ricevi=ricevi;
	}
        @Override
	public void run() {
		try{
                    
		outAlClient =new PrintWriter(new OutputStreamWriter(this.clientSock.getOutputStream()));
		
		while(true)
		{
                        setUltimoMess();
			String msgAlClient;
			BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
			System.out.print(username +": ");
			msgAlClient = tastiera.readLine();
			g=new Gestore(msgAlClient,username,userchange,ultimomess);
                        msgAlClient=g.risposta(msgAlClient, userchange, ultimomess);
                        username=g.getAutore(msgAlClient, userchange);
                        userchange=g.getUserchange();
                        ultimomess=g.getUltimoMess();
                        if(userchange==false){
			this.outAlClient.println(username.concat(": "+msgAlClient));
			this.outAlClient.flush();
                        }else{
                            userchange=false;
                        }
		
			if("end".equals(msgAlClient))
			break;
                        
			}
		clientSock.close();
                } 
                catch (IOException ex) {
                Logger.getLogger(InviaAlClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }	
	}
        
         public void setUltimoMess(){
            ultimomess=ricevi.getUltimoMess();
        }
        public String getUsername(){
            return username;
        }
}

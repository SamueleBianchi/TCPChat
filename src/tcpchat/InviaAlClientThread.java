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
        BufferedReader tastiera;
        String msgAlClient;
        boolean online= false;
	
	public InviaAlClientThread(Socket clientSock,String username,RiceviDalClientThread ricevi)
	{
		this.clientSock = clientSock;
                this.username=username;
                this.ricevi=ricevi;
                this.online=true;
	}
        @Override
	public void run() {
		try{
                if(clientSock.isConnected()){    
                    
		outAlClient =new PrintWriter(new OutputStreamWriter(this.clientSock.getOutputStream()));
		tastiera = new BufferedReader(new InputStreamReader(System.in));
                
		while(true)
		{
                        setUltimoMess();
			System.out.print(username +": ");
			msgAlClient = tastiera.readLine();
                        ultimomess=ricevi.getUltimoMess();
			g=new Gestore(msgAlClient,username,userchange,ultimomess);
                        
                        if((g.setOnline(msgAlClient,online)==true)&&(getOnline()==true)){
                        msgAlClient=g.risposta(msgAlClient, userchange, ultimomess);
                        username=g.getAutore(msgAlClient, userchange);
                        userchange=g.getUserchange();
                        
                        if(userchange==false){
			this.outAlClient.println(username.concat(": "+msgAlClient));
			this.outAlClient.flush();
                        }else{
                            userchange=false;
                        }
		
			if("end".equals(msgAlClient))
			break;
                        
			}else{
                            online=false;
                            while(online==false){
                            String on;
                            System.out.print(username +": ");
                            on=tastiera.readLine();
                            setOnline(g.setOnline(on,online));
                            if(g.setOnline(on,online)==true){
                            this.online=g.setOnline(on,true);
                            online=true;
                            break;}
                            }
                        }
                }
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
        public boolean getOnline(){
            return online;
        }
        public void setOnline(boolean on){
            this.online=on;
        }
}

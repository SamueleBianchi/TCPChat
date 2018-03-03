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
        Gestore g;
        String username;
        boolean userchange = false;
        String ultimomess;
        RiceviThread ricevi;
        String msgtoServerString;
	boolean online= false;
         
	public InviaThread(Socket sock,String username,RiceviThread ricevi)
	{
		this.sock = sock;
                this.username=username;
                this.ricevi=ricevi;
                this.online=true;
                
	}
        
        @Override
	public void run(){
		try{
		if(sock.isConnected())
		{
			System.out.println(username+" connesso a "+sock.getInetAddress() + " nella porta "+sock.getPort());
			this.outAlServer = new PrintWriter(sock.getOutputStream(), true);
                        tastiera = new BufferedReader(new InputStreamReader(System.in));
		while(true){
                        setUltimoMess();
			System.out.print(username +": ");
			msgtoServerString = tastiera.readLine();
                        g=new Gestore(msgtoServerString,username,userchange,ultimomess);
                        ultimomess=ricevi.getUltimoMess();
                        
                        if((g.setOnline(msgtoServerString,online)==true)&&(getOnline()==true)){
                        msgtoServerString=g.risposta(msgtoServerString, userchange, ultimomess);
                        username=g.getAutore(msgtoServerString, userchange);
                        userchange=g.getUserchange();
                        
                        if(userchange==false){
			this.outAlServer.println(username.concat(": "+msgtoServerString));
			this.outAlServer.flush();
                        }else{
                            userchange=false;
                        }
		
			if("end".equals(msgtoServerString))
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
                }}
		sock.close();
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
	}
        
        
        
        public void setUltimoMess(){
            ultimomess=ricevi.getUltimoMess();
        }
        public String getUsername(){
            return username;
        }
        
        public String getUltimoMess(){
            return ultimomess;
        }
        public boolean getOnline(){
            return online;
        }
        public void setOnline(boolean on){
            this.online=on;
        }
}

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
        //stream per inviare messaggi al client
	PrintWriter outAlClient;
        //socket di tipo datasocket del client
	Socket clientSock = null;
        //oggetto di classe Gestore
        Gestore g;
        //username del server
        String username;
        //variabile booleana per il cambio di username inizialmente inizializzata a false
        boolean userchange = false;
        //ultimo messaggio ricevuto
        String ultimomess;
        //oggetto di classe RiceviDalClientThread
        RiceviDalClientThread ricevi;
        //stream per la scrittura da tastiera dei comandi/messaggi da inviare al client
        BufferedReader tastiera;
        //stringa da inviare al client
        String msgAlClient;
        //variabile booleana che specifica se il server online o meno
        boolean online= false;
	
        /**
         * 
         * @param clientSock socket del client
         * @param username username del server
         * @param ricevi oggetto di classe RiceviDalClientThread
         */
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
                        
                        //verifico se sono online, in caso eseguo le istruzioni successive
                        if((g.setOnline(msgAlClient,online)==true)&&(getOnline()==true)){
                        msgAlClient=g.risposta(msgAlClient, userchange, ultimomess);
                        username=g.getAutore(msgAlClient, userchange);
                        userchange=g.getUserchange();
                        outAlClient.flush();
                        
                        if(userchange==false){
			this.outAlClient.println(username.concat(": "+msgAlClient));
			this.outAlClient.flush();
                        }else{
                            userchange=false;
                        }
		
			if("end".equals(msgAlClient))
			break;
                        
			}else{//non sono online, quindi fino a che l'utente non scrive "in linea" i messaggi verranno ignorati
                            online=false;
                            while(online==false){
                            String on;
                            System.out.print(username +": ");
                            on=tastiera.readLine();
                            setOnline(g.setOnline(on,online));
                            if(g.setOnline(on,online)==true){
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
        
        //aggiorna l'ultimo messaggio ricevuto
         public void setUltimoMess(){
            ultimomess=ricevi.getUltimoMess();
        }
         
        /**
         * 
         * @return l'username del server
         */
        public String getUsername(){
            return username;
        }
        
        /**
         * 
         * @return true se il server è online, false se è offline
         */
        public boolean getOnline(){
            return online;
        }
        
        /**
         * 
         * @param on variabile booleana per impostare il server online/offline
         */
        public void setOnline(boolean on){
            this.online=on;
        }
}

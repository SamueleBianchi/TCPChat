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
        //socket del server
	Socket sock=null;
        //stream per inviare messaggi al server
	PrintWriter outAlServer=null;
        //stream per la scrittura da tastiera di comandi/messaggi da inviare al server
	BufferedReader tastiera=null;
        //oggetto di classe Gestore
        Gestore g;
        //username dell'server
        String username;
        //variabile booleana per verificare se l'utente ha cambiato username o meno
        boolean userchange = false;
        //ultimo messaggio ricevuto
        String ultimomess;
        //oggetto di classe RiceviThread
        RiceviThread ricevi;
        //messaggio da inviare al server
        String msgtoServerString;
        //variabile boolena per verificare se il client è onlne/offline
	boolean online= false;
        
        /**
         * 
         * @param sock socket del server
         * @param username username del client
         * @param ricevi oggetto di classe RiceviThread
         */
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
			System.out.println("\u001B[34m"+"Oltre ai semplici messaggi puoi inviare dei messaggi speciali: \n"+"\u001B[34m"+"-autore <nome dell'autore> :se vuoi modificare il tuo username;\n"+"\u001B[34m"+"-non in linea: se vuoi essere temporaneamente offline;\n"+"\u001B[34m"+"-in linea: se vuoi tornare online;\n"+"\u001B[34m"+"-smile :se vuoi inviare l'emoji corrispondente allo smile;\n"+"\u001B[34m"+"-like :se vuoi inviare l'emoji corrispondente al like;\n"+"\u001B[34m"+"-echo :se vuoi inviare l'ultimo messaggio ricevuto;\n"+"\u001B[34m"+"-end :se vuoi terminare la comunicazione;\u001B[0m");
			this.outAlServer = new PrintWriter(sock.getOutputStream(), true);
                        tastiera = new BufferedReader(new InputStreamReader(System.in));
		while(true){
                        setUltimoMess();
			System.out.print(username +": ");
			msgtoServerString = tastiera.readLine();
                        g=new Gestore(msgtoServerString,username,userchange,ultimomess);
                        ultimomess=ricevi.getUltimoMess();
                        
                        //verifico se sono online, in caso eseguo le istruzioni successive
                        if((g.setOnline(msgtoServerString,online)==true)&&(getOnline()==true)){
                        msgtoServerString=g.risposta(msgtoServerString, userchange, ultimomess);//formulo il messaggio da inviare
                        username=g.getAutore(msgtoServerString, userchange);//aggiorno l'username
                        userchange=g.getUserchange();//imposto la variabile di cambio username (true/false)
                        outAlServer.flush();
                        
                        if(userchange==false){//se l'username non cambia invio username e messaggio al destinatario
			this.outAlServer.println(username.concat(": "+msgtoServerString));
			this.outAlServer.flush();
                        }else{
                            userchange=false;
                        }
		
			if("end".equals(msgtoServerString))//se il messaggio da inviare è uguale a end esco dal ciclo while
			break;
                        
                        }else{ //non sono online, quindi fino a che l'utente non scrive "in linea" i messaggi verranno ignorati
                            online=false;
                            while(online==false){//ciclo fino a che non scrivo "in linea", in tal caso la variabile "online" tornerà a true
                            String on;
                            System.out.print(username +": ");
                            on=tastiera.readLine();
                            setOnline(g.setOnline(on,online));
                            if(g.setOnline(on,online)==true){
                            online=true;
                            break;}
                            }
                        }
                }}
                try{
		sock.close();//chiudo la connessione
                }catch(Exception e){
                    System.err.print("Errore nella chiusura della connessione: "+ e.getMessage());
                }
                }catch(IOException e){
                    System.err.println("Errore di I/O"+e.getMessage());
                }
	}
        
        //aggiorna l'ultimo messaggio ricevuto
        public void setUltimoMess(){
            ultimomess=ricevi.getUltimoMess();
        }
        
        /**
         * 
         * @return l'username del client
         */
        public String getUsername(){
            return username;
        }
        
        /**
         * 
         * @return l'ultimo messaggio ricevuto
         */
        public String getUltimoMess(){
            return ultimomess;
        }
        
        /**
         * 
         * @return true se il client è online, false se è offline
         */
        public boolean getOnline(){
            return online;
        }
        
        /**
         * 
         * @param on variabile booleana per impostare il client online/offline
         */
        public void setOnline(boolean on){
            this.online=on;
        }
}

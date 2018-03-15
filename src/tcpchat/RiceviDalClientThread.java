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
        //socket del client
	Socket clientSocket=null;
        //stream per ricevere messaggi dal client
	BufferedReader inDalClient;
        //ultimo messaggio ricevuto
        String ultimo;
        //username del server
        String username;
        //oggetto di classe InviaAlClientThread
        InviaAlClientThread invia;
        //messaggio ricevuto dal client
        String messageString;
        
	/**
         * 
         * @param clientSocket socket del client per ricevere i suoi messaggi
         * @param username username del server
         */
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
                
		while(true && invia.getOnline()){
                    
		while((messageString = inDalClient.readLine())!= null){
                    
			if(messageString.equals("end"))//se il messaggio ricevuto è uguale a "end" significa che la connessione deve terminare
                                                        //perciò esco dal ciclo con il break
			{
				break;
			}
                        if(invia.getOnline()==true){//se sono online ricevo i messaggi
                        setUltimoMess(messageString);//imposto l'ultimo messaggio ricevuto
			System.out.print("\r" + messageString);//elimino il contenuto della riga con \r e stampo il messaggio ricevuto
                        System.out.print("\n");
                        
                        setUsername(invia.getUsername());
                        System.out.print(username+": ");//stampo il mio username
                        }
		}
		this.clientSocket.close();//chiudo la connessione
		System.exit(0);
                }}
	catch(Exception ex){
            System.out.println(ex.getMessage());
        }
	}
        
        /**
         * 
         * @param mess ultimo messaggio ricevuto
         */
        public void setUltimoMess(String mess){
            this.ultimo=mess;
        }
        
        /**
         * 
         * @return l'ultimo messaggio ricevuto
         */
        public String getUltimoMess(){
            return ultimo;
        }
        
        /**
         * 
         * @param user nome dell'username da aggiornare
         */
        public void setUsername(String user){
            username=user;
        }
        
        /**
         * 
         * @param inviat oggetto di classe InviaAlClientThread
         */
        public void setInvioThread(InviaAlClientThread inviat){
            invia=inviat;
        }
        
  
}



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
class RiceviThread implements Runnable
{   
        //socket del server
	Socket sock=null;
        //stream per ricevere messaggi dal server
	BufferedReader inDalServer=null;
        //ultimo messaggio ricevuto
        String ultimo;
        //username del client
        String username;
        //oggetto di classe InviaThread
        InviaThread invia;
	
        /**
         * 
         * @param sock socket del server
         * @param username username del client
         */
	public RiceviThread(Socket sock,String username) {
		this.sock = sock;
                this.username=username;
	}
        
        @Override
	public void run() {
		try{
                String msgRecieved;
		inDalServer = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
		
                while(true && invia.getOnline()){
                    
		while((msgRecieved = inDalServer.readLine())!= null)
		{
                    if(msgRecieved.equals("end"))//se il messaggio ricevuto è uguale a "end" significa che la connessione deve terminare
			{                        //perciò esco dal ciclo con il break
				break;
			}
                    if(invia.getOnline()==true){//se sono online ricevo i messaggi
                        setUltimoMess(msgRecieved);//imposto l'ultimo messaggio
			System.out.print("\r" + msgRecieved);//elimino il contenuto della riga con \r e stampo il messaggio ricevuto
                        System.out.print("\n");
                        setUsername(invia.getUsername());
                        System.out.print(username+": ");//stampo il mio username
                    }
		}
                try{
                this.sock.close();//chiudo la connessione
                System.out.print("Connessione chiusa");
                System.exit(0);
                }catch(Exception e){
                    System.err.print("Errore nella chiusura della connessione: "+ e.getMessage());
                }
                }
		}catch(IOException e){
                    System.out.println(e.getMessage());
                }
                
	}
        
        /**
         * 
         * @param mess ultimo messaggio ricevuto da impostare 
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
         * @param user l'username da impostare
         */
        public void setUsername(String user){
            username=user;
        }
        
        /**
         * 
         * @param inviat oggetto di classe InviaThread
         */
        public void setInviaThread(InviaThread inviat){
            invia=inviat;
        }
}

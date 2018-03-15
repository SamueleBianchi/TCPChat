/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

/**
 *
 * @author Samuele
 */
/**
 *
 * @author Samuele
 */
public class Gestore {
    //stringa contenente il comando/messaggio da inviare al destinatario
    public String cmd;
    //username dell'utente
    public String username;
    //variabile booleana per verificare se l'utente ha cambiato username 
    public boolean userchange;
    //ultimo messaggio inviato
    String ultimomess;
    
    /**
     * 
     * @param cmd messaggio/comando inserito dall'utente
     * @param username username dell'utente
     * @param userchange variabile per la verifica del cambio di username
     * @param ultimomess ultimo messaggio inviato
     */
   public Gestore(String cmd,String username,boolean userchange,String ultimomess){
       this.cmd=cmd;
       this.username=username;
       this.userchange=userchange;
       this.ultimomess=ultimomess;
   }
   /**
    * 
    * @return ritorna la stringa corrispondente al comando, altrimenti ritorna il messaggio inserito
    */
   public String verifica(){//restituisce il comando specifico
       if(cmd.contains("smile")){
           return "smile";
       }
       if(cmd.contains("autore")){
           return "autore";
       }
       if(cmd.contains("non in linea")){
           return "non in linea";
       }
       if(cmd.contains("in linea")){
           return "in linea";
       }
       if(cmd.contains("echo")){
           return "echo";
       }else {
           return cmd;
       }
   }
   /**
    * 
    * @param msgtoServerString messaggio inserito dall'utente
    * @param userchange variabile per la modifica dell'username
    * @param ultimomess ultimo messaggio inviato
    * @return la stringa da inviare al destinatario
    * 
    * Tramite il metodo verifica() verifico a quali comandi corrisponde il messaggio scritto e formulo una risposta da
    * inviare al destinatario. Nel caso di echo seleziono l'ultimo messaggio ricevuto (che contiene anche il nome del mittente)
    * e ne vado inviare solo la parte di messaggio (e non l'username)
    */
   public String risposta(String msgtoServerString,boolean userchange,String ultimomess){
       switch(verifica()){
           
                            case "smile":
                                msgtoServerString="\u263A";
                                break;
                            case "echo":
                                setUltimomess(ultimomess);
                                msgtoServerString=getUltimoMess();
                                String[] array = msgtoServerString.split(" ");
                                msgtoServerString="";
                                for(int i=1;i<array.length;i++){
                                    if(i==1){
                                        msgtoServerString=msgtoServerString.concat(array[i]);
                                    }else{
                                    msgtoServerString=msgtoServerString.concat(" "+array[i]);
                                    }
                                }
                                break;
                            case "like":
                                msgtoServerString="\ud83d\udc4d";
                        }
        setUltimomess(msgtoServerString);
        return msgtoServerString;
       
   }
   
        /**
         * 
         * @param msg comando comprendente la parola "autore"
         * 
         * Il metodo modifica l'username dividendo il messaggio in un array (diviso per ogni spazio presente), di questo
         * array vado ad assegnare l'username l'elemento dell'array di indice 1 (ovvero l'username stesso)
         */
        public void setUsernameCmd(String msg){
            String[] arr = msg.split(" "); 
            setUsername(arr[1]);
        }
        
        /**
         * 
         * @param msgtoServerString stringa inserita dall'utente
         * @param userchange variabile booleana per il cambio di username
         * @return l'username modificato
         */
        public String getAutore(String msgtoServerString,boolean userchange){
            if(cmd.contains("autore")){
                setUsernameCmd(msgtoServerString);
                userchange=true;
                setUserchange(true);
                return username;
            
        }else{
              setUserchange(false);  
            }
            return username;
        }
        
        /**
         * 
         * @param cmd stringa inserita dall'utente
         * @param online variabile booleana per verificare se l'utente è online (true) o no (false)
         * @return true se l'utente ha inserito la stringa "in linea", false se l'utente ha inserito "non in linea"
         */
        public boolean setOnline(String cmd,boolean online){
            boolean on=online;
            if(cmd.contains("non in linea")){
                on= false;
            }else{
                if(cmd.contains("in linea")){
                    on=true;
                }
            }
            return on;
        }
        
        /**
         * 
         * @return l'username dell'utente
         */
        public String getUsername(){
            return username;
        }
        
        /**
         * 
         * @param userchange1 variabile booleana per il cambio di username 
         */
        public void setUserchange(boolean userchange1){
            userchange=userchange1;
        }
        /**
         * 
         * @return true se l'utente ha cambiato username, false se non ha cambiato username
         */
        public boolean getUserchange(){
            return userchange;
        }
        /**
         * 
         * @param user username da modificare
         */
        public void setUsername(String user){
            username=user;
        }
        /**
         * 
         * @return l'ultimo messaggio inviato
         */
        public String getUltimoMess(){
            return ultimomess;
        }
        /**
         * 
         * @param ultimomes l'ultimo messaggio inviato 
         */
    private void setUltimomess(String ultimomes) {
        ultimomess=ultimomes;
    }
}

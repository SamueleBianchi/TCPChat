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
public class Gestore {
    public String cmd;
    public String username;
    public boolean userchange;
    String ultimomess;
    
   public Gestore(String cmd,String username,boolean userchange,String ultimomess){
       this.cmd=cmd;
       this.username=username;
       this.userchange=userchange;
       this.ultimomess=ultimomess;
   }
   
   public String verifica(){
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
   
        
        public void setUsernameCmd(String msg){
            String[] arr = msg.split(" "); 
            setUsername(arr[1]);
        }
        
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
        
        public String getUsername(){
            return username;
        }
        public void setUserchange(boolean userchange1){
            userchange=userchange1;
        }
        public boolean getUserchange(){
            return userchange;
        }
        public void setUsername(String user){
            username=user;
        }
        public String getUltimoMess(){
            return ultimomess;
        }

    private void setUltimomess(String ultimomes) {
        ultimomess=ultimomes;
    }
}

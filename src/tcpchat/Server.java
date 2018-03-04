/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Samuele
 */
public class Server {
    /**
     * 
     * @param args
     * @throws IOException 
     */
	public static void main(String[] args) throws IOException {
            
		final int port = 444;
                String username = "SERVER";
		ServerSocket sSocket = new ServerSocket(port);
		Socket connection = sSocket.accept();
                System.out.println("\u001B[34m"+"Oltre ai semplici messaggi puoi inviare dei messaggi speciali: \n"+"\u001B[34m"+"-autore <nome dell'autore> :se vuoi modificare il tuo username;\n"+"\u001B[34m"+"-non in linea: se vuoi essere temporaneamente offline;\n"+"\u001B[34m"+"-in linea: se vuoi tornare online;\n"+"\u001B[34m"+"-smile :se vuoi inviare l'emoji corrispondente allo smile;\n"+"\u001B[34m"+"-like :se vuoi inviare l'emoji corrispondente al like;\n"+"\u001B[34m"+"-echo :se vuoi inviare l'ultimo messaggio ricevuto;\n"+"\u001B[34m"+"-end :se vuoi terminare la comunicazione;\u001B[0m");
		RiceviDalClientThread ricevi = new RiceviDalClientThread(connection,username);
		Thread thread = new Thread(ricevi);
		InviaAlClientThread invia = new InviaAlClientThread(connection,username,ricevi);
		Thread thread2 = new Thread(invia);
                ricevi.setInvioThread(invia);
                thread.start();
		thread2.start();
	}}
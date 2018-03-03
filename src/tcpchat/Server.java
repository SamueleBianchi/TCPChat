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
	public static void main(String[] args) throws IOException {
            
		final int port = 444;
                String username = "SERVER";
		System.out.println("Server waiting for connection on port "+port);
		ServerSocket sSocket = new ServerSocket(port);
		Socket connection = sSocket.accept();
		System.out.println("Recieved connection from "+connection.getInetAddress()+" on port "+connection.getPort());
		RiceviDalClientThread ricevi = new RiceviDalClientThread(connection,username);
		Thread thread = new Thread(ricevi);
		InviaAlClientThread invia = new InviaAlClientThread(connection,username,ricevi);
		Thread thread2 = new Thread(invia);
                ricevi.setInvioThread(invia);
                thread.start();
		thread2.start();
	}}

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
		System.out.println("Server waiting for connection on port "+port);
		ServerSocket sSocket = new ServerSocket(port);
		Socket connection = sSocket.accept();
		System.out.println("Recieved connection from "+connection.getInetAddress()+" on port "+connection.getPort());
		//create two threads to invia and ricevi from client
		RiceviDalClientThread ricevi = new RiceviDalClientThread(connection);
		Thread thread = new Thread(ricevi);
		thread.start();
		InviaAlClientThread invia = new InviaAlClientThread(connection);
		Thread thread2 = new Thread(invia);
		thread2.start();
	}}

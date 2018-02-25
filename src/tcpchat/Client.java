/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpchat;

import java.net.Socket;

/**
 *
 * @author Samuele
 */
public class Client {
	public static void main(String[] args)
	{
		try {
			Socket socket = new Socket("localhost",444);
			InviaThread invia = new InviaThread(socket);
			Thread thread = new Thread(invia);
                        thread.start();
			RiceviThread ricevi = new RiceviThread(socket);
			Thread thread2 =new Thread(ricevi);
                        thread2.start();
                        
		} catch (Exception e) {
                    System.out.println(e.getMessage());
                } 
	}
}

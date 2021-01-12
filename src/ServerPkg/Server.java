/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerPkg;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohab
 */
public class Server {
    ServerSocket mySocket;
    Socket s;
    String msg;
    Server()
    {
        try {
            mySocket = new ServerSocket(5005);
            while(true)
            {
                s = mySocket.accept();
                new ChatHandler(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args)
    {
        Server server = new Server();
    }
}

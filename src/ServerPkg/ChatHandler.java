/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerPkg;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohab
 */
public class ChatHandler extends Thread{
    DataInputStream hear;
    PrintStream talk;
    String name;
    String msg;
    static Vector<ChatHandler> clint = new Vector<ChatHandler>();
    ChatHandler(Socket s)
    {
        try {
            hear = new DataInputStream(s.getInputStream());
            talk = new PrintStream(s.getOutputStream());
            clint.add(this);
            name = hear.readLine();
            talk.println("Welcome "+ name);
            System.out.println(name + " Logged in");
            start();
        } catch (IOException ex) {
            Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run()
    {
        while(true)
        {
            try {
                msg = hear.readLine();
                SendMsgToAll();
            } catch (IOException ex) {
                Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    public void SendMsgToAll()
    {
        for(ChatHandler x:clint)
        {
            x.talk.println(name+" : "+msg);
        }
        System.out.println(name+" : "+msg);
    }
    
}

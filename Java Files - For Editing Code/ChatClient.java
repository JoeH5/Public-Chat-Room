/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatRoom;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Joseph
 */
public class ChatClient extends Thread{
    private String hostname;
    private int port;
    private String userName;
    
    public ChatClient(String hostname, int port)
    {
        this.hostname = hostname;
        this.port = port;
    }
    
    public void exectue()
    {
        try{
            Socket socket = new Socket(hostname, port);
            
            System.out.println("Succesfully connected to the chat server!");
            
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
            
            
        }
        catch (UnknownHostException ex)
        {
            System.out.println("Server was not found " + ex.getMessage());
        }
        catch (IOException ex)
        {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }
    
    void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    String getUserName()
    {
        return this.userName;
    }
    
    public static void DisplayFrame()
    {
        JTextField tf; JLabel l; JButton b;  
        tf=new JTextField();  
        tf.setBounds(50,50, 150,20);  
        l=new JLabel();  
        l.setBounds(50,100, 250,20);      
        b=new JButton("Find IP");  
        b.setBounds(50,150,95,30);    
        
        tf.setVisible(true);
        l.setVisible(true);
        b.setVisible(true);
    }
    
    public static void main(String[] args)
    {
        DisplayFrame();
        if (args.length < 2) return;
        
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        
        ChatClient client = new ChatClient(hostname, port);
        
        client.exectue();
        
    }
    
   
}

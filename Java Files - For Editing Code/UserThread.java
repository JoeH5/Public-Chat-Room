/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatRoom;

import java.util.*;
import java.net.*;
import java.io.*;
/**
 *
 * @author Joseph
 */
public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
    
    public UserThread(Socket socket, ChatServer server)
    {
        this.socket = socket;
        this.server = server;
    }
    
    public void run()
    {
        try{
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        
        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
        
        printUsers();
        
        String userName = reader.readLine();
        server.addUserName(userName);
        
        String serverMessage = "New user connected: " + userName;
        server.broadcast(serverMessage, this);
        
        String clientMessage;
        
        do{
            clientMessage = reader.readLine();
            serverMessage = "[" + userName + "]: " + clientMessage;
            server.broadcast(serverMessage, this);
        } while(!clientMessage.equals("quit"));
        
        server.removeUser(userName, this);
        socket.close();
        
        serverMessage = userName + " has left.";
        server.broadcast(serverMessage, this);
        
        } catch (IOException ex)
        {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    void printUsers()
    {
        if(server.hasUsers())
        {
            writer.println("Connected users: " + server.getUserNames());
        }
        else
        {
            writer.println("No other users are connected at this time.");
        }
    }
    
    void sendMessage(String message)
    {
        writer.println(message);
    }
}

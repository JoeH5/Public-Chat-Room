package ChatRoom;



import java.io.*;
import java.net.*;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Joseph
 */
public class ChatServer {
    
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
    
    public ChatServer(int port)
    {
        this.port = port;
    }
    
    public void execute() throws IOException
    {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            
            System.out.println("Chat Server Is Listening On Port: " + port);
            
            while(true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected!");
                
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
            
        } catch (IOException ex)
        {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        if(args.length < 1) 
        {
            System.out.println("Syntax: java ChatServer <port-number>");
            System.exit(0);
        }
        
        int port = Integer.parseInt(args[0]);
        
        ChatServer server = new ChatServer(port);
        server.execute();
    }
    
    // deilvers messaghes from one user to the other
    void broadcast(String message, UserThread excludeUser)
    {
        for(UserThread aUser : userThreads)
        {
            if(aUser != excludeUser)
            {
                aUser.sendMessage(message);
            }
        }
    }
    
    void addUserName(String userName)
    {
        userNames.add(userName);
    }
    
    void removeUser(String userName, UserThread aUser)
    {
        boolean removed = userNames.remove(userName);
        if( removed)
        {
            userThreads.remove(aUser);
            System.out.println("The User " + userName + " left");
        }
    }
    
    Set<String> getUserNames()
    {
        return this.userNames;
    }
    
    
    boolean hasUsers()
    {
        return !this.userNames.isEmpty();
    }
}

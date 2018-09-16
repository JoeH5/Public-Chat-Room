/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatRoom;


import java.util.*;
import java.io.*;
import java.net.*;

/**
/**
 *
 * @author Joseph
 */
public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;
    
    public ReadThread(Socket socket, ChatClient client)
    {
        this.socket = socket;
        this.client = client;
    try {
    InputStream input = socket.getInputStream();
    reader = new BufferedReader(new InputStreamReader(input));
    } catch (IOException ex)
    {
        System.out.println("Error getting input stream: " + ex.getMessage());
        ex.printStackTrace();
    }
    }
    
    public void run()
    {
        while(true)
        {
            try{
                String response = reader.readLine();
                System.out.println("\n" + response);
                
                if(client.getUserName() != null)
                {
                    System.out.print("[" + client.getUserName() + "]: ");
                }
            } catch (IOException ex)
                    {
                        System.out.println("Error reading from the server: " + ex.getMessage());
                        ex.printStackTrace();
                        break;
                    }
        }
    }
}
    
   

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rockpaperscissors;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.io.*;

/**
 *
 * @author Hayde
 */
public class Server implements Runnable {
    private Socket clientCon;
    private boolean isDone=false;


    
    Server(Socket c){
        clientCon = c;
    }

    @Override
    public void run() {
        
            while(!isDone){
            try{
            DataInputStream in = new
            DataInputStream(clientCon.getInputStream());
            DataOutputStream out = new
            DataOutputStream(clientCon.getOutputStream());
            
           // String s = in.readUTF();        
           // out.writeUTF("Game Start!");
            //System.out.println(numOfClients);
            try{
                Thread.sleep(2000);
            
        }catch(Exception e){
            e.printStackTrace();
        }
            }
            catch(IOException e){
                System.out.println("Error connecting Client");
            }
        
        
    }
 
}
}

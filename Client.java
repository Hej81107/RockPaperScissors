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
import java.util.Random;
import java.util.Stack;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 *
 * @author Hayde
 */
public class Client implements Runnable{
    private String ip;
    private int port;
    private String name;
    public List<Card> Hand = new ArrayList<Card>();
    public Scanner Sc;
    public boolean ready = false;
    public boolean turn = false;
    public int clientnum=0;
    Socket con;
    DataInputStream in;
    //DataOutputStream out;
    ObjectInputStream oin;
    ObjectOutputStream out;
    public Client(String i, int p , String n, Scanner scan,int c,ObjectInputStream o){
        name = n;
        ip = i;
        port = p;
        Sc=scan;
        clientnum=c;
        try{
            System.out.println("Connecting to Server.");
            con = new Socket(ip,port);
            oin= new ObjectInputStream(con.getInputStream());
            in = new DataInputStream(con.getInputStream());
            out = new ObjectOutputStream(con.getOutputStream());
            
            ready=true;
}
catch (IOException e)
{
System.out.println("ERROR CONNECTING AS A CLIENT! "+e.getMessage());
}
        
    }
    public Client(Socket socket) {
        con= socket;
    }

    public void toHand(Card H){
        Hand.add(H);
    }
    public void setHand(List<Card> hand) {
    this.Hand = hand;
    }
    public void showHand(){
        for(int i =1; i<Hand.size()+1; i++){
                    System.out.println(i+" : "+Hand.get(i-1).toString());
                    }
    }
    public void PrintHand() {
      System.out.println("Your hand: " + Hand.toString());
            
        }
    /*public DataOutputStream getOut() {
    return out;
}*/
    

    @Override
    public void run() {
        try{
            while(!"READY".equals(oin.readUTF())){
                System.out.println("Waiting for other client to connect.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            System.out.println(Hand.toString());
            while(Hand.size()<5&&ready){
                try {
                    //Card c;
                    //c=(Card)oin.readObject();
                    System.out.println("Trying to add");
                    Hand.add((Card)oin.readObject());
                    System.out.println("Add success!");
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            ready=false;
            
            
            //while(Hand.size()>1){
          //System.out.println(Hand.toString());
                //}
           
            
            try {
                if(Hand.size() ==5){
                    if(clientnum==0){
                        System.out.println("reading client info");
                    int x = oin.readInt();
                    clientnum=x;
                    System.out.println("Client info read");
                    }

                    System.out.println(clientnum);
                    System.out.println("Enter a card to play, number 1-5\nEnter 'Done' once finished.");
                    
                    
                }
                    
                    Client.this.showHand();
                    
                    int bud = 0;
                    while(!turn){
                        System.out.println("Current budget remaining: "+(10-bud));
                        String ans = Sc.next();
                        
                        switch(ans){
                            case "1":
                                if((Hand.get(0).getNumber()+bud)<=10){
                                out.writeObject(Hand.get(0));
                                bud+=Hand.get(0).getNumber();
                                //out.flush();
                                Hand.remove(0);
                                out.writeBoolean(turn);
                                out.flush();
                                
                                }
                                else
                                    System.out.println("Choice exceeds budget, please enter another card or type 'Done' if finished.");
                                Client.this.showHand();
                                break;
                                
                                
                            case "2":
                                if((Hand.get(1).getNumber()+bud)<=10){
                                out.writeObject(Hand.get(1));
                                bud+=Hand.get(1).getNumber();
                                
                                Hand.remove(1);
                                out.writeBoolean(turn);
                                out.flush();
                                }
                                else
                                    System.out.println("Choice exceeds budget, please enter another card or type 'Done' if finished.");
                                Client.this.showHand();
                                break;
                                
                            case "3":
                                if((Hand.get(2).getNumber()+bud)<=10){
                                out.writeObject(Hand.get(2));
                                bud+=Hand.get(2).getNumber();
                                
                                Hand.remove(2);
                                out.writeBoolean(turn);
                                out.flush();
                                }
                                else
                                    System.out.println("Choice exceeds budget, please enter another card or type 'Done' if finished.");
                                Client.this.showHand();
                                break;
                                
                            case "4":
                                if((Hand.get(3).getNumber()+bud)<=10){
                                out.writeObject(Hand.get(3));
                                bud+=Hand.get(3).getNumber();
                                
                                Hand.remove(3);
                                out.writeBoolean(turn);
                                out.flush();
                                
                                }
                                else
                                    System.out.println("Choice exceeds budget, please enter another card or type 'Done' if finished.");
                                Client.this.showHand();
                                break;
                                
                            case "5":
                                if((Hand.get(4).getNumber()+bud)<=10){
                                out.writeObject(Hand.get(4));
                                bud+=Hand.get(4).getNumber();
                                
                                Hand.remove(4);
                                out.writeBoolean(turn);
                                out.flush();
                                }
                                else
                                    System.out.println("Choice exceeds budget, please enter another card or type 'Done' if finished.");
                                Client.this.showHand();
                                
                                break;
                            case "Done":
                                turn=true;
                                out.writeObject(new Card(0,0));
                                out.writeBoolean(turn);
                                out.flush();
                                break;
                                                
                        }
                        
                    }
                    System.out.println("Turn finished");
                    System.out.println("Awaiting results from server.");
                    while(turn){
                        
                        //System.out.println("trying to read");
                        System.out.println(oin.readUTF());
                        
                        try{
                            Thread.sleep(500);
                    }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }//catch(IOException e){
        
    }
    }
}
    
    



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.rockpaperscissors;


import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hayde
 */
public class RockPaperScissors {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        List<Card> L = new ArrayList<Card>();
        Stack<Card> deck= new Stack<Card>();
        List<Client> clients= new ArrayList<Client>();
        Client c;
        Thread Serv;
        for (int i =1; i<=3;i++){//1= rock, 2=paper, 3=scissors
            for(int j=1; j<=6;j++){
                L.add(new Card(i,j));
                
            }
        }
        //System.out.println(L.toString());
        Random rand = new Random();
        while(L.size()>0){
            Card temp = L.get(rand.nextInt(L.size()));
            deck.push(temp);
            L.remove(temp);
            
        }
        String ans=s.next();
        if(ans.compareTo("S") == 0)
        {
        System.out.println("Starting Server...");
        Listener Listen=new Listener(9000,100);
        Serv=new Thread(Listen);
        
        }
        else if(ans.compareTo("C")==0)
        {
        System.out.println("Enter Name: ");
                    String n = s.next();
        System.out.println("Starting Client");
        c =new Client("127.0.0.1",9000,n,s,Listener.clients.size(),Listener.objin);
        //clients.add(c);
        //Listener.pushClient(c);
        Serv = new Thread(c);
        
    }
        else{
            return;
        }
        Serv.start();
       
        while(Listener.clients.size()<2){
        //System.out.println("Waiting for 2 players");
            try {
                
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(RockPaperScissors.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Game starting!");
       
        
        for(int i = 1; i <11;i++){
            if(i%2==0)
                 try {
                      
                       Listener.obj.writeObject(deck.elementAt(i));
                       Listener.obj.flush();
                       
            } catch (IOException ex) {
                Logger.getLogger(RockPaperScissors.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(i%2==1)
                try {
                      
                       Listener.obj1.writeObject(deck.elementAt(i));
                       Listener.obj1.flush();
            } catch (IOException ex) {
                Logger.getLogger(RockPaperScissors.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            for(int i = 0; i<5;i++){
                
            
            //Listener.obj.flush();
            //Listener.obj1.flush();
            
            System.out.println("Sending client info");
            Listener.obj.writeInt(1);
            Listener.obj1.writeInt(2);
            System.out.println("client info sent");
           //  Listener.obj.flush();
            //Listener.obj1.flush();
            Thread.sleep(500);
            }
            Listener.obj.reset();
            Listener.obj1.reset();
            
        } catch (IOException ex) {
            Logger.getLogger(RockPaperScissors.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RockPaperScissors.class.getName()).log(Level.SEVERE, null, ex);
        }
         //Listener.openIn();
        
        
        //System.out.println(Listener.Lclients.get(0).Hand.toString());
        //System.out.println(Listener.Lclients.get(1).Hand.toString());
        
        
        while(ans.compareTo("Quit")!=0){
            
        }
        
    }
}

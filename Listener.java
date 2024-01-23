/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rockpaperscissors;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Hayde
 */
public class Listener implements Runnable
    {
    ServerSocket serve;
    int maxNumber;
    ExecutorService connections;
     static List<Server> clients= new ArrayList<Server>();
     static List<Client> Lclients= new ArrayList<Client>();
     static int clientCounter = 0;
     static ObjectOutputStream obj;
     static ObjectOutputStream obj1;
     static DataOutputStream out;
     static DataInputStream in1;
     static DataInputStream in2;
     static Socket c;
     static Socket c1;
     static Boolean inopen = false;
     int Hand1rock=0;
     int Hand1Paper=0;
     int Hand1Scissors=0;
     int Hand2rock=0;
     int Hand2Paper=0;
     int Hand2Scissors=0;
     int c2total = 0;
     int c1total = 0;
     String winner;
     int counter = 0;
     
     static ObjectInputStream objin;
     static ObjectInputStream objin1;
     public static List<Card> Hand1 = new ArrayList<Card>();
     public static List<Card> Hand2 = new ArrayList<Card>();
     
    public Listener(int port, int max)
        {
        
            connections = Executors.newFixedThreadPool(max);
            maxNumber = max;
            try
                {
                    serve = new ServerSocket(port);
                }
                catch (IOException e )
                    {
                    System.out.println("ERROR Creating the server: "+e.getMessage());
                    }
                }
@Override
public void run()
{
    while(true)
    {
    System.out.println("Listening for connections!");
        try
        {  System.out.println(clientCounter);
            if(clientCounter==0){
             c = serve.accept();
            System.out.println("Client recieved, spawning new server thread! 1");
            Server newcon = new Server(c);
            System.out.println("1");
            connections.execute(newcon);
            System.out.println("2");
            clients.add(newcon);
            
            Client newClient = new Client(c);
            Lclients.add(newClient);
            System.out.println("3");
            in1 = new DataInputStream(c.getInputStream());
            System.out.println("4");
            
            out = new DataOutputStream(c.getOutputStream());
            System.out.println("5");
            //objin= new ObjectInputStream(c.getInputStream());
            
            System.out.println("6");
            obj = new ObjectOutputStream(out);
            clientCounter++;
            }
            if(clientCounter==1){
                c1 = serve.accept();
            System.out.println("Client recieved, spawning new server thread! 2");
            Server newcon = new Server(c1);
            connections.execute(newcon);
            clients.add(newcon);
            Client newClient = new Client(c1);
            Lclients.add(newClient);
             in2 = new DataInputStream(c1.getInputStream());
             
            out = new DataOutputStream(c1.getOutputStream());
            //objin1= new ObjectInputStream(c1.getInputStream());
            
            obj1 = new ObjectOutputStream(out);
            System.out.println("OPENING INPUT");
            //Listener.openIn();
            clientCounter++;
            System.out.println("new client added!");
            }
            
            //System.out.println(clientCounter);
            if(clientCounter==2){
             
                 //obj.writeUTF("READY");
                // obj1.writeUTF("READY");
                 clientCounter++;
                 Listener.openIn();
                 inopen=true;
              
            }
            if(clientCounter==3){
                System.out.println(Hand1.toString());
                System.out.println(Hand2.toString());
                try {
                    System.out.println("listening for client hands");
                    Thread.sleep(1000);
                    
                    //Listener.openIn();
                    
                   while(inopen){
                        System.out.println("getting client hands");
                        //Thread.sleep(6000);
                    Hand1.add((Card)objin.readObject());
                    Boolean c1done = objin.readBoolean();
                    Hand2.add((Card)objin1.readObject());
                    Boolean c2done = objin1.readBoolean();
                    System.out.println(Hand1.toString());
                    System.out.println(Hand2.toString());
                    while(c1done&&c2done){
                        inopen=false;
                        System.out.println("Both clients FINISHED");
                        
                        for(int j = 0 ; j < Hand1.size()-1;j++){
                             if(Hand1.get(j).getSuit()==1)
                                 Hand1rock+=Hand1.get(j).getNumber();
                             else if(Hand1.get(j).getSuit()==2)
                                 Hand1Paper+=Hand1.get(j).getNumber();
                             else if(Hand1.get(j).getSuit()==3)
                                 Hand1Scissors+=Hand1.get(j).getNumber();
                             else
                                 continue;
                        }
                        for(int j = 0 ; j < Hand2.size()-1;j++){
                             if(Hand2.get(j).getSuit()==1)
                                 Hand2rock+=Hand2.get(j).getNumber();
                             else if(Hand2.get(j).getSuit()==2)
                                 Hand2Paper+=Hand2.get(j).getNumber();
                             else if(Hand2.get(j).getSuit()==3)
                                 Hand2Scissors+=Hand2.get(j).getNumber();
                             else
                                 continue;
                        }
                        
                        while(counter==0){
                            if(Hand1rock!=0){
                                if(Hand1rock<Hand2Scissors){
                                    c1total+=Hand1rock;
                                }
                                else{
                                    c1total+=Hand2Scissors;
                                }
                            }
                               
                            if(Hand1Paper!=0&&Hand2rock!=0){
                                 if(Hand1Paper<Hand2rock){
                                    c1total+=Hand1Paper;
                                }
                                else{
                                    c1total+=Hand2rock;
                                }
                            }
                               
                            if(Hand1Scissors!=0){
                                 if(Hand1Scissors<Hand2Paper){
                                    c1total+=Hand1Scissors;
                                }
                                else{
                                    c1total+=Hand2Paper;
                                }
                            }
                                
                            if(Hand2rock!=0){
                                 if(Hand2rock<Hand1Scissors){
                                    c2total+=Hand2rock;
                                }
                                else{
                                    c2total+=Hand1Scissors;
                                }
                            }
                               
                            if(Hand2Paper!=0){
                                if(Hand2Paper<Hand1rock){
                                    c2total+=Hand2Paper;
                                }
                                else{
                                    c2total+=Hand1rock;
                                }
                            }
                                
                            if(Hand2Scissors!=0){
                                if(Hand2Scissors<Hand1Paper){
                                    c2total+=Hand2Scissors;
                                }
                                else{
                                    c2total+=Hand1Paper;
                                }
                                
                            }
                                
                        
                        counter++;
                        
                        if(c1total>c2total){
                             winner = "Client 1 wins with "+c1total+" points!";
                        }
                        else if(c1total<c2total){
                             winner = "Client 2 wins with "+c2total+" points!";
                        }
                        else if(c1total==c2total){
                             winner = "There was a Tie!";
                        }
                        }
                            
                        obj.writeUTF(winner);
                        obj1.writeUTF(winner);
                        obj.flush();
                        obj1.flush();
                            
                        
                        
                        try{
                            Thread.sleep(1000);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        
                        
                    }
                    //System.out.println(Hand1.toString());
                    }
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
            }




}
catch(IOException e)
{
System.out.println("Listener failed: "+e.getMessage());
try
{
serve.close();
}
catch(IOException t)
{
System.out.println("ERROR: Listener was already closed!"+t.getMessage());
}
break;
}
}
System.out.println("Server is not longer Listening!");
}
/*public static void pushClient(Client c){
    Lclients.add(c);
}*/
public static void openIn(){
    try{
        objin= new ObjectInputStream(in1);
    }catch(IOException e){
        e.printStackTrace();
    };
    try{
        objin1= new ObjectInputStream(in2);
        inopen = true;
    }catch(IOException e){
        e.printStackTrace();
    };
    
    
}
}


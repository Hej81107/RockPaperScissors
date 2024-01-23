/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rockpaperscissors;

import java.io.Serializable;

/**
 *
 * @author Hayde
 */
public class Card implements Serializable{
    private int suit;
    private int number;

    public Card(int suit, int number) {
        this.suit = suit;
        this.number = number;
    }
    public int getSuit(){
        return suit;
    }
    public int getNumber(){
        return number;
    }
    @Override
    public String toString(){
        if(suit==1){
            return "Rock of "+number;
        }
        else if(suit==2){
            return "Paper of "+number;
        }
        else if(suit==3){
            return "Scissors of "+number;
        }
        return "";
    }
}

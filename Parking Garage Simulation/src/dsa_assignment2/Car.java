/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsa_assignment2;

/**
 *
 * @author USER
 */
public class Car {
    private String licensePlate;//lisence Plate in car
    protected int moves;// number of movements for a car
    
    public Car(String lisencePlate){
        this.licensePlate = lisencePlate;
        this.moves = 0;
    }
    
    public String getLisencePlate(){
        return licensePlate;
    }
    public int getMoves(){
        return moves;
    }
}






/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dsa_assignment2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author USER
 */
public class Garage {

    /**
     * @param args the command line arguments
     */
    //Create queue to store car in the garage
    private static Queue<Car> garage = new LinkedList<>();
    //Create Linked list to strore waiting cars, when haven't space in garage
    private static LinkedList<Car> waitingCars = new LinkedList<>();
    //Maximum capacity can store car in the garage queue
    private static int MAXCAPACITY = 10;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean start = true;
        while(start){
            //Get input from user
            System.out.println("Enter Input(arrival 'a'/ departure 'd'): ");
            // a ABS4679 - ABS4679 car arrived
            // d ABS4679 - ABS4679 car depart
            String input = scanner.nextLine();

            //Taking apart user input
            String[] parts = input.split(" ");
            //action = a or d
            String action = parts[0];
            String licensePlate = parts[1];
            //Create new car object
            Car car = new Car(licensePlate);
            
            
            if("a".equals(action)){
                arriveCar(car);//call arriveCar(car)
            }
            else if("d".equals(action)){
                boolean found = false;
                // Check if the departing car is in the garage
                for(Car car1: garage){
                    if(car1.getLisencePlate().equals(licensePlate)){
                        found = true;
                        departureCarFromGarage(licensePlate);//call departureCar(licensePlate)
                        break;
                    }
                }
                // Check if the departing car is in the waiting list
                for(Car car2: waitingCars){
                    if(car2.getLisencePlate().equals(licensePlate)){
                        found = true;
                        departCarFromWaitings(licensePlate);
                        break;
                    }
                }
                //check if the waiting list is empty.    
                if(waitingCars.isEmpty()&& garage.isEmpty()){
                    System.out.println("Waiting Queue and garage are empty.");
                    return;
                }
                 // If the car is not found in the garage or waiting list
                else if(!found){
                    System.out.println("This car not in garage or waiting queue.");
                    
                }
                
            }
        }
    }
    
    //Method to handle car arrival
    public  static void arriveCar(Car car){
        //check garage queue sixe and add car to the queue
        if(garage.size()< MAXCAPACITY){
            garage.add(car);
            System.out.println("Car with lisence plate "+car.getLisencePlate()+" has arrived to garage.");
        }
        else{
        //check garage is full and add that cars to waitingCar LinkedList
            System.out.println("Garage is full. The car added to waiting list.");
            waitingCars.add(car);
        }
    }
    
    //Method to handle car departure from the Garage
    //licensePlate the license plate of the departing car
    public static void departureCarFromGarage(String licensePlate){ 
        //Create another queue 
        Queue<Car> ref = new LinkedList<>();
        int x = 0; //count number of cars before the departing Car
        Car departingCar = null;
        
        //check if the grage is empty.
        if(garage.isEmpty()){
            System.out.println("Garage is empty.");
            return;

        }
        
        //update moves for the cars in the garage 
        for(Car car1: garage){
            if(car1.getLisencePlate().equals(licensePlate)){
                car1.moves += indexOf(garage, car1)+1;
            }
        }
        
        // Move cars within the garage check depetCar with before it.
        //and add that cars to ref queue.beacase want to rearrage according to the previous order
        while(!garage.peek().getLisencePlate().equals(licensePlate)){
            Car car = garage.peek();
            ref.add(car);
            garage.remove();
            car.moves = car.getMoves() + 10;
            x++;
        }
        // Depart the car from the garage
        if(garage.peek().getLisencePlate().equals(licensePlate)){
            departingCar = garage.peek();
            garage.remove();
            // Move cars within the garage
            if(x>0){
                while(!ref.isEmpty()){
                garage.add(ref.peek());
                ref.remove();
                }
                int n = MAXCAPACITY - (x+1);
                while(n!=0){
                    Car carToAdd = garage.peek();
                    carToAdd.moves = carToAdd.getMoves() +11;
                    garage.remove(carToAdd);
                    garage.add(carToAdd);
                    n--;
                }
            }
            else{
                for(Car car: garage){
                    car.moves = car.getMoves()+1;
                }
            }
            // Print departure message and update waiting list
            System.out.println("Car with lisence plate "+licensePlate+" has depated from garage.");
            System.out.println("There is a space available in garage.");
            if(!(waitingCars.isEmpty())){
                Car nextCar = waitingCars.poll();
                garage.add(nextCar);
                System.out.println("Car with lisence plate "+nextCar.getLisencePlate()+" has arrived to garage.");
            }
            // Print the number of moves for the departed car
            System.out.println("Moves of depating Car: "+departingCar.getMoves());
        }
    }
    
    //Method to handle car departure from the waiting list
    //licensePlate the license plate of the departing car
    public static void departCarFromWaitings(String licensePlate){
        
        // Iterate through waiting list and remove the departing car
        Iterator<Car> iterator = waitingCars.iterator();
        while(iterator.hasNext()) {
            Car car = iterator.next();
            if(car.getLisencePlate().equals(licensePlate)) {
                iterator.remove();
                System.out.println("Car with lisence plate "+licensePlate+" has departed from waiting queue.");
                System.out.println("Moves of depating Car: "+car.getMoves());
                break;
            }
        }  
    }
    
    //Method to find the index of an element in a queue.
    //queue   the queue to search
    //element the element to find
    //return the index of the element, or -1 if not found
    public static <T> int indexOf(Queue<T> queue, T element) {
        int index = 0;
        for (T item : queue) {
            if (item.equals(element)) {
                return index;
            }
            index++;
        }
        return -1; // Element not found
    } 
}

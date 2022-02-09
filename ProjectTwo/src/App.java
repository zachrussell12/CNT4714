/*
  Name: Zachary Russell
  Course: CNT 4714 Spring 2022
  Assignment title: Project 2 – Multi-threaded programming in Java
  Date:  February 13, 2022

  Class:  CNT 4714 Spring 2022 Section: 0001
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.*;

public class App {

    private static WorkStation[] stations;


    private static void initializeWorkStations(){

        int numOfStations;

        File config = new File("../ProjectTwo/src/config.txt");
        Scanner fileScan = null;

        try{
            fileScan = new Scanner(config);
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }

        numOfStations = Integer.parseInt(fileScan.nextLine());

        stations = new WorkStation[numOfStations];

        //System.out.println(numOfStations);

        int[] workloads = new int[numOfStations];


        for(int i = 0; i < numOfStations; i++){
            workloads[i] = Integer.parseInt(fileScan.nextLine());
            //System.out.println("Workload for Station " + i + ": " + workloads[i]);
        }

        Conveyors conveyorSystem = new Conveyors();

        for(int i = 0; i < numOfStations; i++){
            stations[i] = new WorkStation(("S" + i), ("C" + (i - 1 + numOfStations) % numOfStations), ("C" + i), workloads[i], conveyorSystem);
        }

        /*System.out.println("Stations Initialized: ");
        for(int i = 0; i < stations.length; i++){
            System.out.println("Name:" + stations[i].workStation);
            System.out.println("Belt One:" + stations[i].assignedConveyorOne);
            System.out.println("Belt Two:" + stations[i].assignedConveyorTwo);
            System.out.println("Workload:" + stations[i].assignedWorkload);
        }*/


    }

    public static void main(String[] args){

        PrintStream consoleOut = System.out;

        try {
            PrintStream output = new PrintStream("../ProjectTwo/src/simulation-output.txt");
            System.setOut(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        initializeWorkStations();

        int totalWorkload = 0;

        System.out.println("* * * * * * * * * * PACKAGE MANAGEMENT FACILITY SIMULATION BEGINNING * * * * * * * * * *");
        System.out.println();

        for(int i = 0; i < stations.length; i++){
            System.out.println("\t\t\t\t\t\tRouting Station " + stations[i].workStation + " Has Total Workload Of " + stations[i].assignedWorkload);
            totalWorkload += stations[i].assignedWorkload;
        }

        //System.out.println("Total workload is: " + totalWorkload);

        ExecutorService threadPool = Executors.newFixedThreadPool(stations.length);
        int counter = totalWorkload;
        int countLeft = 0;

        do{
            for (int i = 0; i < stations.length; i++) {
                threadPool.execute(stations[i]);
                countLeft = 0;
                for(int j = 0; j < stations.length; j++){
                    countLeft += stations[j].workLoadLeft;
                    //System.out.println(countLeft);
                    counter = 0;
                }
                counter = totalWorkload - countLeft;
                //System.out.println(counter);
            }
        }while((counter != totalWorkload) );

        threadPool.shutdownNow();
        /*try {
            threadPool.awaitTermination(5000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("* * * * * * * * * * ALL WORKLOADS COMPLETE * * * PACKAGE MANAGEMENT FACILITY SIMULATION ENDS * * * * * * * * * *");

        System.setOut(consoleOut);

        System.out.println("You can find any console output pertaining to the simulation in the simulation-output.txt file in the /src/ folder");
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

public class App {

    private static WorkStation[] stations;


    private static void initializeWorkStations(){

        String[][] stationBelts = new String[5][2];
        stationBelts[0][0] = "C0";
        stationBelts[0][1] = "C4";
        stationBelts[1][0] = "C0";
        stationBelts[1][1] = "C1";
        stationBelts[2][0] = "C1";
        stationBelts[2][1] = "C2";
        stationBelts[3][0] = "C2";
        stationBelts[3][1] = "C3";
        stationBelts[4][0] = "C3";
        stationBelts[4][1] = "C4";

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

        for(int i = 0; i < numOfStations; i++){
            stations[i] = new WorkStation(("S" + i), stationBelts[i][0], stationBelts[i][1], workloads[i]);
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

        initializeWorkStations();

        int totalWorkload = 0;

        System.out.println("* * * * * * * * * * PACKAGE MANAGEMENT FACILITY SIMULATION BEGINNING * * * * * * * * * *");
        System.out.println();

        for(int i = 0; i < stations.length; i++){
            System.out.println("\t\t\t\t\t\tRouting Station " + stations[i].workStation + " has a workload of " + stations[i].assignedWorkload);
            totalWorkload += stations[i].assignedWorkload;
        }

        Runnable thread = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable");
            }
        };

        //System.out.println("Total workload is: " + totalWorkload);

        Executor threadPool = new Executor() {
            @Override
            public void execute(Runnable command) {
                thread.run();
                System.out.println("hello");
            }
        };

        threadPool.execute(thread);
    }
}

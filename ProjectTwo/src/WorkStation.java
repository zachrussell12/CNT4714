import java.util.concurrent.locks.ReentrantLock;

public class WorkStation implements Runnable{

    public String workStation;
    public String assignedConveyorOne;
    public String assignedConveyorTwo;
    public Conveyors conveyorSystem;
    public boolean[] conveyorLocks = new boolean[2];
    public int assignedWorkload;
    public int workLoadLeft;
    private Thread currThread;
    private boolean initial = true;

    public WorkStation(String station, String conveyor, String conveyorTwo, int workload, Conveyors system){
        this.workStation = station;
        this.assignedConveyorOne = conveyor;
        this.assignedConveyorTwo = conveyorTwo;
        this.assignedWorkload = workload;
        this.workLoadLeft = workload;
        this.conveyorSystem = system;
        this.conveyorLocks[0] = false;
        this.conveyorLocks[1] = false;
    }

    @Override
    public void run() {

        currThread = Thread.currentThread();
        ReentrantLock lock = new ReentrantLock();

        if(this.workLoadLeft == 0){stopThread(); return;}

        if(initial){
            System.out.println("\n% % % % % % % % ROUTING STATION " + workStation + " Coming Online - Initializing Conveyors % % % % % % % %\n");
            System.out.println("\t\t\tRouting Station " + workStation + ": Input conveyor set to conveyor number " + assignedConveyorOne + "\n");
            System.out.println("\t\t\tRouting Station " + workStation + ": Output conveyor set to conveyor number " + assignedConveyorTwo + "\n");
            System.out.println("* * Routing Station " + workStation + ": Workload set. Station " + workStation + " has a total of " + assignedWorkload + " package groups to move. * *\n");
            System.out.println("\n\t\t\t\t\t\tRouting Station " + workStation + ": Now Online\n");
            initial = false;
        }

        if(!(this.workLoadLeft <= 0)) {
            if (!conveyorSystem.isBeltTaken(assignedConveyorTwo)) {
                conveyorSystem.turnBeltOn(assignedConveyorTwo);
                System.out.println("\t\t\t\tStation " + workStation + " successfully acquired lock on: " + assignedConveyorTwo + "\n");
                conveyorLocks[0] = true;
            }

            if (!conveyorSystem.isBeltTaken(assignedConveyorOne)) {
                conveyorSystem.turnBeltOn(assignedConveyorOne);
                System.out.println("\t\t\t\tStation " + workStation + " successfully acquired lock on: " + assignedConveyorTwo + "\n");
                conveyorLocks[1] = true;
            }
        }

        if(!conveyorLocks[0] || !conveyorLocks[1]){
            if(conveyorLocks[0]){
                System.out.println("\t\t\tStation " + workStation + " cannot acquire all locks, releasing lock on: " + assignedConveyorOne + "\n");
                conveyorSystem.turnBeltOff(assignedConveyorOne);
            }
            if(conveyorLocks[1]) {
                System.out.println("\t\t\tStation " + workStation + " cannot acquire all locks, releasing lock on: " + assignedConveyorTwo + "\n");
                conveyorSystem.turnBeltOff(assignedConveyorTwo);
            }
            //stopThread();
        }
        else {
            if(!(this.workLoadLeft <= 0)){
                lock.lock();
                System.out.println("* * * * * * Routing Station " + workStation + ": * * * * CURRENTLY HARD AT WORK MOVING PACKAGES * * * * * *\n\n");
                System.out.println("\tRouting Station " + workStation + ": successfully moves package into station on input Conveyor " + assignedConveyorOne + "\n");
                this.workLoadLeft--;
                System.out.println("\tRouting Station " + workStation + ": successfully moves package out of station on output Conveyor " + assignedConveyorTwo + "\n");
                if (this.workLoadLeft == 1 && this.workLoadLeft > -1) {
                    System.out.println("\t\t\tRouting Station " + workStation + " has " + this.workLoadLeft + " package group left to move.\n");
                } else if(this.workLoadLeft > -1){
                    System.out.println("\t\t\t\tRouting Station " + workStation + " has " + this.workLoadLeft + " package groups left to move.\n");
                }
                conveyorSystem.turnBeltOff(assignedConveyorOne);
                conveyorSystem.turnBeltOff(assignedConveyorTwo);
                System.out.println();
                if (this.workLoadLeft == 0) {
                    System.out.println("\n* * Station " + workStation + " Workload successfully completed. * * Station: " + workStation + " releasing/unlocking belts and going offline. * *\n");
                }
                else {
                    System.out.println("\t\t\t\tStation " + workStation + " releasing lock on: " + assignedConveyorOne + "\n");
                    //System.out.println(conveyorSystem.isBeltTaken(assignedConveyorOne));
                    System.out.println("\t\t\t\tStation " + workStation + " releasing lock on: " + assignedConveyorTwo + "\n");
                    //System.out.println(conveyorSystem.isBeltTaken(assignedConveyorTwo));
                }
                lock.unlock();
                stopThread();
            }
            //conveyorSystem.turnBeltOff(assignedConveyorOne);
            //conveyorSystem.turnBeltOff(assignedConveyorTwo);
        }
    }

    public void stopThread(){
        currThread.interrupt();
    }
}

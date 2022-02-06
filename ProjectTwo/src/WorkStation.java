public class WorkStation implements Runnable{

    public String workStation;
    public String assignedConveyorOne;
    public String assignedConveyorTwo;
    public boolean[] conveyorLocks = new boolean[2];
    public int assignedWorkload;
    public int workLoadLeft;
    private Thread currThread;
    public Conveyors conveyorSystem;
    private boolean initial = true;

    public WorkStation(String station, String conveyor, String conveyorTwo, int workload){
        this.workStation = station;
        this.assignedConveyorOne = conveyor;
        this.assignedConveyorTwo = conveyorTwo;
        this.assignedWorkload = workload;
        this.workLoadLeft = workload;
        this.conveyorLocks[0] = false;
        this.conveyorLocks[1] = false;
    }

    @Override
    public void run() {
        currThread = Thread.currentThread();
        conveyorSystem = new Conveyors();

        System.out.println("\n\n% % % % % % %% % % % % % % % % % % ROUTING STATION " + workStation + " % % % % % % % % % % % % % % % % % %\n\n");


        if(initial){
            System.out.println("\n% % % % % % % % ROUTING STATION " + workStation + " Coming Online - Initializing Conveyors % % % % % % % %\n");
            System.out.println("Routing Station " + workStation + ": Input conveyor set to conveyor number " + assignedConveyorOne);
            System.out.println("Routing Station " + workStation + ": Output conveyor set to conveyor number " + assignedConveyorTwo);
            System.out.println("Routing Station " + workStation + ": Workload set. Station " + workStation + " has a total of " + assignedWorkload + " package groups to move.");
            System.out.println("Routing Station " + workStation + ": Now Online");
            initial = false;
        }

        if(!conveyorLocks[0] && !conveyorLocks[1]) {
            if (!conveyorSystem.isBeltTaken(assignedConveyorOne)) {
                conveyorSystem.turnBeltOn(assignedConveyorOne);
                System.out.println("Station " + workStation + " successfully acquired: " + assignedConveyorOne);
                conveyorLocks[0] = true;
            }

            if (!conveyorSystem.isBeltTaken(assignedConveyorTwo)) {
                conveyorSystem.turnBeltOn(assignedConveyorTwo);
                System.out.println("Station " + workStation + " successfully acquired: " + assignedConveyorTwo);
                conveyorLocks[1] = true;
            }
        }

        if(!conveyorLocks[0] || !conveyorLocks[1]){
            System.out.println("Station" + workStation + " cannot acquire locks turning any acquired belts.");
            if(conveyorLocks[0]){
                conveyorSystem.turnBeltOff(assignedConveyorOne);
            }
            if(conveyorLocks[1]) {
                conveyorSystem.turnBeltOff(assignedConveyorTwo);
            }
            stopThread();
        }
        else {
            while (workLoadLeft != 0) {
                System.out.println("Running " + workStation);
                workLoadLeft--;
            }
            stopThread();
            conveyorSystem.turnBeltOff(assignedConveyorOne);
            conveyorSystem.turnBeltOff(assignedConveyorTwo);
        }
    }

    public void stopThread(){
        System.out.println("Station " + workStation + " finished. Turned off belts: " + assignedConveyorOne + " " + assignedConveyorTwo);
        currThread.interrupt();
    }
}

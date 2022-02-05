public class WorkStation {

    public String workStation;
    public String assignedConveyorOne;
    public String assignedConveyorTwo;
    public int assignedWorkload;

    public WorkStation(String station, String conveyor, String conveyorTwo, int workload){
        this.workStation = station;
        this.assignedConveyorOne = conveyor;
        this.assignedConveyorTwo = conveyorTwo;
        this.assignedWorkload = workload;
    }
}

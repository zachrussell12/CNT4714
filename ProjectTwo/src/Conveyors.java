public class Conveyors {

    public String[] belt;
    public boolean[] beltOn;

    public Conveyors() {
        this.belt = new String[5];
        this.beltOn = new boolean[5];

        this.belt[0] = "C0";
        this.beltOn[0] = false;
        this.belt[1] = "C1";
        this.beltOn[1] = false;
        this.belt[2] = "C2";
        this.beltOn[2] = false;
        this.belt[3] = "C3";
        this.beltOn[3] = false;
        this.belt[4] = "C4";
        this.beltOn[4] = false;
    }

    public boolean isBeltTaken(String name){

        if (name.equals(this.belt[0])) {
            return this.beltOn[0];
        }else if (name.equals(this.belt[1])) {
            return this.beltOn[1];
        }
        else if (name.equals(this.belt[2])) {
            return this.beltOn[2];
        }
        else if (name.equals(this.belt[3])) {
            return this.beltOn[3];
        }
        else if (name.equals(this.belt[4])) {
            return this.beltOn[4];
        }

        return true;
    }

    public void turnBeltOn(String name){

        if (name.equals(this.belt[0])) {
            //System.out.println("Turning on belt C0");
            this.beltOn[0] = true;
        }else if (name.equals(this.belt[1])) {
            //System.out.println("Turning on belt C1");
            this.beltOn[1] = true;
        }
        else if (name.equals(this.belt[2])) {
            //System.out.println("Turning on belt C2");
            this.beltOn[2] = true;
        }
        else if (name.equals(this.belt[3])) {
            //System.out.println("Turning on belt C3");
            this.beltOn[3] = true;
        }
        else if (name.equals(this.belt[4])) {
            //System.out.println("Turning on belt C4");
            this.beltOn[4] = true;
        }
    }

    public void turnBeltOff(String name){

        if (name.equals(this.belt[0])) {
            //System.out.println("Turning on belt C0");
            this.beltOn[0] = false;
        }else if (name.equals(this.belt[1])) {
            //System.out.println("Turning on belt C1");
            this.beltOn[1] = false;
        }
        else if (name.equals(this.belt[2])) {
            //System.out.println("Turning on belt C2");
            this.beltOn[2] = false;
        }
        else if (name.equals(this.belt[3])) {
            //System.out.println("Turning on belt C3");
            this.beltOn[3] = false;
        }
        else if (name.equals(this.belt[4])) {
            //System.out.println("Turning on belt C4");
            this.beltOn[4] = false;
        }
    }
}

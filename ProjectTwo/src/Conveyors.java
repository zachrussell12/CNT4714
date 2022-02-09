public class Conveyors {

    public String[] belt;
    public boolean[] beltOn;

    public Conveyors() {
        this.belt = new String[10];
        this.beltOn = new boolean[10];

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
        this.belt[5] = "C5";
        this.beltOn[5] = false;
        this.belt[6] = "C6";
        this.beltOn[6] = false;
        this.belt[7] = "C7";
        this.beltOn[7] = false;
        this.belt[8] = "C8";
        this.beltOn[8] = false;
        this.belt[9] = "C9";
        this.beltOn[9] = false;
    }

    public boolean isBeltTaken(String name){

        if (name.equals(this.belt[0])) {
            //System.out.println("C0 " + this.beltOn[0]);
            return this.beltOn[0];
        }else if (name.equals(this.belt[1])) {
            //System.out.println("C1 " + this.beltOn[1]);
            return this.beltOn[1];
        }
        else if (name.equals(this.belt[2])) {
            //System.out.println("C2 " + this.beltOn[2]);
            return this.beltOn[2];
        }
        else if (name.equals(this.belt[3])) {
            //System.out.println("C3 " + this.beltOn[3]);
            return this.beltOn[3];
        }
        else if (name.equals(this.belt[4])) {
            //System.out.println("C4 " + this.beltOn[4]);
            return this.beltOn[4];
        }
        else if(name.equals(this.belt[5])) {
            //System.out.println("C5 " + this.beltOn[5]);
            return this.beltOn[5];
        }else if (name.equals(this.belt[6])) {
            //System.out.println("C6 " + this.beltOn[6]);
            return this.beltOn[6];
        }
        else if (name.equals(this.belt[7])) {
            //System.out.println("C7 " + this.beltOn[7]);
            return this.beltOn[7];
        }
        else if (name.equals(this.belt[8])) {
            //System.out.println("C8 " + this.beltOn[8]);
            return this.beltOn[8];
        }
        else if (name.equals(this.belt[9])) {
            //System.out.println("C9 " + this.beltOn[9]);
            return this.beltOn[9];
        }

        return true;
    }

    public void turnBeltOn(String name){

        if (name.equals(this.belt[0])) {
            //if(!this.beltOn[0]){System.out.println("\t\t\t\t\t\t\tTurning on belt C0");}
            this.beltOn[0] = true;
        }else if (name.equals(this.belt[1])) {
            //if(!this.beltOn[1]){System.out.println("\t\t\t\t\t\t\tTurning on belt C1");}
            this.beltOn[1] = true;
        }
        else if (name.equals(this.belt[2])) {
            ///if(!this.beltOn[2]){System.out.println("\t\t\t\t\t\t\tTurning on belt C2");}
            this.beltOn[2] = true;
        }
        else if (name.equals(this.belt[3])) {
            //if(!this.beltOn[3]){System.out.println("\t\t\t\t\t\t\tTurning on belt C3");}
            this.beltOn[3] = true;
        }
        else if (name.equals(this.belt[4])) {
            //if(!this.beltOn[4]){System.out.println("\t\t\t\t\t\t\tTurning on belt C4");}
            this.beltOn[4] = true;
        }
        else if(name.equals(this.belt[5])) {
            //if(!this.beltOn[5]){System.out.println("\t\t\t\t\t\t\tTurning on belt C5");}
            this.beltOn[5] = true;
        }else if (name.equals(this.belt[6])) {
            //if(!this.beltOn[6]){System.out.println("\t\t\t\t\t\t\tTurning on belt C6");}
            this.beltOn[6] = true;
        }
        else if (name.equals(this.belt[7])) {
            //if(!this.beltOn[7]){System.out.println("\t\t\t\t\t\t\tTurning on belt C7");}
            this.beltOn[7] = true;
        }
        else if (name.equals(this.belt[8])) {
            //if(!this.beltOn[8]){System.out.println("\t\t\t\t\t\t\tTurning on belt C8");}
            this.beltOn[8] = true;
        }
        else if (name.equals(this.belt[9])) {
            //if(!this.beltOn[9]){System.out.println("\t\t\t\t\t\t\tTurning on belt C9");}
            this.beltOn[9] = true;
        }
    }

    public void turnBeltOff(String name){

        if (name.equals(this.belt[0])) {
            //if(this.beltOn[0]){System.out.println("\t\t\t\t\t\t\tTurning off belt C0");}
            this.beltOn[0] = false;
        }else if (name.equals(this.belt[1])) {
            //if(this.beltOn[1]){System.out.println("\t\t\t\t\t\t\tTurning off belt C1");}
            this.beltOn[1] = false;
        }
        else if (name.equals(this.belt[2])) {
            //if(this.beltOn[2]){System.out.println("\t\t\t\t\t\t\tTurning off belt C2");}
            this.beltOn[2] = false;
        }
        else if (name.equals(this.belt[3])) {
            //if(this.beltOn[3]){System.out.println("\t\t\t\t\t\t\tTurning off belt C3");}
            this.beltOn[3] = false;
        }
        else if (name.equals(this.belt[4])) {
            //if(this.beltOn[4]){System.out.println("\t\t\t\t\t\t\tTurning off belt C4");}
            this.beltOn[4] = false;
        }
        else if(name.equals(this.belt[5])) {
            //if(this.beltOn[5]){System.out.println("\t\t\t\t\t\t\tTurning off belt C5");}
            this.beltOn[5] = false;
        }else if (name.equals(this.belt[6])) {
            //if(this.beltOn[6]){System.out.println("\t\t\t\t\t\t\tTurning off belt C6");}
            this.beltOn[6] = false;
        }
        else if (name.equals(this.belt[7])) {
            //if(this.beltOn[7]){System.out.println("\t\t\t\t\t\t\tTurning off belt C7");}
            this.beltOn[7] = false;
        }
        else if (name.equals(this.belt[8])) {
            //if(this.beltOn[8]){System.out.println("\t\t\t\t\t\t\tTurning off belt C8");}
            this.beltOn[8] = false;
        }
        else if (name.equals(this.belt[9])) {
            //if(this.beltOn[9]){System.out.println("\t\t\t\t\t\t\tTurning off belt C9");}
            this.beltOn[9] = false;
        }
    }
}

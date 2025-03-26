package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Values {
    private static int level = 0;
    private static boolean scoreMode = true; //t=algae
    //true is right, false is left
    private static boolean leftOrRight= true;
    
    public Values(){
        //mothing herree
    }
    public static void setLevel(int i){
        level = i;
    }
    public static void setScoreMode(boolean b){
        scoreMode = b;
    }


    public static void increaseLevel(){
        level++;
    }
    public static void decreaseLevel(){
        level--;
    }

    public static void changeScoreMode(){
        scoreMode = !scoreMode;
    }
    public static void setLeftOrRight(boolean b){
        leftOrRight = b;
    }


    public static double getLevel(){
        if (level == 1){ //works
            return -1;
        }
        else if (level == 2){ //works
            return -12;
        }
        else if (level == 3){ //needs to be tested 
            return -21;   
        }
        else if (level == 4){ //needs to be tested 
            return -32;   
        }
        else{
            return 0;
        }
        

    }
    

    public static double getLeftOrRight(){
        if(leftOrRight){
            return 30;
        }
        else{
            return -30;
        }
    }
    public static String getLorR(){ //for alignment since Dylan heard it is centered to the apriltag but needs to center to the reef
        if(leftOrRight){
            return "Left";
        }
        else{
            return "Right";
        }
    }
    public static String getScoreMode(){
        if(scoreMode){
            return "Algae";
        }
        else{
            return "Coral";  
    }

}
}

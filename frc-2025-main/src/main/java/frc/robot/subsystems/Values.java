package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Values {
    private static int coralLevel = 0;
    private static boolean scoreMode = true; //t=algae
    //true is right, false is left
    private static int algaeLevel = 0;
    private static boolean leftOrRight= true;
    
    public Values(){
        //mothing herree
    }
    public static void coralSetLevel(int i){
        coralLevel = i;
    }

    
    public static void setScoreMode(boolean b){
        scoreMode = b;
    }


    public static void coralIncreaseLevel(){
        coralLevel++;
    }
    public static void coralDecreaseLevel(){
        coralLevel--;
    }

    public static void algaeIncreaseLevel(){
        algaeLevel++;
    }
    public static void algaeDecreaseLevel(){
        algaeLevel--;
    }

    public static void changeScoreMode(){
        scoreMode = !scoreMode;
    }
    public static void setLeftOrRight(boolean b){
        leftOrRight = b;
    }


    public static double coralSetLevel(){
        if (coralLevel == 1){ //works
            return 0;
        }
        else if (coralLevel == 2){ //works
            return -7;
        }
        else if (coralLevel == 3){ //needs to be tested 
            return -18;   
        }
        else if (coralLevel == 4){ //needs to be tested 
            return -26; 
        }
        else{
            return 0;
        }
        

    }

    
    public static double algaeSetLevel(){
        if (algaeLevel == 1){ //works
            return -5;
        }
        else if (algaeLevel == 2){ //works
            return -15;
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


    public static double getAlgaeLevel(){
        return algaeLevel;
    }

    public static double getCoralLevel(){
        return coralLevel;
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

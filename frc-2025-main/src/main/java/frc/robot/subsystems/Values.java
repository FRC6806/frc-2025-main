package frc.robot.subsystems;

public class Values {
    private static int level = 0;
    private static boolean scoreMode = true; //t=algae
    //true is right, false is left
    private static boolean leftOrRight = true;
    
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
        level = level % 4;
    }
    public static void decreaseLevel(){
        if(level == 0){
            level = 3;
        }
        else{
        level--;
        }
    }

    public static void changeScoreMode(){
        scoreMode = !scoreMode;
    }
    public static void setLeftOrRight(boolean b){
        leftOrRight = b;
    }


    public static int getLevel(){
        return level +1;
    }

    public static String getLeftOrRight(){
        if(leftOrRight){
            return "right";
        }
        else{
            return "left";
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

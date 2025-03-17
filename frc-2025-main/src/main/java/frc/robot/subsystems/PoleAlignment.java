package frc.robot.subsystems;

public class PoleAlignment {
    private double pitch;
    private double yaw;
    public PoleAlignment(VisionSystem vision){
        pitch = vision.getPitch();
        yaw = vision.getYaw();
    }
    
}

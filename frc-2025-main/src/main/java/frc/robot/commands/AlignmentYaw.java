// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// for cailtin mon, i wrote notes on things to talk to team and dif about 
package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.VisionSystem;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Values;

public class AlignmentYaw extends Command {    
    private CommandSwerveDrivetrain s_Swerve;    
    private VisionSystem s_Vision;
    private boolean run;
    private double pitchChange;
    private double yawChange;
    private double rotChange;
    private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();
    private double TargetYaw; // this is set later


    public AlignmentYaw(CommandSwerveDrivetrain s_Swerve,VisionSystem s_Vision, double TargetYaw) {
        this.s_Vision = s_Vision;
        this.s_Swerve = s_Swerve;
        addRequirements(s_Vision);
        addRequirements(s_Swerve);
        this.TargetYaw = Values.getLeftOrRight(); //30 for left -30 for right 0 for center
    }




public void intitialize(){

    run = true;
}




public void end(boolean interupted){
  s_Swerve.setControl(
    drive.withVelocityX(0)
       .withVelocityY(0)
       .withRotationalRate(0)
 );


}




public boolean isFinished(){


    // if(! s_Vision.HasTarget()){
    //     return true;
    // } 


    if ( Math.abs(s_Vision.getYaw() - TargetYaw) <= 3)
    {
      return true;
    }else {
        return false;
   }
   

   
}






    @Override
    public void execute() {

        if(s_Vision.HasTarget() ){
            TargetYaw = Values.getLeftOrRight();
            yawChange = s_Vision.getYaw() -TargetYaw;
            yawChange /= -30;
            // if (yawChange <= 0.2 && yawChange > 0){
            //     yawChange = 0.2;
            // }
            // else if (yawChange >= -0.2 && yawChange < 0){
            //     yawChange = -0.2;
            // }
        }

            SmartDashboard.putNumber("pitchChange", pitchChange);
            SmartDashboard.putNumber("yawChange", yawChange);
            SmartDashboard.putNumber("rotChange", rotChange);
            SmartDashboard.putNumber("targetYawButActually", TargetYaw);

            if(isFinished() == false){
            s_Swerve.setControl(
              
             drive.withVelocityX(0) //pitchchange for front and back 0 for not that BACK W/ NOTE FROM B4, MAKE SUPER SLOW 
                .withVelocityY(yawChange) 
                .withRotationalRate(0)
            ); }
 
        }




        public void periodic(){
         




        }
       
        }
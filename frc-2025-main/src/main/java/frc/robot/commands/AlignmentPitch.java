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


public class AlignmentPitch extends Command {    
    private CommandSwerveDrivetrain s_Swerve;    
    private VisionSystem s_Vision;
    private boolean run;
    private double pitchChange;
    private double yawChange;
    private double rotChange;
    private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();

    private double TargetPitch = 20;  
    // private boolean end;




    public AlignmentPitch(CommandSwerveDrivetrain s_Swerve,VisionSystem s_Vision) {
        this.s_Vision = s_Vision;
        this.s_Swerve = s_Swerve;
        addRequirements(s_Vision);
        addRequirements(s_Swerve);
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


    if(! s_Vision.HasTarget()){
        return true;
    } 

    if ( Math.abs(s_Vision.getPitch() - TargetPitch) <= .5)
    {
      return true;
    }else {
      return false;
    }


    

   
}




public void setEnd(){
    // end = true;
}




    @Override
    public void execute() {
       


    

        if(s_Vision.HasTarget() ){
            pitchChange = s_Vision.getPitch() - TargetPitch;
            pitchChange /= -7.0;
        }


        SmartDashboard.putNumber("pitchChange", pitchChange);
            SmartDashboard.putNumber("yawChange", yawChange);
            SmartDashboard.putNumber("rotChange", rotChange);
            SmartDashboard.putNumber("THE pitch", s_Vision.getPitch());



            if(isFinished() == false){
            s_Swerve.setControl(
              
             drive.withVelocityX(pitchChange) //pitchchange for front and back 0 for not that BACK W/ NOTE FROM B4, MAKE SUPER SLOW 
                .withVelocityY(0) 
                .withRotationalRate(0)
            

            );
            // if (pitchChange > 0.0001 && pitchChange < 0.1) {
            //     s_Swerve.setControl(
              
            //  drive.withVelocityX(pitchChange) //pitchchange for front and back 0 for not that BACK W/ NOTE FROM B4, MAKE SUPER SLOW 
            //     .withVelocityY(0) 
            //     .withRotationalRate(0)
            

            // ); }
             }




 
        }




        public void periodic(){
         




        }
       
        }
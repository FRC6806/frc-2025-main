  // Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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








public class Alignment extends Command {    
    private CommandSwerveDrivetrain s_Swerve;    
    private VisionSystem s_Vision;
    private boolean run;
    private double pitchChange;
    private double yawChange;
    private double rotChange;
    private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();
    // private boolean end;




    public Alignment(CommandSwerveDrivetrain s_Swerve,VisionSystem s_Vision) {
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
    if ( Math.abs(s_Vision.getYaw()) <= 5)
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
       




        //if( Math.abs(s_Vision.getYaw()) > 2){
            yawChange = s_Vision.getYaw();
            yawChange /= -30.0;
        //}
        //if( Math.abs(s_Vision.getPitch()) > 2){
            pitchChange = s_Vision.getPitch();
            pitchChange /= -23.0;
        //}


        SmartDashboard.putNumber("pitchChange", pitchChange);
            SmartDashboard.putNumber("yawChange", yawChange);
            SmartDashboard.putNumber("rotChange", rotChange);




            if(isFinished() == false){
            s_Swerve.setControl(
              drive.withVelocityX(pitchChange)
                 .withVelocityY(yawChange)
                 .withRotationalRate(0)
            ); }




 
        }




        public void periodic(){
         




        }
       
        }












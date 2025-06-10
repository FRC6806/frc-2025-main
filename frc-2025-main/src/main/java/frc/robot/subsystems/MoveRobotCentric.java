package frc.robot.subsystems;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Command;

public class MoveRobotCentric {

    private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric();

    public void moveForward(CommandSwerveDrivetrain swerve) {
        swerve.setControl(
             drive.withVelocityX(-10) //pitchchange for front and back 0 for not that BACK W/ NOTE FROM B4, MAKE SUPER SLOW 
                .withVelocityY(0) 
                .withRotationalRate(0)
            

            );

    }
    public void stop(CommandSwerveDrivetrain swerve) {
        swerve.setControl(
            drive.withVelocityX(0) //pitchchange for front and back 0 for not that BACK W/ NOTE FROM B4, MAKE SUPER SLOW 
               .withVelocityY(0) 
               .withRotationalRate(0)
           

           );

    }
}

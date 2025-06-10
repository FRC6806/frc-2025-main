package frc.robot.subsystems;
/* 
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.*;
import com.ctre.phoenix6.configs.Slot0Configs;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.ctre.phoenix6.signals.ControlModeValue;
import edu.wpi.first.wpilibj2.command.Command;
*/

import java.util.concurrent.TimeUnit;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.*;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class Elevator extends SubsystemBase {
    
    private TalonFX rightMotor; 
    private TalonFX leftMotor; 
    
    // create a position closed-loop request, voltage output, slot 0 configs
    final PositionVoltage m_request = new PositionVoltage(0).withSlot(0);


    public Elevator(int canID, int canID2){
        TalonFX rM = new TalonFX(canID);
        TalonFX lM = new TalonFX(canID2);
        rightMotor = rM;
        leftMotor = lM;
        leftMotor.setControl(new Follower(rightMotor.getDeviceID(), false)); 

        // TalonFX Configuration
        var talonFXConfigs = new TalonFXConfiguration();

        // Set slot 0 gains
        var slot0Configs = talonFXConfigs.Slot0;
        slot0Configs.kG = -0.65; //-0.65
        slot0Configs.kS = -0.5; 
        slot0Configs.GravityType = GravityTypeValue.Elevator_Static;
        // slot0Configs.kV = 0.12; 
        // slot0Configs.kA = 0.01;
        slot0Configs.kV = 0.6; 
        slot0Configs.kA = 0.8;  
        slot0Configs.kP = 0.75; 
        slot0Configs.kI = 0.4; 
        slot0Configs.kD = 0.1;
        

        // Set Motion Magic Expo settings
        var motionMagicConfigs = talonFXConfigs.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = 0; 
        motionMagicConfigs.MotionMagicExpo_kV = 0.6; 
        motionMagicConfigs.MotionMagicExpo_kA = 0.8; 

        // Apply configurations to the motor
        rightMotor.getConfigurator().apply(talonFXConfigs);
    }

    public void setPose(double Pose) {
        rightMotor.setControl(m_request.withPosition(Pose - 1));
        try {
            TimeUnit.MILLISECONDS.sleep(800);
            m_request.Velocity = .5;
            m_request.FeedForward = .5;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            System.out.print("Error");
        }
        rightMotor.setControl(m_request.withPosition(Pose));
        m_request.Velocity = 0.6;
        m_request.FeedForward = 0.8;
    }
    public void startPose() {
        // set target position to 10 rotations
        rightMotor.setControl(m_request.withPosition(-.5));
        try {
            TimeUnit.MILLISECONDS.sleep(800);
            m_request.Velocity = .5;
            m_request.FeedForward = .5;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            System.out.print("Error");
        }
        rightMotor.setControl(m_request.withPosition(0));
        m_request.Velocity = 0.6;
        m_request.FeedForward = 0.8;
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            System.out.print("Error");
        }
        rightMotor.set(0);
    }
    public void setElevatorspeed(double speed){
            leftMotor.set(speed);
            SmartDashboard.putNumber("elevator pos",leftMotor.getPosition().getValueAsDouble());
    }

    public void stopElevator() {
        rightMotor.set(0);
    }

    public Double getSpeed() {
        return rightMotor.get();
    }


}

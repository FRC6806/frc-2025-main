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

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.*;
import com.ctre.phoenix6.hardware.TalonFX;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralIntake extends SubsystemBase {
    private TalonFX rolls; 
    private TalonFX wrist;
    private TalonFX algae;
    private final double highPosition = 48;
    private final double middlePosition = 24;
    private final double lowPosition = 12;
    private final double playerStation = 10;
    private final double startPosition = 0;
    private final double deadband = 0.1;
   // private LaserCan lasercan = new LaserCan(62);

    private final CANBus canbus;
    final PositionVoltage m_request = new PositionVoltage(0).withSlot(0);


    public CoralIntake(int canID1, int canID2, int canID3, CANBus cb){
        canbus = cb;
        TalonFX r = new TalonFX(canID1, canbus);
        TalonFX w = new TalonFX(canID2, canbus);
        TalonFX a = new TalonFX(canID3, canbus);
        rolls = r;
        wrist = w;
        algae = a;
        var talonFXConfigs = new TalonFXConfiguration();

        // Set slot 0 gains
        var slot0Configs = talonFXConfigs.Slot0;
        slot0Configs.kS = 0.00; //.25
        slot0Configs.kV = 0.12; //.12 
        slot0Configs.kA = 0.001; 
        slot0Configs.kP = 4.8; 
        slot0Configs.kI = 0; 
        slot0Configs.kD = 0.1;

        // Set Motion Magic Expo settings
        var motionMagicConfigs = talonFXConfigs.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = 4000; 
        motionMagicConfigs.MotionMagicExpo_kV = 0.05; //.12
        motionMagicConfigs.MotionMagicExpo_kA = 0.02; //.1

        // Apply configurations to the motor
       wrist.getConfigurator().apply(talonFXConfigs);
       
    //    try{
         
    //     lasercan.setRangingMode(LaserCan.RangingMode.SHORT);
    //     lasercan.setRegionOfInterest(new LaserCan.RegionOfInterest(8, 8, 16, 16));
    //     lasercan.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
        
    //    } catch(ConfigurationFailedException e) {
    //     System.out.println("Configuration Failed!" + e);
    //    }
           
    }

    
    // public double getMeasurement(){
    //     LaserCan.Measurement measurement = lasercan.getMeasurement();
    //     if(measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT){
    //      return measurement.distance_mm;
    //     }
    //     else{
    //         return -1;
    //     }
    // }
        

    public void CoralIntakeSpeed(double speed){
       rolls.set(speed);
    }

    public void startWrist(){
        wrist.setControl(m_request.withPosition(startPosition));
    }
    public void wristpose(double pose){
        wrist.setControl(m_request.withPosition(pose));
    }
    public void wristspeed(double speed){
        wrist.set(speed);
        SmartDashboard.putNumber("wrist pos", wrist.getPosition().getValueAsDouble());
    }

    public void playerStation(){
        wrist.setControl(m_request.withPosition(playerStation));
    }

    public void lowReef(){
        wrist.setControl(m_request.withPosition(lowPosition));
    }

    public void midReef(){
        wrist.setControl(m_request.withPosition(middlePosition));
    }

    public void highReef(){
        wrist.setControl(m_request.withPosition(highPosition));
    }

    // public void laserPeriod(){
    //     double meters = getMeasurement();
    //     if(meters > 0.0635 && meters < .1524) {
    //         rolls.set(0);
    //     }
    // }
    
}


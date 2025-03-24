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
import com.ctre.phoenix6.signals.GravityTypeValue;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralIntake extends SubsystemBase {
    private TalonFX rolls; 
    private TalonFX wrist;
    private TalonFX algae;
    public static final double ALGAE_SHOOT = 94;
    public static final double PLAYER_STATION = 93;
    public static final double MOVE_ELEVATOR = 30;
    public static final double CORAL_SCORE = 40;
    public static final double REEF_ANGLE = 77;
    public static final double BACK_IN_ROBOT = 6;
    public static final double startPosition = 0;
    public static final double deadband = 0.1;
    
   // private LaserCan lasercan = new LaserCan(62);

    private final CANBus canbus;
    final PositionVoltage m_request = new PositionVoltage(0).withSlot(0);


    public CoralIntake(int canID1, int canID2, int canID3){
        canbus = new CANBus("elevator");
        TalonFX r = new TalonFX(canID1, canbus);
        TalonFX w = new TalonFX(canID2, canbus);
        TalonFX a = new TalonFX(canID3, canbus);
        rolls = r;
        wrist = w;
        algae = a;
        var talonFXConfigs = new TalonFXConfiguration();

        // Set slot 0 gains
        var slot0Configs = talonFXConfigs.Slot0;
        //slot0Configs.GravityType = GravityTypeValue.Arm_Cosine; needs an encoder to work and set variables
        slot0Configs.kG = 0.5; //.3
        slot0Configs.kS = 0.6; //.00
        slot0Configs.kV = 0.3; // 0.01
        slot0Configs.kA = 0.3; //0.1
        slot0Configs.kP = 0.75; //4.8
        slot0Configs.kI = 0.2; //00
        slot0Configs.kD = 0.1;

        // Set Motion Magic Expo settings
        var motionMagicConfigs = talonFXConfigs.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = 0; 
        motionMagicConfigs.MotionMagicExpo_kV = 0.5; //.12
        motionMagicConfigs.MotionMagicExpo_kA = 0.6; //.1

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
    public void AlgaeIntakeSpeed(double speed){
        algae.set(speed);
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
    public void outOfWay(){
        wrist.setControl(m_request.withPosition(MOVE_ELEVATOR));
    }
    public void getWrist() {
        SmartDashboard.putNumber("wristpose", wrist.getPosition().getValueAsDouble());

    }
}


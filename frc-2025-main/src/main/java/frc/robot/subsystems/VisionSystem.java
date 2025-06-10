package frc.robot.subsystems;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import java.io.IOException;
import java.lang.annotation.Target;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.SimCameraProperties;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;

public class VisionSystem extends SubsystemBase {
  PhotonCamera camera;
  //private final PhotonCamera camera = new PhotonCamera("photonvision");
  //private final VisionSystemSim visionSim = new VisionSystemSim("main");
  //private final Pose2dSupplier getSimPose;
  //private final PhotonPipelineResult result= camera.getLatestResult();
  //@FunctionalInterface
  private LaserCan lasercan = new LaserCan(0);
  private LaserCan lasercan2 = new LaserCan(2);
  public interface Pose2dSupplier {
    Pose2d getPose2d();
  }

     public VisionSystem(String cameraName){
      camera = new PhotonCamera(cameraName);
      try{
         
        lasercan.setRangingMode(LaserCan.RangingMode.SHORT);
        lasercan.setRegionOfInterest(new LaserCan.RegionOfInterest(8, 8, 16, 16));
        lasercan.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
        
       } catch(ConfigurationFailedException e) {
        System.out.println("Configuration Failed!" + e);
       }

       try{
         
        lasercan2.setRangingMode(LaserCan.RangingMode.SHORT);
        lasercan2.setRegionOfInterest(new LaserCan.RegionOfInterest(8, 8, 16, 16));
        lasercan2.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
        
       } catch(ConfigurationFailedException e) {
        System.out.println("Configuration Failed!" + e);
       }
    }
    
    public void start(){
      var result = camera.getLatestResult();
        PhotonTrackedTarget target = result.getBestTarget();
      if(result.hasTargets()){
    
        double yaw = target.getYaw();
        SmartDashboard.putNumber("Yaw", yaw);
        double pitch = target.getPitch();
        SmartDashboard.putNumber("Pitch", pitch);
        double area = target.getArea();
        SmartDashboard.putNumber("Area", area);
        double skew = target.getSkew();
        SmartDashboard.putNumber("Skew", skew);
        
    }
  }

public double getMeasurement(){
  LaserCan.Measurement measurement = lasercan.getMeasurement();
  if(measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT){
    return measurement.distance_mm;
  }
 else{
      return -1;
  }
}

public double getMeasurement2(){
  LaserCan.Measurement measurement = lasercan2.getMeasurement();
  if(measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT){
   return measurement.distance_mm;
  }
  else{
      return -1;
  }
}
public double skewAngle(){
  double width = 203.2; //distance between LASERCANS!!!!
  return Math.asin((getMeasurement()-getMeasurement2())/width);
}
  public boolean HasTarget(){
    var result = camera.getLatestResult();
    return result.hasTargets(); 
  }
   public double getPitch(){
    var result = camera.getLatestResult();
    PhotonTrackedTarget target = result.getBestTarget();
    if(result.hasTargets()){
      SmartDashboard.putNumber("Pitch",target.getPitch());
      // System.out.println(target.getPitch());
      return target.getPitch();
    }
    else{
      return 0;} // Bye GUYS BAD LUCKKKKKKKKKKKKKKKKKKKKK - nicole :(((((())))))
    }
    
    public double getSkew(){
      var result = camera.getLatestResult();
      PhotonTrackedTarget target = result.getBestTarget();
      if(result.hasTargets() == true){
        SmartDashboard.putNumber("z angle", target.getBestCameraToTarget().getZ());
        return target.getSkew();
      }
      else{
        return 0;}
      }

    public double getYaw(){
      var result = camera.getLatestResult();
      PhotonTrackedTarget target = result.getBestTarget();
      if(result.hasTargets() == true){
        return target.getYaw();
      }
      else {
        return 0;
      }
      
    } 
    public double getPitchDistance(double cameraHeight, double targetHeight, double cameraAngle, double targetAngle){
      //cameraAngle is angle of camera with ground and targetAngle is the pitch
      cameraAngle = Math.toRadians(cameraAngle);
      targetAngle = Math.toRadians(targetAngle);
      double distance = PhotonUtils.calculateDistanceToTargetMeters(cameraHeight,targetHeight,cameraAngle,targetAngle);
      return distance;
    }
    public double getYawDistance(double cameraWidth, double targetWidth, double cameraYaw, double targetYaw){
      cameraYaw = Math.toRadians(cameraYaw);
      targetYaw = Math.toRadians(targetYaw);
      double distance = PhotonUtils.calculateDistanceToTargetMeters(cameraWidth,targetWidth,cameraYaw,targetYaw);
      return distance;
    }

    public PhotonPipelineResult getResult(){
      return camera.getLatestResult();
    }

    // public Transform3d get3D(){
    // var result = camera.get();
    //   try {
    //     PhotonTrackedTarget target = result.getBestTarget();
    //     return target.getBestCameraToTarget();
    //   } catch(NullPointerException e){
    //     System.out.println("Bruh");
    //   }
    // }

    //Transform2d pose = target.getCameraToTarget();
    //List<TargetCorner> corners = target.getCorners();

    @Override
    public void periodic(){
        // SmartDashboard.putNumber("X3D", get3D().getX());
        // SmartDashboard.putNumber("Y3D", get3D().getY());
        // SmartDashboard.putNumber("Z3D", get3D().getZ());

    }
}
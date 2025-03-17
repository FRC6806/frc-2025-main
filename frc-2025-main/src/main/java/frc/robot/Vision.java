package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {

    private static final int EXPECTED_POSE_ARRAY_LENGTH = 6;

    public static class PoseEstimate {
        public Pose2d pose;
        public double timestampSeconds;

        public PoseEstimate(Pose2d pose, double timestampSeconds) {
            this.pose = pose;
            this.timestampSeconds = timestampSeconds;
        }
    }

    private static NetworkTable getPhotonVisionTable(String cameraName) {
        return NetworkTableInstance.getDefault().getTable("photonvision").getSubTable(cameraName);
    }

    private static NetworkTableEntry getPhotonVisionEntry(String cameraName, String entryName) {
        return getPhotonVisionTable(cameraName).getEntry(entryName);
    }

    public static PoseEstimate getBotPoseEstimate(String cameraName) {
        double[] poseArray = getPhotonVisionEntry(cameraName, "estimatedPose").getDoubleArray(new double[0]);
        double timestamp = getPhotonVisionEntry(cameraName, "latency").getDouble(0) / 1000.0;

        if (poseArray.length != EXPECTED_POSE_ARRAY_LENGTH) {
            return null; // Return null if no data
        }

        try {
            Pose2d pose = new Pose2d(
                    new Translation2d(poseArray[0], poseArray[1]),
                    new Rotation2d(Units.degreesToRadians(poseArray[5]))
            );
            return new PoseEstimate(pose, timestamp);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("PhotonVision returned an invalid pose array for camera: " + cameraName);
            return null; // Return null on exception
        }
    }

    public static PoseEstimate getBotPoseEstimate_wpiBlue(String cameraName) {
        return getBotPoseEstimate(cameraName); // Directly return the result (can be null)
    }

    public static PoseEstimate getBotPoseEstimate_wpiRed(String cameraName) {
        PoseEstimate estimate = getBotPoseEstimate(cameraName);
        if (estimate != null) {
            Pose2d rawPose = estimate.pose;
            Pose2d flippedPose = new Pose2d(rawPose.getX(), -rawPose.getY(), rawPose.getRotation().times(-1));
            return new PoseEstimate(flippedPose, estimate.timestampSeconds);
        } else {
            return null;
        }
        
    }
}
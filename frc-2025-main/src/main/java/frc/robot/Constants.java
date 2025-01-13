package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public class Constants {
    public static final class VisionAlignment {
        public static final double kPX = 2.0;
        public static final double kIX = 0.0;
        public static final double kDX = 0.0;

        public static final double kPY = 2.0;
        public static final double kIY = 0.0;
        public static final double kDY = 0.0;

        public static final double kPTheta = 3.0;
        public static final double kITheta = 0.0;
        public static final double kDTheta = 0.0;

        public static final double X_TOLERANCE = 0.05; // Meters
        public static final double Y_TOLERANCE = 0.05; // Meters
        public static final double THETA_TOLERANCE = Math.toRadians(2); // Radians

        public static final double MAX_X_SPEED = 3.0; // Meters per second
        public static final double MAX_Y_SPEED = 3.0; // Meters per second
        public static final double MAX_THETA_SPEED = 3.0; // Radians per second

        public static final double MAX_ACCELERATION = 3;
        public static final double MAX_ANGULAR_VELOCITY = Math.PI;
        public static final double MAX_ANGULAR_ACCELERATION = Math.PI;

        public static final double CAMERA_X_OFFSET = 0.1; // Meters from robot center
        public static final double CAMERA_Y_OFFSET = 0.0; // Meters from robot center
        public static final double CAMERA_ANGLE_OFFSET = 0.0; //Radians
    }
    public class Transforms {
    public static Transform3d robotToCamera =
        new Transform3d(
            new Translation3d(0, 0, 0.5), // Centered on the robot, 0.5m up
            new Rotation3d(0, Math.toRadians(-15), 0)); // Pitched 15 deg up
  }
}

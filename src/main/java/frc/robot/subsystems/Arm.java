package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    private final double TOTAL_GEAR_RATIO = 183.33;
    private final int CURRENT_LIMIT = 30; 
    private final CANSparkMax mMotor = new CANSparkMax(Constants.ARM, CANSparkMax.MotorType.kBrushless);
    private final RelativeEncoder mEncoder = mMotor.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, Constants.MAX_COUNTS_PER_REV);
    private SparkMaxPIDController mPID = mMotor.getPIDController();
    

    private final DigitalInput mRearLimit = new DigitalInput(Constants.ARM_REAR_LIMIT);
    private final DigitalInput mForwardLimit = new DigitalInput(Constants.ARM_FORWARD_LIMIT);
    
    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Arm Rear Limit", getRearLimit());
        SmartDashboard.putBoolean("Arm Forward Limit", getForwardLimit());
    }
    
    public Arm(){
        mMotor.restoreFactoryDefaults();
        mMotor.setIdleMode(IdleMode.kBrake);
        mMotor.setSmartCurrentLimit(CURRENT_LIMIT);
        mMotor.setInverted(false);
        mMotor.burnFlash();

        mEncoder.setPositionConversionFactor(TOTAL_GEAR_RATIO / 360.0);
        mPID.setFeedbackDevice(mEncoder);
        mPID.setP(0.25);
        mPID.setI(0.0);
        mPID.setD(0.0);
    }

    public void setPercentOutput(double output) {
        if (output > 1.0) {
            output = 1.0;
        } else if (output < -1.0) {
            output = -1.0;
        }

        mMotor.set(output);
    }

    public boolean getRearLimit() {
        return !mRearLimit.get();
    }

    public boolean getForwardLimit() {
        return !mForwardLimit.get();
    }

    public void setEncoderValueInDegrees(double positionInDegrees) {
        int positionInMAXUnits = (int) ((TOTAL_GEAR_RATIO * Constants.MAX_COUNTS_PER_REV) * (positionInDegrees / 360.0));
        mMotor.getEncoder().setPosition(positionInMAXUnits);
    }

    public void setAngleInDegrees(double positionInDegrees) {
        mPID.setReference(positionInDegrees, ControlType.kPosition);
    }

    public double getVelocity() {
        return mEncoder.getVelocity();
    }

    public double getEncoderDegrees() {
        return mEncoder.getPosition();
    }
}


// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.lang.Math;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Arm;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmPreset extends CommandBase {
  /** Creates a new ArmPreset. */
  private final Elevator mElevator;
  private final Arm mArm;
  private final int mArmPresetNumber;
  public ArmPreset(Elevator elevator, Arm arm, int armPresetNumber) {
    mElevator = elevator;
    mArm = arm;
    mArmPresetNumber = armPresetNumber;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double angle, length;
    switch(mArmPresetNumber){
      case 7:
      angle = 0.0; length = 0.0;
      break;
      case 9:
      angle = 90.0; length = 0.0;
      break;
      case 11:
      angle = 180.0; length = 0.0;
      break;
      default:
      return;
    }
    double[] Returned = new double[2];
    Returned = mArm.MakeArmMovesLegal(angle, length);

    mArm.HardSetPosition(Returned[0]);
    mElevator.HardSetPosition(Returned[1]);
     
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.cameraserver.CameraServer;

public class Robot extends TimedRobot {

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  Timer timer;

  DifferentialDrive robotDrive;

  PWMVictorSPX intake;
  MotorControllerGroup crane;

  XboxController xbox1;
  XboxController xbox2;



  @Override
  public void robotInit() {

    // Starts camera
    CameraServer.startAutomaticCapture();

    // Define the different controllers as seperate and distinct inputs
    xbox1 = new XboxController(Constants.xboxController1);
    xbox2 = new XboxController(Constants.xboxController2);

    //Defined the left side of the robot drive and collapses them into a single object
    MotorController m_frontLeft = new PWMVictorSPX(Constants.leftDriver1);
    MotorController m_rearLeft = new PWMVictorSPX(Constants.leftDriver2);
    MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_rearLeft);

    //Defined the right side of the robot drive and collapses them into a single object
    MotorController m_frontRight = new PWMVictorSPX(Constants.rightDriver1);
    MotorController m_rearRight = new PWMVictorSPX(Constants.rightDriver2);
    MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_rearRight);

    //Defines crane motor controllers and collapses them into one group
    MotorController m_crane1 = new PWMVictorSPX(Constants.mc_crane1);
    MotorController m_crane2 = new PWMVictorSPX(Constants.mc_crane2);
    crane = new MotorControllerGroup(m_crane1, m_crane2);

    //Defines intake motor
    intake = new PWMVictorSPX(Constants.mc_intake);

    //Defines drive setup
    robotDrive = new DifferentialDrive(m_left, m_right);
  }

  //Sets the intake off at the start, keep global
  double intakeCon = 0;

  @Override
  public void teleopPeriodic() {

    double speedCap = 1;  //Sets speed cap multiplier
    double craneCap = 0.5;  //Crane speed cap (keep low)

    //Sends controller axis information to the drive methods
    robotDrive.arcadeDrive(speedCap * xbox1.getRawAxis(Constants.rightSide), speedCap * xbox1.getRawAxis(Constants.leftUp));

    //Sets crane up for left trigger, down for right trigger
    if(xbox1.getLeftTriggerAxis() != 0.0){
      crane.set(craneCap * xbox1.getLeftTriggerAxis());
    }
    else if(xbox1.getRightTriggerAxis() != 0.0){
      crane.set(craneCap * -xbox1.getRightTriggerAxis());
    }
    

    if(xbox1.getAButton())
    {
      intake.set(1);
    }
    else if(xbox1.getBButton())
    {
      intake.set(-1);
    }
    else{
      intake.stopMotor();
    }

  }

  @Override
  public void autonomousInit() {
    // m_autonomousCommand = m_chooser.getSelected();

    //Idk what this does
    robotDrive.setSafetyEnabled(false);

    // Starts timer object
    timer = new Timer();
    timer.reset();
    timer.start();

    // Declaring variabled and arrays
    double[] fowardSpeed =  { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; // Foward
    double[] angle =        { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; // Angle
    double[] timeIntevals = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; // Time
    double[] in =           { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; // Intake
    double[] flipper =      { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; // Flipper

    //dont touch
    double autonTime;

    //Main for loop
    for(int i = 0; i < timeIntevals.length; i++){

      //Sets the current time to autonTime
      autonTime = timer.get();

      //Runs in between the time intervals
      while(timer.get() < autonTime + timeIntevals[i])
      {

        //Drives the robot
        robotDrive.arcadeDrive(angle[i], fowardSpeed[i]);

        //Controls the motors for the intake and crane
        crane.set(flipper[i]);
        intake.set(in[i]);
      }
    }

    crane.set(0);
    intake.set(0);
    robotDrive.arcadeDrive(0, 0);
  }

  @Override
  public void autonomousPeriodic() {
    /*
     * robotDrive.driveCartesian(0, -0.25, 0);
     * Timer.delay(1);
     * robotDrive.driveCartesian(0, 0, 0);
     * /*
     * /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

  }

}

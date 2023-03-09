// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.management.BadBinaryOpValueExpException;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.cameraserver.CameraServer;


/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  
  Timer timer;

  DifferentialDrive robotDrive;

  PWMVictorSPX crane, frontLeft, frontRight, backLeft, backRight, motor1, motor2;

  XboxController xbox1;
  XboxController xbox2;
   

  PWMVictorSPX shooterLeft;
  PWMVictorSPX shooterRight;

  PWMVictorSPX intake;

  Solenoid clawPH;

  @Override
  public void robotInit() {

    //Starts camera
    CameraServer.startAutomaticCapture();
    
    //Define the different controllers as seperate and distinct inputs
    xbox1 = new XboxController(Constants.xboxController1);
    xbox2 = new XboxController(Constants.xboxController2);
    
    //Define the motors to the robot as a thing on the robot
    frontLeft = new PWMVictorSPX(Constants.leftFrontMotor);
    frontRight = new PWMVictorSPX(Constants.rightFrontMotor);
    backLeft = new PWMVictorSPX(Constants.leftBackMotor);
    backRight = new PWMVictorSPX(Constants.rightBackMotor);
    frontRight.setInverted(true);
    backRight.setInverted(true);

  //Tells the robot that we are using a tank system
   MotorController m_frontLeft = new PWMVictorSPX(1);
   MotorController m_rearLeft = new PWMVictorSPX(2);
   MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_rearLeft);

   MotorController m_frontRight = new PWMVictorSPX(3);
   MotorController m_rearRight = new PWMVictorSPX(4);
   MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_rearRight);

   DifferentialDrive robotDrive = new DifferentialDrive(m_left, m_right);
  }


  boolean clawCon = true;

  @Override
  public void teleopPeriodic() {

    robotDrive.setSafetyEnabled(false);
    double speedCap = .7;
    robotDrive.tankDrive(-speedCap*xbox1.getRawAxis(1), speedCap*xbox1.getRawAxis(5));


  }

  @Override
  public void autonomousInit() {
    //m_autonomousCommand = m_chooser.getSelected();

    robotDrive.setSafetyEnabled(false);

    //Starts timer object
    timer = new Timer();
    timer.reset();
    timer.start();

    
    //Declaring variabled and arrays
    double[] xSpeed =       {0    , 0    , 0.5 , 0   , 0    , -0.5};
    double[] ySpeed =       {0    , 0    , 0   , 0   , 0    , 0};
    double[] zSpeed =       {0    , 0    , 0   , 0   , 0    , 0};
    double[] timeIntevals = {2    , 1    , 1   , 1   , 0    , 3};
    boolean[] lauraHuges =  {false,false ,false,true , true , true}; //Claw
    double[] vernonDouglas= {0    , -1   ,-0.5 , -0.5, 0    , 0};  //Crane
    double[] samPound =     {-1   , 0    , 0   , 0   , 0    , 0};    //wench
    double autonTime;
    
    /*
    //Declaring variabled and arrays
    double[] xSpeed =       {0    , 0    , 0.5 ,-0.2   , 0    , 0};
    double[] ySpeed =       {0    , 0    , 0   , 0   , 0    , 0};
    double[] zSpeed =       {0    , 0    , 0   , 0   , 0    , 0};
    double[] timeIntevals = {2    , 1    , 1   , 0.5   , 0    , 0};
    boolean[] lauraHuges =  {false,false ,false,true , true , true}; //Claw
    double[] vernonDouglas= {0    , -1   ,-0.8 , -0.8, 0    , 0};  //Crane
    double[] samPound =     {-1   , 0    , 0   , 0   , 0    , 0};    //wench
    double autonTime;
    
 
    //Main for loop
    for(int i = 0; i < 6; i++){

      //Sets the current time to autonTime
      autonTime = timer.get();

      //Runs in between the time intervals
      while(timer.get() < autonTime + timeIntevals[i]){

        //Drives the robot
        robotDrive.driveCartesian(xSpeed[i], ySpeed[i], zSpeed[i]);

        crane.set(samPound[i]);
        clawPH.set(lauraHuges[i]);

        motor1.set(vernonDouglas[i]);
        motor2.set(vernonDouglas[i]);
      }
    }

    clawPH.set(false);
    crane.set(0);
    robotDrive.driveCartesian(0, 0, 0);
     */
  }

  @Override
  public void autonomousPeriodic() {
    /*
    robotDrive.driveCartesian(0, -0.25, 0);
    Timer.delay(1);
    robotDrive.driveCartesian(0, 0, 0);
    /*
    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

  }

}

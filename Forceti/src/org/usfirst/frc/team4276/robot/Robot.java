
package org.usfirst.frc.team4276.robot;

import edu.wpi.first.wpilibj.SampleRobot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.I2C.Port;

/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {

	static ADIS16448_IMU imu;
	
	mecanumNavigation robotLocation;
	mecanumDrive driveSystem;
	Climber climbingSystem;
	gearCollection gearMechanism;
	BallShooter Shooter;
	BallCollector ballCollectingMechanism;
	
	static Timer systemTimer;
	static Joystick XBoxController;
	static Joystick logitechJoystick;
 
	public Robot() {
		imu = new ADIS16448_IMU();

		robotLocation = new mecanumNavigation(0,1,2,3,4,5,6,7);//dio ports
		driveSystem = new mecanumDrive(0,1,2,3);//pwm ports
		climbingSystem = new Climber(8,13);//pwm port 8, dio port 13
		gearMechanism = new gearCollection(6,1,2,14,11,12);//pwm port 6, relay ports 1 & 2, dio ports 14, 11, 12
		Shooter = new BallShooter(4,5,10);//pwm ports 4 & 5, dio port 10
		ballCollectingMechanism = new BallCollector(7);//pwm port 7
		
		robotLocation.start();
		
		XBoxController = new Joystick(3);
		logitechJoystick = new Joystick(0);


		driveSystem = new mecanumDrive(0, 1, 2, 3);
	}

	/**
	 * Drive left & right motors for 2 seconds then stop
	 */
	public void autonomous() {
		

	}

	/**
	 * Runs the motors with arcade steering.
	 */
	public void operatorControl() {

		while (isOperatorControl() && isEnabled()) {
			driveSystem.Operatordrive();
			climbingSystem.performMainProcessing();
			gearMechanism.performMainProcessing();
			Shooter.performMainProcessing();
			ballCollectingMechanism.performMainProcessing();
			Timer.delay(.05);
		}
	}

	/**
	 * Runs during test mode
	 */
	public void test() {
		//driveSystem.YTest();
		//driveSystem.XTest();
		//driveSystem.TwistTest();

	}
}

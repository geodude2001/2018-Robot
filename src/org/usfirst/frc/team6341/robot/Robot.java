 package org.usfirst.frc.team6341.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	TalonSRX backRight = new TalonSRX(1); //frontleft //frontRight //backLeft // backRight
	TalonSRX backLeft = new TalonSRX(0);//frontRight
	TalonSRX frontRight = new TalonSRX(3);//backleft
	TalonSRX frontLeft = new TalonSRX(2);//backright
	//led driver
	Spark ledDriver = new Spark(1);
	
	PneumaticsControl P = new PneumaticsControl();
	JoystickCommands stick = new JoystickCommands();
	Joystick stick2 = new Joystick(0);
	
	
	private void power(double rl, double rr, double fl, double fr) 
	{
		fl = limit( fl );
		fr = limit( fr );
		rl = limit( rl );
		rr = limit( rr );
		//fl = -fl;
		//rl = -rl;
		

		backRight.set( ControlMode.PercentOutput, rr );
		frontRight.set( ControlMode.PercentOutput, fr );
		backLeft.set( ControlMode.PercentOutput, rl );
		frontLeft.set( ControlMode.PercentOutput, fl );
	}
	
	protected double limit(double value) {
		return Math.min(1, Math.max(value, -1));
	}
	
	//Drive functions
	

	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		
		CameraServer.getInstance().startAutomaticCapture();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}
	
	
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		System.out.println(stick.getRX());
		System.out.println(stick.getX());
		ledDriver.set(stick.getY());
	}
	@Override
	public void teleopPeriodic() 
	{
		//Attempt1();
		MecCallTwo();
	}
	
	public void MecCall() {
		double x = stick.getX();
		double y = stick.getY();
		double r = stick.getRX();
		double FrontLeftSpeed,FrontRightSpeed,RearLeftSpeed,RearRightSpeed;
		if(!stick.getSideButton())
		{
		    FrontLeftSpeed =  y ; // y + r - x |Proposed Fix Not Tested | x + y + r | OLD
		    FrontRightSpeed= -y ; //-y + r - x |Proposed Fix Not Tested | x - y + r | OLD 
		    RearLeftSpeed =   y ; // y + r + x |Proposed Fix Not Tested |-x + y + r | OLD
		    RearRightSpeed = -y ; //-y + r + x |Proposed Fix Not Tested |- x -y + r | OLD
            power( FrontLeftSpeed*0.5, FrontRightSpeed*0.5, RearLeftSpeed*0.5, RearRightSpeed*0.5 );
            System.out.println("FrontLeftSpeed:" + FrontLeftSpeed);
            System.out.println("FrontRightSpeed:"+ FrontRightSpeed);
            System.out.println(RearLeftSpeed);
            System.out.println(RearRightSpeed);
		}else if(stick.getSideButton()) {
			FrontLeftSpeed  = x ;
		    FrontRightSpeed = -x ;
		    RearLeftSpeed   = -x ;
		    RearRightSpeed  = x ;
		    
		    power( FrontLeftSpeed*0.5, FrontRightSpeed*0.5, RearLeftSpeed*0.5, RearRightSpeed*0.5 );
		}
	}
	public void MecCallTwo() {
		double x = stick.getX();
		double y = stick.getY();
		double r = stick.getRX();
		
		double frontLeftPwr, frontRightPwr, backLeftPwr, backRightPwr;
		if(!stick.getSideButton() && !stick.getB()) {
		backRightPwr  =  y - x + r;//OLD| y + r - x|NEW|y + r + x|//frontleft 
		backLeftPwr = -y - x + r;//OLD|-y + r - x|NEW|y - r - x|//frontRight
		frontRightPwr   =  y + x + r;//OLD| y + r + x|NEW|y + r - x|//backLeft 
		frontLeftPwr  = -y + x + r;//OLD|-y + r + x|NEW|y - r + x|//backRight
		ledDriver.set(stick.getY());
		power( backLeftPwr*0.5, backRightPwr*0.5, frontLeftPwr*0.5, frontRightPwr*0.5 );
	
		}else if(stick.getB()){
			backRightPwr =  -0.5;
			backLeftPwr = -0.5;
			frontRightPwr =   0.5;
			frontLeftPwr =  0.5;
			power( backLeftPwr, backRightPwr, frontLeftPwr, frontRightPwr );
		}else if(stick.getSideButton()) {
			backRightPwr = 0.5;
			backLeftPwr = 0.5;
			frontRightPwr = -0.5;
			frontLeftPwr = -0.5;
			power( backLeftPwr, backRightPwr, frontLeftPwr, frontRightPwr );
		}
	}
	


	
	
}

package org.usfirst.frc.team6341.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class PneumaticsControl {

	public PneumaticsControl() 
	{
		
	}
	
	Compressor c = new Compressor(12);
	
	Solenoid Jack = new Solenoid(2);
	
	Solenoid ReturnJack = new Solenoid(1);
	
	JoystickCommands stick = new JoystickCommands();
	
	public void enableCompressor()
	{
		if(stick.TwelvePressed())
		{

			c.setClosedLoopControl(true);
			System.out.println("Number 12:" + stick.TwelvePressed());
		}else if(!stick.TwelvePressed())
		{
			c.setClosedLoopControl(false);
			System.out.println("Number 12:" + stick.TwelvePressed());
		}
	}
	
	public void enableSolonoid() {
		if(stick.TriggerPressed())
		{
			Jack.set(true);
			Jack.set(false);
			System.out.println("trigger:" + stick.TriggerPressed());
		}
		if(stick.TenPressed())
		{
			ReturnJack.set(true);
			ReturnJack.set(false);
			System.out.println("Number10:" + stick.TenPressed());
			
		} 
		
	}
}

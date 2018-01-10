//DEMOBOT

package org.usfirst.frc.team888.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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

	Victor rearLeft = new Victor(7);
	Victor rearRight = new Victor(6);
	Victor frontLeft = new Victor(9);
	Victor frontRight = new Victor(8);

	Victor leftShooterWheel = new Victor(3);
	Victor shooterAngle = new Victor(4);
	Victor rightShooterWheel = new Victor(5);

	Victor lights = new Victor(2);

	Joystick leftStick = new Joystick(0);
	Joystick rightStick = new Joystick(1); 
	Joystick shooterStick = new Joystick(2);

	Compressor mainCompressor = new Compressor(1);
	DoubleSolenoid piston = new DoubleSolenoid(0, 1);

	Timer timer = new Timer();
	Timer shootTimer = new Timer();

	DigitalInput bannerSensor = new DigitalInput(9);

	boolean firstShot = true;




	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		mainCompressor.start();

		timer.reset();
		timer.start();
		shootTimer.reset();

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

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {


	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

		double leftStickValue = -leftStick.getRawAxis(1);
		double rightStickValue = rightStick.getRawAxis(1);
		double gamepadRightJoystick = shooterStick.getRawAxis(5);
		double gamepadLeftTrigger = shooterStick.getRawAxis(2);
		double gamepadRightTrigger = shooterStick.getRawAxis(3);
		double shooterSpeed = (1 - rightStick.getRawAxis(2)) + 0.1;
		double timeSinceStart = timer.get();

		shooterAngle.set(gamepadRightJoystick*0.5);

		if (!leftStick.getRawButton(1) && !rightStick.getRawButton(1)) {
			leftStickValue *= 0.7;
			rightStickValue *= 0.7;
		}

		rearLeft.set(leftStickValue);
		frontLeft.set(leftStickValue);
		rearRight.set(rightStickValue);
		frontRight.set(rightStickValue);


		leftShooterWheel.set(gamepadLeftTrigger);
		rightShooterWheel.set(-gamepadRightTrigger);


		if ((gamepadLeftTrigger > 0.8) && (gamepadRightTrigger > 0.8)) {
			lights.set(0);

			if (piston.get() == Value.kForward) {
				lights.set(1);
			} else {
				lights.set(0);
			}


			if (shooterStick.getRawButton(1)) {
				if (piston.get() == Value.kReverse) {
					piston.set(DoubleSolenoid.Value.kForward);
				} else {
					piston.set(DoubleSolenoid.Value.kReverse);
				}
			} else {
				piston.set(DoubleSolenoid.Value.kReverse);
			}
		} else {
			piston.set(DoubleSolenoid.Value.kReverse);
			lights.set(Math.abs(Math.sin(timeSinceStart/30*(180/Math.PI))));
		}
	}


	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}

}

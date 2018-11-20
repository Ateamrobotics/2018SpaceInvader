package org.usfirst.frc.team6544.robot.commands;

import org.usfirst.frc.team6544.robot.Robot;
import org.usfirst.frc.team6544.robot.subsystems.MainMast;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchSide extends Command {
	private double drive1Loop = 0;
	private boolean isfinished = false;
	private double scalingConstant = 0.3;
	private double loopcount = 0;
	private double loopcount1 = 0;
	private double loopcount2 = 0;
	private double driveangle1;
    public SwitchSide() {
    	
    	 requires(Robot.drivesystem);
         requires(Robot.liftsystem);
         requires(Robot.claw);
         requires(Robot.mast1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.mast1.autoTimeOut();
    	String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.length() == 3) {
			if(gameData.charAt(0)=='R') {
				Robot.drivesystem.reset();
				while(loopcount < 240) {
		    		double driveAngle = Robot.drivesystem.getAngle();
		    		Robot.drivesystem.arcadeDrive(-0.7, -driveAngle * scalingConstant);
		    		Timer.delay(0.01);
		    		loopcount++;
		    	}
				 while (driveangle1 >= -80){ 
			        	driveangle1 = Robot.drivesystem.getAngle();
			        	Robot.drivesystem.arcadeDrive(0.55, -0.55); 
			      	//chassis.tankDrive(.8,.8); //Sets the speed of the robot for each wheel and makes them turn 
			      	 Timer.delay(0.001);                      //creates a delay
			     	 //Increments the loop counter by 1
			      }
				 Timer.delay(0.1);
				 Robot.drivesystem.reset();
				 Timer.delay(.3);
				
				 Timer.delay(.3);
			    while(MainMast.getTopLimit() == false) {
			    	Robot.mast1.deactivateClamp();
			    	Robot.mast1.goUp();
			    }
			    Robot.drivesystem.reset();
				 while(loopcount2 < 75) {
			    		double driveAngle = Robot.drivesystem.getAngle();
			    		Robot.drivesystem.arcadeDrive(-0.6, -driveAngle * scalingConstant);
			    		Timer.delay(0.01);
			    		loopcount2++;
			    	}
			    Timer.delay(1);
			    Robot.claw.clawDown();
			    Timer.delay(1);
			    Robot.claw.setClawLeft(-1);
			    Robot.claw.setClawRight(-1);
			}else if(gameData.charAt(0)=='L') {	 
				Robot.drivesystem.resetDistance();
				while(drive1Loop < 200) {
		    		double driveAngle = Robot.drivesystem.getAngle();
		    		Robot.drivesystem.arcadeDrive(-0.6, -driveAngle * scalingConstant);
		    		Timer.delay(0.01);
		    		drive1Loop++;
		    	}  
			}
		}
		isfinished = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(isfinished == true) {
    		drive1Loop = 0;
    	}
        return isfinished | Robot.mast1.timeout;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

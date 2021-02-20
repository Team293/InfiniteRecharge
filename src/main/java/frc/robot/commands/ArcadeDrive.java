// RobotBuilder Version: 3.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.robot.Constants.InputConstants.*;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import frc.robot.subsystems.Drivetrain;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class ArcadeDrive extends CommandBase 
{
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private final Drivetrain m_drivetrain;
    private final XboxController m_xboxcontroller;

    double m_deadband;
    boolean m_forzaEnabled;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    public ArcadeDrive(Drivetrain subsystem, XboxController xboxcontroller) 
    {
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        m_drivetrain = subsystem;
        addRequirements(m_drivetrain);

        m_xboxcontroller = xboxcontroller;
        
        m_deadband = DEFAULT_DEADBAND;
        m_forzaEnabled = DEFAULT_FORZA_MODE;
        SmartDashboard.putNumber("Deadband", m_deadband);
        SmartDashboard.putBoolean("Forza Mode", m_forzaEnabled);
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        double turning;
        double speed;
        double triggerRight;
        double triggerLeft;
        
        //Get deadband value set in SmartDashboard
        m_deadband = SmartDashboard.getNumber("Deadband", DEFAULT_DEADBAND);
        m_forzaEnabled = SmartDashboard.getBoolean("Forza Mode", DEFAULT_FORZA_MODE);

        //Checks if joystick value is higher or lower than deadband value
        turning = m_xboxcontroller.getX(Hand.kLeft);

        //Check if we should use the triggers for speed
        if(m_forzaEnabled)
        {
            //Get trigger values
            triggerRight = m_xboxcontroller.getTriggerAxis(Hand.kRight);
            triggerLeft = m_xboxcontroller.getTriggerAxis(Hand.kLeft);

            if(triggerRight >= triggerLeft)
            {
                //Use right trigger for forward speed!
                speed = triggerRight;
            }
            else
            {
                //Going in reverse! Right trigger was zero, set speed to left trigger
                speed = triggerLeft;
            }
        }
        else
        {
            //Use the stick
            speed = m_xboxcontroller.getY(Hand.kLeft);
        }

        //Clamp input to verify they are valid and greater than the deadband
        turning = m_drivetrain.clampInput(turning, m_deadband);
        speed = m_drivetrain.clampInput(speed, m_deadband);

        //Pass input to arcadeDrive
        m_drivetrain.arcadeDrive(speed, turning); 
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        m_drivetrain.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() 
    {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
        return false;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
    }
}

package todo;


import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;


public class SpinController extends PeriodicThread {
	private AbstractWashingMachine theMachine;
	private double theSpeed;
	private SpinEvent spinEvent;
	private int mode;
	private WashingProgram wp;
	private int spinDirection;

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		theMachine = mach;
		theSpeed = speed;
	}

	
	/*
	public static final int SPIN_OFF  = 0;

	public static final int SPIN_SLOW = 1;

	public static final int SPIN_FAST = 2;
	
	***************************************
	public static final int SPIN_LEFT  = 1;

   	public static final int SPIN_RIGHT = 2;	
  	*/	
	
	public void perform() {
		// TODO: implement this method
		RTEvent rte = this.mailbox.tryFetch();
		if (rte != null) {
			spinEvent = (SpinEvent) rte;
			mode = spinEvent.getMode();
			wp = (WashingProgram) spinEvent.getSource();
			if (mode == 0) {
				theMachine.setSpin(0);
			} else if (mode == 1){ 
				spinDirection = 1;
				theMachine.setSpin(spinDirection);				
			} else if (mode == 2) {
				theMachine.setSpin(2);
			}
		} else if (mode == 1) {
			if (spinDirection == 1) {
				spinDirection = 2;
				theMachine.setSpin(spinDirection);
			} else {
				spinDirection = 1;
				theMachine.setSpin(spinDirection);
			}
		}
	}
}

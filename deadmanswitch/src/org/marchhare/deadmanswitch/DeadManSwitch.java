package org.marchhare.deadmanswitch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
/** The above import is for designing text labels in the UI. */
/** And the following three lines are for implementation of a
    timer functionality to trigger the Deadman's Switch.     */
import java.util.Timer;
import java.util.TimerTask;

public class DeadManSwitchMainScreen extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

	TextView tv = new TextView(this);
	tv.setText("Fuck tha five oh");
	setContentView(tv);
    }
    
    protected void onCreate(Bundle icicle) {
	super.onCreate(icicle);

	set ContentView(R.layour.content_layout_id);

	final Button button = (Button) findViewById(R.id.button_id);
	button.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
		    // Perform action on click
		    /** The following implements the timer function and 
			I'm not quite sure if it needs to go into the 
			public class of previous function.
			Code example taken from http://www.java2s.com/Code/
			Java/Development-Class/UsejavautilTimertoschedulea
			tasktoexecuteonce5secondshavepassed.htm */

		    public class SetOffTimer {
			Toolkit toolkit;
			
			Timer timer;
			
			public SetOffTimer(int seconds) {
			    toolkit = Toolkit.getDefaultToolkit();
			    timer = new Timer();

			    timer.schedule(new TrippedSwitch(), seconds * 1000);
			}

			class TrippedSwitch extends TimerTask {
			    public void run() {
				TextView tv = new TextView(this);
				/** Can I use System.out.println() here instead of 
				    android.textView()? */
				tv.setText("Deactivation Failure: Dead Man's Switch has been triggered to execute scheduled tasks");
				setContentView(tv);
				tookit.beep();
				System.exit(0);
			    }
			}
			
			public static void main(String args[]) {
			    TextView tv = new TextView(this);
			    
			    tv.setText("Dead Man's Switch Activating...");
			    new SetOffTimer(10);
			    tv.setText("Dead Man's Switch Activated: You must continue to deactivate the Dead Man's Switch at the specified interval to defer execution of scheduled tasks");
			}
		    }
		}

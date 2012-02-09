package org.marchhare.deadmanswitch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import java.security.* ;
import java.util.Timer;
import java.util.TimerTask;
//import java.util.Toolkit;
//the above was used for the timer, but isn't in java.util

public class DeadManSwitch extends Activity {

    /** Declare our views so that we can access them later.*/
    public Button aliveButton;
    public EditText alivePasswd;
    public TextView passwdResult;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

	TextView tv = new TextView(this);
	tv.setText("Not dead yet? Good. Now smash the state.");
	setContentView(tv);

    }
    /** Main button to prove the person is still alive.*/
    public View.OnClickListener CreateOnClickListener(final Context context, final Class DeadManSwitch) {
	View.OnClickListener btnlistener = new View.OnClickListener() {
		public void onClick(View v) {
		    Intent passwdbox = new Intent(context, DeadManSwitch);
		    context.startActivity(passwdbox);

		    /** Get the listeners*/
		    aliveButton = (Button)findViewById(R.id.alive_button);
		    alivePasswd = (EditText)findViewById(R.id.alive_passwd);
		    passwdResult = (TextView)findViewById(R.id.alive_result);
		    
		    if (v == aliveButton) {
			/** User must input password to prove that they are 
			    pressing the button, and not someone else.*/
			String passwd = alivePasswd.getText().toString();
			
			byte[] data = passwd.getBytes();
			try {
			    MessageDigest md = MessageDigest.getInstance("SHA-256");
			    md.reset();
			    md.update(data);
			    byte hash[] = md.digest();
			    
			    StringBuffer hexString = new StringBuffer();
			    for (int i=0;i<hash.length;i++) {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			    }
			    
			    String foo = hash.toString();
			    //plaintext = passwd
			    //hashed = hexString.toString()
			    
			    if (passwd.equals(hexString.toString())) {
				passwdResult.setText("Authentication successful.");
			    } else {
				passwdResult.setText("Authentication failure.");
				//way to set limit on tries
				//after 3 tries times goes off anyway?
			    }
			} catch (NoSuchAlgorithmException nsae) {
			}	
		    }
		};
	    };
	return btnlistener;
    };
}

/** The following implements the timer function and 
    I'm not quite sure if it needs to go into the 
    public class of previous function.
    Code example taken from http://www.java2s.com/Code/
    Java/Development-Class/UsejavautilTimertoschedulea
    tasktoexecuteonce5secondshavepassed.htm */

/**protected class SetOffTimer {
    Toolkit toolkit;
    
    Timer timer;
    
    public SetOffTimer(int seconds) {
	toolkit = Toolkit.getDefaultToolkit();
	timer = new Timer();
	
	timer.schedule(new TrippedSwitch(), seconds * 1000);
    }

    public class TrippedSwitch extends TimerTask {
	public void run() {
	    TextView tv = new TextView(this);
	    // Can I use System.out.println() here instead of 
		//android.textView()? 
	    tv.setText("Deactivation Failure: Dead Man's Switch has been triggered to execute scheduled tasks");
	    setContentView(tv);
	    tookit.beep();
	    System.exit(0);
	}
    }
    
    public void main(String args[]) {
	TextView tv = new TextView(this);
	
	tv.setText("Dead Man's Switch Activating...");
	new SetOffTimer(10);
	tv.setText("Dead Man's Switch Activated: You must continue to deactivate the Dead Man's Switch at the specified interval to defer execution of scheduled tasks");
    }
    }		*/
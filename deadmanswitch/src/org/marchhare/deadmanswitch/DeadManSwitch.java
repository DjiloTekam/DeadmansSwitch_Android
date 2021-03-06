package org.marchhare.deadmanswitch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
//import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import java.security.* ;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.Toolkit;
//the above was used for the timer, but isn't in java.util

public class DeadManSwitch extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

	/** Passphrase prompt on main screen. */
	TextView enterPasswd = (TextView)findViewById(R.id.enter_passwd);
	
	/** Passphrase box. */
	final EditText alivePasswd = (EditText)findViewById(R.id.alive_passwd);

	/** Button to enter passphrase. */
	final Button aliveButton = (Button)findViewById(R.id.alive_button);
	aliveButton.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
		    /** Check that they are pressing the intended button.*/
		    if (v == aliveButton) {
			/** Grab passphrase from user input.*/
			String plaintext = alivePasswd.getText().toString();
			//NEED WAY TO CATCH BLANK PASSWORD
			
			/** Convert to byte array, needed for hashing.*/
			byte[] plaintextbytes = plaintext.getBytes();
			String sha256hash;
			try {
			    /** Use SHA-256 */
			    MessageDigest md = MessageDigest.getInstance("SHA-256");
			    md.reset();
			    md.update(plaintextbytes);
			    byte hash[] = md.digest();

			    StringBuffer hexstring = new StringBuffer();
			    for (int i=0;i<hash.length;i++) {
				String hex = Integer.toHexString(0xFF & hash[i]);
				if (hex.length()==1) {
				    hexstring.append('0');
				}
				hexstring.append(hex);
				sha256hash = hexstring.toString();
				if (plaintext.equals(sha256hash)) {
					Toast.makeText(DeadManSwitch.this, "Authentication successful", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(DeadManSwitch.this, "Authentication failure", Toast.LENGTH_LONG).show();
					//WAY TO SET LIMIT ON TRIES
					//AFTER THREE TRIES DEADMAN'S SWITCH ACTIVATES ANYWAY?
				}
			    }
			} catch (NoSuchAlgorithmException nsae) {
			};
		    }; //closes "if (v == aliveButton)"
		};
	    });
    };
}
    /** Method for returning button listeners.
    public View.OnClickListener CreateOnClickListener(final Context context, final Class<?> DeadManSwitch) {
	View.OnClickListener btnlistener = new View.OnClickListener() {
		public void onClick(View v) {
		    Intent passwdbox = new Intent(context, DeadManSwitch);
 		    context.startActivity(passwdbox);			    	
		    };
		};
	    };
	return btnlistener;
	}; */

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
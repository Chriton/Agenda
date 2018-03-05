package Utils;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

/**
 *
 * @author dorumuntean
 */
public class Message {
    
    /**
     * Displays a message into a JLabel element and clears it after 10 sec
     * @param label the JLabel to set the text into
     * @param message the message to be displayed
     */
    public static void showMessage(JLabel label, String message) {
        showMessage(label, message, 10000);
    }
    
    /**
     * Displays a message into a JLabel element and clears it after the specified interval
     * @param label the JLabel to set the text into
     * @param message the message to be displayed
     * @param clearAfter the time in milliseconds after which the JLabel will have its text cleared
     */
    public static void showMessage(JLabel label, String message, int clearAfter) {
        Timer timer = new Timer("Timer showMessage");
        //show the new message
        label.setText(message);
        
        //clear the message after 10sec
        TimerTask task = new TimerTask() {
        @Override
        public void run() {
            label.setText(""); 
            timer.cancel();
            }
        };
        
        timer.schedule(task, clearAfter);  
    }
}
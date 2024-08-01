package memoriamain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

public class GameTimer {
    private Timer time;
    private JLabel elapsedTime;
    private ActionListener actionListener;
    int secondsElapsed=0;

    GameTimer(){
        elapsedTime = new JLabel("Tempo decorrido: 00:00:00");
        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                secondsElapsed++;
                updateElapsedTime();
            }
        };
        time = new Timer(1000, actionListener);
    }
        
    private void updateElapsedTime(){
        int hours = secondsElapsed / 3600;
        int minutes = (secondsElapsed % 3600) / 60;
        int seconds = secondsElapsed % 60;

        elapsedTime.setText(String.format("Tempo decorrido: %02d:%02d:%02d", hours, minutes, seconds));
    }

    public void startTimer(){
        time.start();
    }

    public void stopTimer(){
        time.stop();
    }

    public void resetTimer(){
        time.stop();
        secondsElapsed = 0;
    }



    public JLabel getElapsedTime(){
        return elapsedTime;
    }

    
}

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * @author Fernanda Vieira Pagano
 * @version 1.0
 * @since 2024-08-13
 */

public class GameTimer {
    private Timer time;
    protected JLabel elapsedTime;
    protected ActionListener actionListener;
    protected int secondsElapsed=0;
    protected String elapsedTimeString;

    GameTimer(){
        elapsedTimeString = "Tempo decorrido: 00:00:00";
        elapsedTime = new JLabel("<html>"+elapsedTimeString+"<html>");
        initTimerAListener();
        time = new Timer(1000, actionListener);
    }

    protected void initTimerAListener(){
        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                secondsElapsed++;
                updateElapsedTime();
            }
        };
    }
        
    protected void updateElapsedTime(){
        int hours = secondsElapsed / 3600;
        int minutes = (secondsElapsed % 3600) / 60;
        int seconds = secondsElapsed % 60;
        elapsedTimeString = String.format("Tempo decorrido: %02d:%02d:%02d", hours, minutes, seconds);
        elapsedTime.setText(String.format("<html><br><br>"+elapsedTimeString+"<html>", hours, minutes, seconds));
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
        updateElapsedTime();
    }

    public JLabel getElapsedTime(){
        return elapsedTime;
    }

    public String toString(){
        return elapsedTimeString;
    }

    
}

package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CountdownTimer extends GameTimer{
    private int seconds; //indicates how many seconds the cronometer starts with
    private int remainingSeconds;
    private String endTimerMessage;
    private TimeUpListener listener;

    public CountdownTimer(int seconds, String endTimerMessage){
        super();
        this.seconds = seconds;
        this.remainingSeconds = seconds;
        this.endTimerMessage = endTimerMessage;
        elapsedTime.setForeground(Color.green);
    }

    @Override
    public void resetTimer(){
        remainingSeconds = seconds;
    }

    @Override
    protected void initTimerAListener(){
        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                remainingSeconds--;
                updateElapsedTime();
            }
        };
    }

    @Override
    protected void updateElapsedTime(){
        applyColorsToLabel();
        int hours = remainingSeconds / 3600;
        int minutes = (remainingSeconds % 3600) / 60;
        int seconds = remainingSeconds % 60;
        this.elapsedTimeString = String.format("Tempo RESTANTE: %02d:%02d:%02d", hours, minutes, seconds);
        this.elapsedTime.setText(String.format("<html><br><br>"+elapsedTimeString+"</html>", hours, minutes, seconds));
        if(remainingSeconds==0){
            stopTimer();
            showTimerAlert();
            if (listener != null) {
                listener.onTimeUp();
            }
        }   
    }

    private void showTimerAlert(){
        JOptionPane.showMessageDialog(null,this.endTimerMessage);
        this.resetTimer();
    }

    private void applyColorsToLabel(){
        if (remainingSeconds >=30)
        elapsedTime.setForeground(Color.GREEN);
        else if(remainingSeconds<30 && remainingSeconds>=10)
            elapsedTime.setForeground(Color.ORANGE);
        else if (remainingSeconds<10) //Hurry up!
            elapsedTime.setForeground(Color.RED);
    }

    public int getInitialSeconds(){
        return seconds;
    }

    public void addExtraTime(int extraTime){
        this.remainingSeconds += extraTime;
        addExtraTimeLabel(extraTime);
    }

    public void addExtraTimeLabel(int extraTime){
        elapsedTime.setText(elapsedTime.getText()+"<br>"+extraTime+"s extras!<br>");
    }

    public void setTimeUpListener(TimeUpListener listener) {
        this.listener = listener;
    }
    
}

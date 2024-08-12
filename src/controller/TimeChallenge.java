package controller;

import java.awt.Dimension;

public class TimeChallenge extends Graphic {
    private static final int CARD_AMOUNT = 28;
    //Twenty eight cards, 11 valid pairs (22 cards) and 3 extratime pairs
    private static final String IMAGE_PATH = "img/TimeChallenge/";
    private static final int COLUMNS = 7;
    private static final int ROWS = 4;
    private static final Dimension GAME_PANEL_DIMENSION = new Dimension(800,500);
    private static final int TIME_LIMIT = 45; //2 minutes do complete the time
    private static final String END_TIME_MESSAGE = "O tempo acabou!";
    //private String theme;

    public TimeChallenge(String theme){
        super(CARD_AMOUNT, IMAGE_PATH+theme, COLUMNS, ROWS, GAME_PANEL_DIMENSION);
    }

    @Override
    public void initTimer(){
        this.timer = new CountdownTimer(TIME_LIMIT, END_TIME_MESSAGE);
    }

    //TODO implement extra time when extratime pair is found
    
}

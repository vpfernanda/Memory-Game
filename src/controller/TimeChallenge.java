package controller;

import java.awt.Dimension;

public class TimeChallenge extends Graphic {
    private static final int CARD_AMOUNT = 24;
    private static final String IMAGE_PATH = "img/TimeChallenge/";
    private static final int COLUMNS = 6;
    private static final int ROWS = 4;
    private static final Dimension GAME_PANEL_DIMENSION = new Dimension(800,500);
    private static final int TIME_LIMIT = 120; //2 minutes do complete the time
    private static final String END_TIME_MESSAGE = "O tempo acabou!";
    private String theme;

    public TimeChallenge(String theme){
        super(CARD_AMOUNT, IMAGE_PATH, COLUMNS, ROWS, GAME_PANEL_DIMENSION);
        this.theme = theme;

    }

    @Override
    public void initTimer(){
        this.timer = new CountdownTimer(TIME_LIMIT, END_TIME_MESSAGE);
    }
    
}

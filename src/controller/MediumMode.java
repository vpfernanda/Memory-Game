package controller;

import java.awt.Dimension;

public class MediumMode extends Graphic{
    private static final int CARD_AMOUNT = 32;
    private static final String IMAGE_PATH = "img/Medium/";
    private static final int COLUMNS = 8;
    private static final int ROWS = 4;
    private static final Dimension GAME_PANEL_DIMENSION = new Dimension(600,400);
    //private String mode;

    public MediumMode(String mode){
        super(CARD_AMOUNT, IMAGE_PATH+mode, COLUMNS, ROWS, GAME_PANEL_DIMENSION);
    }

    public String getMode(){
        return "Médio";
    }
    
}

    

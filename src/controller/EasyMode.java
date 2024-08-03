package controller;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class EasyMode extends Graphic{
    private static final int CARD_AMOUNT = 20;
    private static final String IMAGE_PATH = "img/Easy/";
    private static final int COLUMNS = 5;
    private static final int ROWS = 4;
    private static final Dimension GAME_PANEL_DIMENSION = new Dimension(600,400);
    //private String mode;

    public EasyMode(String mode){
        super(CARD_AMOUNT, IMAGE_PATH+mode, COLUMNS, ROWS, GAME_PANEL_DIMENSION);
    }
    
}

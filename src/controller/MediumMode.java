package controller;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MediumMode extends Graphic{
    private static final int CARD_AMOUNT = 32;
    private static final String IMAGE_PATH = "img/Medium/";
    private static final int COLUMNS = 8;
    private static final int ROWS = 4;
    //ATTENTION
    private static final Dimension GAME_PANEL_DIMENSION = new Dimension(600,400);
    //private String mode;

    public MediumMode(String mode){
        super(CARD_AMOUNT, IMAGE_PATH+mode, COLUMNS, ROWS, GAME_PANEL_DIMENSION);
    }

    /*@Override
    public void actionButtonsPanelResize(){
        actionButtonsPanel.setMinimumSize(new Dimension(150, MAXIMIZED_VERT));
        actionButtonsPanel.setPreferredSize(new Dimension(150, MAXIMIZED_VERT));
        actionButtonsPanel.setMaximumSize(new Dimension(150, MAXIMIZED_VERT));
    }*/

    @Override //melhorar esse override!
    public JScrollPane initGamePanel(int columns, int rows) {
        //a única coisa que precisamos mexer é a inicialização!
        gameButtonsPanel = new JPanel(new GridLayout(rows, columns, 10, 10));
        //fora gameButtonsPanel, todas as outras variáveis usadas podem continuar private na classe pai.
        gameButtonsPanel.setPreferredSize(gamePanelDimension);
        initUnrevealedCardButtons(cardAmount, false);
        for (JButton b : cardButtons) {
            gameButtonsPanel.add(b);
        }
        return new JScrollPane(gameButtonsPanel); // Adiciona o painel ao JScrollPane
    }

    public String getMode(){
        return "Médio";
    }
    
}

    

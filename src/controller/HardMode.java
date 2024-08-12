package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HardMode extends Graphic{
    private static final int CARD_AMOUNT = 40;
    private static final String IMAGE_PATH = "img/Hard/";
    private static final int COLUMNS = 8;
    private static final int ROWS = 5;
    private static final Dimension GAME_PANEL_DIMENSION = new Dimension(800,500);
    private String theme;
    private static final String MODE = "Difícil";

    public HardMode(String theme){
        super(CARD_AMOUNT, IMAGE_PATH+theme, COLUMNS, ROWS, GAME_PANEL_DIMENSION);
        this.theme = theme;
        this.updateGameInfoLabel();
    }
    
    @Override
    protected void updateGameInfoLabel(){
        text.setText("<html><br>Informações da partida:<br><br>Modo:"+MODE+"<br> Tema: "
        +theme.replace('/', ' ')+"<br><br><br>"+getGameInfo()+"</html>");
        text.setForeground(Color.BLACK);
    }

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
        return MODE;
    }
    
}

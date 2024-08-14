package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * @author Fernanda Vieira Pagano
 * @version 1.0
 * @since 2024-08-13
 */

public class TimeChallenge extends Graphic implements TimeUpListener {
    private static final int CARD_AMOUNT = 28; //Twenty eight cards, 11 valid pairs (22 cards) and 3 extratime pairs
    private static final String IMAGE_PATH = "img/Contra o tempo/";
    private static final int COLUMNS = 7;
    private static final int ROWS = 4;
    private static final int EXTRATIME_PAIRS = 3;
    private static final Dimension GAME_PANEL_DIMENSION = new Dimension(800,500);
    private static final int TIME_LIMIT = 60; //1 minute to complete the game
    private static final String END_TIME_MESSAGE = "O tempo acabou!";
    private static final String MODE = "Contra o tempo";
    private String modeDescription; 
    private String theme;

    public TimeChallenge(String theme){
        super(CARD_AMOUNT, IMAGE_PATH+theme, COLUMNS, ROWS, GAME_PANEL_DIMENSION);
        this.theme = theme.replace('/', '\0');
        updateGameInfoLabel();
        modeDescription = "Bem vindo ao modo de jogo 'Contra o tempo'!\n"+
        "Você escolheu o tema "+this.theme+".\nEncontre os pares antes que o tempo se esgote! Você terá "+
        "1 minuto para resolver o jogo.\nAlgumas cartas são assinaladas com um relógio.\nFique atento à essas cartas"+
        ",você ganhará 10 segundos extras para cada par de relógios que você encontrar.\nConcentre-se!\n"+
        "Quando estiver pronto, clique em Iniciar Jogo.\nA partir daí, o cronômetro começará a contar.\nDivirta-se!";
        showDialog(modeDescription);
    }

    @Override
    protected void updateGameInfoLabel(){
        text.setText("<html><br>Informações da partida:<br><br>Modo:"+MODE+"<br> Tema: "
        +this.theme+"<br><br><br>"+getGameInfo()+"</html>");
        text.setForeground(Color.BLACK);
    }

    @Override
    public void initTimer(){
        this.timer = new CountdownTimer(TIME_LIMIT, END_TIME_MESSAGE);
        ((CountdownTimer)this.timer).setTimeUpListener(this);
    }

    
    /** 
     * @param randomList
     * @param imagePath
     */
    @Override
    public void initRevealedCardIcons(ArrayList<Integer> randomList, String imagePath) {
        icons = new ImageIcon[this.cardAmount];
        int counterList = 0;
        String imgName;
        //is there any way to refactor this for loop?
        for (int counter = 0; counter < this.cardAmount / 2; counter++) {
            imgName = "img"+(counter+1);
            if(counter>=((this.cardAmount/2)-EXTRATIME_PAIRS))
                imgName = "extratime";
            icons[randomList.get(counterList)] = new ImageIcon(getClass().getResource(imagePath + imgName + ".jpg"));
            icons[randomList.get(counterList)].setDescription(imgName);
            counterList++;
            icons[randomList.get(counterList)] = new ImageIcon(getClass().getResource(imagePath + imgName + ".jpg"));
            icons[randomList.get(counterList)].setDescription(imgName);
            counterList++;
        }
    }

    @Override
    protected void pairFoundActions() {
        memory.playerHit();    
    
        // Remove actionListeners to block player clicks on the cards before they are set to unrevealed
        removeCardButtonsAListener();
    
        // Player hit message
        text.setText(text.getText() + "<font color=\"GREEN\">Parabéns! <br>Você acertou! </font>");
        text.repaint(); 
        text.revalidate(); 
    
        if ((img1.getDescription().equals("extratime")) && (img2.getDescription().equals("extratime"))) {
            // Adding extra time when clocks are found
            ((CountdownTimer) TimeChallenge.this.timer).addExtraTime(10);
    
            // Delay: 1 second to show the extratime warning and proceed
            Timer extraTimeTimer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // After 1 second, cards are set to unrevealed
                    continuePairFoundActions();
                }
            });
            extraTimeTimer.setRepeats(false);
            extraTimeTimer.start();
        } else {
            // Se não for um par de extra time, continue imediatamente
            continuePairFoundActions();
        }
    }
    
    private void continuePairFoundActions() {
        // Waiting 1 seconds so the user is able to see the chosen cards before they are set to unrevealed
        Timer actionDelay = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the clicked cards to unrevealed
                setButtonIcon(cardButtons[clickedCardButton1], img1, false);
                setButtonIcon(cardButtons[clickedCardButton2], img2, false);
                //Adding actionlistener again...
                for (int c = 0; c < cardAmount; c++) {
                    cardButtons[c].addActionListener(cardButtonsActionListener);
                }
                updateGameInfoLabel();
            }
        });
        actionDelay.setRepeats(false);
        actionDelay.start(); 
    }
    

    public void onTimeUp(){
        ActionEvent e = new ActionEvent(this, 1, "TimeUp");
        this.timer.resetTimer();
        this.loadEndGameWindow(e);
    }
        
    
    /** 
     * @param string
     */
    private void showDialog(String string){
        JOptionPane.showMessageDialog(null, string, "Instruções de jogo", JOptionPane.INFORMATION_MESSAGE);
    }

}

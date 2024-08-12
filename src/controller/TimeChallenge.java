package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class TimeChallenge extends Graphic implements TimeUpListener {
    private static final int CARD_AMOUNT = 28;
    //Twenty eight cards, 11 valid pairs (22 cards) and 3 extratime pairs
    private static final String IMAGE_PATH = "img/TimeChallenge/";
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

    @Override
    public void initRevealedCardIcons(ArrayList<Integer> randomList, String imagePath) {
        icons = new ImageIcon[this.cardAmount];
        int counterList = 0;
        String imgName;
        //int counterList = initExtraTimeCards(randomList,"extratime", EXTRATIME_PAIRS);
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
    protected void pairFound(){
        super.pairFound();
        closeAllOptionPanes();
        if((img1.getDescription().equals("extratime"))&&(img2.getDescription().equals("extratime"))){
            ((CountdownTimer) this.timer).addExtraTime(10);    
        }           
    }

    @Override
    protected void playerErrorActions(){
        super.playerErrorActions();
        closeAllOptionPanes();
    }

    public void onTimeUp(){
        //TODO - Chamar as ações de fim de jogo. 
        ActionEvent e = new ActionEvent(this, 1, "TimeUp");
        this.timer.resetTimer();
        closeAllOptionPanes();
        this.loadEndGameWindow(e);
    }

     public static void closeAllOptionPanes() {
        // Obtém todos os frames ativos (abertos) na aplicação
        for (Window window : Window.getWindows()) {
            if (window instanceof JDialog) {
                // Fecha o diálogo
                window.dispose();
            }
        }
    }
        
    private void showDialog(String string){
        JOptionPane.showMessageDialog(null, string, "Instruções de jogo", JOptionPane.INFORMATION_MESSAGE);
    }

}

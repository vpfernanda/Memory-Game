package memoriamain;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Graphic extends JFrame {
    //Panels
    private JPanel actionButtonsPanel;
    private JPanel gameButtonsPanel;

    //Buttons
    private JButton cardButtons[];
    private JButton startGameButton;
    private JButton endGameButton;
    private JButton modeButton;
    private JButton pauseResumeGameButton;

    //ActionListeners
    private ActionListener gameStartActionListener;
    private ActionListener cardButtonsActionListener;
    private ActionListener gameEndActionListener;
    private ActionListener modeButtonActionListener;
    private ActionListener pauseResumeGameButtonActionListener;

    //TextArea
    private JTextArea text;

    //Labels
    private JLabel labelTop;

    //Image Icons
    private ImageIcon unrevealed;
    private ImageIcon top;
    private ImageIcon icons[];
    private ImageIcon img1; //First card of the attempt pair
    private ImageIcon img2; //Second card of the attempt pair

    //Timer
    private GameTimer timer;

    //Strings
    private String gameInfo;
    private String playerHitString;
    private String playerErrorString;
    private String gameWarningString;
    private String endGameString;

    //Ints
    private int clickCounter;
    private int clickedCardButton1;
    private int clickedCardButton2;
    private int mode=1;

    //GameObject
    private Memoria memory;

    public Graphic(){
        super("Jogo da Memória!");
        clickCounter = 0;
        //importante: a linha abaixo deve ser implementada em cada filho da classe Graph
        memory = new Memoria(mode);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        loadAllPanels();
    }

    public String getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(String gameInfo) {
        this.gameInfo = gameInfo;
    }

    public String getPlayerHitString() {
        return playerHitString;
    }

    public void setPlayerHitString(String playerHitString) {
        this.playerHitString = playerHitString;
    }

    public String getPlayerErrorString() {
        return playerErrorString;
    }

    public void setPlayerErrorString(String playerErrorString) {
        this.playerErrorString = playerErrorString;
    }

    public String getGameWarningString() {
        return gameWarningString;
    }

    public void setGameWarningString(String gameWarningString) {
        this.gameWarningString = gameWarningString;
    }

    public String getEndGameString() {
        return endGameString;
    }

    public void setEndGameString(String endGameString) {
        this.endGameString = endGameString;
    }

    

    private void initUnrevealedCardButtons(int cardAmount){
        cardButtons = new JButton[cardAmount];
        for(int i=0; i<cardButtons.length; i++){
            cardButtons[i] = new JButton(""+i, unrevealed);
            //cardButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            //cardButtons[i].setVerticalTextPosition(SwingConstants.CENTER);
            cardButtons[i].setActionCommand(""+i);
            cardButtons[i].setText(null);
            cardButtons[i].setContentAreaFilled(false);
            cardButtons[i].setBorderPainted(true);
            cardButtons[i].addActionListener(cardButtonsActionListener);
        }
    }

    private void initCardButtonsActionListener(){
        cardButtonsActionListener = new ActionListener() {
            public void actionPerformed (ActionEvent event){
                int buttonActionCommand = Integer.parseInt(event.getActionCommand());
                
                clickCounter++;
                switch (clickCounter){
                    case 1: //First user click.
                        clickedCardButton1=buttonActionCommand;
                        cardButtons[clickedCardButton1].setIcon(icons[clickedCardButton1]); //Reveals the image of the card.
                        break;
                    case 2: //Second user click.
                        clickedCardButton2=buttonActionCommand;
                        if(clickedCardButton1==clickedCardButton2){ //User clicked the same button.
                            text.setText(gameWarningString);
                            clickCounter=1;
                            break;
                        }
                        else{
                            //clickedCardButton2=buttonActionCommand;
                            cardButtons[clickedCardButton2].setIcon(icons[clickedCardButton2]); //Reveals the image of the card.
                            img1=(ImageIcon)cardButtons[clickedCardButton1].getIcon();
                            img2=(ImageIcon)cardButtons[clickedCardButton2].getIcon();
                            memory.compareCards();
                            break;
                        }   
                }   
            }
            };
        };
    

    public void initRevealedCardIcons(ArrayList<Integer> randomList, int cardAmount, String imagePath){
        icons = new ImageIcon[cardAmount];
        int counterList = 0;
        for(int counter=0; counter<cardAmount/2; counter++){
            icons[randomList.get(counterList)]=new ImageIcon(getClass().getResource(imagePath+"img"+(counter+1)+".jpg"));
            icons[randomList.get(counterList)].setDescription(""+counter+1);
            counterList++;
            icons[randomList.get(counterList)]=new ImageIcon(getClass().getResource(imagePath+"img"+(counter+1)+".jpg"));
            icons[randomList.get(counterList)].setDescription(""+counter+1);
            counterList++;
        }    
    }

    public void initGamePanel(int cardAmount, int columns, int rows){
        initUnrevealedCardButtons(cardAmount);
        for(JButton b : cardButtons)
            gameButtonsPanel.add(b); 

        gameButtonsPanel.setLayout(new GridLayout(rows, columns));
    }

    public void initActionButtonsPanel(){
        actionButtonsPanel = new JPanel();
        actionButtonsPanel.setLayout(new BoxLayout(actionButtonsPanel, BoxLayout.Y_AXIS));
        initStartGameButton("Iniciar jogo");
        initModeButton("Modo de Jogo");
        initEndGameButton("Finalizar jogo");
        actionButtonsPanel.add(startGameButton);
        actionButtonsPanel.add(modeButton);
        actionButtonsPanel.add(endGameButton);
    }

    public void initStartGameButton(String buttonText){
        startGameButton = new JButton(buttonText);
        startGameButton.setBackground(Color.GREEN);
        startGameButton.setForeground(Color.BLACK);
        initStartGameActionListener(20);
        startGameButton.addActionListener(gameStartActionListener);
    }

    public void initModeButton(String buttonText){
        modeButton = new JButton(buttonText);
        modeButton.setBackground(Color.yellow);
        modeButton.setForeground(Color.black);
        //initActionListener
        //addActionListener
    }

    public void initEndGameButton(String buttonText){
        endGameButton = new JButton(buttonText);
        endGameButton.setBackground(Color.RED);
        endGameButton.setForeground(Color.BLACK);
        endGameButton.addActionListener(gameEndActionListener);
    }

    private void initEndGameActionListener(int cardAmount){
        gameEndActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                timer.stopTimer();
                //TODO
                //Call JOptionPane
            }
        };
    }

    private void initLabelTop(){
        ImageIcon topIcon = new ImageIcon(getClass().getResource("img/top.png"));
        labelTop = new JLabel(topIcon);
    }
    

    private void initStartGameActionListener(int cardAmount){
        gameStartActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                timer.startTimer();
                initUnrevealedCardButtons(cardAmount);
            }
        };
    }

    //it is really necessary?
    public void stopTimerFromInterface(){
        timer.stopTimer();
    }

    public void loadAllPanels(){
        //initActionButtonsPanel();
        //initGamePanel(20,5,4);
        initLabelTop();
        this.add(labelTop, BorderLayout.NORTH);
        //this.add(actionButtonsPanel, BorderLayout.WEST);
        //this.add(gameButtonsPanel, BorderLayout.CENTER);
        //this.add(timer.getElapsedTime(), BorderLayout.PAGE_END);
    }
}

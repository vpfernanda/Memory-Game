package memoriamain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Graphic extends JFrame {
    // Panels
    private JPanel actionButtonsPanel;
    private JPanel gameButtonsPanel;

    // Buttons
    private JButton[] cardButtons;
    private JButton startGameButton;
    private JButton endGameButton;
    private JButton modeButton;

    // ActionListeners
    private ActionListener cardButtonsActionListener;
    private ActionListener gameStartActionListener;
    private ActionListener gameEndActionListener;


    // Labels
    private JLabel labelTop;
    private JLabel text;

    // Image Icons
    private ImageIcon unrevealed;
    private ImageIcon top;
    private ImageIcon icons[];
    private ImageIcon img1; //First card of the attempt pair
    private ImageIcon img2; //Second card of the attempt pair

    // Timer
    private GameTimer timer;

    //Strings
    private String gameInfo;
    private String playerHitString;
    private String playerErrorString;
    private String gameWarningString;
    private String endGameString;

    // GameObject
    private Memoria memory;

    // Integers
    private int clickedCardButton1;
    private int clickedCardButton2;
    private int mode = 1;
    private int cardAmount;

    public Graphic(int cardAmount) {
        super("Jogo da Memória!");
        this.cardAmount = cardAmount;
        memory = new Memoria();
        text = new JLabel();
        timer = new GameTimer();
        gameInfo = getGameInfo();
        text.setText("<html><br>Informações da partida:<br><br>"+getGameInfo()+"<html><br>");
        text.setFont(new Font("ComicSans", Font.BOLD, 12));
        unrevealed = new ImageIcon(getClass().getResource("img/cartela.jpg"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Configurando a interface
        add(loadMainPanel());
        setVisible(true);
    }

    public String getGameInfo() {
        return "\nAcertos:" +memory.getPlayerHits()+
        " \nErros: "+memory.getPlayerErrors()+"";
    }

    

    public String getPlayerHitString() {
        return "Parabéns! \nVocê acertou!" + "\nAcertos: "+memory.getPlayerHits()+"\nErros: "+memory.getPlayerErrors();
    }

    public String getPlayerErrorString() {
        return "Você errou!" + "\nAcertos: "+memory.getPlayerHits()+"\nErros: "+memory.getPlayerErrors();
    }


    public String getGameWarningString() {
        return gameWarningString;
    }

    // public void setGameWarningString(String gameWarningString) {
    //     this.gameWarningString = gameWarningString;
    // }

    public String getEndGameString() {
        return endGameString;
    }

    public void setEndGameString(String endGameString) {
        this.endGameString = endGameString;
    }


    private void initUnrevealedCardButtons(int cardAmount, boolean enable) {
        initCardButtonsActionListener();
        cardButtons = new JButton[cardAmount];
        for (int i = 0; i < cardButtons.length; i++) {
            cardButtons[i] = new JButton("" + i, unrevealed);
            cardButtons[i].setActionCommand("" + i);
            cardButtons[i].setText(null);
            cardButtons[i].setContentAreaFilled(false);
            cardButtons[i].setEnabled(enable);
            cardButtons[i].setDisabledIcon(unrevealed);
            cardButtons[i].setBorderPainted(true);
            cardButtons[i].addActionListener(cardButtonsActionListener);
        }
    }

    private void initCardButtonsActionListener() {
        initRevealedCardIcons(memory.randomize(20), "img/");
        cardButtonsActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int buttonActionCommand = Integer.parseInt(event.getActionCommand());

                memory.clickRegister();
                switch (memory.getClickCounter()) {
                    case 1: // First user click.
                        clickedCardButton1 = buttonActionCommand;
                        cardButtons[clickedCardButton1].setIcon(icons[clickedCardButton1]);
                        break;
                    case 2: // Second user click.
                        clickedCardButton2 = buttonActionCommand;
                        if (clickedCardButton1 == clickedCardButton2) {
                            text.setText(text.getText()+"<html><br>Warning: Same card clicked.</html>");
                            memory.clickCancel();
                            break;
                        } else {
                            cardButtons[clickedCardButton2].setIcon(icons[clickedCardButton2]);
                            img1=(ImageIcon)cardButtons[clickedCardButton1].getIcon();
                            img2=(ImageIcon)cardButtons[clickedCardButton2].getIcon();
                            if(memory.compareCards(img1, img2)){
                                //Playerhit!
                                memory.playerHit();
                                JOptionPane.showMessageDialog(null, getPlayerHitString());
                                cardButtons[clickedCardButton1].setEnabled(false);
                                cardButtons[clickedCardButton1].setDisabledIcon(img1);
                                cardButtons[clickedCardButton2].setEnabled(false);
                                cardButtons[clickedCardButton2].setDisabledIcon(img2);
                            }
                            else{
                                memory.playerError();
                                JOptionPane.showMessageDialog(null, getPlayerErrorString());
                                for(int c=0; c<cardAmount; c++)
                                    cardButtons[c].setIcon(unrevealed);    
                            }
                            break;
                        }
                }
                updateGameInfoLabel();
            }
        };
    }

    private void updateGameInfoLabel(){
        text.setText("<html><br>Informações da partida:<br><br>"+getGameInfo()+"<html><br>");
    }

    public void initRevealedCardIcons(ArrayList<Integer> randomList, String imagePath) {
        int cardAmount = randomList.size();
        icons = new ImageIcon[cardAmount];
        int counterList = 0;
        for (int counter = 0; counter < cardAmount / 2; counter++) {
            icons[randomList.get(counterList)] = new ImageIcon(getClass().getResource(imagePath + "img" + (counter + 1) + ".jpg"));
            icons[randomList.get(counterList)].setDescription("" + (counter + 1));
            counterList++;
            icons[randomList.get(counterList)] = new ImageIcon(getClass().getResource(imagePath + "img" + (counter + 1) + ".jpg"));
            icons[randomList.get(counterList)].setDescription("" + (counter + 1));
            counterList++;
        }
    }

    public JPanel initGamePanel(int cardAmount, int columns, int rows) {
        gameButtonsPanel = new JPanel(new GridLayout(rows, columns, 5, 5));
        initUnrevealedCardButtons(cardAmount, false);
        for (JButton b : cardButtons) {
            gameButtonsPanel.add(b);
        }
        return gameButtonsPanel;
    }

    public JPanel initActionButtonsPanel() {
        actionButtonsPanel = new JPanel();
        actionButtonsPanel.setLayout(new BoxLayout(actionButtonsPanel, BoxLayout.Y_AXIS));
        actionButtonsPanel.setPreferredSize(new Dimension(130, getHeight()));
        initStartGameButton("<html>Iniciar jogo<html>");
        initModeButton("<html>Modo de Jogo<html>");
        initEndGameButton("<html>Finalizar jogo<html>");
        actionButtonsPanel.add(startGameButton);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        actionButtonsPanel.add(modeButton);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        actionButtonsPanel.add(endGameButton);
        actionButtonsPanel.add(text);
        actionButtonsPanel.add(timer.getElapsedTime());
        return actionButtonsPanel;
    }

    public void initStartGameButton(String buttonText) {
        startGameButton = new JButton(buttonText);
        startGameButton.setBackground(Color.GREEN);
        startGameButton.setForeground(Color.BLACK);
        initStartGameActionListener(20);
        startGameButton.addActionListener(gameStartActionListener);
    }

    public void initModeButton(String buttonText) {
        modeButton = new JButton(buttonText);
        modeButton.setBackground(Color.YELLOW);
        modeButton.setForeground(Color.BLACK);
    }

    public void initEndGameButton(String buttonText) {
        endGameButton = new JButton(buttonText);
        endGameButton.setBackground(Color.RED);
        endGameButton.setForeground(Color.BLACK);
        initEndGameActionListener(20);
        endGameButton.setEnabled(false);
        endGameButton.addActionListener(gameEndActionListener);
    }

    private void initStartGameActionListener(int cardAmount) {
        gameStartActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.resetTimer();
                timer.startTimer();
                updateGameInfoLabel();
                for (int c=0; c<cardAmount; c++)   
                    cardButtons[c].setEnabled(true); 
                startGameButton.setEnabled(false);
                endGameButton.setEnabled(true);
            }
        };
    }

    private void initEndGameActionListener(int cardAmount){
        gameEndActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                timer.stopTimer();
                JOptionPane.showMessageDialog(null, "Jogo finalizado.\n "
                +timer.toString()+"\n"+getGameInfo());
                memory.gameReset();
                timer.resetTimer();
                startGameButton.setEnabled(true);
                
            }
        };
    }

    private JLabel initLabelTop() {
        ImageIcon topIcon = new ImageIcon(getClass().getResource("img/top.png"));
        labelTop = new JLabel(topIcon, JLabel.CENTER);
        return labelTop;
    }

    private JPanel loadMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(initLabelTop(), BorderLayout.NORTH);
        mainPanel.add(loadSecondaryPanel(), BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel loadSecondaryPanel() {
        JPanel secondaryPanel = new JPanel(new BorderLayout());
        secondaryPanel.add(initActionButtonsPanel(), BorderLayout.WEST);
        secondaryPanel.add(initGamePanel(20, 5, 4), BorderLayout.CENTER);
        return secondaryPanel;
    }

}

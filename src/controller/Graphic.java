package controller;

import javax.swing.*;
import app.Application;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Fernanda Vieira Pagano
 * @version 1.0
 * @since 2024-08-13
 */

public class Graphic extends JFrame {
    // Panels
    protected JPanel actionButtonsPanel;
    protected JPanel gameButtonsPanel;

    // Buttons
    protected JButton[] cardButtons;
    protected JButton startGameButton;
    private JButton endGameButton;
    private JButton modeButton;

    // ActionListeners
    protected ActionListener cardButtonsActionListener;
    private ActionListener gameStartActionListener;
    private ActionListener gameEndActionListener;
    private ActionListener modeButtonActionListener;

    // Labels
    private JLabel labelTop;
    protected JLabel text;
    private JLabel userGreetings;

    // Image Icons
    private ImageIcon unrevealed; //Image of unrevealed cards
    protected ImageIcon icons[];
    protected ImageIcon img1; //First card of the attempt pair
    protected ImageIcon img2; //Second card of the attempt pair

    // Timer
    protected GameTimer timer;

    //Strings
    private String gameWarningString;
    private String endGameString;
    protected String imagePath;

    // GameObject
    protected Memory memory;

    // Integers
    protected int clickedCardButton1;
    protected int clickedCardButton2;
    protected int cardAmount; //IMPORTANT VARIABLE
    protected int rows;
    protected int columns;

    private boolean modesFrameThroughEndGame;
    protected Dimension gamePanelDimension;

    public Graphic(int cardAmount, String imagePath, int columns, int rows, Dimension gamePanelDimension) {
        super("Jogo da Memória!");
        //passing parameters to class atributes.
        this.cardAmount = cardAmount;
        this.rows = rows;
        this.columns = columns;
        this.imagePath = imagePath;
        this.gamePanelDimension = gamePanelDimension;
        //instancing class atributes
        this.memory = new Memory(cardAmount);
        initTimer();
        this.text = new JLabel();
        this.userGreetings = new JLabel();
        //some "set" methods being called
        text.setText("<html><br>Informações da partida:<br>"+getGameInfo()+"<br><br>");
        this.gameWarningString = "Atenção: Você clicou na mesma carta. Escolha uma carta DIFERENTE!";
        this.userGreetings.setText("<html>Olá, "+Application.modesFrame.getUsername()+"!</html>");
        this.userGreetings.setForeground(Color.BLUE);
        this.setUnrevealed(imagePath);
        setAllFonts("Comic Sans MS");
        modesFrameThroughEndGame = false;
        //render all elements
        add(loadMainPanel());
        // Window configs
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza a janela
        setVisible(true);
        //Disabled icons (unrevealed) are now renderized according to the correct size button.
        SwingUtilities.invokeLater(this::updateDisabledIcons); // Garanta que os ícones sejam atualizados após a renderização
    }

    
    /** 
     * @param imgPath
     */
    private void setUnrevealed(String imgPath){
        unrevealed = new ImageIcon(getClass().getResource(imgPath+"cartela.jpg"));
    }

    
    /** 
     * @return String
     */
    protected String getGameInfo() {
        return "\nAcertos: " +memory.getPlayerHits()+
        " \nErros: "+memory.getPlayerErrors();
    }

    public void initTimer(){
        this.timer = new GameTimer();
    }

    private void setAllFonts(String font){
        text.setFont(new Font(font, Font.BOLD, 13));
        timer.getElapsedTime().setFont(new Font(font, Font.BOLD, 13));
        userGreetings.setFont(new Font(font, Font.BOLD, 13));
    }

    private String getPlayerHitString() {
        return "Parabéns! \nVocê acertou!" + "\nAcertos: "+memory.getPlayerHits()+"\nErros: "+memory.getPlayerErrors();
    }

    private String getPlayerErrorString() {
        return "Você errou!" + "\nAcertos: "+memory.getPlayerHits()+"\nErros: "+memory.getPlayerErrors();
    }

    private String getGameWarningString() {
        return gameWarningString;
    }

    private void setGameWarningString(String gameWarningString) {
         this.gameWarningString = gameWarningString;
    }

    private String getEndGameString() {
        return endGameString;
    }

    private void setEndGameString(String endGameString) {
        this.endGameString = endGameString;
    }

    protected void initUnrevealedCardButtons(int cardAmount, boolean enable) {
        initCardButtonsActionListener();
        cardButtons = new JButton[cardAmount];
        for (int i = 0; i < cardButtons.length; i++) {
            cardButtons[i] = new JButton("" + i, unrevealed)
            
            {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    
                        if (!isEnabled() && getDisabledIcon() != null) {
                            getDisabledIcon().paintIcon(this, g, 0, 0);
                        }
                    
                }
            };
            cardButtons[i].setActionCommand("" + i);
            cardButtons[i].setText(null);
            cardButtons[i].setContentAreaFilled(false);
            cardButtons[i].setDisabledIcon(unrevealed); //ATTENTION
            cardButtons[i].setEnabled(false);
            cardButtons[i].setMargin(new Insets(0, 0, 0, 0));
            cardButtons[i].setBorderPainted(true);
            cardButtons[i].setHorizontalAlignment(SwingConstants.CENTER);
            cardButtons[i].setVerticalAlignment(SwingConstants.CENTER);
            cardButtons[i].addActionListener(cardButtonsActionListener);
            
        }
    }

    protected void updateDisabledIcons() {
        SwingUtilities.invokeLater(() -> {
            for (JButton b : cardButtons) {
                setButtonIcon(b, unrevealed, false);
                //b.setEnabled(false);
            }
            //firstLoad = false;
        });
    }

    //Main method of the game. Here is where the "magic" happens :-)
    private void initCardButtonsActionListener() {
        cardButtonsActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int buttonActionCommand = Integer.parseInt(event.getActionCommand());
                //updateGameInfoLabel();
                memory.clickRegister();
                switch (memory.getClickCounter()) {
                    case 1: // First user click
                        clickedCardButton1 = buttonActionCommand;
                        setButtonIcon(cardButtons[clickedCardButton1], icons[clickedCardButton1], true);
                        break;
                    case 2: // Second user click
                        clickedCardButton2 = buttonActionCommand;
                        //User clicked the same card?
                        if (clickedCardButton1 == clickedCardButton2) {
                            //text.setText("<html><br>" + Graphic.this.getGameWarningString() +"<br><br>"+textLabel);
                            text.setText("<html><br>" + Graphic.this.getGameWarningString()+"</html>");
                            text.setForeground(Color.RED);
                            actionButtonsPanel.repaint();
                            memory.clickCancel();
                            break;
                        } 
                        else {
                            //Setting img1 and img2 to compare
                            setButtonIcon(cardButtons[clickedCardButton2], icons[clickedCardButton2], true);
                            img1 = (ImageIcon) cardButtons[clickedCardButton1].getIcon();
                            img2 = (ImageIcon) cardButtons[clickedCardButton2].getIcon();
                            //Comparing cards -> MAYBE it would turn up to be a method
                            if (memory.compareCards(img1, img2)) {
                                pairFoundActions();
                                //Player found all cards?
                                if(memory.getPlayerHits()==cardAmount/2)
                                    loadEndGameWindow(event);
                                return;
                            } 
                            //Then, user has not found a pair...
                            playerErrorActions();
                            return;
                        }
                }
            }
        };
    }

    protected void playerErrorActions(){
        memory.playerError();
        text.setText(text.getText()+"<font color=\"RED\">Tente novamente!</font>");
        //text.setForeground(Color.red);
        
        removeCardButtonsAListener();

        Timer actionDelay = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set all remaing cards to unrevealed
                setButtonIcon(cardButtons[clickedCardButton1],unrevealed, true);
                setButtonIcon(cardButtons[clickedCardButton2],unrevealed, true);
                for (int c = 0; c < cardAmount; c++){
                    cardButtons[c].addActionListener(cardButtonsActionListener);
                }
                updateGameInfoLabel();
                
            } 
        });
        actionDelay.setRepeats(false);
        actionDelay.start();
    }

    protected void pairFoundActions(){
        memory.playerHit();
        text.setText(text.getText()+"<font color=\"GREEN\">Parabéns! <br>Você acertou! </font>");

        removeCardButtonsAListener();

        Timer actionDelay = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Set all remaing cards to unrevealed
                cardButtons[clickedCardButton1].setEnabled(false);
                cardButtons[clickedCardButton1].setDisabledIcon(img1);
                cardButtons[clickedCardButton2].setEnabled(false);
                cardButtons[clickedCardButton2].setDisabledIcon(img2);
                for (int c = 0; c < cardAmount; c++){
                    cardButtons[c].addActionListener(cardButtonsActionListener);
                }
                updateGameInfoLabel();
            } 
        });
        actionDelay.setRepeats(false);
        actionDelay.start();             
    }

    protected void removeCardButtonsAListener(){
        for (int c = 0; c < cardAmount; c++){
            cardButtons[c].removeActionListener(cardButtonsActionListener);
        }
    }

    protected void setButtonIcon(JButton button, ImageIcon icon, boolean enabled) {
        int width = button.getWidth();
        int height = button.getHeight();
        //Resizing image to make it proportional to the button
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(scaledImage);
        //...but without losing the image description, which allows us to compare the pairs.
        image.setDescription(icon.getDescription());
        if(enabled){
            button.setIcon(image);
            return;
        }
        button.setDisabledIcon(image);
    }

    protected void updateGameInfoLabel(){
        text.setText("<html><br>Informações da partida:<br>"+getGameInfo()+"<html><br>");
        text.setForeground(Color.BLACK);
    }

    public void initRevealedCardIcons(ArrayList<Integer> randomList, String imagePath) {
        icons = new ImageIcon[this.cardAmount];
        int counterList = 0;
        //is there any way to refactor this for loop?
        for (int counter = 0; counter < this.cardAmount / 2; counter++) {
            icons[randomList.get(counterList)] = new ImageIcon(getClass().getResource(imagePath + "img" + (counter + 1) + ".jpg"));
            icons[randomList.get(counterList)].setDescription("" + (counter + 1));
            counterList++;
            icons[randomList.get(counterList)] = new ImageIcon(getClass().getResource(imagePath + "img" + (counter + 1) + ".jpg"));
            icons[randomList.get(counterList)].setDescription("" + (counter + 1));
            counterList++;
        }
    }

    public JScrollPane initGamePanel(int columns, int rows) {
        gameButtonsPanel = new JPanel(new GridLayout(rows, columns, 5, 5));
        gameButtonsPanel.setBackground(new Color(0, 0, 102));
        gameButtonsPanel.setPreferredSize(gamePanelDimension);
        initUnrevealedCardButtons(cardAmount, false);
        for (JButton b : cardButtons) {
            gameButtonsPanel.add(b);
        }
        return new JScrollPane(gameButtonsPanel); //Adding the panel.
    }
    
    public JPanel initActionButtonsPanel() {
        actionButtonsPanel = new JPanel();
        actionButtonsPanel.setBackground(new Color(255, 204, 255));
        //allows us to render the components towards vertical
        actionButtonsPanel.setLayout(new BoxLayout(actionButtonsPanel, BoxLayout.Y_AXIS));
        actionButtonsPanel.setPreferredSize(new Dimension(130, getHeight()));
        initStartGameButton("<html>Iniciar jogo<html>");
        initModeButton("<html>Modo de Jogo<html>");
        initEndGameButton("<html>Finalizar jogo<html>");
        actionButtonsPanel.add(userGreetings);
        actionButtonsPanel.add(startGameButton);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        actionButtonsPanel.add(modeButton);
        actionButtonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        actionButtonsPanel.add(endGameButton);
        actionButtonsPanel.add(text);
        actionButtonsPanel.add(timer.getElapsedTime());
        actionButtonsPanelResize();
        return actionButtonsPanel;
    }

    public void actionButtonsPanelResize(){
        actionButtonsPanel.setMinimumSize(new Dimension(175, MAXIMIZED_VERT));
        actionButtonsPanel.setPreferredSize(new Dimension(175, MAXIMIZED_VERT));
        actionButtonsPanel.setMaximumSize(new Dimension(175, MAXIMIZED_VERT));
    }

    public void initStartGameButton(String buttonText) {
        startGameButton = new JButton(buttonText);
        startGameButton.setBackground(new Color(0, 204, 153));
        startGameButton.setForeground(Color.BLACK);
        initStartGameActionListener();
        startGameButton.addActionListener(gameStartActionListener);
    }

    public void initModeButton(String buttonText) {
        modeButton = new JButton(buttonText);
        modeButton.setBackground(new Color(255, 153, 255));
        modeButton.setForeground(Color.BLACK);
        initModeButtonActionListener();
        modeButton.addActionListener(modeButtonActionListener);
    }

    public void initEndGameButton(String buttonText) {
        endGameButton = new JButton(buttonText);
        endGameButton.setBackground(new Color(204, 0, 0));
        endGameButton.setForeground(Color.BLACK);
        initEndGameActionListener();
        endGameButton.setEnabled(false);
        endGameButton.addActionListener(gameEndActionListener);
    }

    protected void initStartGameActionListener() {
        gameStartActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int c=0; c<cardAmount; c++){
                    setButtonIcon(cardButtons[c],unrevealed,true);
                    cardButtons[c].setEnabled(true);
                }   
                initRevealedCardIcons(memory.randomize(), imagePath);
                timer.resetTimer();
                timer.startTimer();
                memory.gameReset();
                updateGameInfoLabel();
                startGameButton.setEnabled(false);
                endGameButton.setEnabled(true);
            }
        };
    }

    private void initEndGameActionListener(){
        gameEndActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                timer.stopTimer();
                int response = JOptionPane.showConfirmDialog(null, 
                "Você tem certeza que deseja finalizar o jogo?", "Confirmação", JOptionPane.OK_CANCEL_OPTION);
                if(response==JOptionPane.CANCEL_OPTION){
                    timer.startTimer();
                    return;
                }
                else{
                    loadEndGameWindow(e);
                }
            }
        };
    }

    private void initModeButtonActionListener(){
        modeButtonActionListener = new ActionListener(){
          public void actionPerformed(ActionEvent e){
            timer.stopTimer();
            Application.modesFrame.setFirstOpen(modesFrameThroughEndGame);
            Application.modesFrame.loadModesFrameThroughButton();    
          }  
        };
    }

    protected void loadEndGameWindow(ActionEvent e){
        timer.stopTimer();
        String message;
        if(memory.getPlayerHits()==cardAmount/2){
            message = "Você ganhou!";
        }
        else
            message = "Jogo finalizado";

        String[] options = {"Novo jogo", "Trocar modo/nível","Sair do jogo"};

        int choice = JOptionPane.showOptionDialog(null, 
            message+"\n"+timer.toString()+"\n"+getGameInfo(), message, 
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
            options, options[0]);

        //memory.gameReset();
        //timer.resetTimer();
            switch (choice){
                case (JOptionPane.YES_OPTION):
                    //timer.resetTimer();
                    //memory.gameReset();
                    //updateGameInfoLabel();
                    gameStartActionListener.actionPerformed(e);
                    break;
                case (JOptionPane.NO_OPTION):
                    modesFrameThroughEndGame = true;
                    Application.modesFrame.setFirstOpen(true);
                    modeButton.doClick();
                    modesFrameThroughEndGame = false;
                    break;
                case (JOptionPane.CANCEL_OPTION):
                    System.exit(0);
            }
    }

    protected JLabel initLabelTop() {
        ImageIcon topIcon = new ImageIcon(getClass().getResource("img/top.png"));
        labelTop = new JLabel(topIcon, JLabel.CENTER);
        return labelTop;
    }

    private JPanel loadMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        top.setBackground(new Color(255, 204, 255));
        top.add(initLabelTop());
        mainPanel.add(top, BorderLayout.NORTH);
        mainPanel.add(loadSecondaryPanel(), BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel loadSecondaryPanel() {
        JPanel secondaryPanel = new JPanel(new BorderLayout());
        secondaryPanel.add(initActionButtonsPanel(), BorderLayout.WEST);
        secondaryPanel.add(initGamePanel(columns, rows), BorderLayout.CENTER);
        return secondaryPanel;
    }
}

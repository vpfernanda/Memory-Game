package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Graphic extends JFrame {
    // Panels
    private JPanel actionButtonsPanel;
    protected JPanel gameButtonsPanel;

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
    private ImageIcon unrevealed; //Image of unrevealed cards
    private ImageIcon icons[];
    private ImageIcon img1; //First card of the attempt pair
    private ImageIcon img2; //Second card of the attempt pair

    // Timer
    protected GameTimer timer;

    //Strings
    private String gameWarningString;
    private String endGameString;
    protected String imagePath;

    // GameObject
    protected Memory memory;

    // Integers
    private int clickedCardButton1;
    private int clickedCardButton2;
    protected int cardAmount; //IMPORTANT VARIABLE
    protected int rows;
    protected int columns;

    private Dimension gamePanelDimension;

    //Default parameters to Graphic (superclass)
    //cardAmount: 20
    //imagePath: "img/Default/"
    //columns: 5
    //rows: 4

    public Graphic(int cardAmount, String imagePath, int columns, int rows, Dimension gamePanelDimension) {
        super("Jogo da Memória!");
        this.cardAmount = cardAmount;
        memory = new Memory(cardAmount);
        this.rows = rows;
        this.columns = columns;
        this.gamePanelDimension = gamePanelDimension;
        gameWarningString = "Atenção: Você clicou na mesma carta. Escolha uma carta DIFERENTE!";
        text = new JLabel();
        timer = new GameTimer();
        this.imagePath = imagePath;
        setUnrevealed(imagePath);
        text.setText("<html><br>Informações da partida:<br><br>"+getGameInfo()+"<html><br>");
        setAllFonts("Comic Sans MS");
        add(loadMainPanel());

        // Configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza a janela
        setVisible(true);
    }

    protected void setUnrevealed(String imgPath){
        unrevealed = new ImageIcon(getClass().getResource(imgPath+"cartela.jpg"));
    }

    public String getGameInfo() {
        return "\nAcertos: " +memory.getPlayerHits()+
        " \nErros: "+memory.getPlayerErrors()+"";
    }

    private void setAllFonts(String font){
        text.setFont(new Font(font, Font.BOLD, 12));
        timer.getElapsedTime().setFont(new Font(font, Font.BOLD, 13));
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

    public void setGameWarningString(String gameWarningString) {
         this.gameWarningString = gameWarningString;
    }

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
            cardButtons[i] = new JButton("" + i, unrevealed) {
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
            cardButtons[i].setEnabled(enable);
            cardButtons[i].setDisabledIcon(unrevealed);
            cardButtons[i].setMargin(new Insets(0, 0, 0, 0));
            cardButtons[i].setBorderPainted(true);
            cardButtons[i].setHorizontalAlignment(SwingConstants.CENTER);
            cardButtons[i].setVerticalAlignment(SwingConstants.CENTER);
            cardButtons[i].addActionListener(cardButtonsActionListener);
        }
    }

    //Main method of the game. Here is where the "magic" happens :-)
    private void initCardButtonsActionListener() {
        cardButtonsActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int buttonActionCommand = Integer.parseInt(event.getActionCommand());
                //updateGameInfoLabel();
                memory.clickRegister();
                switch (memory.getClickCounter()) {
                    case 1: // Primeiro clique do usuário.
                        clickedCardButton1 = buttonActionCommand;
                        setButtonIcon(cardButtons[clickedCardButton1], icons[clickedCardButton1]);
                        break;
                    case 2: // Segundo clique do usuário.
                        clickedCardButton2 = buttonActionCommand;
                        if (clickedCardButton1 == clickedCardButton2) {
                            text.setText(text.getText() + "<html><br>" + getGameWarningString() + "</html>");
                            text.setForeground(Color.RED);
                            memory.clickCancel();
                            break;
                        } else {
                            setButtonIcon(cardButtons[clickedCardButton2], icons[clickedCardButton2]);
                            img1 = (ImageIcon) cardButtons[clickedCardButton1].getIcon();
                            img2 = (ImageIcon) cardButtons[clickedCardButton2].getIcon();
                            if (memory.compareCards(img1, img2)) {
                                memory.playerHit();
                                updateGameInfoLabel();
                                JOptionPane.showMessageDialog(null, getPlayerHitString());
                                cardButtons[clickedCardButton1].setEnabled(false);
                                cardButtons[clickedCardButton1].setDisabledIcon(img1);
                                cardButtons[clickedCardButton2].setEnabled(false);
                                cardButtons[clickedCardButton2].setDisabledIcon(img2);
                                if(memory.getPlayerHits()==cardAmount/2)
                                    loadEndGameWindow(event);
                                return;
                            } 
                            memory.playerError();
                            updateGameInfoLabel();
                            JOptionPane.showMessageDialog(null, getPlayerErrorString());
                            for (int c = 0; c < cardAmount; c++)
                                cardButtons[c].setIcon(unrevealed);
                            
                            return;
                        }
                }
            }
        };
    }

    private void setButtonIcon(JButton button, ImageIcon icon) {
        Image scaledImage = icon.getImage().getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(scaledImage);
        image.setDescription(icon.getDescription());
        button.setIcon(image);
    }

    private void updateGameInfoLabel(){
        text.setText("<html><br>Informações da partida:<br><br>"+getGameInfo()+"<html><br>");
        text.setForeground(Color.BLACK);
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

    public JScrollPane initGamePanel(int columns, int rows) {
        gameButtonsPanel = new JPanel(new GridLayout(rows, columns, 5, 5));
        gameButtonsPanel.setPreferredSize(gamePanelDimension);
        initUnrevealedCardButtons(cardAmount, false);
        for (JButton b : cardButtons) {
            gameButtonsPanel.add(b);
        }
        return new JScrollPane(gameButtonsPanel); // Adiciona o painel ao JScrollPane
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
        initStartGameActionListener();
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
        initEndGameActionListener();
        endGameButton.setEnabled(false);
        endGameButton.addActionListener(gameEndActionListener);
    }

    private void initStartGameActionListener() {
        gameStartActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int c=0; c<cardAmount; c++){
                    cardButtons[c].setIcon(unrevealed);
                    cardButtons[c].setEnabled(true);
                }   
                initRevealedCardIcons(memory.randomize(), imagePath);
                timer.resetTimer();
                timer.startTimer();
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
                    // if(!newGame){ //turn the cards setting them to unrevealed if the player does not start a new game
                    //     for(int c=0; c<cardAmount; c++){
                    //     cardButtons[c].setEnabled(false);
                    //     cardButtons[c].setDisabledIcon(unrevealed);
                    //     }
                    // }
                    startGameButton.setEnabled(true);
                    endGameButton.setEnabled(false);
                }
            }
        };
    }

    private void loadEndGameWindow(ActionEvent e){
        timer.stopTimer();
        String message;
        if(memory.getPlayerHits()==cardAmount/2){
            message = "Você ganhou!";
        }
        else
            message = "Jogo finalizado";
        String[] options = {"Novo jogo", "Sair do jogo"};
        int choice = JOptionPane.showOptionDialog(null, 
            message+"\n"+timer.toString()+"\n"+getGameInfo(), "Encerrar jogo", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
            options, options[0]);
            memory.gameReset();
            timer.resetTimer();
            switch (choice){
                case (JOptionPane.YES_OPTION):
                    gameStartActionListener.actionPerformed(e);
                    break;
                case (JOptionPane.NO_OPTION):
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
        mainPanel.add(initLabelTop(), BorderLayout.NORTH);
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

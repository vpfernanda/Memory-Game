package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ModesFrame extends JFrame{
    private  ArrayList<String> modes; 
    private  ArrayList<String> themes;
    private JLabel modesLabel;
    private JLabel themesLabel;
    private JLabel playerNameLabel;
    private ButtonGroup modesButtonGroup;
    private ButtonGroup themesButtonGroup;
    private ArrayList<JRadioButton> modesButtonsList;
    private ArrayList<JRadioButton> themesButtonsList;
    private JButton okButton, closeWindowButton;
    private JTextField playerNameTextField;
    private ActionListener okButtonAListener;
    private ActionListener closeWindowButtonAListener;
    private String username;
    private String chosenMode;
    private String chosenTheme;
    private boolean firstOpen;
    private Graphic graphic;

    
    public ModesFrame(){
        super("Escolher um modo de jogo");
        modesButtonsList = new ArrayList<>();
        themesButtonsList = new ArrayList<>();
        modesButtonGroup = new ButtonGroup();
        themesButtonGroup = new ButtonGroup();
        firstOpen = true;
        this.setMaximumSize(new Dimension(300, 300));
        this.setMinimumSize(new Dimension(300, 300));
        this.setPreferredSize(new Dimension(300, 300));
        initCloseWindowButtonActionListener();
        initOkButtonActionListener();
        initModesArray();
        initThemesArray(); 
        modesLabel = new JLabel("Escolha o nível de dificuldade:");
        themesLabel = new JLabel("Escolha o tema do seu jogo:");
        this.setLayout(new BorderLayout());
        this.add(renderAll(), BorderLayout.CENTER);
        playerNameTextField.setMaximumSize(new Dimension(this.getWidth(), 20));
        playerNameTextField.setPreferredSize(new Dimension(this.getWidth(), 20));
        playerNameTextField.setMinimumSize(new Dimension(this.getWidth(), 20));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setVisible(true);

    }

    private void initModesArray(){
        modes = new ArrayList<>();
        modes.add("Fácil");
        modes.add("Médio");
        modes.add("Difícil");
        modes.add("Contra o tempo");
    }

    private void initThemesArray(){
        themes = new ArrayList<>();
        themes.add("Animals");
        themes.add("Food");
        themes.add("Objects");
    }

    private void initRadioButtonsGroup(ArrayList<String> arrayList, JPanel p, ButtonGroup bg, ArrayList<JRadioButton> list){
        JRadioButton radioButtonArray[];
        radioButtonArray = new JRadioButton[arrayList.size()];
        for(int c=0; c<arrayList.size(); c++){
            radioButtonArray[c] = new JRadioButton(arrayList.get(c));
            bg.add(radioButtonArray[c]);
            p.add(radioButtonArray[c]);
            list.add(radioButtonArray[c]);
        }
    }

    private JTextField initPlayerNameField(){
        this.playerNameTextField = new JTextField("Digite seu nome",0);
        this.playerNameTextField.setForeground(Color.gray);
        this.playerNameTextField.setFont(playerNameTextField.getFont().deriveFont(Font.ITALIC));
        playerNameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Remove placeholder text when focus is gained
                if (playerNameTextField.getText().equals("Digite seu nome")) {
                    playerNameTextField.setText("");
                    playerNameTextField.setForeground(Color.BLACK);
                    playerNameTextField.setFont(playerNameTextField.getFont().deriveFont(Font.PLAIN));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(playerNameTextField.getText().isEmpty())
                    playerNameTextField.setText("Digite seu nome");
                    playerNameTextField.setForeground(Color.gray);
                    playerNameTextField.setFont(getFont().deriveFont(Font.ITALIC));
            }
        });
        return playerNameTextField;
    } 

    private JPanel initOkCancelButtonsContainer(){
        //TODO: inicializar os botões ok e cancel em um container (Panel?) que tenha layout BOX, com X_AXIS
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        okButton = new JButton("Ok");
        closeWindowButton = new JButton("Fechar jogo");
        panel.add(okButton);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        closeWindowButton.addActionListener(closeWindowButtonAListener);
        okButton.addActionListener(okButtonAListener);
        panel.add(closeWindowButton);
        return panel;
    }

    private JPanel renderAll() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue()); // Espaço flexível vertical
        panel.add(Box.createHorizontalGlue()); // Espaço flexível horizontal
        playerNameLabel = new JLabel("Como você quer ser chamado?");

        playerNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(playerNameLabel);
    
        playerNameTextField = initPlayerNameField();
        playerNameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(playerNameTextField);
    
        modesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(modesLabel);
    
        initRadioButtonsGroup(modes, panel, modesButtonGroup, modesButtonsList);
    
        themesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(themesLabel);
    
        initRadioButtonsGroup(themes, panel, themesButtonGroup, themesButtonsList);
    
        JPanel buttonsPanel = initOkCancelButtonsContainer();
        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(buttonsPanel);

        panel.setMaximumSize(new Dimension(this.getWidth(), this.getHeight()));
        panel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        panel.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));

        return panel;
    }

    private void initCloseWindowButtonActionListener(){
        closeWindowButtonAListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(firstOpen)
                    System.exit(0);
            
                //important knowledge gained below!
                //as I'm referencing the instance of THIS class inside the actionPerformed's method declaration
                //(which belongs to another class), "this" is not enough to make a reference.
                //Syntax: ClassName.this.method(); :-) 
                if(!graphic.startGameButton.isEnabled())
                    graphic.timer.startTimer();
                ModesFrame.this.setVisible(false);
            }
        };
    }

    private void initOkButtonActionListener(){
        okButtonAListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(playerNameTextField.getText().equals("Digite seu nome")){
                    JOptionPane.showMessageDialog(rootPane, "Por favor, insira seu nome!");
                    return;
                }
                username = playerNameTextField.getText();
                if(getSelectedRadio(modesButtonsList)==null||getSelectedRadio(themesButtonsList)==null){
                    JOptionPane.showMessageDialog(rootPane, "Por favor, selecione um modo/tema de jogo.");
                    return;
                }
                chosenMode = getSelectedRadio(modesButtonsList);
                chosenTheme = getSelectedRadio(themesButtonsList);   
                hideModesFrame();  
                if(graphic==null){
                    graphicInitializer();
                }
                //do we have an instance of Graphic already running? then, let's restart it properly
                else if(graphic!=null){
                    graphic.dispose();
                    graphic = null;
                    graphicInitializer();
                }
                    
                
            }
        };
    }

    private void graphicInitializer(){
        switch (chosenMode){
            case "Fácil":
                graphic = new EasyMode(chosenTheme+"/");
                break;
            case "Médio":
                graphic = new MediumMode(chosenTheme+"/");
                break;
            case "Difícil":
                graphic = new HardMode(chosenTheme+"/");
                break;
            case "Contra o tempo":
                graphic = new TimeChallenge(chosenTheme+"/");
                break;
            
            }
    }

    private void hideModesFrame(){
        this.setVisible(false);
    }

    private String getSelectedRadio(ArrayList<JRadioButton> list){
        for(JRadioButton c : list){
            if(c.isSelected())
                return c.getText();
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public String getChosenMode() {
        return chosenMode;
    }

    public String getChosenTheme() {
        return chosenTheme;
    }

    public void loadModesFrameThroughButton(){
            playerNameTextField.setEnabled(false);
            if(firstOpen)
                closeWindowButton.setText("Sair do jogo");
            else
                closeWindowButton.setText("Voltar ao jogo");
            this.repaint();
            this.setVisible(true);
    }

    public void setFirstOpen(boolean b){
        firstOpen = b;
    }

    
    
    
    
    
}

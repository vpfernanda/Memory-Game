package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ModesFrame extends JFrame{
    private  ArrayList<String> modes; 
    private  ArrayList<String> themes;
    private JLabel modesLabel;
    private JLabel themesLabel;
    private JButton okButton, closeWindowButton;
    private JTextField playerNameTextField;
    private String username;
    private int chosenMode;
    private int chosenTheme;

    
    public ModesFrame(){
        this.setTitle("Escolher um modo de jogo");
        this.setMaximumSize(new Dimension(350, 350));
        this.setMinimumSize(new Dimension(350, 350));
        this.setPreferredSize(new Dimension(350, 350));
        initModesArray();
        initThemesArray(); 
        modesLabel = new JLabel("Escolha o nível de dificuldade:");
        themesLabel = new JLabel("Escolha o tema do seu jogo:");
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.add(new JLabel("Como você quer ser chamado?"));
        this.add(initPlayerNameField());
        this.add(modesLabel);
        initRadioButtonsGroup(modes);
        this.add(themesLabel);
        initRadioButtonsGroup(themes);
        okButton = new JButton("Ok");
        closeWindowButton = new JButton("Fechar jogo");
        this.add(okButton);
        this.add(closeWindowButton);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.playerNameTextField.setMinimumSize(new Dimension(playerNameTextField.getParent().getWidth(),20));
        this.playerNameTextField.setMaximumSize(new Dimension(playerNameTextField.getParent().getWidth(),20));
        this.playerNameTextField.setPreferredSize(new Dimension(playerNameTextField.getParent().getWidth(),20));
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
        themes.add("Animais");
        themes.add("Comida");
        themes.add("Objetos");
    }

    private void initRadioButtonsGroup(ArrayList<String> arrayList){
        JRadioButton radioButtonArray[];
        radioButtonArray = new JRadioButton[arrayList.size()];
        ButtonGroup buttonGroup = new ButtonGroup();
        for(int c=0; c<arrayList.size(); c++){
            radioButtonArray[c] = new JRadioButton(arrayList.get(c));
            buttonGroup.add(radioButtonArray[c]);
            this.add(radioButtonArray[c]);
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

    private void initOkCancelButtonsContainer(){
        //TODO: inicializar os botões ok e cancel em um container (Panel?) que tenha layout BOX, com X_AXIS
    }
    
    
}

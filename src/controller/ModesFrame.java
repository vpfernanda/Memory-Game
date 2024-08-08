package controller;

import java.awt.Dimension;
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
    //private ButtonGroup modesButtons;
    //private ButtonGroup themesButtons;
    private JButton okButton, closeWindowButton;
    private JTextField playerName;
    
    public ModesFrame(){
        this.setTitle("Escolher um modo de jogo");
        initModesArray();
        initThemesArray(); 
        modesLabel = new JLabel("Escolha o nível de dificuldade:");
        themesLabel = new JLabel("Escolha o tema do seu jogo:");
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.playerName = new JTextField("Como você quer ser chamado?",50);
        this.add(playerName);
        this.add(modesLabel);
        initRadioButtonsGroup(modes);
        this.add(themesLabel);
        initRadioButtonsGroup(themes);
        okButton = new JButton("Ok");
        closeWindowButton = new JButton("Fechar jogo");
        this.add(okButton);
        this.add(closeWindowButton);
        this.setPreferredSize(new Dimension(350, 400));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    
    
}


package memoriamain;

import java.awt.BorderLayout;
//import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Fernanda
 */
public class Memoria extends JFrame implements ActionListener {
    private JTextArea text;
    private JLabel labelTop;
    private List<Integer> randomList;
    private JButton buttons[];
    private JPanel panel;
    private ImageIcon unrevealed, top;
    private ImageIcon icones[];
    private ImageIcon img1;
    private ImageIcon img2;
    private int clickedButton1;
    private int clickedButton2;
    private int clickCounter=0;
    //private Container container;
    private int cardAmount;
    private int hits=0;
    private int errors=0;
    private GameTimer timer;
    private JLabel timerJLabel;
    
    Memoria(int mode){
        super("Jogo da Memoria!");
        loadMode(mode);
        
    }
    
    public void actionPerformed (ActionEvent event){
        int buttonActionCommand = Integer.parseInt(event.getActionCommand());
        clickCounter++;
        switch (clickCounter){
            case 1: //First user click.
                clickedButton1=buttonActionCommand;
                buttons[clickedButton1].setIcon(icones[clickedButton1]); //Reveals the image of the card.
                break;
            case 2: //Second user click.
                clickedButton2=buttonActionCommand;
                if(clickedButton1==clickedButton2){ //User clicked the same button.
                    text.setText(gameInfo()+"\nNÃO PODE CLICAR NA\n MESMA CARTA!");
                    clickCounter=1;
                    break;
                }
                else{
                    //clickedButton2=buttonActionCommand;
                    buttons[clickedButton2].setIcon(icones[clickedButton2]); //Reveals the image of the card.
                    img1=(ImageIcon)buttons[clickedButton1].getIcon();
                    img2=(ImageIcon)buttons[clickedButton2].getIcon();
                    compareCards();
                    break;
                }   
        }   
    }
    
    private String gameInfo(){
        return "Bem vindo ao jogo da memória! \nAcertos:"+hits+"\nErros:"+errors;
    }  

    private void compareCards(){
        if(img1.getDescription().equals(img2.getDescription()))
            playerHit();
        else
            playerError();
    }

    private void playerHit(){
        hits++;
        if(hits==cardAmount/2){
            //timer.stopTimer();
            JOptionPane.showMessageDialog( null, "Você ganhou! Parabéns!");
        }
        else {
            text.setText(gameInfo()+"\nPARABÉNS! ACERTOU!");
            JOptionPane.showMessageDialog( null, "Acertou! Boa memória!");
                   
            buttons[clickedButton1].setEnabled(false);
            buttons[clickedButton2].setEnabled(false);
            buttons[clickedButton1].setDisabledIcon(img1);
            buttons[clickedButton2].setDisabledIcon(img2);
            clickCounter=0;
        }
       
    }

    private void playerError(){
        errors++;
        JOptionPane.showMessageDialog( null, "Errou, errou feio, errou rude!");
        for(int s=0; s<buttons.length; s++) 
            buttons[s].setIcon(unrevealed);
        text.setText("Bem vindo ao jogo da memória! \nAcertos:"+hits+"\nErros:"+errors);
        clickCounter=0;
    }

    private void imageIconInicialize(int cardAmount, String imagePath){
        icones = new ImageIcon[cardAmount];
        int counterList = 0;
        for(int counter=0; counter<cardAmount/2; counter++){
            icones[randomList.get(counterList)]=new ImageIcon(getClass().getResource(imagePath+"img"+(counter+1)+".jpg"));
            icones[randomList.get(counterList)].setDescription(""+counter+1);
            counterList++;
            icones[randomList.get(counterList)]=new ImageIcon(getClass().getResource(imagePath+"img"+(counter+1)+".jpg"));
            icones[randomList.get(counterList)].setDescription(""+counter+1);
            counterList++;
        }    
    }

    private void randomize(int cardAmount){
        Random r = new Random();
        Integer n;
        while(randomList.size() < cardAmount ){
            n = r.nextInt(cardAmount);
            if(!(randomList.contains(n)))
                randomList.add(n);
        }
    }

    private void initUnrevealedCards(int cardAmount){
        buttons = new JButton[cardAmount];
        for(int i=0; i<buttons.length; i++){
            buttons[i] = new JButton(""+i, unrevealed);
            panel.add(buttons[i]);
            buttons[i].addActionListener(this);
        }
    }

    private void loadMode(int mode){
        int cardAmount;
        unrevealed = new ImageIcon(getClass().getResource("img/cartela.jpg"));
        text = new JTextArea();
        /*timer = new GameTimer();
        timer.startTimer();
        JLabel timerLabel = new JLabel();
        timerLabel = timer.getElapsedTime();*/
        text.setText(gameInfo());
        //text.add(timerLabel);
        text.setEditable(false);
        labelTop = new JLabel();
        labelTop.setIcon(top);
        //container = getContentPane();
        panel = new JPanel();
        switch(mode){
            case 1:
            {
                cardAmount = 20;
                randomList = new ArrayList<>(cardAmount);
                randomize(cardAmount);
                imageIconInicialize(cardAmount,"img/");
                top = new ImageIcon (getClass().getResource("img/top.png"));
                initUnrevealedCards(cardAmount);
                timer = new GameTimer();
                timer.startTimer();
                panel.setLayout(new GridLayout(4,5,5,5));
                add(labelTop, BorderLayout.PAGE_START);
                add(text, BorderLayout.LINE_START);
                add(timer.getElapsedTime(), BorderLayout.AFTER_LAST_LINE);
                add(panel, BorderLayout.CENTER);
            }

            break;

        }
        
       
    }
    
}

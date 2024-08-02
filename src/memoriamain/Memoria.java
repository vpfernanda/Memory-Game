
package memoriamain;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.SwingConstants;

/**
 *
 * @author Fernanda
 */
public class Memoria {
    private JTextArea text;
    private JLabel labelTop;
    private ArrayList<Integer> randomList;
    private JButton buttons[];
    private JButton startGameButton;
    private JPanel panel;
    private ImageIcon unrevealed, top;
    public ImageIcon icones[];
    private ImageIcon img1;
    private ImageIcon img2;
    private int clickedButton1;
    private int clickedButton2;
    private int clickCounter=0;
    private int cardAmount;
    private int hits=0;
    private int errors=0;
    private GameTimer timer;
    

    // public void actionPerformed (ActionEvent event){
    //     int buttonActionCommand = Integer.parseInt(event.getActionCommand());
    //     clickCounter++;
    //     switch (clickCounter){
    //         case 1: //First user click.
    //             clickedButton1=buttonActionCommand;
    //             buttons[clickedButton1].setIcon(icones[clickedButton1]); //Reveals the image of the card.
    //             break;
    //         case 2: //Second user click.
    //             clickedButton2=buttonActionCommand;
    //             if(clickedButton1==clickedButton2){ //User clicked the same button.
    //                 text.setText(gameInfo()+"\nNÃO PODE CLICAR NA\n MESMA CARTA!");
    //                 clickCounter=1;
    //                 break;
    //             }
    //             else{
    //                 //clickedButton2=buttonActionCommand;
    //                 buttons[clickedButton2].setIcon(icones[clickedButton2]); //Reveals the image of the card.
    //                 img1=(ImageIcon)buttons[clickedButton1].getIcon();
    //                 img2=(ImageIcon)buttons[clickedButton2].getIcon();
    //                 compareCards();
    //                 break;
    //             }   
    //     }   
    // }
    
    private String gameInfo(){
        return "Bem vindo ao jogo da memória! \nAcertos:"+hits+"\nErros:"+errors;
    }  

    public boolean compareCards(ImageIcon img1, ImageIcon img2){
        clickReset();
        return (img1.getDescription().equals(img2.getDescription()));
    }

    public void playerHit(){
        hits++;
        if(hits==cardAmount/2){
            JOptionPane.showMessageDialog( null, "Você ganhou! Parabéns! \n");
        }
        clickCounter=0;
    }

    public void playerError(){
        errors++;
        clickCounter=0;
    }

    public void clickRegister(){
        clickCounter++;
    }

    public void clickCancel(){
        clickCounter--;
    }

    public void clickReset(){
        clickCounter=0;
    }

    public int getClickCounter(){
        return clickCounter;
    }

    public ArrayList<Integer> randomize(int cardAmount){
        randomList = new ArrayList<>();
        Random r = new Random();
        Integer n;
        while(randomList.size() < cardAmount ){
            n = r.nextInt(cardAmount);
            if(!(randomList.contains(n)))
                randomList.add(n);
        }
        return randomList;
    }

    public int getPlayerHits(){
        return hits;
    }

    public int getPlayerErrors(){
        return errors;
    }

    public void gameReset(){
        hits = 0;
        errors = 0;
        clickCounter = 0;
    }

    

    // private void loadMode(int mode){
    //     unrevealed = new ImageIcon(getClass().getResource("img/cartela.jpg"));
    //     text = new JTextArea();
    //     /*timer = new GameTimer();
    //     timer.startTimer();
    //     JLabel timerLabel = new JLabel();
    //     timerLabel = timer.getElapsedTime();*/
    //     text.setText(gameInfo());
    //     //text.add(timerLabel);
    //     text.setEditable(false);
    //     labelTop = new JLabel();
    //     labelTop.setIcon(top);
    //     startGameButton = new JButton();
    //     startGameButton.setText("Jogar");
    //     //container = getContentPane();
    //     panel = new JPanel();
    //     switch(mode){
    //         case 1:
    //         {
    //             loadMode1();
    //         }

    //         break;

    //     }
        
       


    // public void loadMode1(){
    //     cardAmount = 20;
    //     randomList = new ArrayList<>(cardAmount);
    //     randomize(cardAmount);
    //     imageIconInicialize(cardAmount,"img/");
    //     top = new ImageIcon (getClass().getResource("img/top.png"));
    //     labelTop.setIcon(top);
    //     initUnrevealedCards(cardAmount);
    //     timer = new GameTimer();
    //     timer.startTimer();
    //     panel.setLayout(new GridLayout(4,5, 2, 2));
    //     add(labelTop, BorderLayout.PAGE_START);
    //     add(startGameButton, BorderLayout.BEFORE_LINE_BEGINS);
    //     add(text, BorderLayout.LINE_START);
    //     add(timer.getElapsedTime(), BorderLayout.AFTER_LAST_LINE);
    //     add(panel, BorderLayout.CENTER);
    // }
    
}

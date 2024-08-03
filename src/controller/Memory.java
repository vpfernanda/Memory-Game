
package controller;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 *
 * @author Fernanda
 */
public class Memory {
    private ArrayList<Integer> randomList;
    
    private int clickCounter;
    private int cardAmount;
    private int hits;
    private int errors;

    public Memory(int cardAmount){
        clickCounter=0;
        this.cardAmount = cardAmount;
        hits = 0;
        errors = 0;
    }
    
   
    public ArrayList<Integer> randomize(){
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

    public int getClickCounter(){
        return clickCounter;
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

}

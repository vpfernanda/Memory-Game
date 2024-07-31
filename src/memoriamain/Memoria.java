
package memoriamain;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
// import java.util.logging.Level;
// import java.util.logging.Logger;
// import javax.swing.Icon;
// import java.awt.Component;

/**
 *
 * @author Fernanda
 */
public class Memoria extends JFrame implements ActionListener {
    private JTextArea text;
    private JLabel label;
    private List<Integer> randomList;
    private JButton botoes[];
    private JPanel panel;
    private ImageIcon unrevealed, top;
    private ImageIcon icones[];
    private ImageIcon imagem1;
    private ImageIcon imagem2;
    private Container container;
    //private boolean value;
    private int num[];
    private int acertos, erros;
    private int cont;
    private int b1,b2;
    private long time;
    
    Memoria(int mode){
        super("Jogo da Memoria!");
        loadMode(mode);
    }
    
   public void actionPerformed (ActionEvent event){
        int bot = Integer.parseInt(event.getActionCommand());
        
        cont++;
        if(cont==1){
            b1=bot;
           botoes[b1].setIcon(icones[b1]);  
        }
        if(cont==2){
            b2=bot;
            botoes[b2].setIcon(icones[b2]);
            if(b1==b2){
                cont=1;
                text.setText("Bem vindo ao jogo da memória! \nAcertos:"+acertos+"\nErros:"+erros+
                        "\nNÃO PODE CLICAR NA\n MESMA CARTA!");
                
            }
            else{
            imagem1=(ImageIcon) botoes[b1].getIcon();
            imagem2=(ImageIcon) botoes[b2].getIcon();
            if(imagem1.getDescription().equals(imagem2.getDescription())){
                acertos++;
                
                text.setText("Bem vindo ao jogo da memória! \nAcertos:"+acertos+"\nErros:"+erros+
                        "\nPARABÉNS! ACERTOU!");
                JOptionPane.showMessageDialog( null, "Acertou! Boa memória!");
               
                botoes[b1].setEnabled(false);
                botoes[b2].setEnabled(false);
                botoes[b1].setDisabledIcon(imagem1);
                botoes[b2].setDisabledIcon(imagem2);
                cont=0;
                if(acertos==10){
                    JOptionPane.showMessageDialog(null, "Você ganhou!"
                            + "\nSeus acertos: "+acertos+"\nSeus erros: "+erros);
                    System.exit(0);
                }
                
            }
            else{
                JOptionPane.showMessageDialog( null, "Errou, errou feio, errou rude!");
                   for(int s=0; s<botoes.length; s++) 
                    botoes[s].setIcon(unrevealed);
                    
                erros++;
                text.setText("Bem vindo ao jogo da memória! \nAcertos:"+acertos+"\nErros:"+erros);
                cont=0;
            }  
        }
    }
        
            
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
        botoes = new JButton[cardAmount];
        for(int i=0; i<botoes.length; i++){
            botoes[i] = new JButton(""+i, unrevealed);
            panel.add(botoes[i]);
            botoes[i].addActionListener(this);
        }
    }

    private void loadMode(int mode){
        int cardAmount;
        unrevealed = new ImageIcon(getClass().getResource("img/cartela.jpg"));
        text = new JTextArea();
        text.setText("Bem vindo ao jogo da memória! \nAcertos:0 \nErros: 0");
        text.setEditable(false);
        label = new JLabel();
        label.setIcon(top);
        container = getContentPane();
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
                
                panel.setLayout(new GridLayout(4,5,5,5));
                add(label, BorderLayout.PAGE_START);
                add(text, BorderLayout.BEFORE_LINE_BEGINS);
                add(panel, BorderLayout.CENTER);
            }

            break;

        }
        
       
    }
    
}

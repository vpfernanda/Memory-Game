/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package memoriamain;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
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
    private JLabel label;
    private Random random;
    private JButton botoes[];
    private JPanel panel;
    private ImageIcon virado, top;
    private ImageIcon icones[];
    private ImageIcon imagem1;
    private ImageIcon imagem2;
    private Container container;
    //private boolean value;
    private int num[], a;
    private int acertos, erros;
    private int cont;
    private int b1,b2;
    private long time;
    
    

    
    Memoria(){
        super("Jogo da Memoria!");
        icones = new ImageIcon[20];
        label = new JLabel();
        num = new int[20];
        random = new Random();
        
        top = new ImageIcon (getClass().getResource("img/top.png"));
       
        text = new JTextArea();
        text.setText("Bem vindo ao jogo da memória! \nAcertos:0 \nErros: 0");
        text.setEditable(false);
        botoes = new JButton[20];
        virado = new ImageIcon(getClass().getResource("img/cartela.jpg"));
        top = new ImageIcon (getClass().getResource("img/top.png"));
        label.setIcon(top);
        //value=false;
        cont=0;
        
         int pos=0;
        for(int x=0; x<num.length;x++){
            num[x]=-1;
        }
            random=new Random();
        for(int z=0; z<num.length; z++) {
            do
                pos = random.nextInt(20);
            while(num[pos]!=-1);
                num[pos]=z;
        }
        
        
        for(int r=0; r<10; r++){
            int img = r+1;
            icones[num[r]]=new ImageIcon(getClass().getResource("img/img"+img+".jpg"));
            icones[num[r]].setDescription("" +img);
        }
        for(int r2=10; r2<20; r2++){
            int img2 = r2-9;
            icones[num[r2]]=new ImageIcon(getClass().getResource("img/img"+img2+".jpg"));
            icones[num[r2]].setDescription("" +img2);
        }
        
        
  
        
        container = getContentPane();
        panel = new JPanel();
        panel.setLayout(new GridLayout(4,5,5,5));
        
       
        
        for(int i=0; i<botoes.length; i++){
            botoes[i] = new JButton(""+i, virado);
            panel.add(botoes[i]);
            botoes[i].addActionListener(this);
        }
        
        add(label, BorderLayout.PAGE_START);
        add(text, BorderLayout.BEFORE_LINE_BEGINS);
        add(panel, BorderLayout.CENTER);
        
       
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
                    botoes[s].setIcon(virado);
                    
                erros++;
                text.setText("Bem vindo ao jogo da memória! \nAcertos:"+acertos+"\nErros:"+erros);
                cont=0;
            }  
        }
        }
        
            
    }
    
    
}

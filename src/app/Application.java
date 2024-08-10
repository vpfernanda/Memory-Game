/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;
import controller.*;



/**
 *
 * @author Fernanda
 */
public class Application {
        public static ModesFrame modesFrame;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        modesFrame = new ModesFrame();
        if(!modesFrame.isVisible()){
            String mode = modesFrame.getChosenMode();
            
        }
    }
        
}

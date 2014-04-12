/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Gui;

import java.beans.PropertyChangeEvent;
import javax.swing.SwingWorker;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 *
 * @author Other
 */


public class GuiUpdater  {
    
    private SwingPropertyChangeSupport pChange = new SwingPropertyChangeSupport(this);
    public static final String MESSAGE = "1";
    
    
    public void updategui(String msg){
        
        String old;
        old = "asdf";
        
        pChange.firePropertyChange(MESSAGE,old , msg);
        
    }


    
}

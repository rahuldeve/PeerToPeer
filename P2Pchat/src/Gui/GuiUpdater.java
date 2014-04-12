/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.SwingWorker;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 *
 * @author Other
 */


public class GuiUpdater implements Runnable  {
    
    private SwingPropertyChangeSupport pChange = new SwingPropertyChangeSupport(this);
    public static final String MESSAGE = "1";
    
    
    public void updategui(String msg){
        
        String old;
        old = "asdf";
        
        pChange.firePropertyChange(MESSAGE,old , msg);
        
    }
    
    public void addPropertyChangeListener (PropertyChangeListener listener){
            pChange.addPropertyChangeListener(MESSAGE, listener);
        }
    
    
    public void removePropertyChangeListener(PropertyChangeListener listener){
            pChange.removePropertyChangeListener(MESSAGE, listener);
        }

    @Override
    public void run() {
        
        
        
    }


    
}

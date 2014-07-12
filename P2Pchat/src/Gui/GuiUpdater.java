/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Gui;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author rahul dev e
 */
public class GuiUpdate implements Serializable {
    
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    
    
    private Object sampleProperty;
    
    private PropertyChangeSupport propertySupport;
    
    public GuiUpdate() {
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public Object getSampleProperty() {
        return sampleProperty;
    }
    
    //change type to message?
    public void updateGui(String propertyName, Object value) {
        Object oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(propertyName, oldValue, sampleProperty);
    }
    
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(propertyName,listener);
    }
    
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(propertyName,listener);
    }
    
}

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
public class Guiupdate implements Serializable {
    
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    
    private Object sampleProperty;
    
    private PropertyChangeSupport propertySupport;
    
    public Guiupdate() {
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public Object getSampleProperty() {
        return sampleProperty;
    }
    
    //change type to message?
    public void updateGui(Object value) {
        Object oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
}

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.parts;

import gmsis.di.DependencyManager;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 
 * @author Abdullah
 */
public class PartsUI { 
     private StackPane root;
     private Node partsScreen;
     
     private DependencyManager dm; 
    
     public PartsUI(DependencyManager dm) throws IOException{
         this.dm = dm;
         
         root = new StackPane();
         partsScreen = new PartsComponentLoader(dm).load();
         root.getChildren().add(partsScreen);
         
         
     }
     
     public Node get(){
         return root;
     }
}

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.authentication;

import gmsis.controllers.authentication.AuthenticationController;
import gmsis.di.DependencyManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class AuthenticationComponentLoader {
    private DependencyManager dm;
    
    public AuthenticationComponentLoader(DependencyManager dm) {
        this.dm = dm;
    }
    
    public Node load() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(("/gmsis/ui/authentication/AuthenticationComponent.fxml")));
        Node auth = loader.load();
        AuthenticationController ctrl = loader.getController();
        ctrl.setDependencyManager(this.dm);
        return auth;
    }
}

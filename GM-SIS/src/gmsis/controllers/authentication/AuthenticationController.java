/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.authentication;

import gmsis.di.DependencyManager;
import gmsis.models.authentication.User;
import gmsis.persistence.users.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class AuthenticationController implements Initializable {
    @FXML private Node root;
    @FXML private TextField userInput;
    @FXML private PasswordField passwordInput;
    private DependencyManager dm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private String someHashFunction(String in) {
        return in;
    }
    
    public void makeLogin(ActionEvent e) throws Exception {
        int userId = Integer.parseInt(userInput.getText()); // uncaught
        UserRepository repo = dm.getUserRepository();
        User u = repo.get(User.class, (long) userId); // uncaught
        if (u == null) {
            new Alert(Alert.AlertType.ERROR, "Please enter the right ID and Password").showAndWait();
            userInput.clear();
            passwordInput.clear();
            return;
        }
        
        if (u.getPassword().equals(someHashFunction(passwordInput.getText()))) {
            root.fireEvent(new UserLoggedInEvent(u));
        } else {
            new Alert(Alert.AlertType.ERROR, "Please enter the right ID and Password").showAndWait();
            userInput.clear();
            passwordInput.clear();
            return;
        }
        
    }

    public void setDependencyManager(DependencyManager dm) {
        this.dm = dm;
    }
}

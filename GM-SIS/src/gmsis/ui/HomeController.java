/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui;

import gmsis.controllers.authentication.UserLoggedOutEvent;
import gmsis.models.authentication.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class HomeController implements Initializable {

    @FXML
    private TabPane homeTabs;

    @FXML
    private Tab usersTab;

    @FXML
    private Label userString;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeTabs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> homeTabs.fireEvent(new TabChangeEvent(newValue)));
    }

    public void setTabByName(String name) {
        Tab tab = homeTabs.getTabs().filtered(t -> t.getText().equals(name)).get(0);
        homeTabs.getSelectionModel().select(tab);
    }

    public void setTabsForUser(User u) {
        if (!u.getIsAdmin()) {
            homeTabs.getTabs().remove(usersTab);
        }

        userString.setText("Logged in as " + u.getFirstName() + ' ' + u.getLastName());
    }

    public void logOut(ActionEvent e) {
        homeTabs.fireEvent(new UserLoggedOutEvent());
    }
}

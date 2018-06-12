/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.users;

import com.jfoenix.controls.JFXPasswordField;
import gmsis.di.DependencyManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by filip on 17/03/17.
 */
public class ChangePasswordPopupController implements Initializable {
    public JFXPasswordField passwordField;
    private DependencyManager dm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setDependencyManager(DependencyManager dependencyManager) {
        this.dm = dependencyManager;
    }

    public void changePassword(ActionEvent actionEvent) {
        passwordField.fireEvent(PasswordChangePopupFinishEvent.changed(passwordField.getText()));
    }

    public void closePopup(ActionEvent actionEvent) {
        passwordField.fireEvent(PasswordChangePopupFinishEvent.notChanged());
    }
}

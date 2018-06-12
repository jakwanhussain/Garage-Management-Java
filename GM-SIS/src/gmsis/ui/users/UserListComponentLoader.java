/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.ui.users;

import gmsis.controllers.users.UserListController;
import gmsis.di.DependencyManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by filip on 17/03/17.
 */
public class UserListComponentLoader {
    private DependencyManager dm;

    public UserListComponentLoader(DependencyManager dm) {
        this.dm = dm;
    }

    public Node load() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/users/UserList.fxml"));
        Node node = loader.load();
        UserListController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        Platform.runLater(() -> {
            node.getParent().addEventHandler(ActionEvent.ACTION, e -> {
                try {
                    ctrl.update();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not update user list:", ex);
                }
            });
        });
        return node;
    }

}

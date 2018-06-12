/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.ui.users;

import gmsis.controllers.users.ChangePasswordPopupController;
import gmsis.di.DependencyManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Created by filip on 17/03/17.
 */
public class ChangePasswordPopupComponentLoader {
    private Node popupContents;

    public ChangePasswordPopupComponentLoader(DependencyManager dm) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/users/ChangePasswordPopup.fxml"));
        popupContents = loader.load();
        ChangePasswordPopupController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
    }

    public Node get() {
        return popupContents;
    }
}

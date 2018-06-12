/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.ui.users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import gmsis.controllers.users.MechanicChangeEvent;
import gmsis.models.authentication.User;
import gmsis.models.bookings.Mechanic;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.InputStream;

public class MechanicRateEditorComponentLoader {
    private static InputStream res(String s) {
        return MechanicRateEditorComponentLoader.class.getResourceAsStream(s);
    }

    public static Node loadExistingEditor(User user) {
        HBox box = new HBox();
        box.getStylesheets().add("/gmsis/ui/main.css");
        box.setPadding(new Insets(5, 10, 5, 10));
        box.setSpacing(10);

        JFXTextField rate = new JFXTextField();
        rate.setLabelFloat(true);
        rate.setPromptText("Rate");
        rate.getValidators().add(new DoubleValidator());
        rate.setText(Double.toString(user.getMechanic().getHourlyRate()));

        JFXButton confirm = new JFXButton(null, new ImageView(new Image(res("/gmsis/ui/users/check.png"), 24.0, 24.0, true, true)));
        confirm.setButtonType(JFXButton.ButtonType.RAISED);
        confirm.getStyleClass().add("btn-raised");
        confirm.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        JFXButton delete = new JFXButton("Delete rate");
        delete.getStyleClass().add("btn-danger");

        confirm.setOnAction(e -> {
            user.getMechanic().setHourlyRate(Double.parseDouble(rate.getText()));
            box.fireEvent(MechanicChangeEvent.save(user, user.getMechanic()));
        });

        delete.setOnAction(e -> box.fireEvent(MechanicChangeEvent.delete(user, user.getMechanic())));

        box.getChildren().addAll(rate, confirm, delete);

        return box;
    }

    public static Node loadNewEditor(User user) {
        JFXButton create = new JFXButton("Add Mechanic Rate");
        create.getStylesheets().add("/gmsis/ui/main.css");
        create.getStyleClass().add("btn");
        create.setOnAction(e -> {
            Mechanic mechanic = new Mechanic();
            user.setMechanic(mechanic);
            create.fireEvent(MechanicChangeEvent.save(user, mechanic));
        });
        return create;
    }
}

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.users;

import com.jfoenix.controls.JFXButton;
import gmsis.di.DependencyManager;
import gmsis.models.authentication.User;
import gmsis.ui.users.ChangePasswordPopupComponentLoader;
import gmsis.ui.users.MechanicRateEditorComponentLoader;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Popup;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by filip on 17/03/17.
 * stephen added code on 23/03/17
 */
public class UserListController implements Initializable{
    public TableColumn<User, Long> idCol;
    public TableColumn<User, String> firstNameCol;
    public TableColumn<User, String> lastNameCol;
    public TableColumn<User, Boolean> isAdminCol;
    public TableColumn<User, User> passwordCol;
    public TableColumn<User, User> mechanicCol;
    public TableView<User> table;

    private DependencyManager dm;

    private ObservableList<User> userList;
    private User selectedUser = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
            drawTable();
    }
    
    public void drawTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        firstNameCol.setOnEditCommit(e -> {
            String firstName = e.getNewValue();
            User user = e.getRowValue();
            user.setFirstName(firstName);
            try {
                dm.getUserRepository().save(user);
                update();

            } catch (Exception ex) {
                Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        lastNameCol.setOnEditCommit(e -> {
            String lastName = e.getNewValue();
            User user = e.getRowValue();
            user.setLastName(lastName);
            try {
                dm.getUserRepository().save(user);
                update();

            } catch (Exception ex) {
                Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        isAdminCol.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        isAdminCol.setCellFactory(CheckBoxTableCell.forTableColumn(x -> new SimpleBooleanProperty(x == 0)));
        isAdminCol.setOnEditCommit(e -> {
            User user = e.getRowValue();

            Boolean admin = e.getNewValue();
            user.setIsAdmin(admin);
            try {
                dm.getUserRepository().save(user);
                update();

            } catch (Exception ex) {
                Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        passwordCol.setCellValueFactory(row -> new ReadOnlyObjectWrapper<>(row.getValue()));
        passwordCol.setCellFactory(col -> {
            JFXButton editButton = new JFXButton("Change Password");
            editButton.getStyleClass().add("btn");
            TableCell<User, User> cell = new TableCell<User, User>() {
                @Override
                protected void updateItem(User item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                    }
                }
            };

            editButton.setOnAction(e -> showPasswordUpdateDialog(cell.getItem()));

            return cell;
        });

        mechanicCol.setCellValueFactory(row -> new ReadOnlyObjectWrapper<>(row.getValue()));
        mechanicCol.setCellFactory(col -> {
            TableCell<User, User> cell = new TableCell<User, User>() {
                @Override
                protected void updateItem(User item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        return;
                    }

                    if (item.getMechanic() != null) {
                        setGraphic(MechanicRateEditorComponentLoader.loadExistingEditor(item));
                    } else {
                        setGraphic(MechanicRateEditorComponentLoader.loadNewEditor(item));
                    }
                }
            };
            

            cell.addEventHandler(MechanicChangeEvent.SAVE_MECHANIC, e -> {
                try {
                    dm.getMechanicsRepository().save(e.getMechanic());
                    dm.getUserRepository().save(e.getUser());
                    update();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not save mechanic information:", ex);

                }
            });

            cell.addEventHandler(MechanicChangeEvent.DELETE_MECHANIC, e -> {
                try {
                    dm.getMechanicsRepository().delete(e.getMechanic());
                    e.getUser().setMechanic(null);
                    dm.getUserRepository().save(e.getUser());
                    update();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not delete mechanic information:", ex);
                }
            });

            return cell;
        });
            table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                selectedUser= table.getSelectionModel().getSelectedItem();
            }
        });
        userList = table.getItems();
    }
    

    private void showPasswordUpdateDialog(@NotNull User user) {
        final Popup popup = new Popup();
        try {
            popup.getContent().add(new ChangePasswordPopupComponentLoader(dm).get());
            popup.show(table.getScene().getWindow());
            popup.addEventHandler(PasswordChangePopupFinishEvent.NOT_CHANGED, e -> popup.hide());
            popup.addEventHandler(PasswordChangePopupFinishEvent.CHANGED, e -> {
                user.setPassword(e.getNewPassword());
                try {
                    dm.getUserRepository().save(user);
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not set user password:", ex);
                }
                popup.hide();
            });
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error displaying password change dialogue:", e);
        }
    }

    public void update() throws Exception {
        userList.clear();
        userList.addAll(dm.getUserRepository().all(User.class));
    }

    public void setDependencyManager(DependencyManager dependencyManager) {
        this.dm = dependencyManager;

        try {
            update();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Could not display the user list");
            a.setContentText(e.toString());
            a.show();
        }
    }
    public void addRow(ActionEvent e){
        User newUser = new User();
        newUser.setIsAdmin(false);
        userList.add(newUser);
        try {
            dm.getUserRepository().save(newUser);
            update();
        } catch (Exception ex) {
            Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteUser(ActionEvent e){
        if(selectedUser==null){
            new Alert(Alert.AlertType.ERROR, "Please select a customer to delete.").showAndWait();
        } else {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Delete this User?");
            confirm.setHeaderText("Do you want to delete User " + selectedUser.getFirstName() + " " + selectedUser.getLastName() + "?");
            confirm.setContentText("Are you sure you wish to delete this User.");

            ButtonType deleteBtn = new ButtonType("Delete User  " + selectedUser.getFirstName() + " " + selectedUser.getLastName(), ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirm.getButtonTypes().setAll(cancelBtn, deleteBtn);

            ButtonType result = confirm.showAndWait().orElse(cancelBtn);
            if (result == deleteBtn) {
                try {
                    dm.getUserRepository().delete(selectedUser);
                    selectedUser = null;
                    update();
                    table.getSelectionModel().select(null);
                } catch (Exception ex) {
                    Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

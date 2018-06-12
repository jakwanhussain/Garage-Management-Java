/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.ui;

import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.skins.JFXProgressBarSkin;
import gmsis.controllers.authentication.UserLoggedInEvent;
import gmsis.controllers.authentication.UserLoggedOutEvent;
import gmsis.controllers.bookings.BookingViewEvent;
import gmsis.controllers.customers.CustomerBookingEvent;
import gmsis.controllers.customers.CustomerEditRequestEvent;
import gmsis.controllers.specialistRepairs.SPCBookingNewWithBookingInfoRequestEvent;
import gmsis.controllers.specialistRepairs.SPCBookingViewRequestEvent;
import gmsis.di.ApplicationDependencyManager;
import gmsis.models.authentication.User;
import gmsis.models.bookings.Booking;
import gmsis.models.customers.Customer;
import gmsis.persistence.hibernate.UserSeeder;
import gmsis.persistence.hibernate.bookings.BookingSeeder;
import gmsis.persistence.hibernate.bookings.MechanicSeeder;
import gmsis.persistence.hibernate.customers.CustomerSeeder;
import gmsis.persistence.hibernate.parts.PartsItemSeeder;
import gmsis.persistence.hibernate.parts.PartsModelSeeder;
import gmsis.persistence.hibernate.specialistRepairs.SPCSeeder;
import gmsis.persistence.hibernate.specialistRepairs.SpecialistRepairBookingSeeder;
import gmsis.persistence.hibernate.vehicles.VehicleSeeder;
import gmsis.persistence.hibernate.vehicles.WarrantyCompanySeeder;
import gmsis.ui.authentication.AuthenticationComponentLoader;
import gmsis.ui.bookings.BookingsUI;
import gmsis.ui.customers.CustomerUI;
import gmsis.ui.parts.PartsUI;
import gmsis.ui.specialistRepairs.SpecialistRepairsUI;
import gmsis.ui.users.UserListComponentLoader;
import gmsis.ui.vehicles.VehiclesUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author filip
 */
public class GmsisApp extends Application {
    private StackPane root;
    private User user;

    private void loadHomeView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/HomeView.fxml"));
        Node home = loader.load();
        HomeController homeController = loader.getController();
        homeController.setTabsForUser(user);
        root.getChildren().clear();
        root.getChildren().add(home);

        home.addEventHandler(TabChangeEvent.TAB_CHANGE, e -> {
            e.getTab().getContent().lookup("StackPane").fireEvent(new ActionEvent());
        });

        final StackPane customerUI = (StackPane) home.lookup("#customersPane");
        customerUI.getChildren().add(new CustomerUI(ApplicationDependencyManager.getInstance()).get());

        final StackPane bookingUI = (StackPane) home.lookup("#bookingListPane");
        bookingUI.getChildren().add(new BookingsUI(ApplicationDependencyManager.getInstance()).get());
        final StackPane spcUI = (StackPane) home.lookup("#specialistRepairsPane");
        spcUI.getChildren().add(new SpecialistRepairsUI(user, ApplicationDependencyManager.getInstance()).get());

        // add vehicles pane

        final StackPane vehicleUI = (StackPane) home.lookup("#vehiclesPane");
        vehicleUI.getChildren().add(new VehiclesUI(ApplicationDependencyManager.getInstance()).getRoot());

        ((StackPane) home.lookup("#partsPane")).getChildren().add(new PartsUI(ApplicationDependencyManager.getInstance()).get());

        customerUI.addEventHandler(CustomerBookingEvent.NEW_BOOKING, e -> {
            homeController.setTabByName("Booking Manager");
            Customer customer = e.getCustomer();
            bookingUI.getChildren().get(0).fireEvent(new BookingViewEvent(customer));
        });
        bookingUI.addEventHandler(SPCBookingViewRequestEvent.SPC_VIEW_REQUEST_SPC_BOOKING_ADD, e -> {
            homeController.setTabByName("Specialist Repair Booking");
            Booking booking = e.getBooking();
            spcUI.getChildren().get(0).fireEvent(new SPCBookingNewWithBookingInfoRequestEvent(booking));
        });
        spcUI.addEventHandler(CustomerEditRequestEvent.CUSTOMER_SHOW_PROFILE, e -> {
            homeController.setTabByName("Customers");
            Customer customer = e.getCustomer();
            customerUI.getChildren().get(0).fireEvent(CustomerEditRequestEvent.showProfile(customer));
        });

        Optional.ofNullable((StackPane) home.lookup("#usersPane")).map(pane -> {
            try {
                VBox userUI = (VBox) new UserListComponentLoader(ApplicationDependencyManager.getInstance()).load();
                pane.getChildren().add(userUI);
                userUI.prefHeightProperty().bind(pane.heightProperty());
                userUI.prefWidthProperty().bind(pane.widthProperty());
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error loading user UI:", e);
            }
            return pane;
        });
    }

    private void loadAuthenticationView() throws IOException {
        root.getChildren().clear();
        root.getChildren().add(new AuthenticationComponentLoader(ApplicationDependencyManager.getInstance()).load());
    }

    private void onUserLogin(UserLoggedInEvent e) {
        this.user = e.getUser();
        try {
            loadHomeView();
        } catch (IOException ex) {
            Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
            Platform.exit();
        }
    }

    private void onUserLogout(UserLoggedOutEvent e) {
        try {
            loadAuthenticationView();
        } catch (IOException ex) {
            Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
            Platform.exit();
        }
    }

    private void startMain() throws Exception {
        Stage primaryStage = new Stage();
        root = new StackPane();

        loadAuthenticationView();

        root.addEventHandler(UserLoggedInEvent.LOGGED_IN, this::onUserLogin);
        root.addEventHandler(UserLoggedOutEvent.LOGGED_OUT, this::onUserLogout);

        final double rem = javafx.scene.text.Font.getDefault().getSize();
        Scene scene = new Scene(root, 60 * rem, 48 * rem);
        primaryStage.setWidth(895);
        primaryStage.setHeight(685);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(500);

        primaryStage.setTitle("GM-SIS");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            // Ensures that all database connections are closed properly
            ApplicationDependencyManager.getInstance().getSessionFactory().close();
            // Villain exit stage left
            Platform.exit();
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        ImageView image = new ImageView("/gmsis/ui/gmsis-splash.png");
        image.setLayoutX(0);
        image.setLayoutY(0);
        root.getChildren().add(image);
        JFXProgressBar progress = new JFXProgressBar();
        progress.setPrefWidth(254);
        progress.setLayoutX(335);
        progress.setLayoutY(440);
        progress.setProgress(-1.0);
        progress.setSkin(new JFXProgressBarSkin(progress) {
            @Override
            public void initialize() {
                super.initialize();
                ((StackPane) getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.valueOf("#ffc107"), CornerRadii.EMPTY, Insets.EMPTY)));
                (getChildren().get(0)).setStyle("-fx-background-color: transparent;");
            }
        });
        root.getChildren().add(progress);

        final double rem = javafx.scene.text.Font.getDefault().getSize();
        Scene scene = new Scene(root, 640, 480);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("GM-SIS");
        primaryStage.setScene(scene);
        primaryStage.show();

        Task<Boolean> initTask = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                int i = 0, max = 10; // Declare for keeping track of where roughly in the seeding process we are
                UserSeeder.seedUsersIfNecessary(ApplicationDependencyManager.getInstance());
                this.updateProgress(++i, max);
                CustomerSeeder.seedCustomersIfNecessary(ApplicationDependencyManager.getInstance());
                this.updateProgress(++i, max);
                MechanicSeeder.seedMechanicsIfNecessary(ApplicationDependencyManager.getInstance());
                this.updateProgress(++i, max);
                SPCSeeder.seedSPCIfNecessary(ApplicationDependencyManager.getInstance());
                this.updateProgress(++i, max);
                PartsModelSeeder.seedPartsModelIfNecessary(ApplicationDependencyManager.getInstance());
                this.updateProgress(++i, max);
                WarrantyCompanySeeder.seedWarrantyCompanyIfNecessary(ApplicationDependencyManager.getInstance());
                this.updateProgress(++i, max);
                VehicleSeeder.seedVehicleIfNecessary(ApplicationDependencyManager.getInstance());
                this.updateProgress(++i, max);
                BookingSeeder.seedBookingNecessary(ApplicationDependencyManager.getInstance());
                this.updateProgress(++i, max);
                PartsItemSeeder.seedPartsItemIfNecessary(ApplicationDependencyManager.getInstance());
                this.updateProgress(++i, max);
                SpecialistRepairBookingSeeder.seedSPCIfNecessary(ApplicationDependencyManager.getInstance());
                this.updateProgress(++i, max);
                return true;
            }
        };
        initTask.progressProperty().addListener((obsv, oldv, newv) -> progress.setProgress(newv.doubleValue()));
        initTask.stateProperty().addListener((obsv, oldv, newv) -> {
            Logger.getLogger(getClass().getName()).fine("Init state change: " + newv.name());
            if (newv == Worker.State.SUCCEEDED) {
                Logger.getLogger(getClass().getName()).fine("Init complete.");
                try {
                    startMain();
                    primaryStage.hide();
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not start main window:", e);
                    ApplicationDependencyManager.getInstance().getSessionFactory().close();
                    Platform.exit();
                }
            } else if (newv == Worker.State.FAILED) {
                Logger.getLogger(getClass().getName()).severe("Initialisation failed.");
                ApplicationDependencyManager.getInstance().getSessionFactory().close();
                Platform.exit();
            }
        });
        new Thread(initTask).start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.customers;

import gmsis.models.customers.Bill;
import gmsis.models.customers.InvoiceStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by filip on 21/03/17.
 */
public class BillListItemController implements Initializable {
    @FXML
    private Node itemRoot;
    @FXML
    private Label label;
    @FXML
    private Node settleButton;


    private Bill bill;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setBill(Bill bill) {
        this.bill = bill;

        label.setText(String.format("£%.2f %s for booking on %s", bill.getAmount(), bill.getStatus().name(), bill.getBooking().getBookingDate().format(DateTimeFormatter.ISO_DATE)));
        settleButton.setVisible(bill.getStatus() == InvoiceStatus.OUTSTANDING);
        settleButton.setDisable(bill.getStatus() != InvoiceStatus.OUTSTANDING);
    }

    public void settleBill(ActionEvent e) {
        itemRoot.fireEvent(new SettleBillEvent(bill));
    }
}

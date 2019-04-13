package UI;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import CustomExceptions.PozitivePriceException;
import Service.TransactionService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class TranzactionAddController {

    public Button btnAdd;
    public Button btnCancel;
    public TextField txtDate;
    public TextField txtTime;
    public Spinner spnIdTransaction;
    public TextField txtIdMed;
    public TextField txtIdCardClient;
    public TextField txtQuantity;

    private TransactionService transactionService;

    public void setService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnAddClick(ActionEvent actionEvent) throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException {

        try {
            String idTransaction = String.valueOf(spnIdTransaction.getValue());
            String idMed = txtIdMed.getText();
            String idCardClient = txtIdCardClient.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            String date = txtDate.getText();
            String time = txtTime.getText();

            transactionService.addOrUpdate(idTransaction, idMed, idCardClient, quantity, date, time);
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }
}

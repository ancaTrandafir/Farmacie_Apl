package UI;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import CustomExceptions.PozitivePriceException;
import Service.ClientService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientAddController {

    public Spinner spnId;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtCNP;
    public TextField txtDateOfBirth;
    public TextField txtDateOfRegistration;
    public Button btnAdd;
    public Button btnCancel;


    private ClientService clientService;

    public void setService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnAddClick(ActionEvent actionEvent) throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException {

        try {
            String id = String.valueOf(spnId.getValue());
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String CNP = txtCNP.getText();
            String dateOfBirth = txtDateOfBirth.getText();
            String dateOfRegistration = txtDateOfRegistration.getText();

            clientService.addOrUpdate(id, firstName, lastName, CNP, dateOfBirth, dateOfRegistration);
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }
}

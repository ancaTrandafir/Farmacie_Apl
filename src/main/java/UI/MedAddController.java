package UI;

import CustomExceptions.PozitivePriceException;
import Service.MedicamentService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MedAddController {

    public TextField txtName;
    public TextField txtManufacturer;
    public TextField txtPrice;
    public CheckBox chkPrescriptionNeeded;
    public Button btnAdd;
    public Button btnCancel;
    public Spinner spnId;

    private MedicamentService medicamentService;

    public void setService(MedicamentService medicamentService) {
        this.medicamentService = medicamentService;
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnAddClick(ActionEvent actionEvent) {

        try {
            String id = String.valueOf(spnId.getValue());
            String name = txtName.getText();
            String manufacturer = txtManufacturer.getText();
            double price = Double.parseDouble(txtPrice.getText());
            boolean prescriptionNeeded = chkPrescriptionNeeded.isSelected();

            try {
                medicamentService.addOrUpdate(id, name, manufacturer, price, prescriptionNeeded);
            } catch (PozitivePriceException e) {
                e.printStackTrace();
            }

            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }
}

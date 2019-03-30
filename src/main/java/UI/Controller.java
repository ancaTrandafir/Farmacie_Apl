package UI;

import CustomExceptions.PozitivePriceException;
import Domain.Medicament;
import Service.ClientService;
import Service.MedicamentService;
import Service.TransactionService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller {

    public TableView tableViewMeds;
    public TableColumn tableColumnId;
    public TableColumn tableColumnName;
    public TableColumn tableColumnManufacturer;
    @FXML
    public TableColumn tableColumnPrice;
    public TableColumn tableColumnPrescriptionNeeded;
    public Button btnMedAdd;
    public Button btnMedDelete;

    private MedicamentService medicamentService;
    private ClientService clientService;
    private TransactionService transactionService;

    private ObservableList<Medicament> meds = FXCollections.observableArrayList();

    public void setService(MedicamentService medicamentService, ClientService clientService, TransactionService transactionService) {
        this.medicamentService = medicamentService;
        this.clientService = clientService;
        this.transactionService = transactionService;
    }

    @FXML
    private void initialize() {

        Platform.runLater(() -> {
            meds.addAll(medicamentService.getAll());
            tableViewMeds.setItems(meds);
        });
    }

    public void editMedName(TableColumn.CellEditEvent cellEditEvent) {
        Medicament editedMed = (Medicament) cellEditEvent.getRowValue();
        try {
            String newName = (String)cellEditEvent.getNewValue();
            medicamentService.addOrUpdate(editedMed.getId(), newName, editedMed.getManufacturer(), editedMed.getPrice(), editedMed.isPrescriptionNeeded());
            editedMed.setName(newName);
        } catch (PozitivePriceException rex) {
            Common.showValidationError(rex.getMessage());
        }
        tableViewMeds.refresh();
    }

    public void editMedManufacturer(TableColumn.CellEditEvent cellEditEvent) {
        Medicament editedMed = (Medicament) cellEditEvent.getRowValue();
        try {
            String newManufacturer = (String)cellEditEvent.getNewValue();
            medicamentService.addOrUpdate(editedMed.getId(), editedMed.getName(), newManufacturer, editedMed.getPrice(), editedMed.isPrescriptionNeeded());
            editedMed.setManufacturer(newManufacturer);
        } catch (PozitivePriceException rex) {
            Common.showValidationError(rex.getMessage());
        }
        tableViewMeds.refresh();
    }

    public void editMedPrice(TableColumn.CellEditEvent cellEditEvent) {
        Medicament editedMed = (Medicament) cellEditEvent.getRowValue();
        try {
            double newPrice = (double)cellEditEvent.getNewValue();
            medicamentService.addOrUpdate(editedMed.getId(), editedMed.getName(), editedMed.getManufacturer(), newPrice, editedMed.isPrescriptionNeeded());
            editedMed.setPrice(newPrice);
        } catch (PozitivePriceException rex) {
            Common.showValidationError(rex.getMessage());
        }
        tableViewMeds.refresh();
    }

    public void btnMedAddClick(ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("medAdd.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 200);
            Stage stage = new Stage();
            stage.setTitle("Med add");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            MedAddController controller =  fxmlLoader.getController();
            controller.setService(medicamentService);
            stage.showAndWait();
            meds.clear();
            meds.addAll(medicamentService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Med add.", e);
        }
    }

    public void btnMedDeleteClick(ActionEvent actionEvent) {
        Medicament selected = (Medicament) tableViewMeds.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                medicamentService.remove(selected.getId());
                meds.clear();
                meds.addAll(medicamentService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }
}

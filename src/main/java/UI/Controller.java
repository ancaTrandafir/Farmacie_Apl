package UI;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import CustomExceptions.PozitivePriceException;
import Domain.Client;
import Domain.Medicament;
import Domain.Transaction;
import Service.ClientService;
import Service.MedicamentService;
import Service.TransactionService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;


    public class Controller {

        public TableView tableViewMeds;
        public TableView tableViewTranzactions;
        public TableView tableViewClients;
        public TableColumn tableColumnId;
        public TableColumn tableColumnName;
        public TableColumn tableColumnManufacturer;
        public TableColumn tableColumnPrice;

        public TableColumn tableColumnId_transaction;
        public TableColumn tableColumnId_med;
        public TableColumn tableColumnId_card_client;
        public TableColumn tableColumnQuantity;
        public TableColumn tableColumnDate;
        public TableColumn tableColumnTime;
        public TableColumn tableColumnBasePrice;
        public TableColumn tableColumnFirstName;
        public TableColumn tableColumnLastName;
        public TableColumn tableColumnCNP;
        public TableColumn tableColumnDateOfBirth;
        public TableColumn tableColumnDateOfRegistration;
        @FXML
        public Button btnMedDelete;
        public Button btnMedAdd;
        public Button btnMedUndo;
        public Button btnMedRedo;
        public Button btnClientDelete;
        public Button btnClientAdd;
        public Button btnClientUndo;
        public Button btnClientRedo;
        public Button btnTransactionDelete;
        public Button btnTransactionAdd;
        public Button btnTransactionUndo;
        public Button btnTransactionRedo;
        public TextField txtPercent;
        public TextField txtMinVal;
        public TextField txtBeginDate;
        public TextField txtEndDate;
        public Button btnSearchTransaction;
        public Button btnDeleteTransactions;
        public Button btnReverseSales;
        public TextField txtReverseSalesResult;
        public TextField txtSearchTransactions;
        public Button btnSortDiscounts;


        private MedicamentService medicamentService;
        private ClientService clientService;
        private TransactionService transactionService;

        private ObservableList<Medicament> meds = FXCollections.observableArrayList();

        public void setServices(MedicamentService medicamentService, ClientService clientService, TransactionService transactionService) {
            this.medicamentService = medicamentService;
            this.clientService = clientService;
            this.transactionService = transactionService;
        }



        @FXML
        private void initialize() {

            Platform.runLater(() -> {
                meds.addAll(medicamentService.getAll());
                tableViewMeds.setItems(meds);

                clients.addAll(clientService.getAll());
                clients.addAll(clientService.getAll());
                tableViewClients.setItems(clients);

                transactions.addAll(transactionService.getAll());
                tableViewTranzactions.setItems(transactions);
                });
        }

        public void editMedName(TableColumn.CellEditEvent cellEditEvent) throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException {
            Medicament editedMed = (Medicament) cellEditEvent.getRowValue();
            try {
                String newName = (String)cellEditEvent.getNewValue();
                medicamentService.addOrUpdate(editedMed.getId(), newName, editedMed.getManufacturer(), editedMed.getPrice(), editedMed.isPrescriptionNeeded());
                editedMed.setName(newName);
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
            tableViewMeds.refresh();
        }

        public void btnMedAddClick(ActionEvent actionEvent) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/MedAdd.fxml"));

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

        public void btnMedUndoClick(ActionEvent actionEvent) {
            medicamentService.undo();
            meds.clear();
            meds.addAll(medicamentService.getAll());
        }

        public void btnMedRedoClick(ActionEvent actionEvent) throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException {
            medicamentService.redo();
            meds.clear();
            meds.addAll(medicamentService.getAll());
        }




        private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        public void btnTransactionAddClick(ActionEvent actionEvent) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/TranzactionAdd.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 600, 200);
                Stage stage = new Stage();
                stage.setTitle("Transaction add");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                TranzactionAddController controller =  fxmlLoader.getController();
                controller.setService(transactionService);
                stage.showAndWait();
                transactions.clear();
                transactions.addAll(transactionService.getAll());
            } catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window: Transaction add.", e);
            }
        }

        public void btnTransactionDeleteClick(ActionEvent actionEvent) {
            Transaction selected = (Transaction) tableViewTranzactions.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    transactionService.remove(selected.getId());
                    transactions.clear();
                    transactions.addAll(transactionService.getAll());
                } catch (RuntimeException rex) {
                    Common.showValidationError(rex.getMessage());
                }
            }
        }

        public void btnTransactionUndoClick(ActionEvent actionEvent) {
            transactionService.undo();
            transactions.clear();
            transactions.addAll(transactionService.getAll());
        }

        public void btnTransactionRedoClick(ActionEvent actionEvent) throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException {
            transactionService.redo();
            transactions.clear();
            transactions.addAll(transactionService.getAll());
        }



        private ObservableList<Client> clients = FXCollections.observableArrayList();

        public void btnClientAddClick(ActionEvent actionEvent) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ClientAdd.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 600, 200);
                Stage stage = new Stage();
                stage.setTitle("Client add");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                ClientAddController controller =  fxmlLoader.getController();
                controller.setService(clientService);
                stage.showAndWait();
                clients.clear();
                clients.addAll(clientService.getAll());
            } catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window: Client add.", e);
            }
        }

        public void btnClientDeleteClick(ActionEvent actionEvent) {
            Client selected = (Client) tableViewClients.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    clientService.remove(selected.getId());
                    clients.clear();
                    clients.addAll(clientService.getAll());
                } catch (RuntimeException rex) {
                    Common.showValidationError(rex.getMessage());
                }
            }
        }

        public void btnClientUndoClick(ActionEvent actionEvent) {
            clientService.undo();
            clients.clear();
            clients.addAll(clientService.getAll());
        }

        public void btnClientRedoClick(ActionEvent actionEvent) throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException {
            clientService.redo();
            clients.clear();
            clients.addAll(clientService.getAll());
        }


        public void btnIncreaseValueClick(ActionEvent actionEvent) {
            Integer percent = Integer.valueOf(txtPercent.getText());
            Integer minimumValue = Integer.valueOf(txtMinVal.getText());
            medicamentService.increasePrices(percent, minimumValue);
            meds.clear();
            meds.addAll(medicamentService.getAll());
        }

        public void btnSearchTransactionsClick(ActionEvent actionEvent) throws InvalidCNPException, NonUniqueCNPException, ParseException, IOException {
            Parent root = FXMLLoader.load(getClass().getResource("window.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Medicine manager");
            stage.setScene(new Scene(root, 650, 500));

            stage.show();

            String beginDate = txtBeginDate.getText();
            String endDate = txtEndDate.getText();
            transactionService.SearchTransaction(beginDate, endDate);
            transactions.clear();
            transactions.addAll(transactionService.getAll());
        }

        public void btnDeleteTransactionsClick(ActionEvent actionEvent) throws ParseException {
            String beginDate = txtBeginDate.getText();
            String endDate = txtEndDate.getText();
            transactionService.deleteTransactions(beginDate, endDate);
            transactions.clear();
            transactions.addAll(transactionService.getAll());
        }

        public void btnReverseSalesClick(ActionEvent actionEvent) {
            transactionService.runSaleSearch();
            transactions.clear();
            transactions.addAll(transactionService.getAll());
        }

        public void btnSortDiscountsClick(ActionEvent actionEvent) {
            transactionService.runDiscounts();
            transactions.clear();
            transactions.addAll(transactionService.getAll());
        }


    }


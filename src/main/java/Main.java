import Domain.*;
import Repository.IRepository;
import Repository.JsonFileRepository;
import Service.ClientService;
import Service.MedicamentService;
import Service.TransactionService;
import UI.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller =  fxmlLoader.getController();

        IValidator<Medicament> medicamentValidator = new MedicamentValidator();
        IValidator<Client> clientValidator = new ClientValidator();
        IValidator<Transaction> transactionValidator = new TransactionValidator();

        IRepository<Medicament> medicamentRepository = new JsonFileRepository<>(medicamentValidator, "meds.json", Medicament.class);
        IRepository<Client> clientRepository = new JsonFileRepository<>(clientValidator, "clients.json", Client.class);
        IRepository<Transaction> transactionRepository = new JsonFileRepository<>(transactionValidator, "transactions.json", Transaction.class);
        //IRepository<Transaction> transactionRepository = new InMemoryRepository<>(transactionValidator);

        MedicamentService medicamentService = new MedicamentService(medicamentRepository);
        ClientService clientService = new ClientService(clientRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, medicamentRepository);

        controller.setServices(medicamentService, clientService, transactionService);

        primaryStage.setTitle("Medicine manager");
        primaryStage.setScene(new Scene(root, 650, 500));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

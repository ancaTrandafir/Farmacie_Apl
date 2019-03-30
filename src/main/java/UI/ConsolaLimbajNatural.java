package UI;

import Domain.Medicament;
import Domain.Client;
import Domain.Transaction;
import Service.MedicamentService;
import Service.ClientService;
import Service.TransactionService;

import java.util.Scanner;

public class ConsolaLimbajNatural {
    private MedicamentService medicamentService;
    private ClientService clientService;
    private TransactionService transactionService;

    private Scanner scanner;

    public ConsolaLimbajNatural(MedicamentService medicamentService, ClientService clientService, TransactionService transactionService) {
        this.medicamentService = medicamentService;
        this.clientService = clientService;
        this.transactionService = transactionService;

        this.scanner = new Scanner(System.in);
    }

    private void showMenu() {
        System.out.println("1. Add medicine");
        System.out.println("2. Delete medicine");
        System.out.println("3. View all");
        System.out.println("4. Back");
    }
    public void run() {
        while (true) {
            showMenu();

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    runAddMed();
                    break;
                case "2":
                    runDeleteMed();
                    break;
                case "3":
                    runShowAll();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void runAddMed() {
        try {
            System.out.println("Medicine to be added: id, name, manufacturer, price, prescriptionNeeded");
            String line = scanner.nextLine();           // line is the input which has comma separated words
            String[] input = line.split(",");
            String addMed[] = new String[10];           // instantiate an array of 10
            for (int i = 0; i < input.length; i++) {
                addMed[i] = input[i];
            }
            String id = addMed[0];
            String name = addMed[1];
            String manufacturer = addMed[2];
            Double price = Double.parseDouble(addMed[3]);
            Boolean prescriptionNeeded = Boolean.parseBoolean(addMed[4]);

            medicamentService.addOrUpdate(id, name, manufacturer, price, prescriptionNeeded);

            System.out.println("Medicine added!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void runDeleteMed() {
        try {
            System.out.print("Enter the id to remove:");
            String id = scanner.nextLine();
            medicamentService.remove(id);

            System.out.println("Medicine removed!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void runShowAll() {
        for (Medicament med : medicamentService.getAll()) {
            System.out.println(med);
        }
    }

}



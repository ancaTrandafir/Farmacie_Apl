package UI;

import Domain.Medicament;
import Domain.Client;
import Domain.Transaction;
import Service.MedicamentService;
import Service.ClientService;
import Service.TransactionService;

import java.util.Scanner;

public class ConsolaLimbaj {
    private MedicamentService medicamentService;
    private ClientService clientService;
    private TransactionService transactionService;

    private Scanner scanner;

    public ConsolaLimbaj(MedicamentService medicamentService, ClientService clientService, TransactionService transactionService) {
        this.medicamentService = medicamentService;
        this.clientService = clientService;
        this.transactionService = transactionService;

        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            try {
                //System.out.println("AddMed, id, name, manufacturer, price, prescriptionNeeded");
                String line = scanner.nextLine();           // line is the input which has comma separated words
                if (line.equals("exit"))
                    break;
                String[] words = line.split(",");
                String[] input = new String[10];             // instantiate an array of 10
                for (int i = 0; i < words.length; i++) {
                    input[i] = words[i];
                }
                if (input[0].equalsIgnoreCase("AddMed")) {
                    String id = input[1];
                    String name = input[2];
                    String manufacturer = input[3];
                    Double price = Double.parseDouble(input[4]);
                    Boolean prescriptionNeeded = Boolean.parseBoolean(input[5]);
                    medicamentService.addOrUpdate(id, name, manufacturer, price, prescriptionNeeded);
                    System.out.println("Medicine added!");
                } else if (input[0].equalsIgnoreCase("Delete")) {
                    String id = input[1];
                    medicamentService.remove(id);
                    System.out.println("Medicine removed!");
                } else if (input[0].equalsIgnoreCase("ShowAll")) {
                    for (Medicament med : medicamentService.getAll()) {
                        System.out.println(med);
                    }
                }
            } catch (Exception ex) {
                System.out.println("Errors:\n" + ex.getMessage());
            }
        }
    }
}




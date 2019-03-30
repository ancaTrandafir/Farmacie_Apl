package UI;

import Domain.Client;
import Domain.Medicament;
import Domain.Transaction;
import Service.ClientService;
import Service.MedicamentService;
import Service.TransactionService;

import java.util.*;

public class Console {
    private MedicamentService medicamentService;
    private ClientService clientService;
    private TransactionService transactionService;

    private Scanner scanner;

    public Console(MedicamentService medicamentService, ClientService clientService, TransactionService transactionService) {
        this.medicamentService = medicamentService;
        this.clientService = clientService;
        this.transactionService = transactionService;

        this.scanner = new Scanner(System.in);
    }

    private void showMenu() {
        System.out.println("1. Med CRUD");
        System.out.println("2. Client CRUD");
        System.out.println("3. Transaction CRUD");
        System.out.println("4. Search Meds");
        System.out.println("5. Search Clients");
        System.out.println("6. Search transactions during period of time");
        System.out.println("7. Search sales, descending order");
        System.out.println("8. Search Id cards, descending order regarding discounts");
        System.out.println("9. Raise small prices with a certain percentage");

        System.out.println("x. Exit");
    }

    public void run() {
        while (true) {
            showMenu();

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    runMedCrud();
                    break;
                case "2":
                    runClientCrud();
                    break;
                case "3":
                    runTransactionCrud();
                    break;
                case "4":
                    runMedSearch();
                    break;
                case "5":
                    runClientSearch();
                    break;
              /*  case "6":
                    runTransactionSearch();
                    break; */
                case "7":
                    runSaleSearch();
                    break;
                case "8":
                    runIdCardSearch();
                    break;
                case "9":
                    runRaisePrices();
                    break;
                case "x":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void runRaisePrices() {
        System.out.println("Set prices lower limit.");
        String stringLimit = scanner.nextLine();
        System.out.println("Set percentage.");
        String stringPercentage = scanner.nextLine();
        Double priceLimit = Double.parseDouble(stringLimit);
        Double percentage = Double.parseDouble(stringPercentage);

        for (Medicament m : medicamentService.getAll())
            if (m.getPrice()<= priceLimit) {
                m.setPrice(m.getPrice() * (100 + percentage)); // majora pretul cu procentul
            }

        for (Medicament m : medicamentService.getAll())
            System.out.println(m);
    }


    private void runIdCardSearch() {
        // Cream un dictionar in care retinem id_card si valoare discount
        Map<String, Double> idCard = new HashMap<>();
        for (Transaction t : transactionService.getAll()) {
            if (t.getId_card_client() != null) {  // are card deci are discount
                // fctia Transaction are ca parametru si basePrice si discount, setate in Service
                // val reducere = basePrice * discount * cantitate vanduta
                idCard.put(t.getId_card_client(), t.getDiscount() * t.getBasePrice() * t.getQuantity());
            }
        }
        // Cream o lista care retine valorile dictionarului, cu reverse ordonam descrescator
        List<Double> discountedPrices = new ArrayList<>(idCard.values());
        Collections.reverse(discountedPrices);

        // Cream un dictionar ordonat TreeMap in care copiem valorile ordonate anterior
        TreeMap<String, Double> sorted = new TreeMap<>();
        sorted.putAll(idCard);

        for (Map.Entry<String, Double> entry : idCard.entrySet())
            System.out.println("Id card = " + entry.getKey() +
                    ", Discouts = " + entry.getValue());
    }



    private void runSaleSearch() {
        // Cream un dictionar in care retinem id_medicament si vanzarile
        Map<String, Double> sales = new HashMap<>();
        for (Transaction t : transactionService.getAll()) {
            for (Medicament m : medicamentService.getAll())
                // id_med din tranzactie treb sa corespunda cu id med.
                if (m.getId() == t.getId_med()) {
                    // Vanzari = cantitate * pret
                    sales.put(t.getId_med(), t.getQuantity() * m.getPrice());
                }
        }

        // Cream o lista care retine valorile dictionarului, cu reverse ordonam descrescator
        List<Double> vanzari = new ArrayList<>(sales.values());
        Collections.reverse(vanzari);

        // Cream un dictionar ordonat TreeMap in care copiem valorile ordonate anterior
        TreeMap<String, Double> sorted = new TreeMap<>();
        sorted.putAll(sales);

        // for-each loop for iteration over Map.entrySet()
        for (Map.Entry<String, Double> entry : sales.entrySet())
            System.out.println("Id med = " + entry.getKey() +
                    ", Sales = " + entry.getValue());
    }

/*
        private void runTransactionSearch() throws ParseException {

        System.out.println("Data inceput: ");
        String date1 = scanner.nextLine();
        System.out.println("Data sfarsit: ");
        String date2 = scanner.nextLine();

        // Formatam din String in data
        DateTimeFormatter formatter = new DateTimeFormatter.ISO_DATE;
        LocalDate beginDate = LocalDate.parse(date1);
        LocalDate endDate = LocalDate.parse(date1);

        // Cream o lista de date formatate (importam LocalDate)
        // Datele se compara cu isAfter, isBefore in Java8
        List <LocalDate> formatDate = new ArrayList<LocalDate>();
        for (Transaction t : transactionService.getAll()) {
           formatDate.add(LocalDate.parse(t.getDate()));
           for (int i = 0; i < formatDate.size(); i++)
                if (formatDate.get(i).isAfter(beginDate) && formatDate.get(i).isBefore(endDate)) {
                         System.out.println(t);
                     }
        }
    }
*/


    private void runMedSearch() {
        System.out.println("Dati cautarea: ");
        String text = scanner.nextLine();
        System.out.println("Rezultatele cautarii sunt:");
        for (Medicament m : medicamentService.fullTextSearch(text)) {
            System.out.println(m);
        }
    }

    private void runClientSearch() {
        System.out.println("Dati cautarea: ");
        String text = scanner.nextLine();
        System.out.println("Rezultatele cautarii sunt:");
        for (Client c : clientService.fullTextSearch(text)) {
            System.out.println(c);
        }
    }

    private void runTransactionCrud() {
        while (true) {
            System.out.println("1. Add or update a transaction");
            System.out.println("2. Remove a transaction");
            System.out.println("3. View all transactions");
            System.out.println("4. Back");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    handleAddUpdateTransaction();
                    break;
                case "2":
                    handleRemoveTransaction();
                    break;
                case "3":
                    handleViewTransactions();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void handleViewTransactions() {
        for (Transaction transaction : transactionService.getAll()) {
            System.out.println(transaction);
        }
    }

    private void handleRemoveTransaction() {
        try {
            System.out.print("Enter the id to remove:");
            String id = scanner.nextLine();
            transactionService.remove(id);

            System.out.println("Transaction removed!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void handleAddUpdateTransaction() {
        try {
            System.out.print("Enter id: ");
            String id = scanner.nextLine();
            System.out.print("Enter cake id (empty to not change for update): ");
            String idCake = scanner.nextLine();
            System.out.print("Enter client card (empty to not change for update): ");
            String idClientCard = scanner.nextLine();
            System.out.print("Enter number of items (0 to not change for update): ");
            int numberOfItems = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter date (empty to not change for update): ");
            String date = scanner.nextLine();
            System.out.print("Enter time (empty to not change for update): ");
            String time = scanner.nextLine();

            Transaction transaction = transactionService.addOrUpdate(id, idCake, idClientCard, numberOfItems, date, time);
            System.out.println(String.format("Added transaction id=%s, paid price=%f, discount=%f%%", transaction.getId(), transaction.getDiscountedPrice(), transaction.getDiscount()));
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void runClientCrud() {
        while (true) {
            System.out.println("1. Add or update a client");
            System.out.println("2. Remove a client");
            System.out.println("3. View all clients");
            System.out.println("4. Back");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    handleAddUpdateClient();
                    break;
                case "2":
                    handleRemoveClient();
                    break;
                case "3":
                    handleViewClients();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void handleViewClients() {
        for (Client client : clientService.getAll()) {
            System.out.println(client);
        }
    }

    private void handleRemoveClient() {
        try {
            System.out.print("Enter the id to remove:");
            String id = scanner.nextLine();
            clientService.remove(id);

            System.out.println("Client removed!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void handleAddUpdateClient() {
        try {
            System.out.print("Enter id: ");
            String id = scanner.nextLine();
            System.out.print("Enter last name (empty to not change for update): ");
            String lastName = scanner.nextLine();
            System.out.print("Enter first name (empty to not change for update): ");
            String firstName = scanner.nextLine();
            System.out.print("Enter CNP (empty to not change for update): ");
            String CNP = scanner.nextLine();
            System.out.print("Enter date of birth (empty to not change for update): ");
            String dateOfBirth = scanner.nextLine();
            System.out.print("Enter date of registration (empty to not change for update): ");
            String dateOfRegistration = scanner.nextLine();

            clientService.addOrUpdate(id, lastName, firstName, CNP, dateOfBirth, dateOfRegistration);

            System.out.println("Client added!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void runMedCrud() {
        while (true) {
            System.out.println("1. Add or update a medicine");
            System.out.println("2. Remove a medicine");
            System.out.println("3. View all meds");
            System.out.println("4. Back");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    handleAddUpdateMed();
                    break;
                case "2":
                    handleRemoveMed();
                    break;
                case "3":
                    handleViewMeds();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void handleViewMeds() {
        for (Medicament medicament : medicamentService.getAll()) {
            System.out.println(medicament);
        }
    }

    private void handleRemoveMed() {
        try {
            System.out.print("Enter the id to remove:");
            String id = scanner.nextLine();
            medicamentService.remove(id);

            System.out.println("Med removed!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }

    private void handleAddUpdateMed() {

        try {
            System.out.print("Enter id: ");
            String id = scanner.nextLine();
            System.out.print("Enter name (empty to not change for update): ");
            String name = scanner.nextLine();
            System.out.print("Enter manufacturer (empty to not change for update): ");
            String ingredients = scanner.nextLine();
            System.out.print("Enter price (0 to not change for update): ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter prescription needed (true / false): ");
            boolean prescriptionNeeded = Boolean.parseBoolean(scanner.nextLine());

            medicamentService.addOrUpdate(id, name, ingredients, price, prescriptionNeeded);

            System.out.println("Medicine added!");
        } catch (Exception ex) {
            System.out.println("Errors:\n" + ex.getMessage());
        }
    }
}

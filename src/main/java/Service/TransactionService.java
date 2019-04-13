package Service;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import CustomExceptions.PozitivePriceException;
import Domain.Medicament;
import Domain.Transaction;
import Repository.IRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionService {
    private IRepository<Transaction> transactionRepository;
    private IRepository<Medicament> medicamentRepository;
    //private Stack<UndoRedoOperation<Medicament>> undoableOperations = new Stack<>();
    // private Stack<UndoRedoOperation<Medicament>> redoableeOperations = new Stack<>();
    private Stack<UndoRedoOperation<Transaction>> undoableOperations = new Stack<>();
    private Stack<UndoRedoOperation<Transaction>> redoableeOperations = new Stack<>();

    public TransactionService(IRepository<Transaction> transactionRepository, IRepository<Medicament> medicamentRepository) {
        this.transactionRepository = transactionRepository;
        this.medicamentRepository = medicamentRepository;
    }

    public Transaction addOrUpdate(String idTransaction, String idMed, String idClientCard, int quantity, String date, String time) throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException {
        Transaction existing = transactionRepository.findById(idTransaction);
        if (existing != null) {
            if (idMed.isEmpty()) {
                idMed = existing.getId_med();
            }
            if (idClientCard.isEmpty()) {
                idClientCard = existing.getId_card_client();
            }
            if (quantity == 0) {
                quantity = existing.getQuantity();
            }
            if (date.isEmpty()) {
                date = existing.getDate();
            }
            if (time.isEmpty()) {
                time = existing.getTime();
            }
        }


        Medicament medSold = medicamentRepository.findById(idMed);
        if (medSold == null) {
            throw new RuntimeException(String.format("There is no medicine with id=%s", medSold.getId()));
        }
        double basePrice = medSold.getPrice();
        double discount = 0;
        if (idClientCard != null && !medSold.isPrescriptionNeeded()) {
            discount = 0.1;
        }
        if (idClientCard != null && medSold.isPrescriptionNeeded()) {
            discount = 0.15;
        }


        Transaction transaction = new Transaction(idTransaction, idMed, idClientCard, quantity, date, time, basePrice, discount);
        transactionRepository.upsert(transaction);
        return transaction;
    }

    public void remove(String id) {
        transactionRepository.remove(id);
    }

    public List<Transaction> getAll() {
        return transactionRepository.getAll();
    }

    public void undo() {
        if (!undoableOperations.empty()) {
            UndoRedoOperation<Transaction> lastOperation = undoableOperations.pop();
            lastOperation.doUndo();
            redoableeOperations.add(lastOperation);

        }
    }

    public void redo() throws InvalidCNPException, PozitivePriceException, NonUniqueCNPException {
        if (!redoableeOperations.empty()) {
            UndoRedoOperation<Transaction> lastOperation = redoableeOperations.pop();
            lastOperation.doRedo();
            undoableOperations.add(lastOperation);
        }
    }

    public void SearchTransaction(String beginDate, String endDate) throws InvalidCNPException, NonUniqueCNPException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date beginData = format.parse(beginDate);
        Date endData = format.parse(endDate);
        for (Transaction t : transactionRepository.getAll()) {
            Date transactionDate = format.parse(t.getDate());
            if (beginData.compareTo(transactionDate) == -1 && endData.compareTo(transactionDate) == 1) {
                System.out.println(t);
            }
        }
    }


    public void deleteTransactions(String beginDate, String endDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date beginData = format.parse(beginDate);
        Date endData = format.parse(endDate);
        for (Transaction t : transactionRepository.getAll()) {
            Date transactionDate = format.parse(t.getDate());
            if (beginData.compareTo(transactionDate) == -1 && endData.compareTo(transactionDate) == 1) {
                transactionRepository.remove(t.getId());
                System.out.println(transactionRepository.getAll());
            }
        }
        //System.out.println(transactionRepository.getAll());
    }






   /* public void runRaisePrices(Double priceLimit, Double percentage) {
        for (Medicament m : medicamentRepository.getAll())
            if (m.getPrice()<= priceLimit) {
                m.setPrice(m.getPrice() * (100 + percentage)); // majoreaza pretul cu procentul
            }

        for (Medicament m : medicamentRepository.getAll())
            System.out.println(m);



    public void runIdCardSearch() {
        // Cream un dictionar in care retinem id_card si valoare discount
        Map<String, Double> idCard = new HashMap<>();
        for (Transaction t : transactionRepository.getAll()) {
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




    public void runTransactionSearch(String date1, String date2) throws ParseException {
        // Formatam din String in data
        // DateTimeFormatter formatter = new DateTimeFormatter.ISO_DATE;
        LocalDate beginDate = LocalDate.parse(date1);
        LocalDate endDate = LocalDate.parse(date2);


        // Cream o lista de date formatate (importam LocalDate)
        // Datele se compara cu isAfter, isBefore in Java8
        List <LocalDate> formatDate = new ArrayList<LocalDate>();
        for (Transaction t : transactionRepository.getAll()) {
            formatDate.add(LocalDate.parse(t.getDate()));
            for (int i = 0; i < formatDate.size(); i++)
                if (formatDate.get(i).isAfter(beginDate) && formatDate.get(i).isBefore(endDate)) {
                    System.out.println(t);
                }
        }
    }

*/

    public void runSaleSearch() {

        double sales;
        List<Double> list = new ArrayList<>();
        for (Transaction t : transactionRepository.getAll()) {
            sales = t.getQuantity() * t.getDiscountedPrice();
            list.add(sales);
        }
        Collections.sort(list);
        Collections.reverse(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public void runDiscounts() {
        Map<String, Double> map = new HashMap<>();
        double discounts = 0;
        List<Double> list = new ArrayList<>();
        for (Transaction t : transactionRepository.getAll()) {
            map.put(t.getId_card_client(), t.getDiscount() * t.getBasePrice() * t.getQuantity());
        }

        List<Double> discountedPrices = new ArrayList<>(map.values());
        Collections.reverse(discountedPrices);

        TreeMap<String, Double> sorted = new TreeMap<>();
        sorted.putAll(map);

        for (Map.Entry<String, Double> entry : map.entrySet())
            System.out.println("Id card = " + entry.getKey() +
                    ", Discouts = " + entry.getValue());

        }





       /*

        // Cream un dictionar in care retinem id_medicament si vanzarile
        Map<String, Double> sales = new HashMap<>();
        for (Transaction t : transactionRepository.getAll()) {
            for (Medicament m : medicamentRepository.getAll())
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
    */



}









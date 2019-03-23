package Service;

import Domain.Medicament;
import Domain.IValidator;
import Domain.Transaction;
import Repository.IRepository;

import java.util.List;

public class TransactionService {
    private IRepository<Transaction> transactionRepository;
    private IRepository<Medicament> medicamentRepository;

    public TransactionService(IRepository<Transaction> transactionRepository, IRepository<Medicament> medicamentRepository) {
        this.transactionRepository = transactionRepository;
        this.medicamentRepository = medicamentRepository;
    }

    public Transaction addOrUpdate (String idTransaction, String idMed, String idClientCard, int quantity, String date, String time) {
        Transaction existing = transactionRepository.findById(idTransaction);
        if (existing!=null) {
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

    public void remove (String id) {
        transactionRepository.remove(id);
    }

    public List<Transaction> getAll(){
        return  transactionRepository.getAll();
    }
}

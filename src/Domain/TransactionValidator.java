package Domain;

public class TransactionValidator implements IValidator<Transaction>{

    public void validate(Transaction transaction) {
        if (transaction.getQuantity() <= 0) {
            throw new RuntimeException("The number of items must be at least 1.");
        }
    }
}


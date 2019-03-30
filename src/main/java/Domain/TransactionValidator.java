package Domain;

import CustomExceptions.PozitivePriceException;

public class TransactionValidator implements IValidator<Transaction>{

    public void validate(Transaction transaction) throws PozitivePriceException {
        if (transaction.getQuantity() <= 0) {
            throw new PozitivePriceException("The number of items must be at least 1.");
        }
    }
}


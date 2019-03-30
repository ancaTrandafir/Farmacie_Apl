package Domain;

import CustomExceptions.PozitivePriceException;

public class MedicamentValidator implements IValidator<Medicament> {
    public void validate(Medicament med) throws PozitivePriceException {
        if (med.getPrice() <= 0) {
            throw new PozitivePriceException("Price should be greater then 0");
        }
    }
}

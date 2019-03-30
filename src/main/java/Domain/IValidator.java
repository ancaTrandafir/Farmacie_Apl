package Domain;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import CustomExceptions.PozitivePriceException;

public interface IValidator<T extends Entity> {

    void validate(T entity) throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException;
}

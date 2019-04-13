package Service;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import CustomExceptions.PozitivePriceException;
import Domain.Entity;
import Repository.IRepository;

public abstract class UndoRedoOperation<T extends Entity> {

    protected IRepository<T> repository;

    public UndoRedoOperation(IRepository<T> repository) {
        this.repository = repository;
    }

    public abstract void doUndo();
    public abstract void doRedo() throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException;
}

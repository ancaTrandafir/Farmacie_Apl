package Service;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import CustomExceptions.PozitivePriceException;
import Domain.Entity;
import Repository.IRepository;

public class AddOperation<T extends Entity> extends UndoRedoOperation<T> {

    private T addedEntity;

    public AddOperation(IRepository<T> repository, T addedEntity) {
        super(repository);
        this.addedEntity = addedEntity;
    }

    @Override
    public void doUndo() {
        repository.remove(addedEntity.getId());
    }

    @Override
    public void doRedo() throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException {
        repository.upsert(addedEntity);
    }


}
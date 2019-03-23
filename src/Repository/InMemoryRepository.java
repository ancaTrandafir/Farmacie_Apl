package Repository;

import Domain.Medicament;
import Domain.MedicamentValidator;
import Domain.Entity;
import Domain.IValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository <T extends Entity>
    implements IRepository<T> {

    private Map<String, T> storage = new HashMap<>();
    private IValidator<T> validator;

    public InMemoryRepository (IValidator<T> validator) {
        this.validator = validator;
    }

    public T findById(String id) {
        return storage.get(id);
    }

    /**
     * Adds or updates an entity if it alrdeay exists.
     * @param entity the entity to add or update.
     */
    public void upsert (T entity) {
        validator.validate(entity);
        storage.put(entity.getId(),entity);
    }

    public void remove(String  id) {
        if (storage.containsKey((id))) {
            throw new RuntimeException("There is no entity with the given id to remove");
        }
        storage.remove(id);
    }

    public List<T> getAll(){
        return new ArrayList<>(storage.values());
    }
}

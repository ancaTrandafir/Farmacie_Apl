package Repository;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import CustomExceptions.PozitivePriceException;
import Domain.Entity;
import Domain.IValidator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerializableFileRepository<T extends Entity> implements IRepository<T> {

    private IValidator<T> validator;
    private String filename;
    private Map<String, T> storage = new HashMap<>();

    public SerializableFileRepository(IValidator<T> validator, String filename) {
        this.validator = validator;
        this.filename = filename;
    }

    private void loadFromFile() {
        storage.clear();
        try (FileInputStream in = new FileInputStream(filename)) {
            try (ObjectInputStream objIn = new ObjectInputStream(in)) {
                while (true) {
                    T entity = (T)objIn.readObject();
                    storage.put(entity.getId(), entity);
                }
            }
        } catch (Exception ex) {

        }
    }

    private void writeToFile() {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            try (ObjectOutputStream objOut = new ObjectOutputStream(out)) {
                for (T entity : storage.values()) {
                    objOut.writeObject(entity);
                }
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public T findById(String id) {
        loadFromFile();
        return storage.get(id);
    }

    @Override
    public void upsert(T entity) throws InvalidCNPException, PozitivePriceException, NonUniqueCNPException {
        loadFromFile();
        validator.validate(entity);
        storage.put(entity.getId(), entity);
        writeToFile();
    }

    @Override
    public void remove(String id) {
        loadFromFile();
        if (!storage.containsKey(id)) {
            throw new RuntimeException("There is no entity with the given id to remove.");
        }

        storage.remove(id);
        writeToFile();
    }

    @Override
    public List<T> getAll() {
        loadFromFile();
        return new ArrayList<>(storage.values());
    }
}

package Repository;

import Domain.Medicament;
import Domain.MedicamentValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicamentRepository {

    private Map<String, Medicament> storage = new HashMap<>();
    // storage asociaza id cu obiectul, cand sterg id de fapt sterg obiectul storage care are asociat id

    private MedicamentValidator validator;

    public MedicamentRepository(MedicamentValidator validator) {
        this.validator = validator;
    }

    public Medicament findbyId(String id) {
        return storage.get(id);
    }

    /**
     * Adds or updates a med if it already exists.
     * @param med the med to add or update.
     */
    public void upsert(Medicament med) {
        validator.validate(med);
        storage.put(med.getId(), med);
    }

    /**
     * Removes a med with the given id.
     * @param id the id.
     * @throws RuntimeException if there's no med with the given id.
     */
    public void remove (String id) {
        if (!storage.containsKey(id)) {
            throw new RuntimeException("There's no cake with id=%s to remove.");
        }
        storage.remove(id);
    }

    public List<Medicament> getAll() {
        return new ArrayList<>(storage.values());
    }
}

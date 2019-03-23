package Service;

import Domain.Client;
import Domain.Medicament;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class MedicamentService {

    private IRepository<Medicament> repository;

    public MedicamentService(IRepository<Medicament> repository) {
        this.repository = repository;
    }

    public void addOrUpdate (String id, String name, String manufacturer, double price, boolean prescriptionNeeded) {
        Medicament existing = repository.findById(id);
        if (existing != null ) {
            // keep unchanged fields as they were
            if (name.isEmpty()) {
                name = existing.getName();
            }
            if (manufacturer.isEmpty()) {
                manufacturer = existing.getManufacturer();
            }
            if (price == 0) {
                price = existing.getPrice();
            }
        }
        Medicament med = new Medicament(id, name, manufacturer, price, prescriptionNeeded);
        repository.upsert(med);
    }

    /**
     * Searches meds whose fields contain a given text
     * @param text the text searched for
     * @return A list of meds whose fields contain text
     */
    public List<Medicament> fullTextSearch(String text) {
        List<Medicament> results = new ArrayList<>();
        for (Medicament m : repository.getAll()) {
            // Might return false positives
            if (m.toString().contains(text)) {
                results.add(m);
            }
          /*  if (c.getFirstName().contains(text)) ||
            (c.getLastName().contains(text)) ||
                    (c.getCNP().contains(text)) ||
                    (c.getDateOfBirth().contains(text)) ||
                    (c.getDateOfRegistration().contains(text))
            */
        }
        return results;
    }

    public void remove(String id){
        repository.remove(id);
    }

    public List<Medicament> getAll(){
        return repository.getAll();
    }
}

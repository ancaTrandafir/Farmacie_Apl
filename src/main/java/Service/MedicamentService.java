package Service;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import CustomExceptions.PozitivePriceException;
import Domain.Medicament;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MedicamentService {

    private IRepository<Medicament> repository;
    private Stack<UndoRedoOperation<Medicament>> undoableOperations = new Stack<>();
    private Stack<UndoRedoOperation<Medicament>> redoableeOperations = new Stack<>();

    public MedicamentService(IRepository<Medicament> repository) throws PozitivePriceException, InvalidCNPException, NonUniqueCNPException {
        this.repository = repository;
    }

    public void idPrim(String id) {
        int n = Integer.parseInt(id);
            for (int i = 2; 2 * i < n; i++) {
                if (n % i != 0) {

                }

        }
    }

    public void addOrUpdate(String id, String name, String manufacturer, double price, boolean prescriptionNeeded) throws PozitivePriceException, InvalidCNPException, NonUniqueCNPException {


       //     int n = Integer.parseInt(id);
        //    for (int i = 2; 2 * i < n; i++) {
         //       if (n % i != 0) {
                    Medicament existing = repository.findById(id);
                    if (existing != null) {
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

                        Medicament med = new Medicament(id, name, manufacturer, price, prescriptionNeeded);
                         repository.upsert(med);
                         } else {
                            // sigur e add
                             Medicament med = new Medicament(id, name, manufacturer, price, prescriptionNeeded);
                        int n = Integer.parseInt(id);
                        for (int i = 2; 2 * i < n; i++) {
                            if (n % i != 0) {
                              repository.upsert(med);
                              undoableOperations.add(new AddOperation<>(repository, med));
                            redoableeOperations.clear();
                        } else {

                                repository.remove(id);
                                undoableOperations.add(new AddOperation<>(repository, med));
                                redoableeOperations.clear();

                            }
                     }
                 }
    }


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

    public void undo() {
        if (!undoableOperations.empty()) {
            UndoRedoOperation<Medicament> lastOperation = undoableOperations.pop();
            lastOperation.doUndo();
            redoableeOperations.add(lastOperation);

        }
    }

    public void redo() throws InvalidCNPException, PozitivePriceException, NonUniqueCNPException {
        if (!redoableeOperations.empty()) {
            UndoRedoOperation<Medicament> lastOperation = redoableeOperations.pop();
            lastOperation.doRedo();
            undoableOperations.add(lastOperation);
        }
    }

    public void remove(String id) {
        repository.remove(id);
    }

    public List<Medicament> getAll() {
        return repository.getAll();
    }

    public void increasePrices(Integer procent, Integer minimumValue) {
        for (Medicament med : repository.getAll()){
            if (med.getPrice() < minimumValue){
                med.setPrice(med.getPrice() + procent / 100.0 * med.getPrice());
                try {
                    repository.upsert(med);
                } catch (InvalidCNPException e) {
                    e.printStackTrace();
                } catch (PozitivePriceException e) {
                    e.printStackTrace();
                } catch (NonUniqueCNPException e) {
                    e.printStackTrace();
                }
                System.out.println(med);
                System.out.println(repository.getAll());
            }
        }
    }


}

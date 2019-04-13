package Service;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import CustomExceptions.PozitivePriceException;
import Domain.Client;
import Domain.ClientValidator;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClientService {

    private ClientValidator clientValidator = new ClientValidator();
    private IRepository<Client> repository;
    private Stack<UndoRedoOperation<Client>> undoableOperations = new Stack<>();
    private Stack<UndoRedoOperation<Client>> redoableeOperations = new Stack<>();

    public ClientService(IRepository<Client> repository) {
        this.repository = repository;
    }

    public void addOrUpdate(String id, String lastName, String firstName, String CNP, String dateOfBirth, String dateOfRegistration) throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException {
        Client client = new Client(id, lastName, firstName, CNP, dateOfBirth, dateOfRegistration);
        clientValidator.validate(client);
        try {
            for (Client c : repository.getAll()) {
                if (c.getCNP().equals(CNP)) {
                    throw new NonUniqueCNPException("nu e unic");
                }
            }
        }catch (NonUniqueCNPException exeption) {
            throw new RuntimeException(exeption.getMessage());
        }

        Client existing = repository.findById(id);
        if (existing != null) {
            // keep unchanged fields as they were
            if (lastName.isEmpty()) {
                lastName = existing.getLastName();
            }
            if (firstName.isEmpty()) {
                firstName = existing.getFirstName();
            }
            if (CNP.isEmpty()) {
                CNP = existing.getCNP();
            }
            if (dateOfBirth.isEmpty()) {
                dateOfBirth = existing.getDateOfBirth();
            }
            if (dateOfRegistration.isEmpty()) {
                dateOfRegistration = existing.getDateOfRegistration();
            }
        }

        repository.upsert(client);
    }

    /**
     * Searches clients whose fields contain a given text
     * @param text the text searched for
     * @return A list of clients whose fields contain text
     */
    public List<Client> fullTextSearch(String text) {
        List<Client> results = new ArrayList<>();
        for (Client c : repository.getAll()) {
            // Might return false positives
            if (c.toString().contains(text)) {
                results.add(c);
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

    public void remove(String id) {
        repository.remove(id);
    }

    public List<Client> getAll() {
        return repository.getAll();
    }

    public void undo() {
        if (!undoableOperations.empty()) {
            UndoRedoOperation<Client> lastOperation = undoableOperations.pop();
            lastOperation.doUndo();
            redoableeOperations.add(lastOperation);

        }
    }

    public void redo() throws InvalidCNPException, PozitivePriceException, NonUniqueCNPException {
        if (!redoableeOperations.empty()) {
            UndoRedoOperation<Client> lastOperation = redoableeOperations.pop();
            lastOperation.doRedo();
            undoableOperations.add(lastOperation);
        }
    }
}

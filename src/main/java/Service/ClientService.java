package Service;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import CustomExceptions.PozitivePriceException;
import Domain.Client;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private IRepository<Client> repository;

    public ClientService(IRepository<Client> repository) {
        this.repository = repository;
    }

    public void addOrUpdate(String id, String lastName, String firstName, String CNP, String dateOfBirth, String dateOfRegistration) throws InvalidCNPException, NonUniqueCNPException, PozitivePriceException {
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
        Client client = new Client(id, lastName, firstName, CNP, dateOfBirth, dateOfRegistration);
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
}

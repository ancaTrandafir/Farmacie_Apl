/*package Repository;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;
import Domain.Client;
import Domain.ClientValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRepository {
    private Map<String, Client> storage = new HashMap<>();
    private ClientValidator validator;

    public ClientRepository(ClientValidator validator) {
        this.validator = validator;
    }

    public Client findById(String id) {
        return storage.get(id);             // returneaza obiectul storage de tip Dictionar identificat cu id dat la cheie, nu id in sine.
    }

    /**
     * Adds or updates a client if it already exists.
     * @param client the client to add or to update
     */
   /* public void upsert(Client client) throws InvalidCNPException, NonUniqueCNPException {
        validator.validate(client);
        storage.put(client.getId(),client);
    }

    /**
     * Removes a client with the given id
     * @param id the id
     * @throws  RuntimeException if there's no client with the given id.
     */
  /*  public void remove(String id) {
      if (!storage.containsKey(id)) {
         throw new RuntimeException("There's no client with id=%s id to remove.");
      }
      storage.remove(id);
    }

    public List<Client> getAll() {
        return  new ArrayList<>(storage.values());
    }
}
*/
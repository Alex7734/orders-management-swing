package logic;

import data.ClientDAO;
import model.Client;
import util.MaxIdFlagEnum;

import java.util.ArrayList;

import static model.Client.validClient;
import static util.IdGenerator.generateId;

/**
 *  A class that contains the business logic of the Client class.
 *  It is used to validate the data and to access the data in the Client table.
 *  The class contains a validator, a data access object and a client object.
 */
public class ClientBLL {
    private final ClientDAO clientDAO;

    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    /**
     * Creates a new client object and inserts it into the database.
     * @param firstName the first name of the client
     * @param lastName the last name of the client
     * @param age the age of the client
     * @param address the address of the client
     * @param cnp the CNP of the client
     * @return true if the client was successfully inserted, false otherwise
     */
    public boolean insertClient(String firstName, String lastName, int age, String address, String cnp) {
        Client client = new Client(generateId(MaxIdFlagEnum.CLIENT), firstName, lastName, age, address, cnp);
        if (!validClient(client))
            return false;

        clientDAO.insert(client);
        return true;
    }

    /**
     * Updates a client object from the database.
     * @param clientId the id of the client to be updated
     * @param firstName the new first name of the client
     * @param lastName the new last name of the client
     * @param age the new age of the client
     * @param address the new address of the client
     * @param cnp the new CNP of the client
     * @return true if the client was successfully updated, false otherwise
     */
    public boolean updateClient(long clientId, String firstName, String lastName, int age, String address, String cnp) {
        Client clientToUpdate = createClient(clientId, firstName, lastName, age, address, cnp);
        if (!validClient(clientToUpdate))
            return false;

        clientDAO.update(clientToUpdate);

        return true;
    }

    /**
     * Deletes a client object from the database.
     * @param id the id of the client to be deleted
     */
    public void deleteClient(long id) {
        clientDAO.delete(id);
    }

    /**
     * Finds a client object in the database.
     * @param id the id of the client to be found
     * @return the client object if it was found, null otherwise
     */
    public Client findClient(long id) {
        return clientDAO.findById(id);
    }

    /**
     * Finds all client objects in the database.
     * @return an ArrayList containing all the client objects
     */
    public ArrayList<Client> findAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Creates a client object.
     * @param id the id of the client
     * @param firstName the first name of the client
     * @param lastName the last name of the client
     * @param age the age of the client
     * @param address the address of the client
     * @param cnp the CNP of the client
     * @return the client object
     */
    private Client createClient(long id, String firstName, String lastName, int age, String address, String cnp) {
        return new Client(id, firstName, lastName, age, address, cnp);
    }
}

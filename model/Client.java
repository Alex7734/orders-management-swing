package model;


/**
 * A class modeling a client.
 *
 */
public class Client {
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String address;
    private String cnp;

    public Client() {
    }

    public Client(long id, String firstName, String lastName, int age, String address, String cnp) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.cnp = cnp;
    }

    /**
     * Validates a client.
     *
     * @param client The client to be validated.
     * @return true if the client is valid, false otherwise.
     */
    public static boolean validClient(Client client) {
        return client.getAge() > 0 && client.getAge() < 100;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }
}

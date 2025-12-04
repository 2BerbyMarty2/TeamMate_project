/**
 * Represents a user in the TeamMate system.
 *
 * <p>A User has a unique ID, a name, and an email address. This class
 * provides basic getters and setters for these fields.</p>
 *
 * <p>It serves as the base class for more specialized user types
 * such as Player and Organizer.</p>
 *
 * @author vidura nayanawickrama
 * @version 1.0
 */
public class User {

    /** Unique identifier for the user */
    private String ID;

    /** Full name of the user */
    private String name;

    /** Email address of the user */
    private String email;

    /**
     * Constructs a new User with the specified ID, name, and email.
     *
     * @param ID    the unique identifier for the user
     * @param name  the full name of the user
     * @param email the email address of the user
     */
    public User(String ID, String name, String email){
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    /**
     * Returns the user's unique ID.
     * @return the ID
     */
    public String getID(){
        return ID;
    }

    /**
     * Sets the user's unique ID.
     * @param ID the new ID
     */
    public void setID(String ID){
        this.ID = ID;
    }

    /**
     * Returns the user's full name.
     * @return the name
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the user's full name.
     * @param name the new name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Returns the user's email address.
     * @return the email
     */
    public String getEmail(){
        return email;
    }

    /**
     * Sets the user's email address.
     * @param email the new email
     */
    public void setEmail(String email){
        this.email = email;
    }

} // end class

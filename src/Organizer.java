/**
 * Represents an event organizer within the TeamMate system.
 *
 * <p>This class extends {@link User} and adds additional attributes
 * specific to event management, including:</p>
 * <ul>
 *   <li>Event name assigned to the organizer</li>
 *   <li>Organizational role or position</li>
 *   <li>Hashed password for secure authentication</li>
 * </ul>
 *
 * <p>Used to manage events, authenticate organizers, and oversee
 * team formation within the system.</p>
 */
public class Organizer extends User {

    private String eventName;   // Event this organizer is responsible for
    private String position;    // Organizational role or title
    private String password;    // Hashed password used for secure login

    /**
     * Constructs an Organizer with event-specific and credential details.
     *
     * @param ID        unique organizer identifier
     * @param name      organizer's full name
     * @param email     organizer's email address
     * @param eventName name of the event assigned to the organizer
     * @param position  organizer's job title or role
     * @param password  securely hashed password
     */
    public Organizer(String ID, String name, String email,
                     String eventName, String position, String password) {

        super(ID, name, email);
        this.eventName = eventName;
        this.position = position;
        this.password = password;
    }

    /**
     * Returns the event name assigned to this organizer.
     * @return event name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the event name for this organizer.
     * @param eventName the event name
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Returns the position or role of this organizer.
     * @return organizer's position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position or role of this organizer.
     * @param position the new position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Returns the hashed password of the organizer.
     * @return hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the organizer's password.
     * Expected to receive a SHA-256 hashed string.
     *
     * @param password hashed password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a formatted organizer profile string.
     * <p>Passwords are intentionally omitted for security reasons.</p>
     * @return formatted profile
     */
    @Override
    public String toString() {
        return  "========== ORGANIZER PROFILE ==========\n" +
                "ID              : " + getID() + "\n" +
                "Name            : " + getName() + "\n" +
                "Email           : " + getEmail() + "\n" +
                "Event Name      : " + eventName + "\n" +
                "Position        : " + position + "\n" +
                "=======================================\n";
    }
} // end Organizer

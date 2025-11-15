public class Organizer extends User {
    private String eventName;
    private String position;

    public Organizer(String ID, String name, String email,
                     String eventName, String position) {
        super(ID, name, email);
        this.eventName = eventName;
        this.position = position;
    }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    @Override
    public String toString() {
        return "\nID: " + getID() + ", Name: " + getName() + ", Email: " + getEmail() +
                ", Event: " + eventName + ", Position: " + position;
    }
}

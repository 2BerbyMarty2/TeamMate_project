public class Organizer extends User {
    private String eventName;
    private String position;
    private String password;

    // Constructor
    public Organizer(String ID, String name, String email, String eventName, String position, String password){
        super(ID, name, email);
        this.eventName = eventName;
        this.position = position;
        this.password = password;
    }

    // Getters and Setters
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getPosition(){
        return position;
    }
    public void setPosition(String position){
        this.position = position;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    // To string
    @Override
    public String toString(){
        return "========== ORGANIZER PROFILE ==========\n" +
                "ID              : " + getID() + "\n" +
                "Name            : " + getName() + "\n" +
                "Email           : " + getEmail() + "\n" +
                "Event Name      : " + eventName + "\n" +
                "Position        : " + position + "\n" +
                "Password        : " + password + "\n" +
                "=======================================";
    }
}
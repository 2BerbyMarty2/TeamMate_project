public class User{
    private String ID;
    private String name;
    private String email;

    // Constructor
    public User(String ID, String name, String email){
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public String getID(){
        return ID;
    }
    public void setID(String ID){
        this.ID = ID;
    }
    public String getName(){
        return name;
    }
    public void getName(String name){
        this.name = name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
public class Player {
    private String name ;
    private Role role ;
    private int voters = 0 ;
    private boolean isSilenced = false ;
    private boolean isAlive = true ;
    private boolean saved = false;

    public Player(String name, Role roll) {
        this.name = name;
        this.role = roll;
    }

    public Player(String name) {
        this.name = name;
    }

    public void addVoters() {
        voters++ ;
    }

    public void deleteVoters () {
        voters = 0 ;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public boolean isSilenced() {
        return isSilenced;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getVoters() {
        return voters;
    }

    public boolean isMafia() {
        return false ;
    }

    public boolean isAwake() {
        return false ;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setSilenced(boolean silenced) {
        isSilenced = silenced;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean getSaved() {
        return saved ;
    }

    public void setSaved(boolean b) {
        this.saved = b ;
    }
}

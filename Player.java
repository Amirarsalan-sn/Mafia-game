public class Player {
    private String name ;
    private Role role ;
    private String[] voters = new String[100] ;
    private int votersIterator = 0 ;
    private boolean isSilenced = false ;
    private boolean isAlive = true ;

    public Player(String name, Role roll) {
        this.name = name;
        this.role = roll;
    }

    public Player(String name) {
        this.name = name;
    }

    public void addVoters(String name) {
        voters[votersIterator++] = name ;
    }

    public void deleteVoters () {
        voters = new String[100] ;
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

    public int getVotersIterator() {
        return votersIterator;
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
}

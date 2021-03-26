public class Player {
    private String name ;
    private Role role ;

    public Player(String name, Role roll) {
        this.name = name;
        this.role = roll;
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

public class Player {
    private String name ;
    private Roll roll ;

    public Player(String name, Roll roll) {
        this.name = name;
        this.roll = roll;
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Roll getRoll() {
        return roll;
    }

    public void setRoll(Roll roll) {
        this.roll = roll;
    }
}

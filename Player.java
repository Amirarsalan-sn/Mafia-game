public abstract class Player {
    private String name ;
    private Roll roll ;

    public Player(String name, Roll roll) {
        this.name = name;
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public Roll getRoll() {
        return roll;
    }
}

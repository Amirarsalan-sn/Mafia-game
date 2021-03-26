public class Mafia extends Player {
    public Mafia(String name) {
        super(name, Role.mafia);
    }

    public Mafia(String name, Role role) {
        super(name , role);
    }

    @Override
    public boolean isMafia() {
        return true ;
    }

    @Override
    public boolean isAwake() {
        return true ;
    }
}

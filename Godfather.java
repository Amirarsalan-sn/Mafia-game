public class Godfather extends Mafia {
    public Godfather(String name) {
        super(name, Role.godfather);
    }

    @Override
    public boolean isMafia() {
        return false ;
    }
}

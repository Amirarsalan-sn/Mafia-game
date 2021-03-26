public class Silencer extends Mafia {
    private boolean silencedSomeone = false ;

    public Silencer(String name) {
        super(name, Role.silencer);
    }

    public boolean isSilencedSomeone() {
        return silencedSomeone;
    }

    public void setSilencedSomeone(boolean silencedSomeone) {
        this.silencedSomeone = silencedSomeone;
    }
}

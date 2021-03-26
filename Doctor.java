public class Doctor extends Player {
    public Doctor(String name) {
        super(name, Role.doctor);
    }

    @Override
    public boolean isAwake() {
        return true ;
    }
}

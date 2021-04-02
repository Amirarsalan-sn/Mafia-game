public class Doctor extends Villager {
    public Doctor(String name) {
        super(name, Role.doctor);
    }

    @Override
    public boolean isAwake() {
        return true ;
    }
}

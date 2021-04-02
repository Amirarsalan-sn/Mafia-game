public class Bulletproof extends Villager {
    private boolean beingShot = false ;
    public Bulletproof(String name) {
        super(name, Role.bulletproof);
    }

    public boolean isBeingShot() {
        return beingShot;
    }

    public void setBeingShot(boolean beingShot) {
        this.beingShot = beingShot;
    }
}


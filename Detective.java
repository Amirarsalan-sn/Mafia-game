public class Detective extends Villager {
    private boolean asked = false ;

    public Detective(String name) {
        super(name, Role.detective);
    }

    @Override
    public boolean isAwake() {
        return true ;
    }

    public boolean isAsked() {
        return asked;
    }

    public void setAsked(boolean b){
        this.asked = b ;
    }
}

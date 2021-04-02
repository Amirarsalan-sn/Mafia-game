import java.util.ArrayList;
import java.util.List;

public class Mafia extends Player {
    private boolean informed = false ;
    public static List<Player> voteeNames = new ArrayList<Player>() ;
    public Mafia(String name) {
        super(name, Role.mafia);
    }

    public Mafia(String name, Role role) {
        super(name , role);
    }

    public boolean isInformed() {
        return informed;
    }

    public void setInformed(boolean informed) {
        this.informed = informed;
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

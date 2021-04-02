import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Player[] players = new Player[100] ;
    public List<Player> mafias = new ArrayList<Player>() ;
    private Player joker ;
    private int playerIterator = 0 ;
    private boolean started = false ;
    private int dayIterator = 1 ;
    private int nightIterator = 1 ;
    private String gameStatusAfterNight = "" ;
    private int mafiaNumber = 0 ;
    private int villagerNumber = 0 ;
    private int jokerNumber = 0 ;


    private void addPlayer (Player player) {
        players[playerIterator++] = player ;
    }

    private int findPlayer (String name) {
        for (int i = 0; i < playerIterator; i++) {
            if(players[i].getName().equals(name))
                return i ;
        }
        return -1;
    }

    @Override
    public String toString() {
        String result = "" ;
        for (int i = 0; i < playerIterator; i++) {
            result += players[i].getName() + ": " + players[i].getRole().name() + "\n" ;
        }
        return result + "\nReady? Set! Go." ;
    }

    private void addMafiaNumber () {
        mafiaNumber++ ;
    }

    private void subtractMafiaNumber () {
        mafiaNumber -- ;
    }

    private void setVillagerNumber () {
        villagerNumber = playerIterator - mafiaNumber -jokerNumber ;
    }

    public Player getJoker() {
        return joker;
    }

    public void setJoker(Player joker) {
        this.joker = joker;
    }

    private void subtractVillagerNumber () {
        villagerNumber -- ;
    }

    private void addJokerNumber () {
        jokerNumber ++ ;
    }

    public int getMafiaNumber() {
        return mafiaNumber;
    }

    public int getDayIterator() { // for random name in informer
        return dayIterator;
    }

    public static void main(String[] args) {
        Game god = null ;
        Scanner scanner = new Scanner(System.in) ;
        game : while (scanner.hasNext()) {
            String[] command = scanner.nextLine().split(" ") ;
            if(command[0].equals("create_game")) {
                god = new Game() ;
                for (int i = 1; i < command.length ; i++) {
                    god.addPlayer(new Player(command[i]));
                }
            } else if(command[0].equals("assign_role")) {
                if(god == null) {
                    System.out.println("no game created");
                } else if (god.findPlayer(command[1]) == -1) {
                    System.out.println("user not found");
                } else if (!contains(command[2])) {
                    System.out.println("role not found");
                } else {
                    int number = god.findPlayer(command[1]) ;
                    god.players[number].setRole(Role.valueOf(command[2]));
                    god.convert(number) ;
                }
            } else if (command[0].equals("start_game")) {
                if(god == null) {
                    System.out.println("no game created");
                } else if (god.started) {
                    System.out.println("game has already started");
                } else if(!god.checkRoles()){
                    System.out.println("one or more player do not have a role");
                } else if(command[0].equals("swap_character")) {
                    System.out.println("no game started");
                } else {
                    god.started = true ;
                    System.out.println(god.toString());
                    start : while(true) {
                        System.out.println("Day " + god.dayIterator++) ;
                        System.out.print(god.gameStatusAfterNight);
                        String silence = "" ;
                        god.gameStatusAfterNight = "" ;
                        Day : while(scanner.hasNext()) {
                            command = scanner.nextLine().split(" ") ;
                            if (command.length == 2) {
                                int number0 = god.findPlayer(command[0]);
                                int number1 = god.findPlayer(command[1]);
                                if (number0 == -1 || number1 == -1) {
                                    System.out.println("user not found");
                                } else if (!god.players[number0].isAlive()) {
                                    System.out.println("voter already dead"); // new , i added this one .
                                } else if (god.players[number0].isSilenced()) {
                                    System.out.println("voter is silenced");
                                } else if (!god.players[number1].isAlive()) {
                                    System.out.println("votee already dead");
                                } else {
                                    god.players[number0].setVote(god.players[number1]);
                                }
                            } else if (command.length == 1 && command[0].equals("end_vote")) {
                                god.completeVotes() ;
                                int num = god.findMax() ;
                                Player[] deadBodies = god.findVotee(num) ;
                                if(god.checkDeadBodies(deadBodies)) {
                                    System.out.println(god.gameStatusAfterNight);
                                    break game;
                                }
                                else {
                                    break Day;
                                }
                            } else if (command[0].equals("start_game")) {
                                System.out.println("game has already started");//added
                            } else if (command[0].equals("swap_character")) {
                                System.out.println("voting in progress");
                            } else if (command[0].equals("get_game_state")) {
                                System.out.println(god.statusToString());
                            } else {
                                System.err.println("command not found !");
                            }
                        }
                        god.deleter();
                        System.out.println("Night " + god.nightIterator++) ;
                        System.out.println(god.nightToString());
                        Night : while (scanner.hasNext()) {
                            command = scanner.nextLine().split(" ");
                            if (command.length == 2) {
                                int number0 = god.findPlayer(command[0]);
                                int number1 = god.findPlayer(command[1]);
                                if (number0 == -1) {
                                    System.out.println("user not joined");
                                } else if (!god.players[number0].isAwake()) {//silence for mafias ???
                                    System.out.println("user can not wake up during night");
                                } else if (!god.players[number0].isAlive()) {
                                    System.out.println("user already dead");
                                } else {
                                    switch (god.players[number0].getRole()) {
                                        case silencer:
                                            if (!((Silencer) god.players[number0]).isSilencedSomeone()) {
                                                if (checkSecond(number1, god)) {
                                                    god.players[number1].setSilenced(true);
                                                    silence += god.players[number1].getName()  + " Silenced" + "\n" ;
                                                    ((Silencer) god.players[number0]).setSilencedSomeone(true);
                                                }
                                                break ;
                                            }
                                        case mafia:
                                        case godfather:
                                        case doctor :
                                            if(checkSecond(number1 , god))
                                                god.players[number0].setVote(god.players[number1]);
                                            break ;
                                        case detective :
                                            if(checkSecond2(number0 , number1 , god)) {
                                                System.out.println(god.players[number1].isMafia() ? "YES" : "NO");
                                                ((Detective) god.players[number0]).setAsked(true);
                                            }
                                            break ;
                                    }
                                }
                            } else if (command.length == 1 && command[0].equals("end_night")) {
                                god.completeVotesForNight();
                                int num = god.findMax();
                                Player[] deadBodies = god.findVotee(num) ;
                                if(deadBodies.length == 1) {
                                    god.gameStatusAfterNight += "mafia tried to kill " + deadBodies[0].getName() + "\n";
                                    deadBodies[0].setVotedToBeKilled(true);
                                }
                                god.completeTargets() ;
                                for (int i = 0; i < deadBodies.length; i++) {
                                    if(deadBodies[i].getSaved())
                                        deadBodies[i] = null;
                                }
                                god.gameStatusAfterNight += silence ;
                                if(god.checkDeadBodiesAtNight(deadBodies)) {
                                    System.out.println("Day " + god.dayIterator++) ;
                                    System.out.print(god.gameStatusAfterNight);
                                    break game;
                                }
                                else
                                    break Night;

                            } else if (command[0].equals("start_game")) {
                                System.out.println("game has already started");//added
                            } else if (command[0].equals("get_game_state")) {
                                System.out.println(god.statusToString());
                            } else if (command[0].equals("swap_character")) {
                                System.out.println("can’t swap before end of night");
                            } else {
                                System.err.println("command not found !");
                            }
                        }
                        god.nightDeleter();
                        swap : while(true) {
                            command = scanner.nextLine().split(" ");
                            if (command[0].equals("swap_character") && command.length == 3) {
                                int number0 = god.findPlayer(command[1]);
                                int number1 = god.findPlayer(command[2]);
                                if(checkSecond(number0 , god) && checkSecond(number1 , god)) {
                                    Swap.swap(god.players[number0], god.players[number1]);
                                    god.gameStatusAfterNight += god.players[number0].getName() + " swapped characters with " + god.players[number1].getName() + "\n";
                                    break ;
                                }
                            } else if (command[0].equals("start_game")) {
                                System.out.println("game has already started");
                            } else if (command[0].equals("get_game_state")) {
                                System.out.println(god.statusToString());
                            } else {
                                System.err.println("command not found !");
                            }
                        }
                    }
                }
            } else if (command[0].equals("get_game_state")) {
                if(god == null)
                    System.out.println("no game created");
                else
                    System.out.println(god.statusToString());
            } else {
                System.err.println("command not found !");
            }
        }
        scanner.close();
    }

    private void completeVotesForNight() {
        for (int i = 0; i < playerIterator; i++) {
            if(players[i].getVote() != null)
                if(players[i] instanceof Doctor)
                    players[i].getVote().setSaved(true);
                else
                    players[i].getVote().addVoters();
        }
    }

    private void nightDeleter() {
        for (int i = 0; i < playerIterator; i++) {
            players[i].deleteVoters();
            players[i].setVote(null);
            players[i].setSaved(false);
            if(players[i] instanceof Silencer) {
                ((Silencer) players[i]).setSilencedSomeone(false);
            } else if(players[i] instanceof Detective) {
                ((Detective) players[i]).setAsked(false);
            }
        }

    }

    private void completeTargets() {
        for (int i = 0; i < mafias.size(); i++) {
            if(mafias.get(i).getVote() != null && mafias.get(i).isAlive() && !mafias.get(i).getVote().isVotedToBeKilled()) {
                Mafia.voteeNames.add(mafias.get(i).getVote());
            }
        }
    }

    public String statusToString() {
        return "Mafia = " + mafiaNumber + "\nVillager = " + villagerNumber;
    }

    private boolean checkDeadBodiesAtNight(Player[] deadBodies) {
        int number = 0 ;
        for (int i = 0; i < deadBodies.length; i++) {
            if(deadBodies[i] != null)
                number ++ ;
        }
        if(number == 1) {
            for (int i = 0; i < deadBodies.length; i++) {
                if(deadBodies[i] != null) {
                    if(!(deadBodies[i] instanceof Bulletproof) || (deadBodies[i] instanceof Bulletproof && ((Bulletproof) deadBodies[i]).isBeingShot())) {
                        deadBodies[i].setAlive(false);
                        gameStatusAfterNight += deadBodies[i].getName() + " was killed\n";
                        subtractVillagerNumber();
                        if (deadBodies[i].getRole().equals(Role.Joker)) {
                            gameStatusAfterNight += "Joker win !" ;
                            return true;
                        }
                        if (deadBodies[i].getRole().equals(Role.informer)) {
                            gameStatusAfterNight += "\n" + deadBodies[i].getName() + " was an informer\n";
                            gameStatusAfterNight += ((Informer) deadBodies[i]).informerFinalize(this);
                        }
                    } else {
                        ((Bulletproof) deadBodies[i]).setBeingShot(true);
                    }
                    break;
                }
            }
        }
        return gameCheck() ;
    }

    private boolean checkDeadBodies(Player[] deadBodies) {
        if(deadBodies.length > 1) {
            System.out.println("nobody died");
            return false;
        } else if(deadBodies[0].getRole().equals(Role.Joker)) {
            System.out.println(deadBodies[0].getName() + " died");
            System.out.println("Joker won!");
            return true;
        } else {
            deadBodies[0].setAlive(false);
            System.out.println(deadBodies[0].getName() + " died");
            if (deadBodies[0].getRole().equals(Role.informer)) {
                System.out.println(deadBodies[0].getName() + " was an informer");
                System.out.println(((Informer) deadBodies[0]).informerFinalize(this));
            }
            if(deadBodies[0].isMafia())
                subtractMafiaNumber();
            else if(deadBodies[0].getRole().equals(Role.godfather))
                subtractMafiaNumber();
            else
                subtractVillagerNumber();
            return gameCheck();
        }
    }

    private boolean gameCheck() {
        if(villagerNumber <= mafiaNumber) {
            gameStatusAfterNight += "Mafia won!" ;
            return true;
        } else if (mafiaNumber == 0) {
            gameStatusAfterNight += "Villagers won!" ;
            return true;
        }
        return false;
    }

    private void deleter() {
        for (int i = 0; i < playerIterator; i++) {
            players[i].deleteVoters();
            players[i].setVote(null);
            players[i].setSilenced(false);
            players[i].setSaved(false);
            players[i].setVotedToBeKilled(false);
            if(players[i] instanceof Silencer) {
                ((Silencer) players[i]).setSilencedSomeone(false);
            }
        }
        Mafia.voteeNames = new ArrayList<Player>() ;
    }

    private void completeVotes() {
        for (int i = 0; i < playerIterator; i++) {
            if(players[i].getVote() != null)
                players[i].getVote().addVoters();
        }
    }

    private static boolean checkSecond2(int number0, int number1, Game god) {
        if(number1 == -1) {
            System.out.println("user not found");
            return false ;
        } if (((Detective)god.players[number0]).isAsked()) {
            System.out.println("detective has already asked");
            return false;
        }if(!god.players[number1].isAlive()) {
            System.out.println("suspect is dead");
            return false ;
        }
        return true;
    }

    private static boolean checkSecond(int number1 , Game god) {
        if(number1 == -1) {
            System.out.println("user not joined");
            return false;
        } if (!god.players[number1].isAlive()) {
            System.out.println("votee already dead");
            return false ;
        }
        return true ;
    }

    public String nightToString() {
        String result = "" ;
        for (int i = 0; i < playerIterator; i++) {
            if(players[i].isAwake() && players[i].isAlive())
                result += players[i].getName() + ": " + players[i].getRole().name()+"\n";
        }
        return result;
    }

    private Player[] findVotee(int num) {
        List<Player> votees = new ArrayList<Player>();
        for (int i = 0; i < playerIterator; i++) {
            if(players[i].getVoters() == num)
                votees.add(players[i]) ;
        }
        return votees.toArray(Player[]::new) ;
    }

    private int findMax() {
        int max = players[0].getVoters() ;
        for (int i = 1; i < playerIterator; i++) {
            if(max < players[i].getVoters())
                max = players[i].getVoters() ;
        }
        return max ;
    }

    private boolean checkRoles() {
        for (int i = 0; i < playerIterator; i++) {
            if(players[i].getRole() == null)
                return false ;
        }
        return true ;
    }

    private void convert(int player) {
        Player temp = players[player] ;
        switch (players[player].getRole()) {
            case Joker :
                players[player] = new Joker(temp.getName()) ;
                addJokerNumber();
                setJoker(players[player]);
                break ;
            case mafia :
                players[player] = new Mafia(temp.getName()) ;
                addMafiaNumber();
                mafias.add(players[player]) ;
                break ;
            case doctor :
                players[player] = new Doctor(temp.getName()) ;
                break ;
            case informer :
                players[player] = new Informer(temp.getName()) ;
                break ;
            case silencer :
                players[player] = new Silencer(temp.getName()) ;
                addMafiaNumber();
                mafias.add(players[player]) ;
                break ;
            case villager :
                players[player] = new Villager(temp.getName()) ;
                break ;
            case detective :
                players[player] = new Detective(temp.getName()) ;
                break ;
            case godfather :
                players[player] = new Godfather(temp.getName()) ;
                addMafiaNumber();
                mafias.add(players[player]) ;
                break ;
            case bulletproof :
                players[player] = new Bulletproof(temp.getName()) ;
                break ;
        }
        setVillagerNumber();
    }

    private static boolean contains(String s) {
        for (Role role: Role.values()) {
            if(role.name().equals(s))
                return true ;
        }
        return false ;
    }

}

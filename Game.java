import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Player[] players = new Player[100] ;
    private int playerIterator = 0 ;
    private boolean started = false ;
    private int dayIterator = 1 ;
    private int nightIterator = 1 ;

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

    private void swap (Player player1 , Player player2) {
        Role temp = player1.getRole() ;
        player1.setRole(player2.getRole());
        player2.setRole(temp);
    }

    @Override
    public String toString() {
        String result = "" ;
        for (int i = 0; i < playerIterator; i++) {
            result += players[i].getName() + ": " + players[i].getRole().name() + "\n" ;
        }
        return result + "\nReady? Set! Go." ;
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
                    convert(god.players ,number) ;
                }
            } else if (command[0].equals("start_game")) {
                if(god == null) {
                    System.out.println("no game created");
                } else if (god.started) {
                    System.out.println("game has already started");
                } else if(!god.checkRoles()){
                    System.out.println("one or more player do not have a role");
                } else {
                    god.started = true ;
                    System.out.println(god.toString());
                    start : while(true) {
                        System.out.println("Day " + god.dayIterator++) ;
                        Day : while(scanner.hasNext()) {
                            command = scanner.nextLine().split(" ") ;
                            if (command.length == 2) {
                                int number0 = god.findPlayer(command[0]);
                                int number1 = god.findPlayer(command[1]);
                                if (number0 == -1 || number1 == -1) {
                                    System.out.println("user not found");
                                } else if (god.players[number0].isSilenced()) {
                                    System.out.println("voter is silenced");
                                } else if (!god.players[number1].isAlive()) {
                                    System.out.println("votee already dead");
                                } else if (!god.players[number0].isAlive()) {
                                    System.out.println("voter already dead"); // new , i added this one .
                                } else {
                                    god.players[number1].addVoters();
                                }
                            } else if (command.length == 1 && command[0].equals("end_vote")) {
                                int num = god.findMax() ;
                                Player[] deadBodies = god.findVotee(num) ;
                                if(deadBodies.length > 1) {
                                    System.out.println("nobody died");
                                    break Day;
                                } else if(deadBodies[0].getRole().equals(Role.Joker)) {
                                    System.out.println("Joker won!");
                                    break game;
                                } else {
                                    deadBodies[0].setAlive(false);
                                    System.out.println(deadBodies[0].getName() + "died");
                                    for (int i = 0; i < god.playerIterator; i++) {
                                        god.players[i].deleteVoters();
                                    }
                                    break Day;
                                }
                            } else if (command[0].equals("start_game")) {
                                System.out.println("game has already started");
                            } else {
                                System.err.println("command not found !");
                            }
                        }
                        System.out.println("Night " + god.nightIterator++) ;
                        Night : while (scanner.hasNext()) {
                            command = scanner.nextLine().split(" ") ;
                        }
                    }
                }
            } else {
                System.err.println("command not found !");
            }
        }
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

    private static void convert(Player[] players, int player) {
        Player temp = players[player] ;
        switch (players[player].getRole()) {
            case Joker :
                players[player] = new Joker(temp.getName()) ;
                break ;
            case mafia :
                players[player] = new Mafia(temp.getName()) ;
                break ;
            case doctor :
                players[player] = new Doctor(temp.getName()) ;
                break ;
            case informer :
                players[player] = new Informer(temp.getName()) ;
                break ;
            case silencer :
                players[player] = new Silencer(temp.getName()) ;
                break ;
            case villager :
                players[player] = new Villager(temp.getName()) ;
                break ;
            case detective :
                players[player] = new Detective(temp.getName()) ;
                break ;
            case godfather :
                players[player] = new Godfather(temp.getName()) ;
                break ;
            case bulletproof :
                players[player] = new Bulletproof(temp.getName()) ;
                break ;
        }
    }

    private static boolean contains(String s) {
        for (Role role: Role.values()) {
            if(role.name().equals(s))
                return true ;
        }
        return false ;
    }

}

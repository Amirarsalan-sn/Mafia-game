import java.util.Scanner;

public class Game {
    public Player[] players = new Player[100] ;
    private int playerIterator = 0 ;

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
        Roll temp = player1.getRoll() ;
        player1.setRoll(player2.getRoll());
        player2.setRoll(temp);
    }

    public static void main(String[] args) {
        Game god = null ;
        Scanner scanner = new Scanner(System.in) ;
        while (scanner.hasNext()) {
            String[] command = scanner.nextLine().split(" ") ;
            if(command[0].equals("creat_game")) {
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
                    god.players[god.findPlayer(command[1])].setRoll(Roll.valueOf(command[2]));
                    convert(god.players ,god.findPlayer(command[1])) ;
                }
            }
        }
    }

    private static void convert(Player[] players, int player) {
        Player temp = players[player] ;
        switch (players[player].getRoll()) {
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
        for (Roll roll: Roll.values()) {
            if(roll.name().equals(s))
                return true ;
        }
        return false ;
    }

}

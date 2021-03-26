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
                }
            }
        }
    }

    private static boolean contains(String s) {
        for (Roll roll: Roll.values()) {
            if(roll.equals(s))
                return true ;
        }
        return false ;
    }

}

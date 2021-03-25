import java.util.Scanner;

public class Game {
    private Player[] players = new Player[100] ;
    private int playerIterator = 0 ;

    private void getPlayer (Player player) {
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
        Game god = new Game();
        Scanner scanner = new Scanner(System.in) ;
    }

}

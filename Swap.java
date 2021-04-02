public class Swap {
    public static boolean swapped = false ;

    public static void swap (Player player1 , Player player2) {
        String tempName = player1.getName();
        player1.setName(player2.getName());
        player2.setName(tempName);
    }
}

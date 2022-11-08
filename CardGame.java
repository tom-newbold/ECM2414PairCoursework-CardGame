import java.util.Scanner;

public class CardGame {
    /**
     * Takes input, checks validity
     * Creates n Player threads
     * Distributes cards, inital win condition check, distribute decks
     * Start threads
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter the number of players: ");
        int players = reader.nextInt();
        reader.close();
        
        for (int i = 0; i < players; i++) {
            new Thread(Integer.toString(i)) {
                public void run() {
                    System.out.println("Player " + this.getName());
                }
            }.start();
        }
    }
}

/* 
 * placeholder n; mainloop
 * 
int n = 4;
Thread[] threads = new Thread[4];
for(int i=0;i<n;i++) {
    threads[i] = new Thread(); // anon class in here?
}

 */


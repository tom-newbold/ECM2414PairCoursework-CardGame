import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CardGame {
    /**
     * Takes input, checks validity
     * Creates n Player threads
     * Distributes cards, inital win condition check, distribute decks
     * Start threads
     */

    /* TASKS TO DO:
     * *DONE* player input and test validity 
     * *DONE* pack file input and validity
     * player threads created
     * *DONE* new card object created and written to pack file
     * cards are distributed to make list of hands and decks for each player
     *      NOTE: Order is 1 card to each player in a round robin until 4 cards, then decks 
     * output file is made for players and the decks EACH
     * wincondition checked upon each player, if no win is made
     * atomic action of drawing, checking win condition and discarding for player threads
     * atomic action of drawing from top of deck and add from bottom of deck
     * wincondition is met and player that has one notifies other players so game is stopped
     */

    public static Integer players;
    public static String packFile;

    public static void main(String[] args) {

        Scanner playerInput = new Scanner(System.in);
        // Will loop until input is an integer and is postive
        do {
            System.out.println("Please enter a positive number of players:");
            while (!playerInput.hasNextInt()) {
                System.out.println("Enter a valid number of players:");
                playerInput.next(); // Allows another input and is checked against conditions
            }
            players = playerInput.nextInt();
        } while (players < 0);
        //playerinput.close();
        
        Scanner fileInput = new Scanner(System.in); // Second scanner object needed for file input
        Boolean fileExists = false;
        // input is used for file object creation to check if file exists, if not it will loop
        do {
            System.out.println("Enter a valid location of pack to load:");
            packFile = fileInput.nextLine();
            File f = new File(packFile);
            if (f.exists()) {
                fileExists = true;
            }
        } while (fileExists == false);
        fileInput.close();
        
        // main loop for creating threads, make into List of threads?
        Thread[] threads = new Thread[players];
        for (int i = 0; i < players; i++) {
            threads[i] = new Thread();
            System.out.println("Player: " + threads[i].getName());
        }

        Integer numberOfCards = 8 * players;
        Random rand = new Random();
        try {
            Integer valueRange = 15; // Card Value range from 1 to 15
            FileWriter writeFile = new FileWriter(packFile);
            for (Integer i = 0; i < numberOfCards; i++) {
                Card newCard = new Card(rand.nextInt(valueRange) + 1);
                writeFile.write(newCard.getValue().toString() + "\n"); 
                // System.out.println(newCard.getValue());
            }
            writeFile.close();
            System.out.println("Pack is set.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
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

/*
        for (int i = 0; i < players; i++) {
            new Thread(Integer.toString(i)) {
                public void run() {
                    System.out.println("Player " + this.getName());
                }
            }.start();
        } */
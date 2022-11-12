import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Collections;

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
        Boolean fileIsValid = false;
        // input is used for file object creation to check if file exists, if not it will loop
        do {
            System.out.println("Enter a location of valid pack to load:");
            packFile = fileInput.nextLine();
            File f = new File(packFile);
            if (f.exists()) {
                // validity check
                ArrayList<String> denoms = new ArrayList<String>();
                ArrayList<Integer> counts = new ArrayList<Integer>();
                try {
                    Scanner fileReader = new Scanner(f);
                    while(fileReader.hasNextLine()) {
                        String nl = fileReader.nextLine();
                        if(denoms.contains(nl)) {
                            Integer i = denoms.indexOf(nl);
                            counts.set(i,counts.get(i)+1);
                        } else {
                            denoms.add(nl);
                            counts.add(1);
                        }
                    }
                    for(Integer c : counts) {
                        if(c>=4) {
                            fileIsValid = true;
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File not found.");
                    e.printStackTrace();
                }
            }
        } while (fileIsValid == false);

        try {
            // read pack
            File f = new File(packFile);
            Scanner fileReader = new Scanner(f);
            ArrayList<String> denoms = new ArrayList<String>();
            while(fileReader.hasNextLine()) {
                denoms.add(fileReader.nextLine());
            }
            // shuffle pack
            Random r = new Random();
            Collections.shuffle(denoms, r);
            // ***
            FileWriter writeFile = new FileWriter(packFile);
            // dealing player hands
            Card[][] playerHands = new Card[players][4];
            for(Integer i=0;i<4;i++) {
                for(Integer j=0;j<players;j++) {
                    Integer value = Integer.parseInt(denoms.get(i*players + j));
                    playerHands[j][i] = new Card(value);
                    writeFile.write(value.toString() + "\n"); 
                }
            }
            // dealing decks
            Card[][] decks = new Card[players][4];
            for(Integer i=4*players;i<8*players;i++) {
                for(Integer j=0;j<players;j++) {
                    Integer value = Integer.parseInt(denoms.get(i*players + j));
                    decks[j][i] = new Card(value);
                    writeFile.write(value.toString() + "\n"); 
                }
            }
            // ***
            fileReader.close();
            writeFile.close();
            System.out.println("Pack is set.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // main loop for creating threads, make into List of threads?
        Thread[] threads = new Thread[players];
        for (int i = 0; i < players; i++) {
            threads[i] = new Thread();
            System.out.println("Player: " + threads[i].getName());
        }
        


        // close remaining scanners ***
        playerInput.close();
        fileInput.close();
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
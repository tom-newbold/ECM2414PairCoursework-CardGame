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
     * *DONE* player threads created
     * *DONE* new card object created and written to pack file
     * *DONE* cards are distributed to make list of hands and decks for each player
     *      NOTE: Order is 1 card to each player in a round robin until 4 cards, then decks 
     * output file is made for players and the decks EACH
     * *DONE* wincondition checked upon each player, if no win is made
     * *DONE* atomic action of drawing, checking win condition and discarding for player threads
     * *DONE* atomic action of drawing from top of deck and add from bottom of deck
     * *DONE* wincondition is met and player that has one notifies other players so game is stopped
     * TODO : Synchronise file writing
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
        Scanner fileReader = new Scanner(System.in);
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
                    fileReader = new Scanner(f);
                    while(fileReader.hasNextLine()) {
                        String nl = fileReader.nextLine();
                        if(denoms.contains(nl)) {
                            Integer i = denoms.indexOf(nl.replace("\n",""));
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

        Card[][] playerHands = new Card[players][4];
        Card[][] deckCards = new Card[players][4];
        Deck[] decks = new Deck[players];
        try {
            FileWriter writeFile = new FileWriter("output.txt");
            FileWriter[] playerOutputs = new FileWriter[players];
            for(Integer i=0; i<players; i++) {
                playerOutputs[i] = new FileWriter(String.format("player%d_output.txt", i+1));
            }
            // read pack
            File f = new File(packFile);
            fileReader = new Scanner(f);
            ArrayList<String> denoms = new ArrayList<String>();
            while(fileReader.hasNextLine()) {
                denoms.add(fileReader.nextLine().replace("\n",""));
            }
            // shuffle pack
            Random r = new Random();
            Collections.shuffle(denoms, r);
            // dealing player hands
            for(Integer i=0;i<4;i++) {
                for(Integer j=0;j<players;j++) {
                    Integer value = Integer.parseInt(denoms.get(i*players + j));
                    playerHands[j][i] = new Card(value);
                }
            }
            // dealing decks
            for(Integer i=4;i<8;i++) {
                for(Integer j=0;j<players;j++) {
                    Integer value = Integer.parseInt(denoms.get(i*players + j));
                    deckCards[j][i-4] = new Card(value);
                }
            }
            decks = new Deck[players];
            for(Integer i=0;i<players;i++) {
                decks[i] = new Deck(deckCards[i]);
            }
            // ***
            fileReader.close();
            //System.out.println("Pack is set.");
        

            // main loop for creating threads, make into List of threads?
            PlayerThread[] playerThreads = new PlayerThread[players];
            for (Integer p = 1; p < players+1; p++) {
                Card[] pHand = playerHands[p-1];
                // announce player hands
                writeFile.write(String.format("Player %d inital hand %d %d %d %d\n",
                    p,pHand[0].getValue(),pHand[1].getValue(),pHand[2].getValue(),pHand[3].getValue()));
                playerThreads[p-1] = new PlayerThread(Thread.currentThread(), writeFile, playerOutputs[p-1], p, pHand, decks[p-1], decks[p%players]);
                playerThreads[p-1].setName(Integer.toString(p));
            }

            Integer winPlayer = 0;
            for(PlayerThread pt : playerThreads) {
                if(pt.getPlayer().winCondition()) {
                    // announce pre-game win condition
                    winPlayer = Integer.parseInt(pt.getName());
                    writeFile.write(String.format("Player %d wins\n",(winPlayer+1)));
                    playerOutputs[winPlayer].write(String.format("Player %d wins\n",(winPlayer+1)));
                    break;
                }
            }

            if(winPlayer==0) {
                // announce decks
                for(Integer d=0; d<players;d++) {
                    Card[] deck = decks[d].getDeck();
                    writeFile.write(String.format("Deck%d contents: %d %d %d %d\n",
                        (d+1),deck[0].getValue(),deck[1].getValue(),deck[2].getValue(),deck[3].getValue()));
                }
                for(PlayerThread pt : playerThreads) {
                    pt.start();
                }
                // pausing main thread
                synchronized(Thread.currentThread()) {
                    try {
                        Thread.currentThread().wait();
                    } catch (InterruptedException e) {}
                    // interrupt all threads
                    for(PlayerThread pt : playerThreads) {
                        pt.interrupt();
                        for(FileWriter fw : new FileWriter[]{writeFile, playerOutputs[pt.getPlayer().playerID-1]}) {
                            synchronized (fw) {
                                fw.write(String.format("Player %d terminated\n",pt.getPlayer().playerID));
                            }
                        }
                    }
                    // ***
                    for(PlayerThread pt : playerThreads) {
                        if(pt.winFlag) {
                            winPlayer = Integer.parseInt(pt.getName());
                            // announce winner and hand
                            for(FileWriter fw : new FileWriter[]{writeFile, playerOutputs[winPlayer-1]}) {
                                synchronized (fw) {
                                    fw.write(String.format("Player %d wins\n",winPlayer));
                                    Card[] pHand = pt.getPlayer().getHand();
                                    fw.write(String.format("Player %d hand %d %d %d %d\n",
                                        winPlayer,pHand[0].getValue(),pHand[1].getValue(),pHand[2].getValue(),pHand[3].getValue()));
                                }
                            }
                            break;
                        }
                    }
                }
            }
            // writes deck output
            FileWriter[] deckOutputs = new FileWriter[players];
            for(Integer d=0; d<players; d++) {
                deckOutputs[d] = new FileWriter(String.format("deck%d_output.txt", d+1));
                Card[] deck = decks[d].getDeck();
                String deckContents = String.format("Deck%d contents:",d+1);
                for(Integer c=0;c<deck.length;c++) {
                    deckContents = deckContents + String.format(" %d",deck[c].getValue());
                }
                deckOutputs[d].write(deckContents+"\n");
            }
            // closing output writers
            for(Integer i=0; i<players; i++) {
                playerOutputs[i].close();
                deckOutputs[i].close();
            }
            writeFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // close remaining scanners ***
        playerInput.close();
        fileInput.close();
        fileReader.close();
    }
}

package CardGame;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Collections;

public class CardGame {
    public static Integer players = 0;
    public static String packFile;
    public volatile static Integer winPlayer = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Will loop until input is an integer and is postive
        System.out.println("Please enter a positive number of players:");
        while (players <= 0) {
            try { players = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) { System.out.println("Enter a valid number of players:"); }
        }
        System.out.print(CardGame.players);
        
        Scanner fileReader = new Scanner(System.in);
        Boolean fileIsValid = false;
        // input is used for file object creation to check if file exists, if not it will loop
        while (fileIsValid == false) {
            System.out.println("Enter a location of valid pack to load:");
            packFile = input.nextLine();
            System.out.print(packFile);
            File f = new File(packFile);
            System.out.print(f.exists());
            if (f.exists()) {
                // validity check
                ArrayList<String> denoms = new ArrayList<String>();
                ArrayList<Integer> counts = new ArrayList<Integer>();
                try {
                    fileReader = new Scanner(f);
                    while(fileReader.hasNextLine()) {
                        String nl = fileReader.nextLine();
                        if(Integer.parseInt(nl)<0) { break; }
                        if(denoms.contains(nl)) {
                            Integer i = denoms.indexOf(nl.replace("\n",""));
                            counts.set(i,counts.get(i)+1);
                        } else {
                            denoms.add(nl);
                            counts.add(1);
                        }
                    }
                    for(Integer c : counts) {
                        if(c>=CardGame.players) {
                            fileIsValid = true;
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File not found.");
                    e.printStackTrace();
                }
            }
        }

        Card[][] playerHands = new Card[players][4];
        Card[][] deckCards = new Card[players][4];
        Deck[] decks = new Deck[players];
        try {
            FileWriter[] playerOutputs = new FileWriter[players];
            for(Integer i=0; i<players; i++) {
                playerOutputs[i] = new FileWriter(String.format("./player%d_output.txt", i+1));
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
        

            // loop for creating threads
            PlayerThread[] playerThreads = new PlayerThread[players];
            for (Integer p = 1; p < players+1; p++) {
                Card[] pHand = playerHands[p-1];
                // announce player hands
                playerOutputs[p-1].write(String.format("Player %d inital hand %d %d %d %d\n",
                    p,pHand[0].getValue(),pHand[1].getValue(),pHand[2].getValue(),pHand[3].getValue()));
                playerThreads[p-1] = new PlayerThread(Thread.currentThread(), playerOutputs[p-1], p, pHand, decks[p-1], decks[p%players]);
            }

            //CardGame.winPlayer = 0;
            for(PlayerThread pt : playerThreads) {
                if(pt.getPlayer().winCondition()) {
                    // announce pre-game win condition
                    CardGame.winPlayer = Integer.parseInt(pt.getName());
                    playerOutputs[CardGame.winPlayer-1].write(String.format("Player %d wins\n",CardGame.winPlayer));
                    System.out.println(String.format("Player %d wins\n",CardGame.winPlayer));
                    break;
                }
            }

            if(CardGame.winPlayer==0) {
                for(PlayerThread pt : playerThreads) {
                    pt.start();
                }
                // pausing main thread
                synchronized(Thread.currentThread()) {
                    try {
                        Thread.currentThread().wait();
                    } catch (InterruptedException e) {}
                }
                // interrupt all threads
                for(PlayerThread pt : playerThreads) {
                    pt.interrupt();
                }
            }
            // writes deck output
            FileWriter[] deckOutputs = new FileWriter[players];
            for(Integer d=0; d<players; d++) {
                deckOutputs[d] = new FileWriter(String.format("./deck%d_output.txt", d+1));
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
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // close remaining scanners ***
        fileReader.close();
        input.close();
        fileReader.close();
    }
}

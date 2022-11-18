package CardGame;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerThread extends Thread {
    private Thread MAIN_THREAD;
    private FileWriter wFile_g;
    private FileWriter wFile;
    private Player player;
    private Deck drawDeck;
    private Deck discardDeck;
    public PlayerThread(Thread MAIN_THREAD, FileWriter fileWriter_global, FileWriter fileWriter_individual, Integer pId, Card[] playerHand, Deck draw, Deck discard) {
        this.MAIN_THREAD = MAIN_THREAD;
        this.wFile_g = fileWriter_global;
        this.wFile = fileWriter_individual;
        this.player = new Player(fileWriter_global,fileWriter_individual,pId,playerHand);
        this.drawDeck = draw;
        this.discardDeck = discard;
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                this.player.atomicTurn(drawDeck,discardDeck);
                if(this.player.winCondition()) {
                    if(CardGame.winPlayer==0) {
                        CardGame.winPlayer = Integer.parseInt(Thread.currentThread().getName());
                    
                        // notify main thread
                        synchronized(this.MAIN_THREAD) {
                            this.MAIN_THREAD.notify();
                        }
                        for(FileWriter fw : new FileWriter[]{this.wFile_g, this.wFile}) {
                            synchronized (fw) {
                                try {
                                    fw.write(String.format("Player %s wins\n", Thread.currentThread().getName()));
                                    fw.write(String.format("Player %s exits\n", Thread.currentThread().getName()));
                                    Card[] pHand = this.getPlayer().getHand();
                                    fw.write(String.format("Player %d final hand %d %d %d %d\n",
                                        CardGame.winPlayer,pHand[0].getValue(),pHand[1].getValue(),pHand[2].getValue(),pHand[3].getValue()));
                                } catch (IOException e) {}
                            }
                        }
                        System.out.println(String.format("Player %d wins",CardGame.winPlayer));
                    }
                    Thread.currentThread().interrupt();
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Player getPlayer() { return player; }
}
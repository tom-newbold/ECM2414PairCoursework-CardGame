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
    public Boolean winFlag = false;
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
                    this.winFlag = true;
                    // notify main thread
                    synchronized(this.MAIN_THREAD) {
                        this.MAIN_THREAD.notify();
                    }
                    for(FileWriter fw : new FileWriter[]{this.wFile_g, this.wFile}) {
                        synchronized (fw) {
                            try {
                                fw.write(String.format("Player %s exits\n", Thread.currentThread().getName()));
                            } catch (IOException e) {}
                        }
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Player getPlayer() { return player; } // TODO: modify to getPlayerId() to prevent stacked calls
}
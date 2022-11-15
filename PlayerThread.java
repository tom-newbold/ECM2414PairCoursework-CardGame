import java.io.FileWriter;
import java.io.IOException;

public class PlayerThread extends Thread {
    private Thread MAIN_THREAD;
    private FileWriter wFile;
    private Player player;
    private Deck drawDeck;
    private Deck discardDeck;
    public Boolean winFlag = false;
    public PlayerThread(Thread MAIN_THREAD, FileWriter fileWriter, Integer pId, Card[] playerHand, Deck draw, Deck discard) {
        this.MAIN_THREAD = MAIN_THREAD;
        this.wFile = fileWriter;
        this.player = new Player(fileWriter,pId,playerHand);
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
                    synchronized (this.wFile) {
                        try {
                            this.wFile.write(String.format("player %s exits\n", Thread.currentThread().getName()));
                        } catch (IOException e) {}
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Player getPlayer() { return player; }
}
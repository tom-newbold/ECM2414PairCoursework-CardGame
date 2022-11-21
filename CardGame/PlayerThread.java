package CardGame;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerThread extends Thread {
    private Thread MAIN_THREAD;
    private FileWriter fw;
    private Player player;
    private Deck drawDeck;
    private Deck discardDeck;
    public PlayerThread(Thread MAIN_THREAD, FileWriter fileWriter, Integer pId, Card[] playerHand, Deck draw, Deck discard) {
        this.MAIN_THREAD = MAIN_THREAD;
        this.fw = fileWriter;
        this.player = new Player(fileWriter,pId,playerHand);
        this.drawDeck = draw;
        this.discardDeck = discard;
        this.setName(Integer.toString(pId));
    }

    /**
     * Thread mainloop - runs player's atomic turn, checks for win condition;
     * If player has won, set CardGame.winPlayer to PlayerID and interrupt player thread;
     * Also writes win anouncements / final hands to the player's output file
     */
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
                        synchronized (this.fw) {
                            try {
                                this.fw.write(String.format("Player %s wins\n", Thread.currentThread().getName()));
                                this.fw.write(String.format("Player %s exits\n", Thread.currentThread().getName()));
                                Card[] pHand = this.getPlayer().getHand();
                                this.fw.write(String.format("Player %d final hand %d %d %d %d\n",
                                    CardGame.winPlayer,pHand[0].getValue(),pHand[1].getValue(),pHand[2].getValue(),pHand[3].getValue()));
                            } catch (IOException e) {}
                        }
                        System.out.println(String.format("Player %d wins",CardGame.winPlayer));
                    }
                    Thread.currentThread().interrupt();
                }
            } catch (InterruptedException e) {
                break;
            }
        }
        Integer pId = Integer.parseInt(Thread.currentThread().getName());
        if(CardGame.winPlayer != pId)
        synchronized (this.fw) {
            try {
                this.fw.write(String.format("Player %d informs Player %d that Player %d has won\n", CardGame.winPlayer, pId, CardGame.winPlayer));
                this.fw.write(String.format("Player %d exits\n", pId));
                Card[] pHand = this.getPlayer().getHand();
                this.fw.write(String.format("Player %d final hand %d %d %d %d\n",
                    pId,pHand[0].getValue(),pHand[1].getValue(),pHand[2].getValue(),pHand[3].getValue()));
            } catch (IOException e) {}
        }
    }

    /**
     * @return Player associated with thread
     */
    public Player getPlayer() { return player; }
}